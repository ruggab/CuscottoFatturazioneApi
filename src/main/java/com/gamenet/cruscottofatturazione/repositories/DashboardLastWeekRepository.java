package com.gamenet.cruscottofatturazione.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.VWDashboardLastWeek;


@Repository
public interface DashboardLastWeekRepository extends CrudRepository<VWDashboardLastWeek, Integer>
{
	@Query(nativeQuery = true, value="EXEC [dbo].[getFattureLastWeek] :societa")
	public List<VWDashboardLastWeek> getVWDashboardLastWeekBySocieta(@Param("societa") String societa);
	
	@Query(nativeQuery = true, value=""
			+ "SET LANGUAGE Italian; "
			+ "SET DATEFIRST 1; "
			+ "SELECT FORMAT(cast(data_fattura as date), 'yyyy-MM-dd') as giorno, COUNT(id) AS numero "
			+ "FROM   dbo.fattura "
			+ "WHERE  (data_fattura > DATEADD(day, - :giorni, GETDATE())) AND (data_fattura <= GETDATE()) "
			+ "AND societa=:societa "
			+ "GROUP BY cast(data_fattura as date) "
			+ "ORDER BY cast(data_fattura as date) ")
	public List<VWDashboardLastWeek> getVWDashboardBySocieta(@Param("societa") String societa,@Param("giorni") Integer giorni);
	
	@Query(nativeQuery = true, value="select * from [dbo].[VWdashboardFattureLastWeek]")
	public List<VWDashboardLastWeek> getAdminDashboardLastWeekBySocieta();
	
}
