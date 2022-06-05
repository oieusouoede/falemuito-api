package com.telefake.falemuito.domain.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.telefake.falemuito.domain.models.Localidade;
import com.telefake.falemuito.domain.models.Tarifa;
import com.telefake.falemuito.repositories.ILocalidadeRepository;
import com.telefake.falemuito.repositories.ITarifaRepository;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired ITarifaRepository tarifaRepo;
  @Autowired ILocalidadeRepository localidadeRepo;

  @Override
  public void run(String... args) throws Exception {
    
    if (localidadeRepo.count() == 0) {
      inserirLocalidades();
    }

    if (tarifaRepo.count() == 0) {
      inserirTarifas();
    }
  }

  private void inserirLocalidades() {
    localidadeRepo.saveAll(List.of(
      Localidade.builder().id(1)
                          .regiao("São Paulo")
                          .uf("SP")
                          .ddd("011")
                          .build(),
      Localidade.builder().id(2)
                          .regiao("São Paulo")
                          .uf("SP")
                          .ddd("016")
                          .build(),
      Localidade.builder().id(3)
                          .regiao("São Paulo")
                          .uf("SP")
                          .ddd("017")
                          .build(),
      Localidade.builder().id(4)
                          .regiao("São Paulo")
                          .uf("SP")
                          .ddd("018")
                          .build()
    ));
  }

  private void inserirTarifas() {
    tarifaRepo.saveAll(List.of(
      Tarifa.builder().tarifa(1.9)
                      .origem(localidadeRepo.findById(1L).get())
                      .destino(localidadeRepo.findById(2L).get())
                      .build(),
      Tarifa.builder().tarifa(1.7)
                      .origem(localidadeRepo.findById(1L).get())
                      .destino(localidadeRepo.findById(3L).get())
                      .build(),
      Tarifa.builder().tarifa(0.9)
                      .origem(localidadeRepo.findById(1L).get())
                      .destino(localidadeRepo.findById(4L).get())
                      .build(),
      Tarifa.builder().tarifa(2.9)
                      .origem(localidadeRepo.findById(2L).get())
                      .destino(localidadeRepo.findById(1L).get())
                      .build(),
      Tarifa.builder().tarifa(2.7)
                      .origem(localidadeRepo.findById(3L).get())
                      .destino(localidadeRepo.findById(1L).get())
                      .build(),
      Tarifa.builder().tarifa(1.9)
                      .origem(localidadeRepo.findById(4L).get())
                      .destino(localidadeRepo.findById(1L).get())
                      .build()
    ));
  }

}
