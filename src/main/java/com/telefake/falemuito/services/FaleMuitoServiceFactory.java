package com.telefake.falemuito.services;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;
import com.telefake.falemuito.services.impl.FaleMuito120Service;
import com.telefake.falemuito.services.impl.FaleMuito30Service;
import com.telefake.falemuito.services.impl.FaleMuito60Service;
import com.telefake.falemuito.services.impl.SemPlanoService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FaleMuitoServiceFactory {

  private FaleMuitoEnum plano;

  public static FaleMuitoServiceFactory INSTANCE (FaleMuitoEnum plano){
        return new FaleMuitoServiceFactory(plano);
    } 

    public IFaleMuitoService semPlano(){
        return retornaPlano(FaleMuitoEnum.SEMPLANO);
    }

    public IFaleMuitoService comPlano(){
        return retornaPlano(plano);
    }

    private IFaleMuitoService retornaPlano(FaleMuitoEnum plano){
      switch (plano){
        case FALEMUITO30:
          return new FaleMuito30Service();
        case FALEMUITO60:
          return new FaleMuito60Service();
        case FALEMUITO120:
          return new FaleMuito120Service();
        case SEMPLANO:
        default:
          return new SemPlanoService();
    }
  }
}
