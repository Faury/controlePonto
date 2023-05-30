package com.controle.ponto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Registro
 */
public class Registro   {
  @JsonProperty("dia")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
  private LocalDate dia;

  @JsonProperty("horarios")
  @Valid
  private List<String> horarios = null;

  public Registro dia(LocalDate dia) {
    this.dia = dia;
    return this;
  }

  /**
   * Get dia
   * @return dia
  */
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getDia() {
    return dia;
  }

  public void setDia(LocalDate dia) {
    this.dia = dia;
  }

  public Registro horarios(List<String> horarios) {
    this.horarios = horarios;
    return this;
  }

  public Registro addHorariosItem(String horariosItem) {
    if (this.horarios == null) {
      this.horarios = new ArrayList<>();
    }
    this.horarios.add(horariosItem);
    return this;
  }

  /**
   * Get horarios
   * @return horarios
  */
  @ApiModelProperty(example = "[\"08:00:00\",\"12:00:00\",\"13:00:00\",\"18:00:00\"]", value = "")


  public List<String> getHorarios() {
    return horarios;
  }

  public void setHorarios(List<String> horarios) {
    this.horarios = horarios;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Registro registro = (Registro) o;
    return Objects.equals(this.dia, registro.dia) &&
        Objects.equals(this.horarios, registro.horarios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dia, horarios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Registro {\n");
    
    sb.append("    dia: ").append(toIndentedString(dia)).append("\n");
    sb.append("    horarios: ").append(toIndentedString(horarios)).append("\n");
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

