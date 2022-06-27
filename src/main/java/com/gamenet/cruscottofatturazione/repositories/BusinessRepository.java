package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Business;

@Repository
public interface BusinessRepository extends CrudRepository<Business, Integer>
{
	@Query(nativeQuery = true, value="EXEC [Anagraphics].[SP_GetAvaiableBusinessByUser] :idUser, :isAdmin")
	public List<String> getAvaiableBusinessByUser(@Param("idUser") Integer idUser, @Param("isAdmin") Boolean isAdmin);
}
