package com.telefake.falemuito.services.impl;

import org.springframework.stereotype.Service;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;
import com.telefake.falemuito.domain.models.Tarifa;
import com.telefake.falemuito.services.IFaleMuitoService;

@Service
public class FaleMuito30Service implements IFaleMuitoService {

    @Override
    public Double calcular(Tarifa tarifa, int minutos) {
        if (minutos <= 30){
            return 0.0;
        } else {
            return tarifa.getTarifa() * (minutos - 30) * 1.1;
        }
    }

    @Override
    public FaleMuitoEnum GetNomeDoPlano() {
        return FaleMuitoEnum.FALEMUITO30;
    }

}
