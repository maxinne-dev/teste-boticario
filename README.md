# Teste Boticário

## Execução
> Por favor leia este documento todo antes de executar o projeto.

O projeto foi configurado para gerar um artefato WAR para deploy no Tomcat. Como implementei no _IntelliJ IDEA_, usei a função de configuração automática de execução do projeto. Vou providenciar um artefato WAR para deploy, e um arquivo _docker compose_ para simplificar, caso prefira, basta usar o comando `docker compose up`.

### Autênticação:

- [POST] /authenticate - Obter o token JWT
> #### Corpo [JSON]
> ```
> {
> "username": "xmrGmb7PWFwSzzx6TBxxEGyA7n9zGaC7UWs6GWMruGZMGMNG",
> "password": "0y1GaVyJ6szGu7O3dR2Ax8ijucQujxkUZs6Y715MiQ5hhLiEi6NAbfMJGHqpad96"
> }
> ```
> #### Resposta [texto plano]
> ```
> eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ4bXJHbWI3UFdGd1N6eng2VEJ4eEVHeUE3bjl6R2FDN1VXczZHV01ydUdaTUdNTkciLCJpYXQiOjE3MTg4MTk2MDUsImV4cCI6MTcxODgzNzYwNX0.viOoSQlDUB02MxtvIzAQzUoj1nWmSC_ZNehxWXPoEcNe_kClRF_uJv6gCodhmO3V
> ```

## Decisões
Optei por implementar o projeto usando **Spring 3** em **Kotlin**, já que está dentro do escopo da vaga. Decidi por não usar um banco de dados, tanto por não estar entre a demandas do exercicio, quanto para simplificar a aplicação.

Então parti para primeiro implementar uma versão inicial compativel com a API base, para depois implementar cada um dos pontos de melhoria mencionados no documento (_Rate Limit_, JWT e Cache). Implementei cada _feature_ em seu respectivo branch, e usei a função de PR do _Github_ para fazer o _merge_ das branches para a _Main_.

## Comentários

Gostaria de comentar que achei o teste muito bom, nem simples demais para não permitir uma boa avaliação técnica, nem complexo demais para demorar na implementação. Tive alguns desafios na implementação do **Token JWT** por conta que, em geral, trabalhei com provedores *OAUTH* para gerar o token cuidar do login, o mesmo vale para o **Rate Limit** que em geral fica configurado no Gateway de API, seja do provedor (AWS API Gateway por exemplo), ou em um SaaS, como o Sensedia. Sobre o uso da **Cache** fiquei na duvida entre se queriam uma cache a nivel de controle, ou apenas os cabeçalhos HTTP, no interesse de não complicar demais, fui pela segunda opção.

## Perguntas
### 1. Como você consegue fazer com que essa aplicação fique no ar através de um gerenciador de containers?
> Depende da tecnologia de containerização adotada pelo time. Já trabalhei tanto usando **Docker** com ou sem **Docker Compose**, **Amazon EBS**, **Red Hat OpenShift**, no caso dessa aplicação eu provavelmente usaria um arquivo do compose para simplificar o processo.

### 2. Como garantir uma fácil análise em caso de "troubleshooting?
> Primeiramente, eu reproduziria o erro em um ambiente de teste novo, criado especificamente para averiguar o erro. Identificando o erro e então levantando o comportamento esperado. Depois de discutir com o time a melhor forma de implementar a solução, partiria para execução, seguindo o processo definido pelo time.

### 3. Como conseguir ter observabilidade desta solução?
> Depende, caso o time já tenha uma solução, como o **NewRelic** ou **Dynatrace** por exemplo, as integraria seguindo o fluxo usual. Caso me fosse dada liberdade para implementar, provavelmente usaria alguma solução _open source_, como **Prometheus** com **ElasticSearch** e **Kibana**.