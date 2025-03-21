# API de Gerenciamento de Veículos e Pneus

Esta é uma API REST desenvolvida em Spring Boot para gerenciar veículos e seus pneus.

## Requisitos

- Java 17
- Maven
- Docker e Docker Compose
- PostgreSQL (opcional, pode usar o container Docker)

## Configuração do Ambiente

1. Clone o repositório
2. Inicie o banco de dados usando Docker Compose:
   ```bash
   docker-compose up -d
   ```
3. Compile o projeto:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints Disponíveis

### Veículos

- `GET /api/v1/veiculos` - Lista todos os veículos
- `GET /api/v1/veiculos/{placa}` - Busca um veículo específico
- `POST /api/v1/veiculos` - Cria um novo veículo

### Pneus

- `GET /api/v1/pneus` - Lista todos os pneus
- `GET /api/v1/pneus/{numeroFogo}` - Busca um pneu específico
- `POST /api/v1/pneus` - Cria um novo pneu

### Vinculação de Pneus

- `POST /api/v1/veiculos/{placa}/pneus/{numeroFogo}/posicao/{posicao}` - Vincula um pneu a um veículo
- `DELETE /api/v1/veiculos/{placa}/pneus/{numeroFogo}` - Desvincula um pneu de um veículo

## Exemplos de Uso

### Criar um Veículo

```bash
curl -X POST http://localhost:8080/api/veiculos \
  -H "Content-Type: application/json" \
  -d '{
  "placa": "ABC1D23",
  "marca": "Volvo",
  "quilometragem": 120000,
  "status": "ATIVO",
  "pneus": [
    {
      "veiculoPlaca": "ABC1D23",
      "pneu": {
        "numeroFogo": "FOGO090",
        "marca": "Michelin",
        "pressaoAtual": 32.5,
        "status": "EM_USO"
      },
      "posicao": "A"
    },
    {
      "veiculoPlaca": "ABC1D23",
      "pneu": {
        "numeroFogo": "FOGO091",
        "marca": "Bridgestone",
        "pressaoAtual": 30.0,
        "status": "EM_USO"
      },
      "posicao": "B"
    },
    {
      "veiculoPlaca": "ABC1D23",
      "pneu": {
        "numeroFogo": "FOGO092",
        "marca": "Goodyear",
        "pressaoAtual": 28.5,
        "status": "EM_USO"
      },
      "posicao": "C"
    },
    {
      "veiculoPlaca": "ABC1D23",
      "pneu": {
        "numeroFogo": "FOGO093",
        "marca": "Pirelli",
        "pressaoAtual": 29.0,
        "status": "EM_USO"
      },
      "posicao": "D"
    },
    {
      "veiculoPlaca": "ABC1D23",
      "pneu": {
        "numeroFogo": "FOGO094",
        "marca": "Dunlop",
        "pressaoAtual": 31.0,
        "status": "EM_USO"
      },
      "posicao": "E"
    },
    {
      "veiculoPlaca": "ABC1D23",
      "pneu": {
        "numeroFogo": "FOGO095",
        "marca": "Continental",
        "pressaoAtual": 30.5,
        "status": "EM_USO"
      },
      "posicao": "F"
    }
  ]
}'
```

### Criar um Pneu

```bash
curl -X POST http://localhost:8080/api/pneus \
  -H "Content-Type: application/json" \
  -d '{
    "numeroFogo": "FOGO001",
    "marca": "Michelin",
    "pressaoAtual": 32.0,
    "status": "DISPONIVEL"
  }'
```

### Vincular um Pneu a um Veículo

```bash
curl -X POST "http://localhost:8080/api/veiculos/ABC1234/pneus/FOGO001/posicao/A"

```
### Desvincular um Pneu a um Veículo


```bash
curl -X DELETE "http://localhost:8080/api/veiculos/ABC1234/pneus/FOGO001"

```

## Testes

Para executar os testes:

```bash
mvn test
```

## Tecnologias Utilizadas

- Spring Boot 3.2.3
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker
- Maven
- Lombok
- MapStruct 