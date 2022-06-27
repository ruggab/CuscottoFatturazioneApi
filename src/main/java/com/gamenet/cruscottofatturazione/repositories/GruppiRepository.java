package com.gamenet.cruscottofatturazione.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.GruppoUtenti;

@Repository
public interface GruppiRepository extends CrudRepository<GruppoUtenti, Integer> {

}
