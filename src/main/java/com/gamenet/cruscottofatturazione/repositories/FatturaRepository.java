package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface FatturaRepository extends CrudRepository<Fattura, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [dbo].[fattura]",nativeQuery=true)
	public List<Fattura> getFatture();
	
	@Query(value="SELECT * FROM [dbo].[fattura] where societa=:societa",nativeQuery=true)
	public List<Fattura> getFattureBySocieta(@Param("societa") String societa);
	
	@Query(value="SELECT * FROM (SELECT TOP(10) * FROM [dbo].[fattura] where societa=:societa order by data_fattura desc) as a order by a.data_fattura asc",nativeQuery=true)
	public List<Fattura> getLast10DayFattureBySocieta(@Param("societa") String societa);

}