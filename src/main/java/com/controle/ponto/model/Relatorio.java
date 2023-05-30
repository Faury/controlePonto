package com.controle.ponto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Relatório mensal
 */
@ApiModel(description = "Relatório mensal")
public class Relatorio   {
  @JsonProperty("mes")
  private String mes;

  @JsonProperty("horasTrabalhadas")
  private String horasTrabalhadas;

  @JsonProperty("horasExcedentes")
  private String horasExcedentes;

  @JsonProperty("horasDevidas")
  private String horasDevidas;

  @JsonProperty("registros")
  @Valid
  private List<Registro> registros = null;

  public Relatorio mes(String mes) {
    this.mes = mes;
    return this;
  }

  /**
   * Get mes
   * @return mes
  */
  @ApiModelProperty(example = "2018-08", value = "")


  public String getMes() {
    return mes;
  }

  public void setMes(String mes) {
    this.mes = mes;
  }

  public Relatorio horasTrabalhadas(String horasTrabalhadas) {
    this.horasTrabalhadas = horasTrabalhadas;
    return this;
  }

  /**
   * Get horasTrabalhadas
   * @return horasTrabalhadas
  */
  @ApiModelProperty(example = "PT69H35M5S", value = "")


  public String getHorasTrabalhadas() {
    return horasTrabalhadas;
  }

  public void setHorasTrabalhadas(String horasTrabalhadas) {
    this.horasTrabalhadas = horasTrabalhadas;
  }

  public Relatorio horasExcedentes(String horasExcedentes) {
    this.horasExcedentes = horasExcedentes;
    return this;
  }

  /**
   * Get horasExcedentes
   * @return horasExcedentes
  */
  @ApiModelProperty(example = "PT25M5S", value = "")


  public String getHorasExcedentes() {
    return horasExcedentes;
  }

  public void setHorasExcedentes(String horasExcedentes) {
    this.horasExcedentes = horasExcedentes;
  }

  public Relatorio horasDevidas(String horasDevidas) {
    this.horasDevidas = horasDevidas;
    return this;
  }

  /**
   * Get horasDevidas
   * @return horasDevidas
  */
  @ApiModelProperty(example = "PT0S", value = "")


  public String getHorasDevidas() {
    return horasDevidas;
  }

  public void setHorasDevidas(String horasDevidas) {
    this.horasDevidas = horasDevidas;
  }

  public Relatorio registros(List<Registro> registros) {
    this.registros = registros;
    return this;
  }

  public Relatorio addRegistrosItem(Registro registrosItem) {
    if (this.registros == null) {
      this.registros = new ArrayList<>();
    }
    this.registros.add(registrosItem);
    return this;
  }

  /**
   * Get registros
   * @return registros
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<Registro> getRegistros() {
    return registros;
  }

  public void setRegistros(List<Registro> registros) {
    this.registros = registros;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Relatorio relatorio = (Relatorio) o;
    return Objects.equals(this.mes, relatorio.mes) &&
        Objects.equals(this.horasTrabalhadas, relatorio.horasTrabalhadas) &&
        Objects.equals(this.horasExcedentes, relatorio.horasExcedentes) &&
        Objects.equals(this.horasDevidas, relatorio.horasDevidas) &&
        Objects.equals(this.registros, relatorio.registros);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mes, horasTrabalhadas, horasExcedentes, horasDevidas, registros);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Relatorio {\n");
    
    sb.append("    mes: ").append(toIndentedString(mes)).append("\n");
    sb.append("    horasTrabalhadas: ").append(toIndentedString(horasTrabalhadas)).append("\n");
    sb.append("    horasExcedentes: ").append(toIndentedString(horasExcedentes)).append("\n");
    sb.append("    horasDevidas: ").append(toIndentedString(horasDevidas)).append("\n");
    sb.append("    registros: ").append(toIndentedString(registros)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

