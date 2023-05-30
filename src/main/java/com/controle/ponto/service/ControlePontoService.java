package com.controle.ponto.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.controle.ponto.api.V1ApiDelegate;
import com.controle.ponto.dao.RegistroPontoRepository;
import com.controle.ponto.entity.RegistroPonto;
import com.controle.ponto.exception.InvalidRegisterException;
import com.controle.ponto.exception.InvalidRequestException;
import com.controle.ponto.exception.NotFoundException;
import com.controle.ponto.exception.TimeAlreadyRegisteredException;
import com.controle.ponto.model.Momento;
import com.controle.ponto.model.Registro;
import com.controle.ponto.model.Relatorio;

@Service
public class ControlePontoService implements V1ApiDelegate {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Autowired
	RegistroPontoRepository registroPontoRepository;
	
	@Override
	public ResponseEntity<Relatorio> geraRelatorioMensal(String mesAno) {

		String[] args = mesAno.split("/");
		Integer mes = Integer.parseInt(args[0]);
		Integer ano = Integer.parseInt(args[1]);
		
		List<RegistroPonto> listRegistroPonto = registroPontoRepository.findAllByMonth(mes, ano);
	
		if (listRegistroPonto == null || listRegistroPonto.isEmpty()) {
			throw NotFoundException.newReportNotFoundException();
		}
		
		long segundosTrabalhoMes = getWorkingTimeInSeconds(mes, ano);
		long segundosTrabalhados = 0l;
		List<Registro> registros = new ArrayList<>();
		for (RegistroPonto registroPonto : listRegistroPonto) {
			Registro registro = new Registro();
			registro.setDia(Instant.ofEpochMilli(registroPonto.getDia().getTime())
				      .atZone(ZoneId.systemDefault())
				      .toLocalDate());
			registroPonto.setHorarios(registroPonto.getHorarios());
			registros.add(registro);
			
			List<String> horarios = registroPonto.getHorarios();
			if (registroPonto.getHorarios().size() >= 2) {
				LocalTime periodo1 = LocalTime.parse(horarios.get(0));
				LocalTime periodo2 = LocalTime.parse(horarios.get(1));
				segundosTrabalhados += periodo1.until(periodo2, ChronoUnit.SECONDS);	
			}
			if (registroPonto.getHorarios().size() == 4) {
				LocalTime periodo1 = LocalTime.parse(horarios.get(2));
				LocalTime periodo2 = LocalTime.parse(horarios.get(3));
				segundosTrabalhados += periodo1.until(periodo2, ChronoUnit.SECONDS);	
			}
		}
		
		long horasDevidas = 0l;
		long horasExcedentes = 0l;
		
		if (segundosTrabalhoMes > segundosTrabalhados) {
			horasDevidas = segundosTrabalhoMes - segundosTrabalhados;
		} else {
			horasExcedentes = segundosTrabalhados - segundosTrabalhoMes;
		}
		
		Relatorio relatorio = new Relatorio();
		
		relatorio.setMes(mesAno);
		relatorio.setRegistros(registros);
		relatorio.setHorasTrabalhadas(Duration.ofSeconds(segundosTrabalhados).toString());
		relatorio.setHorasDevidas(Duration.ofSeconds(horasDevidas).toString());
		relatorio.setHorasExcedentes(Duration.ofSeconds(horasExcedentes).toString());
		
		return new ResponseEntity<>(relatorio, HttpStatus.OK);
	}
	
	private Long getWorkingTimeInSeconds(Integer mes, Integer ano) { 
		LocalDate startDate = LocalDate.of(ano, mes, 1);
		LocalDate endDate = startDate.plusMonths(1);
		Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		long weekDaysBetween = startDate.datesUntil(endDate)
		        .filter(d -> !weekend.contains(d.getDayOfWeek()))
		        .count();
		return weekDaysBetween * 8 * 60 * 60;
	}
	
	@Override
	public ResponseEntity<Registro> insereBatida(Momento momento) {
		LocalDate localDate = null;
		
		if (momento == null || momento.getDataHora() == null) {
			throw InvalidRequestException.newRequiredFieldsException();
		}

		String dia = momento.getDataHora().substring(0, momento.getDataHora().indexOf('T'));
		String hora = momento.getDataHora().substring(momento.getDataHora().indexOf('T'));
		
		try {
			localDate = LocalDate.parse(dia, formatter);
		} catch (RuntimeException e) {
			throw InvalidRequestException.newInvalidDateTimeException();
		}
		
		if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY 
				|| localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
			throw InvalidRegisterException.newWeekendNotValidException();
		}
		
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Optional<RegistroPonto> registroPontoOptional = registroPontoRepository.findById(date);
		RegistroPonto registroPonto = null;
		if(registroPontoOptional.isPresent()) {
			registroPonto = registroPontoOptional.get();
		} else {
			registroPonto = new RegistroPonto(date, new ArrayList<String>());
		}
		
		List<String> horarios = registroPonto.getHorarios();
		
		if (horarios.size() >= 4) {
			throw InvalidRegisterException.newMaximumRegistersPerDayException();
		}
		
		if (horarios.contains(hora)) {
			throw new TimeAlreadyRegisteredException();
		} else {
			horarios.add(hora);
			horarios.sort(Comparator.naturalOrder());
		}
		
		if (horarios.size() == 4) {
			LocalTime periodo2 = LocalTime.parse(horarios.get(1));
			LocalTime periodo3 = LocalTime.parse(horarios.get(2));
			long almoco = periodo2.until(periodo3, ChronoUnit.HOURS);
			if (almoco < 1l) {
				throw InvalidRegisterException.newLunchMissingException();
			}
		}
		
		registroPonto.setHorarios(horarios);
		
		registroPontoRepository.save(registroPonto);
		
		Registro registro = new Registro();
		registro.setDia(localDate);
		registro.setHorarios(horarios);
		
		return new ResponseEntity<>(registro, HttpStatus.CREATED);
	}

}
