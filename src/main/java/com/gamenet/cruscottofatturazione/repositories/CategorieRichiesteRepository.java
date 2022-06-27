package com.gamenet.cruscottofatturazione.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gamenet.cruscottofatturazione.entities.CategoriaRichieste;

@Repository
public interface CategorieRichiesteRepository extends CrudRepository<CategoriaRichieste, Integer> {

}
