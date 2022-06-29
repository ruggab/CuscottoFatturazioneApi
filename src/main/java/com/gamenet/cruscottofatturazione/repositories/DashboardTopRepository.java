package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.VWDashboardTopSummary;


@Repository
public interface DashboardTopRepository extends CrudRepository<VWDashboardTopSummary, String>
{
	@Query(nativeQuery = true, value="EXEC [dbo].[getTopSummary] :societa")
	public List<VWDashboardTopSummary> getVWDashboardTopSummaryBySocieta(@Param("societa") String societa);
	
	@Query(nativeQuery = true, value="select * from [dbo].[VWdashboardFatture]")
	public List<VWDashboardTopSummary> getAdminDashboardTopSummaryBySocieta();
	
}
