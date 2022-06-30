package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.DettaglioFattura;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface DettaglioFatturaRepository extends CrudRepository<DettaglioFattura, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [dbo].[dettaglio_fattura]",nativeQuery=true)
	public List<DettaglioFattura> getDettagliFatture();
	//@Query(value="SELECT * FROM [dbo].[dettaglio_fattura] where id_fattura=:fatturaId",nativeQuery=true)
	public  List<DettaglioFattura> findByIdFattura(@Param("fatturaId")Integer fatturaId);
	
	

}