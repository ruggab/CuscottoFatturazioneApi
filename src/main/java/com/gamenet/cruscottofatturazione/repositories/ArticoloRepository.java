package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Articolo;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface ArticoloRepository extends CrudRepository<Articolo, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [dbo].[articolo]",nativeQuery=true)
	public List<Articolo> getArticoli();
	

	

	

}