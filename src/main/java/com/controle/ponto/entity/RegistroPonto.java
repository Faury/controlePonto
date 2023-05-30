package com.controle.ponto.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class RegistroPonto {

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	private Date dia;
	
	@Column
	private List<String> horarios;
	
	public RegistroPonto() {
		super();
	}

	public RegistroPonto(Date dia, List<String> horarios) {
		super();
		this.dia = dia;
		this.horarios = horarios;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public List<String> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<String> horarios) {
		this.horarios = horarios;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RegistroPonto registroPonto = (RegistroPonto) o;
        return dia.equals(registroPonto.dia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dia);
    }
}
