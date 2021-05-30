### Índice
* [Arquitetura](#arquitetura)
* [Backend](#backend)
    * [Controller](#controller)
    * [Implementation](#implementation)
        * [App](#app)
            * [Batch](#batch)
            * [Database](#database)



# ARQUITETURA <a name="arquitetura"></a>
A arquitetura do projeto foi pensada para ser escalável e reutilizável. Planejamos de forma que tivéssemos menos acoplamento entre o cliente final e os serviços, para que qualquer migração ou inserção de novas funcionalidades não tenha grande impacto no projeto.

![macro_arquitetura](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/arquitetura%20macro.jpg)

# BACKEND <a name="backend"></a>

Nosso backend é divido em módulos, onde cada um possui suas próprias classes e objetos.

![macro arquitetura backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/arquitetura%20backend%20macro.jpg)

## CONTROLLER <a name="controller"></a>

Nossa camada de API possui opções HTTP para inserir novo report, buscar report por cidade, buscar todos reports e incrementar iterações like, dislike e strike

![macro controller backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/controller%20macro.jpg)

## IMPLEMENTATION <a name="implementation"></a>

Módulo responsável por toda a lógica do projeto.

![impl macro](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/implementation%20macro.jpg)

### APP <a name="app"></a>

Regra de negócio do projeto.

#### BATCH <a name="batch"></a>

Pacote responsável por ser o Job do escopo. Aqui está implementado o nosso "agendador" para chamar a operação de delete marker de 20 em 20 minutos.

![macro impl batch backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/batch_implementation_macro.jpg)

#### DATABASE <a name="database"></a>

Pacote responsável pela comunicação com nosso banco de dados. Possui objetos únicos que não deverão ser alterados!

![macro impl database backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/database%20macro.jpg)
