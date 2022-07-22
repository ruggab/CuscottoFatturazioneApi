package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Articolo;

@Repository
public interface ArticoloRepository extends CrudRepository<Articolo, Integer>, JpaSpecificationExecutor<Articolo> {
	
	@Query(value="SELECT * FROM [dbo].[articolo]",nativeQuery=true)
	public List<Articolo> getArticoli();
	
	@Query(value="SELECT * FROM [dbo].[articolo] a where a.data_validita >= GETDATE() ",nativeQuery=true)
	public List<Articolo> getActiveArticoli();
	
	@Query(value="SELECT * FROM [dbo].[articolo] a where a.codice_articolo = :codiceArticolo ",nativeQuery=true)
	public Articolo getArticoloByCodice(@Param("codiceArticolo") String codiceArticolo);

	@Query(value="SELECT a.codice_articolo FROM [dbo].[articolo] a where a.codice_articolo in (:codiciArticoli) and a.data_validita < GETDATE()",nativeQuery=true)
	public List<String> findArticoliNonValidi(@Param("codiciArticoli") List<String> codiciArticoli);
	

}