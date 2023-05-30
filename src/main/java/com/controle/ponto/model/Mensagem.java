package com.controle.ponto.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Mensagem
 */
public class Mensagem   {
  @JsonProperty("mensagem")
  private String mensagem;

  public Mensagem mensagem(String mensagem) {
    this.mensagem = mensagem;
    return this;
  }

  /**
   * Get mensagem
   * @return mensagem
  */
  @ApiModelProperty(value = "")


  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Mensagem mensagem = (Mensagem) o;
    return Objects.equals(this.mensagem, mensagem.mensagem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mensagem);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Mensagem {\n");
    
    sb.append("    mensagem: ").append(toIndentedString(mensagem)).append("\n");
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

