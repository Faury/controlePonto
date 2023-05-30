package com.controle.ponto.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.controle.ponto.exception.InvalidRegisterException;
import com.controle.ponto.exception.InvalidRequestException;
import com.controle.ponto.exception.NotFoundException;
import com.controle.ponto.exception.TimeAlreadyRegisteredException;
import com.controle.ponto.model.Registro;
import com.controle.ponto.model.Relatorio;

@RunWith(SpringRunner.class)
@WebMvcTest(V1ApiController.class)
class V1ApiControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private V1ApiDelegate delegate; 
	
	@Test
	void testInsereBatidaSuccess() throws Exception {
		
		Registro registro = new Registro();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "13:00:00", "17:00:00"));
		registro.setDia(LocalDate.of( 2023, 6, 1));
		registro.setHorarios(horarios);
		
		Mockito
        .when(delegate.insereBatida(any()))
        .thenReturn(registro);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isOk());
	}
	
	@Test
	void testGeraRelatorioSuccess() throws Exception {
		
		Registro registro = new Registro();
		List<String> horarios = new ArrayList<>(Arrays.asList("08:00:00", "12:00:00", "13:00:00", "17:00:00"));
		registro.setDia(LocalDate.of( 2023, 6, 1));
		registro.setHorarios(horarios);
		List<Registro> listaRegistros = new ArrayList<>();
		listaRegistros.add(registro);
		
		Relatorio relatorio = new Relatorio();
		
		relatorio.setHorasTrabalhadas("PT8H");
		relatorio.setHorasDevidas("PT168H");
		relatorio.setHorasExcedentes("PT0S");
		relatorio.setMes("2023-06");
		relatorio.setRegistros(listaRegistros);
		
		Mockito
        .when(delegate.geraRelatorioMensal(any()))
        .thenReturn(relatorio);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/v1/folhas-de-ponto/2023-06")
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isOk());
	}
	
	@Test
	void testGeraRelatorioNotFound() throws Exception {
		doThrow(NotFoundException.newReportNotFoundException())
		.when(delegate).geraRelatorioMensal(any());
		
		mvc.perform(MockMvcRequestBuilders
				.get("/v1/folhas-de-ponto/2023-06")
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isNotFound());
	}
	
	@Test
	void testInsereBatidaInvalidDateTime() throws Exception {
		
		doThrow(InvalidRequestException.newInvalidDateTimeException())
		.when(delegate).insereBatida(any());
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isBadRequest());
	}
	
	@Test
	void testInsereBatidaRequiredFields() throws Exception {
		
		doThrow(InvalidRequestException.newRequiredFieldsException())
		.when(delegate).insereBatida(any());
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isBadRequest());
	}
	
	@Test
	void testInsereBatidaWeekendNotValid() throws Exception {
		
		doThrow(InvalidRegisterException.newWeekendNotValidException())
		.when(delegate).insereBatida(any());
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isForbidden());
	}
	
	@Test
	void testInsereBatidaMaximumOf4() throws Exception {
		
		doThrow(InvalidRegisterException.newMaximumRegistersPerDayException())
		.when(delegate).insereBatida(any());
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isForbidden());
	}
	
	@Test
	void testInsereBatidaLunchMissing() throws Exception {
		
		doThrow(InvalidRegisterException.newLunchMissingException())
		.when(delegate).insereBatida(any());
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isForbidden());
	}
	
	@Test
	void testInsereBatidaConflict() throws Exception {
		
		doThrow(new TimeAlreadyRegisteredException())
		.when(delegate).insereBatida(any());
		
		mvc.perform(MockMvcRequestBuilders
				.post("/v1/batidas")
				.content("{\"dataHora\": \"2023-06-02T12:00:00\"}")
	      		.contentType(MediaType.APPLICATION_JSON)
	      		.accept(MediaType.APPLICATION_JSON))
		      	.andExpect(status().isConflict());
	}

}
