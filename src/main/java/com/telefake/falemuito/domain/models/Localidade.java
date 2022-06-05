package com.telefake.falemuito.domain.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.telefake.falemuito.domain.enums.UFEnum;

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
  private UFEnum uf;

  @OneToMany(mappedBy = "origem", cascade = CascadeType.MERGE)
  private List<Tarifa> tarifas;
}
