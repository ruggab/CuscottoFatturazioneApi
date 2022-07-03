package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.VoceMenu;

@Repository
public interface VociMenuRepository extends CrudRepository<VoceMenu, Integer>
{
	// @Query(value="SELECT DISTINCT [V_M].* FROM [Anagraphics].[voci_menu] [V_M] JOIN [Anagraphics].[roles_voci_menu] [R_V_M] ON [V_M].[id] = [R_V_M].[voce_id] WHERE ([R_V_M].[role_id] = :roleId OR :isAdmin = 1) ORDER BY [V_M].[order_number]", nativeQuery = true)
//	@Query(value="	SELECT DISTINCT [V_M].*" +
//				    " FROM [Anagraphics].[voci_menu] [V_M] LEFT JOIN [Anagraphics].[roles_voci_menu] [R_V_M] ON [V_M].[id] = [R_V_M].[voce_id]" +
//				   " WHERE ( " + 
//				  		  "[R_V_M].[role_id] = :roleId OR " + 
//				  		  " :isAdmin = 1 OR " + 
//				  		  " ( " + 
//				  		  "		ISNULL([V_M].[is_dettaglio],0) = 1) OR " + 
//				  		  "		[R_V_M].[voce_id] IS NOT NULL " + 
//				  		  "	) " + 
//				" ORDER BY [V_M].[order_number]", nativeQuery = true)
//	public List<VoceMenu> getVociMenuByRoleId(@Param("roleId") Integer roleId, @Param("isAdmin") Boolean isAdmin);
	
	
	
	@Query(value="	SELECT DISTINCT [V_M].*" +
		    " FROM [Anagraphics].[voci_menu] [V_M] LEFT JOIN [Anagraphics].[roles_voci_menu] [R_V_M] ON [V_M].[id] = [R_V_M].[voce_id]" +
		   " WHERE " + 
		  		  "[R_V_M].[role_id] = :roleId OR " + 
		  		  " :isAdmin = 1 " + 
		  		 
		" ORDER BY [V_M].[order_number]", nativeQuery = true)
public List<VoceMenu> getVociMenuByRoleId(@Param("roleId") Integer roleId, @Param("isAdmin") Boolean isAdmin);
}
