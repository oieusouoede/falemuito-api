package com.telefake.falemuito.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telefake.falemuito.domain.models.Tarifa;

@Repository
public interface ITarifaRepository extends JpaRepository <Tarifa, Long> {
  
}
