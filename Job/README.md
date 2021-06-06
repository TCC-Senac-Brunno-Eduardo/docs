Job, como o nome já diz, é uma tarefa que será executada! No nosso projeto, optamos por automatizar a limpeza dos marcadores de aglomerações antigos.

Temos um agendador marcado para ser executado a cada 20 minutos, durante todos os dias da semana. Ao ser disparado, ele executa uma task (tarefa) que realiza uma busca de todos os marcadores existentes em nosso sistema e para cada um validamos sua data de criação!

Um  marcador que está em nosso sistema há mais de 6 horas é um **marcador apto a ser excluído**, sendo assim, realizamos a exclusão do mesmo e enviamos uma mensagem para queue, dizendo que há marcadores que foram removidos da nossa base, assim, todos os clients serão atualizados em tempo real!


## CÓDIGO

### Passo a passo

Tudo se inicia com o seu agendamento, onde está setado para ser executdo de 20 em 20 minutos:

```java
public class ReportScheduled {

    private final JobLauncher jobLauncher;
    private final ReportJob reportJob;

    @Scheduled(cron = "0 */20 * ? * *")
    public void deleteReportScheduled() {
        log.info("REPORT - delete report job started");
        try {
            jobLauncher.run(reportJob.deleteReportJob(),
                    new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
        } catch (Exception e) {
            log.info("REPORT - delete report job error: {} - {}", e.getMessage(), String.valueOf(e));
        }
        log.info("REPORT - delete report job successfully completed");
    }
}

```

Após ser acionado, chamamos nosso job:

```java
public class ReportJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final ReportStep reportStep;

    @Bean
    public Job deleteReportJob() {
        return jobBuilderFactory
                .get("deleteReportJob")
                .incrementer(new RunIdIncrementer())
                .start(reportStep.deleteReportStep())
                .build();
    }
}
```

Com isso, o job chama sua unidade de processamento, o step:

```java
public class ReportStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final ReportTasklet reportTasklet;

    @Bean
    public Step deleteReportStep() {
        return stepBuilderFactory.get("deleteReportStep")
                .tasklet(reportTasklet.deleteReportTasklet())
                .build();
    }
}
```

Por fim, o step faz a ligação com a task (tarefa):

```java
public class ReportTasklet implements Tasklet {

    private final DeleteReportService service;

    @Bean
    public ReportTasklet deleteReportTasklet() {
        return new ReportTasklet(service);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        service.execute();
        return RepeatStatus.FINISHED;
    }
}
```

E é essa tarefa que irá realizar a chamada do método a ser executado:

```java
public class DeleteReportService {

    private final MarkerReportRepository reportRepository;
    private final ReportValidator validator;
    private final DeleteReportProperty properties;
    private final RabbitProducer producer;

    public void execute() {
        try {
            List<MarkerReportDTO> listMarkerToDelete = getMarkers();
            if (!ObjectUtils.isEmpty(listMarkerToDelete)) {
                listMarkerToDelete.forEach(this::removeForDatabase);
                sendToQueue(listMarkerToDelete);
            }
        } catch (Exception ex) {
            log.error("Erro ao executar delecao de marcadores: {}", ex.getMessage());
            throw new ExceptionInternalError("Erro ao executar delecao de marcadores: " + ex.getMessage());
        }
    }
```

## ARQUITETURA

![macro impl batch backend](https://github.com/TCC-Senac-Brunno-Eduardo/docs/blob/master/Arquitetura/batch_implementation_macro.jpg)
