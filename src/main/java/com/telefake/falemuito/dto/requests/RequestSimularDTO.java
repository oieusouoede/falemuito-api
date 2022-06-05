package com.telefake.falemuito.dto.requests;

import com.telefake.falemuito.domain.enums.FaleMuitoEnum;
import com.telefake.falemuito.domain.models.Tarifa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestSimularDTO {

  private Tarifa tarifa;
  private int minutos;
  private FaleMuitoEnum plano;

}
