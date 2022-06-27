package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.WorkflowStep;

@Repository
public interface WorkflowStepRepository extends CrudRepository<WorkflowStep, Integer> {

	//@Query(value="SELECT [W_S].* FROM [dbo].[workflow_step_roles] [W_S_R] JOIN [dbo].[Workflow_step] [W_S] ON [W_S].[id] = [W_S_R].[workflow_step_id] JOIN [dbo].[Workflow] [W] ON [W_S].[workflow_id] = [W].[id] WHERE [W_S_R].[role_id] = :RoleId AND [W].[business] = :business",nativeQuery=true)
	@Query(value="SELECT DISTINCT [W_S].* " + 
				"  FROM [dbo].[workflow_step_roles] [W_S_R] " + 
				"  JOIN [dbo].[Workflow_step] [W_S] ON [W_S].[id] = [W_S_R].[workflow_step_id] " + 
				"  JOIN [dbo].[Workflow] [W] ON [W_S].[workflow_id] = [W].[id]" + 
				" WHERE ([W_S_R].[role_id] = :RoleId OR " + 
				"		   (" + 
				"			SELECT [is_admin]" + 
				"			  FROM [Anagraphics].[Roles]" + 
				"			  WHERE [id] = :RoleId" + 
				"		   ) = 1" + 
				"	   ) AND " + 
				"	   [W].[business] = :business",nativeQuery=true)
	public List<WorkflowStep> getActiveWorkflowStepByRole(@Param("RoleId") Integer RoleId, @Param("business") String business);

}
