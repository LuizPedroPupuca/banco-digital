# Banco digital

## Code review:
- **Funcionamento:**
  - A aplicação não está iniciando devido a erros de código (por exemplo, falta de return na classe CorrentistaService);
  - Na classe AuthController vi que foi utilizada a annotation `@Valid` para validar os DTO de entrada representado pela classe CredenciaisRequest, porém nela não foi utilizada nenhuma annotation
  para verificar se os campos obrigatórios foram enviados com strings valida.
  - No método autenticarCliente da classe AuthFilter, vi que é realizado um get no optional do correntista sem antes realizar a verificação se optional está realmente preenchido. Faltou tratar o caso
  no qual não é encontrado o correntista no banco.
  - No método consulta da classe SqlCorrentistaAdapter, está sendo retornada uma exception do próprio spring caso não seja encontrado um correntista, mas faltou também logar as o que está ocorrendo. Uma
  ideia bacana, seria ter um RestControllerAdvice para centralizar o tratamento de exceptions na sua aplicação. Você consegue inclusive padronizar a realização dos logs com essa estratégia.
  - No método consulta da classe SqlCorrentistaAdapter, daria para utilizar paginação para evitar sobrecarregar a memória da aplicação trazendo todos os correntistas salvos do banco. Além disso, por que
  utilizar List como retorno desse método ao invés de Set? Set funcionária bem, uma vez que não vai existir correntistas repetidos.
  - Como garante que cada classe gerada funciona sem testes unitários e de controller do spring? Testes são extremamente importantes, pois é muito mais rápido e barato testar uma classe unitáriamente do que
  iniciar a aplicação, esperar o tomcat subir, abrir insomnia, configurar a request, esperar o retorno e avaliar o resultado.
  - Não é recomendado armazenar valores sensiveis na aplicação. Vi que o seu application.properties tem o jwt.secret e a senha do banco expostas! Daria para esconder essas propriedades com variaveis de ambiente,
  assim quem fosse rodar a aplicação, só precisaria declarar essa variavel.
  - Vi que no application.properties você deixou o próprio hibernate criar as tabelas quando a aplicação iniciar. Para aplicações produtivas, isso não é recomendado, uma vez que não é possível ter um controle fino
  da criação, atualização e deleção das tabelas. A recomendação é utilizar migrations através de libs como liquibase ou flyway para fazer esse tipo de trabalho.
  - Por que não criar uma documentação da aplicação com o springdocs? Seria legal ter a documentação de cada endpoint, quais os parametros obrigatórios e etc.


