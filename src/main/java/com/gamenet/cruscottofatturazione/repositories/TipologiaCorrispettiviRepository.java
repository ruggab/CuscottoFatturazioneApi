package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.TipologiaCorrispettivi;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface TipologiaCorrispettiviRepository extends CrudRepository<TipologiaCorrispettivi, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [dbo].[tipologia_corrispettivi]",nativeQuery=true)
	public List<TipologiaCorrispettivi> getTipologiaCorrispettivi();
	

}