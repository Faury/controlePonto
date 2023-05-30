package com.controle.ponto.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.controle.ponto.entity.RegistroPonto;

public interface RegistroPontoRepository extends CrudRepository<RegistroPonto, Date>{

	@Query("SELECT a FROM RegistroPonto a WHERE month(a.dia) = :monthParam AND year(a.dia = :yearParam")
    List<RegistroPonto> findAllByMonth(@Param("month") Integer monthParam, @Param("year") Integer yearParam);
}
