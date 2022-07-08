package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.VWDashboardMonth;


@Repository
public interface DashboardMonthRepository extends CrudRepository<VWDashboardMonth, Integer>
{
	@Query(nativeQuery = true, value=""
			+ "	SET LANGUAGE Italian; "
			+ "	SET DATEFIRST 1; "
			+ "	SELECT DATEPART(WEEK, data_fattura) AS settimana, COUNT(id) AS numero "
			+ "	FROM  dbo.fattura "
			+ "	WHERE DATEPART(MONTH, data_fattura) = DATEPART(MONTH, GETDATE()) "
			+ "	AND societa=:societa "
			+ "	AND stato_fattura=:stato "
			+ "	GROUP BY DATEPART(WEEK, data_fattura) "
			+ "	ORDER BY settimana")
	public List<VWDashboardMonth> getVWDashboardMonthBySocietaAndStato(@Param("societa") String societa, @Param("stato") String stato);
	
	@Query(nativeQuery = true, value="select * from [dbo].[VWdashboardFattureMonth]")
	public List<VWDashboardMonth> getAdminDashboardMonthBySocieta();
	
}
