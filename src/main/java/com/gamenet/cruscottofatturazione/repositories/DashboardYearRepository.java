package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.VWDashboardYear;


@Repository
public interface DashboardYearRepository extends CrudRepository<VWDashboardYear, Integer>
{
	//@Query(nativeQuery = true, value="EXEC [dbo].[getFattureYear] :societa")
	@Query(nativeQuery = true, value=""
			+ " SET LANGUAGE Italian; "
			+ "	SET DATEFIRST 1; "
			+ "	SELECT        DATEPART(MONTH, data_fattura) AS mese, DATENAME(MONTH, data_fattura) AS nome_mese, COUNT(id) AS numero "
			+ "	FROM            dbo.fattura "
			+ "	WHERE        (YEAR(data_fattura) = YEAR(GETDATE())) "
			+ " "
			+ "	AND societa=:societa "
			+ "	GROUP BY DATEPART(MONTH, data_fattura), DATENAME(MONTH, data_fattura) "
			+ "	ORDER BY mese")
	public List<VWDashboardYear> getVWDashboardYearBySocieta(@Param("societa") String societa);
	
	@Query(nativeQuery = true, value="select * from [dbo].[VWdashboardFattureYear]")
	public List<VWDashboardYear> getAdminDashboardYearWeekBySocieta();
	
}
