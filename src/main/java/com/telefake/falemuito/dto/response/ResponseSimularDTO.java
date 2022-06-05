package com.telefake.falemuito.dto.response;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSimularDTO {

    private String origem;
    private String destino;
    private int minutos;
    private FaleMuitoEnum plano;
    private Double valorComFaleMais;
    private Double valorSemFaleMais;

}
