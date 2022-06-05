package com.telefake.falemuito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.telefake.falemuito.domain.models.Localidade;
import com.telefake.falemuito.dto.requests.RequestSimularDTO;
import com.telefake.falemuito.dto.response.ResponseSimularDTO;
import com.telefake.falemuito.repositories.ILocalidadeRepository;
import com.telefake.falemuito.services.FaleMuitoServiceFactory;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FaleMuitoController {

  private @Autowired ILocalidadeRepository localidadeRepo;

    @PostMapping("/simular")
    public ResponseEntity<ResponseSimularDTO> simularPlano(@RequestBody RequestSimularDTO request){
      FaleMuitoServiceFactory factory = FaleMuitoServiceFactory.INSTANCE(request.getPlano());
      
      Double valorSemPlano = factory.semPlano().calcular(request.getTarifa(), request.getMinutos());
      Double valorComPlano = factory.comPlano().calcular(request.getTarifa(), request.getMinutos());

      return ResponseEntity
        .ok()
        .body(
          ResponseSimularDTO.builder()
                            .origem(request.getTarifa().getOrigem().getDdd())
                            .destino(request.getTarifa().getDestino().getDdd())
                            .minutos(request.getMinutos())
                            .plano(request.getPlano())
                            .valorComFaleMuito(valorComPlano)
                            .valorSemFaleMuito(valorSemPlano)
                            .build()
    );
  }

  @GetMapping("/localidades")
  public ResponseEntity<List<Localidade>> getLocalidades (){
    return ResponseEntity.ok().body(localidadeRepo.findAll());
  }
  
}
