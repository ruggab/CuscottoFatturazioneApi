package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Fattura;

@Repository
public interface FatturaRepository extends CrudRepository<Fattura, Integer>, JpaSpecificationExecutor<Fattura> {
	
	@Query(value="SELECT * FROM [dbo].[fattura]",nativeQuery=true)
	public List<Fattura> getFatture();
	
	@Query(value="SELECT * FROM [dbo].[fattura] where societa=:societa",nativeQuery=true)
	public List<Fattura> getFattureBySocieta(@Param("societa") String societa);
	
	@Query(value="SELECT * FROM (SELECT TOP(10) * FROM [dbo].[fattura] where societa=:societa order by last_mod_date desc) as a order by a.last_mod_date desc",nativeQuery=true)
	public List<Fattura> getLast10DayFattureBySocieta(@Param("societa") String societa);
	
	@Query(value="SELECT * FROM (SELECT TOP(10) * FROM [dbo].[fattura] where societa=:societa and stato_fattura=:stato order by last_mod_date desc) as a order by a.last_mod_date desc",nativeQuery=true)
	public List<Fattura> getLast10DayFattureBySocieta(@Param("societa") String societa,@Param("stato") String stato);

	@Query(value="SELECT * FROM [dbo].[fattura] where societa=:societa and stato_fattura=:stato",nativeQuery=true)
	public List<Fattura> getFattureBySocietaAndStato(@Param("societa") String societa,@Param("stato") String stato);

}