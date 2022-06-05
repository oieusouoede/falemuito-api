package com.telefake.falemuito.services.impl;

import org.springframework.stereotype.Service;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;
import com.telefake.falemuito.domain.models.Tarifa;
import com.telefake.falemuito.services.IFaleMuitoService;

@Service
public class SemPlanoService implements IFaleMuitoService {

    @Override
    public Double calcular(Tarifa tarifa, int minutos) {
        return tarifa.getTarifa() * minutos;
    }

    @Override
    public FaleMuitoEnum GetNomeDoPlano() {
        return FaleMuitoEnum.SEMPLANO;
    }
}
