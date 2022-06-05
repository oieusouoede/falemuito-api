package com.telefake.falemuito.domain.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_tarifas")
public class Tarifa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private double tarifa;

  @ManyToOne
  private Localidade origem;

  @OneToOne
  @JoinColumn(name = "destino_id")
  private Localidade destino;

}
