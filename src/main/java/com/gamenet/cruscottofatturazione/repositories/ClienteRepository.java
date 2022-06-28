package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [dbo].[cliente]",nativeQuery=true)
	public List<Cliente> getClienti();

	@Query(value="DELETE FROM [dbo].[cliente] WHERE id=:clienteId",nativeQuery=true)
	@Modifying
	public void deleteCliente(@Param("clienteId") Integer  clienteId);

	

}