package com.telefake.falemuito.services.impl;

import org.springframework.stereotype.Service;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;
import com.telefake.falemuito.domain.models.Tarifa;
import com.telefake.falemuito.services.IFaleMuitoService;

@Service
public class FaleMuito60Service implements IFaleMuitoService{

    @Override
    public Double calcular(Tarifa tarifa, int minutos) {
        if (minutos <= 60){
            return 0.0;
        }
        return tarifa.getTarifa() * (minutos - 60) * 1.1;
    }

    @Override
    public FaleMuitoEnum GetNomeDoPlano() {
        return FaleMuitoEnum.FALEMUITO60;
    }
}
