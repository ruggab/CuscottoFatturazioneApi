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
	
	@Query(nativeQuery = true, value="select * from [dbo].[VWdashboardFattureLastWeek]")
	public List<VWDashboardLastWeek> getAdminDashboardLastWeekBySocieta();
	
}
