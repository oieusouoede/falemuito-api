package com.telefake.falemuito.services;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;
import com.telefake.falemuito.domain.models.Tarifa;

public interface IFaleMuitoService {

    public Double calcular(Tarifa tarifa, int minutos);
    public FaleMuitoEnum GetNomeDoPlano();
  
}
