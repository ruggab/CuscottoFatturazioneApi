package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Societa;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface SocietaRepository extends CrudRepository<Societa, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [dbo].[societa]",nativeQuery=true)
	public List<Societa> getSocieta();
	

	

	

}