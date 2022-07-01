package com.gamenet.cruscottofatturazione.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gamenet.cruscottofatturazione.entities.Cliente;
import com.gamenet.cruscottofatturazione.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
	
	@Query(value="SELECT * FROM [Anagraphics].[Users]",nativeQuery=true)
	public List<User> getMiaLista();
	
	public User findByUsernameAndPassword(String username, String password);

	public User findByUsername(String username);
	
	public User findByToken(String token);	

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_CreateUpdateUser] :id , :role_id , :name, :email, :societa, :username, :password, :valid_from, :valid_to, :utenteUpdate")
	public List<Integer> saveUser(@Param("id") Integer id,
					   	 @Param("role_id") Integer role_id,
						 @Param("name") String name,
						 @Param("societa") String societa,
						 @Param("email") String email,
						 @Param("username") String username,
						 @Param("password") String password,
				   	     @Param("valid_from") Date valid_from,
				   	     @Param("valid_to") Date valid_to,
						 @Param("utenteUpdate") String utenteUpdate
				   	 );	

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_DeleteUser] :IdUtente, :utenteUpdate")
	public void deleteUser(@Param("IdUtente") Integer idUtente,
					   	   @Param("utenteUpdate") String utenteUpdate
				   	 	   );
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_LogoutUser] :IdUtente")
	public void logoutUser(@Param("IdUtente") Integer idUtente);
	
//	@Query(value="EXEC [Logs].[SP_GetDestinatariNotificaRichiestaSLA] :RichiestaId",nativeQuery=true)
//	public List<User> getDestinatariNotificaRichiestaSLA(@Param("RichiestaId") Integer RichiestaId);
//	
//	@Query(value="EXEC [Logs].[SP_GetDestinatariNotificheByCodice] :CodiceNotifica, :TipologiaNotifica",nativeQuery=true)
//	public List<User> getDestinatariNotificheByCodice(@Param("CodiceNotifica") String codiceNotifica, @Param("TipologiaNotifica") String tipologiaNotifica);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value="EXEC [Anagraphics].[SP_UpdateUserPassword] :idUser, :passwordPrecedente, :passwordNuova, :utenteUpdate")
	public List<Integer> changePasswordUser(@Param("idUser") Integer idUser,
					   	   					@Param("passwordPrecedente") String passwordPrecedente,
					   	   					@Param("passwordNuova") String passwordNuova,
					   	   					@Param("utenteUpdate") String utenteUpdate
				   	 	   				);
	
	
	
	
	@Query(value="SELECT * FROM [Anagraphics].[Users] u inner join [Anagraphics].[Roles] r on u.role_id=r.id"
			+ " where (:nome is null or u.name like %:nome%)"
			+ " AND (:email is null or u.email like %:email%)"
			+ " AND (:username is null or u.username like %:username%)"
			+ " AND (:ruolo is null or r.name like %:ruolo%)"
			+ " AND ( (:active= '0' AND u.valid_to > GETDATE()) OR (:active='1' AND u.valid_to < GETDATE()) )",nativeQuery=true)
	public List<User> search(@Param("nome") String nome, @Param("email") String email, @Param("username") String username,@Param("ruolo")  String ruolo,@Param("active")  String active);

	
	

}