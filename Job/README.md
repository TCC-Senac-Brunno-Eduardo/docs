Job, como o nome já diz, é uma tarefa que será executada! No nosso projeto, optamos por automatizar a limpeza dos marcadores de aglomerações antigos.

Temos um agendador marcado para ser executado a cada 20 minutos, durante todos os dias da semana. Ao ser disparado, ele executa uma task (tarefa) que realiza uma busca de todos os marcadores existentes em nosso sistema e para cada um validamos sua data de criação!

Um  marcador que está em nosso sistema há mais de 6 horas é um **marcador apto a ser excluído**, sendo assim, realizamos a exclusão do mesmo e enviamos uma mensagem para queue, dizendo que há marcadores que foram removidos da nossa base, assim, todos os clients serão atualizados em tempo real!


## ARQUITETURA

![macro impl batch backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/batch_implementation_macro.jpg)

## CÓDIGO

