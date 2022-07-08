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
	
	@Query(nativeQuery = true, value=" "
			+ " SELECT CASE WHEN stato_fattura  IS NULL THEN 'I' ELSE stato_fattura END as stato_fattura, "
			+ " COUNT(id) AS numero, SUM(importo) AS importo "
			+ "	FROM            dbo.fattura "
			+ "	WHERE  societa=:societa "
			+ "	GROUP BY stato_fattura")
	public List<VWDashboardTopSummary> getVWDashboardTopSummaryBySocieta(@Param("societa") String societa);
	
	@Query(nativeQuery = true, value="select * from [dbo].[VWdashboardFatture]")
	public List<VWDashboardTopSummary> getAdminDashboardTopSummaryBySocieta();
	
}