- **Clean code:**
  - Vi que na classe CredenciaisRequest foram gerados os métodos getters e o construtor, porém com o Java é possível evitar boderplaite com o uso do [Lombok](https://projectlombok.org/).
  Se usado corretamente, o lombok pode deixar seu código mais limpo, dando o enfoque apenas no que importa.
  - Na classe AuthController, vi que as injeções de dependencias são realizadas utilizando a annotation `@Autowired`, porém com o Lombok conseguimos fazer essa injeção de modo mais limpo.
  Para isso basta anotar a classe com AllArgsConstructor(Essa annotation gera um construtor com todos os atributos marcados como `final`) e o próprio spring se encarrega de fazer as injeções.
  - Por que manter código comentado? Vi que no pom.xml e em outras classes existem códigos comentados, e se estão comentados não vão ser executados, o que não traz um ganho para aplicação, mas por
  outro lado atrapalha na legibilidade.
  - Na classe AuthController, o retorno é realizado utilizando o ResponseEntity do spring, porém esse tipo de encapsulamento não é obrigatório. Com o spring boot conseguimos dar um retorno apenas
  na variavel a ser devolvida para o client.
  - Na classe AuthFilter, existe o método principal doFilterInternal e vi que ele chama dois outros métodos privados (autenticarCliente e getToken). Uma boa prática de desenvolvimento é escrever
  o código como se fosse uma manchete de jornal. Os métodos públicos (nesse caso temos apenas método protected como principal) devem estar logo abaixo dos atributos da classe, e os métodos
  privados seriam os detalhes do texto do nosso jornal, que se caso o leitor tenha interesse, vai olhar com mais cuidado sua implementação. Sendo assim, para não perder o fluxo da leitura, é legal
  manter a mesma ordem na qual os métodos são utilizados com a sua declaração na classe. Se no doFilterInternal foi utilizado o método getToken, e logo após o autenticarCliente, então logo após o 
  termino do doFilterInternal, deveria estar a implementação do getToken e logo após o autenticarCliente.
  - Na classe AuthFilter, vi que o limite de 120 caracteres não foi respeitado. É uma boa prática definir um styleguide para limitar quantidade de caracteres por linha, quantas linhas em branco antes
  de iniciar um novo método ou finalizar uma classe, espaçamento e etc. Existem alguns stylesguides conhecidos para o intellij, exemplo [google](https://google.github.io/styleguide/intellij-java-google-style.xml)
  - No método cadastraCorrentista da classe CorrentistaController, vi que tem um parametro que não é utilizado (uriBuilder), daria para remove-lo. Além disso, nesse método foi utilizado uma variavel com nome "r",
  mesmo para lambdas e forEach é uma boa prática colocar nomes relevantes (sem abreviações) para as variaveis, isso facilita a leitura e uma prática fácil de adquirir.
  - Na classe NovoCorrentistaRequest, vi que foi declarado um atributo com o nome "documento". Qual seria o documento? Por que não utilizar cpf como nome? Na mesma classe, vi que foram utilizadas as annotation
  para validar se os atributos estão preenchidos, mas faltou colocar uma mensagem de erro, caso alguma annotation de validação seja violada.
  - Uma boa prática é limitar a quantidade de linhas por classe e método, por exemplo na classe CorrentistaService, que está com mais de 100 linhas de código! Imaginou um desenvolvedor fazendo code review dessa classe?
  Código extenso pode indicar violação do SOLID ou até mesmo falta de clareza do código e nesse caso está ocorrendo a violação do Single Responsability do SOLID.
  - Na classe SqlCorrentistaAdapter, existe o método `salvaTudo`. Me pergunto, salva tudo o que? Por que não especificar melhor o nome do método, ou deixar apenas salvar? Já que ele recebe uma lista de correntistas, imagino
  que a ideia seria salvar todos correntistas da lista.
  - Essa é mais uma dica e não uma critica, não acha que a leitura do seu código ficaria mais fluída se ele fosse todo em inglês?
  - Uma boa prática quando usando streams é quebra-las por cada ação executada. Se vou aplicar stream em uma lista de objetos, para executar um map e logo após um collect, é uma boa quebrar linha em cada uma das ações executadas.


- **Arquitetura da aplicação:**
  - Vi que na classe NovoCorrentistaRequest foi utilizada a interface CadastraCorrentistaRequest que obriga a implementação do método `paraCorrentista`. Esse método é utilizado pela CorrentistaService para salvar
  um novo registro no banco. Se formos pensar no hexagonal archicture/ports & adapters archicture/clean archicture/onion archicture (todos fazem a mesma coisa) esse trecho não respeita as fronteiras do sistema,
  pois temos um DTO (NovoCorrentistaRequest) conhecendo detalhes do model do banco de dados (Correntista). Além disso, está sendo também infringido principio da Single Responsibility (Responsabilidade Única do SOLID),
  pois a classe NovoCorrentistaRequest tem a responsabilidade de traduzir para a classe Correntista e servir como DTO para obter os dados enviados pela request.
  - Vi que existem várias services na aplicação, e pelo o que entendi elas são as orquestradoras das regras de negócio. Na classe CorrentistaService, por exemplo, temos diversas regras de negócio diferentes sendo tratadas,
  o que viola o Single Responsability e outros principios do SOLID, pois ela lida com regras do correntista, da conta poupança, conta de investimento, cartão temporário, bloqueia cartão além de gerar fatura. O ideal seria
  ter services especificas para cada regra de negócio, por exemplo, uma service para criar correntista, outra para busca, e assim por diante.
  - Nas classe dentro do pacote de domain (Correntista e CorrentistaService por exemplo) estão com annotation do Spring, e isso viola um dos principios do hexagonal archicture, que fala que a aplicação deve ter uma camada
  para dependencias externas, libs e framework, e outra camada separada para conter as regras de negócio da aplicação (java puro na maioria das vezes). Esse principio serve para proteger as regras de negócio da sua aplicação
  de mudanças de libs e frameworks. Por que uma classe de dominio que contém as regras de negócio do correntista deveria ser alterado se eu resolver trocar o banco de dados ou o framework da aplicação? O hexagonal archicture
  deveria isolar essas camadas da aplicação, sem que uma influencie na outra.
  - Quando falamos no padrão de projeto adapter que é utilizado no hexagonal architecture, ele consiste em converter uma interface de código em outra para que o client (classe que vai utilizar o adapter) consiga realizar a
  comunicação com a classe alvo (adaptee), e quem faz essa conversão é a classe adapter. No seu código, eu vi que a classe SqlCorrentistaAdapter, apesar do nome não é de fato um adapter, pois não é realizada nenhuma
  conversão nela. Pensando no clean arch e também no conceito do adapter, deveria ser essa classe a responsável por converter uma objeto de domínio da aplicação que iria representar um correntista para um model do banco de dados
  que iria representar a tabela do banco de dados para o correntista. No seu projeto essa conversão ficou no DTO (NovoCorrentistaRequest).
  - Outro principio do hexagonal architecture é separar as libs/dependencias da aplicação em camadas especificas. Por ter sido utilizado apenas um módulo na sua aplicação, eu consigo por exemplo criar na classe Correntista um
  endpoint rest para receber chamadas HTTP na aplicação, consigo salvar itens no banco de dados e etc. Umas das premissas do hexagonal é evitar que esse tipo de coisa aconteceça na aplicação, restringindo as dependencias nas
  camadas que tratamos regras de negócio.
  
  
## Instruções para rodar o projeto

1. Clonar o repositório para a sua máquina

```sh 
$ git clone https://github.com/zup-academy/bancodigital.git
```

2. Ir para a branch `hexagonal-tc2-lt2`

```sh
$ git checkout hexagonal-tc2-lt2
```

> O projeto está com o H2 como banco de dados, caso queria utilizar outro banco, basta adicionar a dependência e realizar as configurações no application.properties
