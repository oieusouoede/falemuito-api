# falemuito-api

Esse repositório é uma cópia de um projeto que eu fiz em 2022 para um processo seletivo. O objetivo era criar um simulador de plano telefônico que calcula o valor de uma chamada telefônica de acordo com o plano que vai ser simulado, e de acordo também com as taxas específicas para determinados pares de DDD.

## Sumário

- [Executando](#executando)
  - [Especificações](#especificaçôes)
  - [Testes](#testes)
  - [Spring Boot](#spring-boot)
  - [Docker](#docker)
  - [docker-compose](#docker-compose)
- [Implementação](#implementação)
  - [Localidade](#localidade)
  - [Tarifa](#tarifa)
  - [Controller](#controller)
  - [Simulador](#simulador)
  - [Banco de dados](#banco-de-dados)

## Executando

É possível rodar a api de várias formas, ela deverá estar disponível em http://localhost:8083/.

### Especificaçôes

- Java 11
- Spring Boot 2.7.0

### Testes

Com o Maven instalado é só abrir um terminal dentro da pasta do projeto e entrar com o comando:

`mvn clean test`

### Spring Boot

Pra rodar com o Spring Boot é só, novamente a partir da pasta do projeto, executar:

`mvn spring-boot:run`

Também é possível compilar, rodar os testes e empacotar o projeto usando:

`mvn clean install`

pra depois rodar com:

`java -jar target/falemais-0.0.1-SNAPSHOT.jar`

### Docker

Baixar a imagem:

`docker image pull edemarinho/falemuito-api`

Iniciar o container:

`docker run --name falemuito-api -it edemarinho/falemuito-api`

### docker-compose

Na pasta raiz do projeto:

`docker-compose -f falemuito-api.yml up`

### Swagger

A documentação da api está disponível contexto padrão do Swagger: http://localhost:8083/swagger-ui.html

## Implementação

O algoritmo trabalha com as entidades Localidade e Tarifa. Cada localidade está associada a uma ou mais Tarifa. Essas tarifas representam as taxas que serão aplicadas entre uma Localidade origem e várias Localidade destino.

### Localidade

```
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

  ...
}
```

A Tarifa funciona como uma associação entre duas localidades, ela contém uma Localidade origem e uma Localidade destino, além de um atributo tarifa que será a taxa utilizada para fazer a simulação:

### Tarifa

```
@Table(name = "tb_tarifas")
public class Tarifa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private double tarifa;

  @ManyToOne
  @JsonIgnoreProperties("tarifas")
  private Localidade origem;

  @OneToOne
  @JoinColumn(name = "destino_id")
  @JsonIgnoreProperties("tarifas")
  private Localidade destino;

}
```

Com essas entidades já populadas de acordo os exemplos que havian na descrição do desafio, a execução inicia com algumas Localidades registradas na base, bem como as Tarifas relacionadas.

### Controller

Na controler eu tenho dois endpoints mapeados `/localidades` e `/simular`. O meu frontend está programado para obter uma lista de Localidades ao inicializar a página principal. Por meio do primeiro endpoint ele popula o select de origens para o usuário escolher na tela.

Quando o usuário escolhe um DDD de origem, ele está selecionando a Localidade que possui aquele DDD. A aplicação então itera na lista de Tarifas dessa Localidade para obter os possíveis destinos.

O usuário também vai escolher os minutos e o plano que vai ser simulado. A partir dessas informações o frontend vai montar um objeto RequestSimularDTO, que serve mandar para a api os parâmetros usados no cálculo.

```
public class RequestSimularDTO {

  private Tarifa tarifa;
  private int minutos;
  private FaleMuitoEnum plano;

}
```

O front envia esses parâmetros por meio do segundo endpoint.

### Simulador

No endpoint `/simular` a api recebe os parâmetros que vieram do front para fazer o cálculo e montar um objeto de resposta ResponseSimularDTO.

```
public class ResponseSimularDTO {

    private String origem;
    private String destino;
    private int minutos;
    private FaleMuitoEnum plano;
    private Double valorComFaleMuito;
    private Double valorSemFaleMuito;

}
```

Para fazer o cálculo é instanciada uma Factory disponibiliza a implementação do cálculo de acordo com o plano. Ela recebe este plano como parâmetro do seu construtor e disponibiliza duas implementações de cálculo por meio dos métodos _semPlano()_ e _comPlano()_.

```
public interface IFaleMuitoService {

    public Double calcular(Tarifa tarifa, int minutos);
    public FaleMuitoEnum GetNomeDoPlano();

}
```

Esses métodos retornam uma implementação da interface IFaleMuitoService que estabelece um método _calcular()_, recebendo a Tarifa e a quantidade de minutos e um _GetNomeDoPlano()_, que retorna o nome do plano.

A Factory tem um switch case que retorna a implementação do cálculo de acordo com o plano informado:

```
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
```

Desse jeito o método _semPlano()_ vai sempre retornar a implementação sem um plano, multiplicando a taxa pelos minutos.
O método _comPlano()_ vai cair no switch case, e de acordo com o plano informado pelo usuário o cálculo vai ter uma lógica diferente.

Lá na controller são chamados os dois métodos para montar o objeto de resposta que vão ser carregados na lista de resultados do frontend.

```
    @PostMapping("/simular")
    public ResponseEntity<ResponseSimularDTO> simularPlano(@RequestBody RequestSimularDTO request){
      FaleMuitoServiceFactory factory = FaleMuitoServiceFactory.INSTANCE(request.getPlano());

      Double valorSemPlano = factory.semPlano().calcular(request.getTarifa(), request.getMinutos());
      Double valorComPlano = factory.comPlano().calcular(request.getTarifa(), request.getMinutos());

      return ResponseEntity
        .ok()
        .body(
          ResponseSimularDTO.builder()
                            .origem(request.getTarifa().getOrigem().getDdd())
                            .destino(request.getTarifa().getDestino().getDdd())
                            .minutos(request.getMinutos())
                            .plano(request.getPlano())
                            .valorComFaleMuito(valorComPlano)
                            .valorSemFaleMuito(valorSemPlano)
                            .build()
    );
  }
```

### Banco de dados

A api usa um banco H2 porque é mais pratico nesse nível de complexidade.
