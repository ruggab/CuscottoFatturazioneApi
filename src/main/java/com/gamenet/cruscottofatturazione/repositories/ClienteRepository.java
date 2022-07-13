package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.models.response.ClienteAutoComplete;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, String> , JpaSpecificationExecutor<Cliente> {
	
	@Query(value="SELECT * FROM [dbo].[cliente]",nativeQuery=true)
	public List<Cliente> getClienti();

	@Query(value="DELETE FROM [dbo].[cliente] WHERE codice_cliente= :codiceCliente",nativeQuery=true)
	@Modifying
	public void deleteCliente(@Param("codiceCliente") String  codiceCliente);

	public List<Cliente> getClientiBySocieta(String codiceSocieta);
	
	@Query(value="SELECT * FROM [dbo].[cliente] where codice_cliente= :codiceCliente",nativeQuery=true)
	public Cliente findByCodiceCliente(@Param("codiceCliente") String codiceCliente);

	@Query(value="SELECT *  FROM [dbo].[cliente] c where societa= :codiceSocieta",nativeQuery=true)
	public List<Cliente> getActiveClientiBySocieta(@Param("codiceSocieta") String codiceSocieta);
	

}