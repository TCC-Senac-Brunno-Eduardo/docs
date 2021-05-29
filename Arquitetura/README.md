A arquitetura do projeto foi pensada para ser escalável e reutilizável. Planejamos de forma que tivéssemos menos acoplamento entre o cliente final e os serviços, para que qualquer migração ou inserção de novas funcionalidades não tenha grande impacto no projeto.

![macro_arquitetura](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/arquitetura%20macro.jpg)

# BACKEND

Nosso backend é divido em módulos, onde cada um possui suas próprias classes e objetos.

![macro arquitetura backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/arquitetura_backend_macro.jpg)

### CONTROLLER

Nossa camada de API possui opções HTTP para inserir novo report, buscar report por cidade, buscar todos reports e incrementar iteração like | dislike | strike

![macro controller backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/controller_macro.jpg)
