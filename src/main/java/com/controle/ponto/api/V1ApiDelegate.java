package com.controle.ponto.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controle.ponto.model.Momento;
import com.controle.ponto.model.Registro;
import com.controle.ponto.model.Relatorio;

/**
 * A delegate to be called by the {@link V1ApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface V1ApiDelegate {

    /**
     * GET /v1/folhas-de-ponto/{mes} : Relatório mensal
     * Geração de relatório mensal de usuário.
     *
     * @param mes  (required)
     * @return Relatório mensal (status code 200)
     *         or Relatório não encontrado (status code 404)
     * @see V1Api#geraRelatorioMensal
     */
    default ResponseEntity<Relatorio> geraRelatorioMensal(String mes) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * POST /v1/batidas : Bater ponto
     * Registrar um horário da jornada diária de trabalho
     *
     * @param momento  (optional)
     * @return Created  (status code 201)
     *         or Bad Request  (status code 400)
     *         or Forbidden  (status code 403)
     *         or Conflict  (status code 409)
     * @see V1Api#insereBatida
     */
    default ResponseEntity<Registro> insereBatida(Momento momento) {
    	return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
