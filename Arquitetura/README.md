A arquitetura do projeto foi pensada para ser escalável e reutilizável. Planejamos de forma que tivéssemos menos acoplamento entre o cliente final e os serviços, para que qualquer migração ou inserção de novas funcionalidades não tenha grande impacto no projeto.

![macro_arquitetura](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/arquitetura%20macro.jpg)

# BACKEND

Nosso backend é divido em módulos, onde cada um possui suas próprias classes e objetos.

![macro arquitetura backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/arquitetura_backend_macro.jpg)

## CONTROLLER

Nossa camada de API possui opções HTTP para inserir novo report, buscar report por cidade, buscar todos reports e incrementar iterações like, dislike e strike

![macro controller backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/controller_macro.jpg)

## IMPLEMENTATION

Módulo responsável por toda a lógica da regra de negócio do projeto!

### BATCH

Pacote responsável por ser o Job do escopo. Aqui está implementado o nosso "agendador" para chamar a operação de delete marker de 20 em 20 minutos.

![macro impl batch backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/implementation_batch_macro.jpg)
