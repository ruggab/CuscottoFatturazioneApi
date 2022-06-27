package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gamenet.cruscottofatturazione.entities.WorkflowStepRole;

@Repository
public interface WorkflowStepRoleRepository extends CrudRepository<WorkflowStepRole, Integer>, JpaSpecificationExecutor<WorkflowStepRole>
{
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value="EXEC [Anagraphics].[SP_CreateUpdateWorkflowStepRole] :id, :roleId, :workflowStepId, :utenteUpdate")
	public List<Integer> saveWorkflowStepRole(@Param("id") Integer id,
										@Param("roleId") Integer roleId,
										@Param("workflowStepId") Integer workflowStepId,
										@Param("utenteUpdate") String utenteUpdate
							   	 		);	

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(nativeQuery = true, value=" EXEC [Anagraphics].[SP_DeleteWorkflowStepRole] :workflowStepRoleId")
	public void deleteWorkflowStepRole(@Param("workflowStepRoleId") Integer workflowStepRoleId);
}