package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;

@Repository
public interface TipologiaCorrispettiviRepository extends CrudRepository<TipologiaCorrispettivi, Integer>, JpaSpecificationExecutor<TipologiaCorrispettivi> {
	
	@Query(value="SELECT * FROM [dbo].[tipologia_corrispettivi]",nativeQuery=true)
	public List<TipologiaCorrispettivi> getTipologiaCorrispettivi();

	@Query(value="SELECT * FROM [dbo].[tipologia_corrispettivi] t where t.data_validita >= GETDATE()",nativeQuery=true)
	public List<TipologiaCorrispettivi> getActiveTipologiaCorrispettivi();
	
	@Query(value="SELECT * FROM [dbo].[tipologia_corrispettivi] t where t.codice_corrispettivo = :codiceCorrispettivo ",nativeQuery=true)
	public TipologiaCorrispettivi getTipologiaByCodice(@Param("codiceCorrispettivo") String codiceCorrispettivo);

}