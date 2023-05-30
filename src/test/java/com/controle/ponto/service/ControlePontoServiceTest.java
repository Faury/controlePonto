package com.controle.ponto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.controle.ponto.dao.RegistroPontoRepository;
import com.controle.ponto.entity.RegistroPonto;
import com.controle.ponto.exception.InvalidRegisterException;
import com.controle.ponto.exception.InvalidRequestException;
import com.controle.ponto.exception.NotFoundException;
import com.controle.ponto.exception.TimeAlreadyRegisteredException;
import com.controle.ponto.model.Momento;
import com.controle.ponto.model.Registro;
import com.controle.ponto.model.Relatorio;
import com.controle.ponto.util.MessageCodes;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControlePontoServiceTest {

	@Mock
	RegistroPontoRepository registroPontoRepository;
	
	@InjectMocks
	ControlePontoService controlePontoService;
	
	@Test
	public void testGeraRelatorioMensalSuccess() {
		
		List<RegistroPonto> listRegistroPonto = new ArrayList<>();
		Date date = new GregorianCalendar(2023, Calendar.JUNE, 1).getTime();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "13:00:00", "17:00:00")); 

		RegistroPonto registroPonto = new RegistroPonto(date, horarios);

		listRegistroPonto.add(registroPonto);
		
		Mockito
        .when(registroPontoRepository.findAllByMonth(any(), any()))
        .thenReturn(listRegistroPonto);
		
		Relatorio relatorio = controlePontoService.geraRelatorioMensal("2023-06");
		
		assertEquals(relatorio.getHorasTrabalhadas(), "PT8H");
		assertEquals(relatorio.getHorasDevidas(), "PT168H");
		assertEquals(relatorio.getHorasExcedentes(), "PT0S");
		assertEquals(relatorio.getMes(), "2023-06");
		assertEquals(relatorio.getRegistros().size(), listRegistroPonto.size());
	}
	
	@Test
	public void testGeraRelatorioMensalError() {
		
		List<RegistroPonto> listRegistroPonto = new ArrayList<>();
		
		Mockito
        .when(registroPontoRepository.findAllByMonth(any(), any()))
        .thenReturn(listRegistroPonto);
		
		try {
			Relatorio relatorio = controlePontoService.geraRelatorioMensal("2023-06");
			fail("No Exception");
		} catch (NotFoundException e) {
			assertEquals(e.getMessage(), MessageCodes.REPORT_NOT_FOUND);
		}
	}
	
	@Test
	public void testGeraRelatorioMensalRequestError() {
		
		try {
			Relatorio relatorio = controlePontoService.geraRelatorioMensal("ABCDADE");
			fail("No Exception");
		} catch (NotFoundException e) {
			assertEquals(e.getMessage(), MessageCodes.REPORT_NOT_FOUND);
		}
	}

	@Test
	public void testInsereBatidaSucesso() {
		Momento momento = new Momento();
		momento.setDataHora("2023-06-01T17:00:00");
		
		Date date = new GregorianCalendar(2023, Calendar.JUNE, 1).getTime();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "13:00:00"));
		RegistroPonto registroPonto = new RegistroPonto(date, horarios);
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.of(registroPonto));
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(registroPonto);
		
		Registro registro = controlePontoService.insereBatida(momento);
		
		assertEquals(registro.getDia(), LocalDate.of( 2023, 6, 1));
		assertEquals(registro.getHorarios().size(), 4);
	}
	
	@Test
	public void testInsereBatidaConflict() {
		Momento momento = new Momento();
		momento.setDataHora("2023-06-01T13:00:00");
		
		Date date = new GregorianCalendar(2023, Calendar.JUNE, 1).getTime();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "13:00:00"));
		RegistroPonto registroPonto = new RegistroPonto(date, horarios);
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.of(registroPonto));
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(registroPonto);
		
		try {
			Registro registro = controlePontoService.insereBatida(momento);
			fail("No Exception");
		} catch (TimeAlreadyRegisteredException e) {
			assertEquals(e.getMessage(), MessageCodes.TIME_ALREADY_REGISTERED);
		}
	}
	
	@Test
	public void testInsereBatida4horariosMaxError() {
		Momento momento = new Momento();
		momento.setDataHora("2023-06-01T20:00:00");
		
		Date date = new GregorianCalendar(2023, Calendar.JUNE, 1).getTime();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "13:00:00", "17:00:00"));
		RegistroPonto registroPonto = new RegistroPonto(date, horarios);
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.of(registroPonto));
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(registroPonto);
		
		try {
			Registro registro = controlePontoService.insereBatida(momento);
			fail("No Exception");
		} catch (InvalidRegisterException e) {
			assertEquals(e.getMessage(), MessageCodes.MAXIMUM_REGISTERS_DAY);
		}
	}
	
	@Test
	public void testInsereBatidaAlmocoError() {
		Momento momento = new Momento();
		momento.setDataHora("2023-06-01T20:00:00");
		
		Date date = new GregorianCalendar(2023, Calendar.JUNE, 1).getTime();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "12:30:00"));
		RegistroPonto registroPonto = new RegistroPonto(date, horarios);
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.of(registroPonto));
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(registroPonto);
		
		try {
			Registro registro = controlePontoService.insereBatida(momento);
			fail("No Exception");
		} catch (InvalidRegisterException e) {
			assertEquals(e.getMessage(), MessageCodes.LUNCH_TIME_MISSING);
		}
	}

	@Test
	public void testInsereBatidaFimSemanaError() {
		Momento momento = new Momento();
		momento.setDataHora("2023-06-03T08:00:00");
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.empty());
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(null);
		
		try {
			Registro registro = controlePontoService.insereBatida(momento);
			fail("No Exception");
		} catch (InvalidRegisterException e) {
			assertEquals(e.getMessage(), MessageCodes.WEEKEND_INVALID);
		}
	}
	
	@Test
	public void testInsereBatidaBadRequestError() {
		Momento momento = new Momento();
		momento.setDataHora("ADADADATADADAD");
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.empty());
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(null);
		
		try {
			Registro registro = controlePontoService.insereBatida(momento);
			fail("No Exception");
		} catch (InvalidRequestException e) {
			assertEquals(e.getMessage(), MessageCodes.INVALID_DATETIME);
		}
	}
	
	@Test
	public void testInsereBatidaMissingParameterError() {
		Momento momento = null;
		
		Mockito
        .when(registroPontoRepository.findById(any()))
        .thenReturn(Optional.empty());
		
		Mockito
        .when(registroPontoRepository.save(any()))
        .thenReturn(null);
		
		try {
			Registro registro = controlePontoService.insereBatida(momento);
			fail("No Exception");
		} catch (InvalidRequestException e) {
			assertEquals(e.getMessage(), MessageCodes.REQUIRED_FIELD);
		}
	}
}
