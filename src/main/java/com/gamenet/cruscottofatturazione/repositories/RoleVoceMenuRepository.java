package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gamenet.cruscottofatturazione.entities.RoleVoceMenu;

public interface RoleVoceMenuRepository extends CrudRepository<RoleVoceMenu, Integer>, JpaSpecificationExecutor<RoleVoceMenu>
{
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value="EXEC [Anagraphics].[SP_CreateUpdateRoleVociMenu] :id, :roleId, :voceId, :utenteUpdate")
	public List<Integer> saveRoleVoceMenu(@Param("id") Integer id,
										  @Param("roleId") Integer roleId,
										  @Param("voceId") Integer voceId,
										  @Param("utenteUpdate") String utenteUpdate
							   	 		);	

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_DeleteRoleVociMenu] :roleVoceId")
	public void deleteRoleVoceMenu(@Param("roleVoceId") Integer roleVoceId);
}
