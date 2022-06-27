package com.gamenet.cruscottofatturazione.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.ApplicationLog;

@Repository
public interface ApplicationLogsRepository extends CrudRepository<ApplicationLog, Integer> {

}
