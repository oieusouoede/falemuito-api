package com.telefake.falemuito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telefake.falemuito.domain.models.Localidade;
import com.telefake.falemuito.repositories.ILocalidadeRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FaleMuitoController {

  private @Autowired ILocalidadeRepository localidadeRepo;

  @GetMapping("/localidades")
  public ResponseEntity<List<Localidade>> getLocalidades (){
    return ResponseEntity.ok().body(localidadeRepo.findAll());
  }
  
}
