package com.telefake.falemuito.domain.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "tb_localidade")
public class Localidade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String ddd;
  private String regiao;
  private String uf;

  @OneToMany(mappedBy = "origem", cascade = CascadeType.MERGE)
  @JsonIgnoreProperties("origem")
  private List<Tarifa> tarifas;
}
