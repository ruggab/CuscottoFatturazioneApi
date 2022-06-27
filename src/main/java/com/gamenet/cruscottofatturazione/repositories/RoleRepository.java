package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gamenet.cruscottofatturazione.entities.RoleUser;

@Repository
public interface RoleRepository extends CrudRepository<RoleUser, Integer>, JpaSpecificationExecutor<RoleUser>
{

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_CreateUpdateRole] :id, :name, :isAdmin, :email, :description, :utenteUpdate")
	public List<Integer> saveRole(@Param("id") Integer id,
						 @Param("name") String name,
						 @Param("isAdmin") Boolean isAdmin,
						 @Param("email") String email,
						 @Param("description") String description,
						 @Param("utenteUpdate") String utenteUpdate
				   	 );	

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_DeleteRole] :IdRuolo, :utenteUpdate")
	public void deleteRole(@Param("IdRuolo") Integer idRuolo,
					   	   @Param("utenteUpdate") String utenteUpdate
				   	 	   );
	
	
	@Query(nativeQuery=true, value="SELECT [id] FROM [Anagraphics].[Roles]  WHERE UPPER([name]) IN (:roleNameList)")
	public List<Integer> getAvaiableRolesDerogaMerito(@Param("roleNameList") List<String> roleNameList);
}
