package com.controle.ponto.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controle.ponto.model.Mensagem;
import com.controle.ponto.model.Momento;
import com.controle.ponto.model.Registro;
import com.controle.ponto.model.Relatorio;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("${openapi.controleDePonto.base-path:}")
@Validated
@Api(value = "v1", description = "the v1 API")
public class V1ApiController {

    private final V1ApiDelegate delegate;

    public V1ApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) V1ApiDelegate delegate) {
        this.delegate = delegate;
    }

    public V1ApiDelegate getDelegate() {
        return delegate;
    }

    /**
     * GET /v1/folhas-de-ponto/{mes} : Relatório mensal
     * Geração de relatório mensal de usuário.
     *
     * @param mes  (required)
     * @return Relatório mensal (status code 200)
     *         or Relatório não encontrado (status code 404)
     */
    @ApiOperation(value = "Relatório mensal", nickname = "geraRelatorioMensal", notes = "Geração de relatório mensal de usuário.", response = Relatorio.class, tags={ "Folhas de Ponto", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Relatório mensal", response = Relatorio.class),
        @ApiResponse(code = 404, message = "Relatório não encontrado") })
    @GetMapping(
        value = "/v1/folhas-de-ponto/{mes}",
        produces = { "application/json" }
    )
    public ResponseEntity<Relatorio> geraRelatorioMensal(@ApiParam(value = "",required=true) @PathVariable("mes") String mes) {
 		return new ResponseEntity<>(getDelegate().geraRelatorioMensal(mes), HttpStatus.OK);
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
     */
    @ApiOperation(value = "Bater ponto", nickname = "insereBatida", notes = "Registrar um horário da jornada diária de trabalho", response = Registro.class, tags={ "Batidas", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created ", response = Registro.class),
        @ApiResponse(code = 400, message = "Bad Request ", response = Mensagem.class),
        @ApiResponse(code = 403, message = "Forbidden ", response = Mensagem.class),
        @ApiResponse(code = 409, message = "Conflict ", response = Mensagem.class) })
    @PostMapping(
        value = "/v1/batidas",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    public ResponseEntity<Registro> insereBatida(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) Momento momento) {
        return new ResponseEntity<>(getDelegate().insereBatida(momento), HttpStatus.OK);
    }
}
