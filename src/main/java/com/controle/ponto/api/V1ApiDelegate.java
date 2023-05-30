package com.controle.ponto.api;

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
     * @return Relatório mensal
     * @see V1Api#geraRelatorioMensal
     */
    Relatorio geraRelatorioMensal(String mes);

    /**
     * POST /v1/batidas : Bater ponto
     * Registrar um horário da jornada diária de trabalho
     *
     * @param momento  (optional)
     * @return Registro
     * @see V1Api#insereBatida
     */
    Registro insereBatida(Momento momento);

}
