package com.telefake.falemuito.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telefake.falemuito.domain.models.Localidade;

@Repository
public interface ILocalidadeRepository extends JpaRepository <Localidade, Long> {
  
}
