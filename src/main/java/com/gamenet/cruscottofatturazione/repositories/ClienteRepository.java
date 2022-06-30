package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	
	@Query(value="SELECT * FROM [dbo].[cliente]",nativeQuery=true)
	public List<Cliente> getClienti();

	@Query(value="DELETE FROM [dbo].[cliente] WHERE id=:clienteId",nativeQuery=true)
	@Modifying
	public void deleteCliente(@Param("clienteId") Integer  clienteId);

	public List<Cliente> getClientiBySocieta(String codiceSocieta);
	
	
	@Query(value="SELECT * FROM [dbo].[cliente] where"
			+ " (:codiceCliente is null or codice_cliente=:codiceCliente) "
			+ "AND (:ragioneSociale is null or ragione_sociale like %:ragioneSociale%) "
			+ "AND (:partitaIva is null or partita_iva=:partitaIva) "
			+ "AND (:codiceFiscale is null or codice_fiscale=:codiceFiscale)",nativeQuery=true)
	public List<Cliente> search(@Param("codiceCliente") String codiceCliente, @Param("ragioneSociale") String ragioneSociale, @Param("partitaIva") String partitaIva,@Param("codiceFiscale")  String codiceFiscale);

	

}