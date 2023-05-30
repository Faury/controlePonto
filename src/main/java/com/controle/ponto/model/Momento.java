package com.controle.ponto.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * O momento da batida
 */
@ApiModel(description = "O momento da batida")
public class Momento   {
  @JsonProperty("dataHora")
  private String dataHora;

  public Momento dataHora(String dataHora) {
    this.dataHora = dataHora;
    return this;
  }

  /**
   * Data e hora da batida
   * @return dataHora
  */
  @ApiModelProperty(example = "2018-08-22T08:00:00", value = "Data e hora da batida")


  public String getDataHora() {
    return dataHora;
  }

  public void setDataHora(String dataHora) {
    this.dataHora = dataHora;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Momento momento = (Momento) o;
    return Objects.equals(this.dataHora, momento.dataHora);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataHora);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Momento {\n");
    
    sb.append("    dataHora: ").append(toIndentedString(dataHora)).append("\n");
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

