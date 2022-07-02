package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Fattura;
import com.gamenet.cruscottofatturazione.entities.StatoFatturaLog;

@Repository
public interface StatoFatturaLogRepository extends CrudRepository<StatoFatturaLog, Integer>, JpaSpecificationExecutor<StatoFatturaLog> {
	
	@Query(value="SELECT * FROM [dbo].[stato_fattura_log] WHERE id_fattura=:idFattura order by create_date",nativeQuery=true)
	public List<StatoFatturaLog> getStatofatturaLogByIdFattura(@Param("idFattura") Integer idFattura);
	
}