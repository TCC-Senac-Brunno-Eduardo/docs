## Testes Unitários

Os testes unitários foram criados entorno dos métodos e serviços cruciais para o funcionamento da aplicação!


# BACKEND

![completed](https://user-images.githubusercontent.com/26313713/119911794-6e03a480-bf30-11eb-813e-8d5c74232476.png)

### ReportServiceTest

```java
@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private MarkerReportRepository reportRepository;

    @Mock
    private ReportValidator validator;

    @Mock
    private RabbitProducer producer;

    @Mock
    private ReportProperty properties;

    @InjectMocks
    private ReportService service;

    @Test
    void findReportMarkerByCity() {
        List<MarkerReportDTO> expected = MarkerReportDTOStub.getList();
        when(reportRepository.findAllByCity(anyString())).thenReturn(MarkerReportEntityStub.get());
        List<MarkerReportDTO> response = service.findReportMarker("city");
        assertEquals(expected, response);
        verify(reportRepository).findAllByCity(anyString());
    }

    @Test
    void findReportMarkerByCityError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao buscar marcadores por cidade: Erro de conexao com o banco";
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).findAllByCity(any());
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () ->
                service.findReportMarker("city"));
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
    }

    @Test
    void findReportMarker() {
        List<MarkerReportDTO> expected = MarkerReportDTOStub.getList();
        when(reportRepository.findAll()).thenReturn(MarkerReportEntityStub.get());
        List<MarkerReportDTO> response = service.findReportMarker();
        assertEquals(expected, response);
        verify(reportRepository).findAll();
    }

    @Test
    void findReportMarkerError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao buscar todos os marcadores: Erro de conexao com o banco";
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).findAll();
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () ->
                service.findReportMarker());
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
    }

    @Test
    void insertReport() {
        when(reportRepository.save(any())).thenReturn(MarkerReportEntityStub.getObject());
        service.insertReport(MarkerReportDTOStub.get());
        verify(reportRepository).save(any());
        verify(producer).sendMessage(any());
    }

    @Test
    void insertReportError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao inserir novo marcador no banco de dados: Erro de conexao com o banco";
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).save(any());
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () ->
                service.insertReport(MarkerReportDTOStub.get()));
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
    }
}

```

### ReportIterationServiceTest

```java
@ExtendWith(MockitoExtension.class)
class ReportIterationServiceTest {


    @Mock
    private ReportIterationValidator validator;

    @Mock
    private MarkerReportRepository reportRepository;

    @Mock
    private ReportIterationProperty properties;

    @Mock
    private DeleteReportService deleteReportService;

    @Mock
    private RabbitProducer producer;

    @InjectMocks
    private ReportIterationService service;

    @Test
    void likeReport() {
        when(properties.getLike()).thenReturn("like");
        when(validator.verifyMarker(any(), anyString())).thenReturn(MarkerReportDTOStub.get());
        when(reportRepository.save(any())).thenReturn(MarkerReportEntityStub.getObject());
        when(properties.getUpdateMessage()).thenReturn("update");
        when(properties.getReportRabbitUri()).thenReturn("uri");
        when(properties.getReportRabbitQueue()).thenReturn("queue");
        service.likeReport(MarkerReportIterationDTOStub.get());
        verify(properties).getLike();
        verify(validator).verifyMarker(any(), anyString());
        verify(reportRepository).save(any());
        verify(properties).getReportRabbitQueue();
        verify(properties).getReportRabbitUri();
        verify(producer).sendMessage(any());
    }

    @Test
    void likeReportWithError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao atualizar iteracao like para marcador com id 1: Erro de conexao com o banco";
        when(properties.getLike()).thenReturn("like");
        when(validator.verifyMarker(any(), anyString())).thenReturn(MarkerReportDTOStub.get());
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).save(any());
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () ->
                service.likeReport(MarkerReportIterationDTOStub.get()));
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
        verify(properties).getLike();
        verify(validator).verifyMarker(any(), anyString());
        verify(reportRepository).save(any());
    }

    @Test
    void dislikeReport() {
        when(properties.getDislike()).thenReturn("dislike");
        when(validator.verifyMarker(any(), anyString())).thenReturn(MarkerReportDTOStub.get());
        when(reportRepository.save(any())).thenReturn(MarkerReportEntityStub.getObject());
        when(properties.getUpdateMessage()).thenReturn("update");
        when(properties.getReportRabbitUri()).thenReturn("uri");
        when(properties.getReportRabbitQueue()).thenReturn("queue");
        service.dislikeReport(MarkerReportIterationDTOStub.get());
        verify(properties).getDislike();
        verify(validator).verifyMarker(any(), anyString());
        verify(reportRepository).save(any());
        verify(properties).getReportRabbitQueue();
        verify(properties).getReportRabbitUri();
        verify(producer).sendMessage(any());
    }

    @Test
    void dislikeReportWithError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao atualizar iteracao dislike para marcador com id 1: Erro de conexao com o banco";
        when(properties.getDislike()).thenReturn("dislike");
        when(validator.verifyMarker(any(), anyString())).thenReturn(MarkerReportDTOStub.get());
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).save(any());
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () ->
                service.dislikeReport(MarkerReportIterationDTOStub.get()));
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
        verify(properties).getDislike();
        verify(validator).verifyMarker(any(), anyString());
        verify(reportRepository).save(any());
    }

    @Test
    void strikeReport() {
        when(properties.getStrike()).thenReturn("strike");
        when(validator.verifyMarker(any(), anyString())).thenReturn(MarkerReportDTOStub.get());
        when(reportRepository.save(any())).thenReturn(MarkerReportEntityStub.getObject());
        when(validator.deleteMarker(any())).thenReturn(true);
        service.strikeReport(MarkerReportIterationDTOStub.get());
        verify(properties).getStrike();
        verify(validator).verifyMarker(any(), anyString());
        verify(reportRepository).save(any());
        verify(validator).deleteMarker(any());
        verify(reportRepository).deleteById(any());
        verify(deleteReportService).sendToQueue(any());
    }

    @Test
    void strikeReportWithError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao atualizar iteracao strike para marcador com id 1: Erro de conexao com o banco";
        when(properties.getStrike()).thenReturn("strike");
        when(validator.verifyMarker(any(), anyString())).thenReturn(MarkerReportDTOStub.get());
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).save(any());
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () ->
                service.strikeReport(MarkerReportIterationDTOStub.get()));
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
        verify(properties).getStrike();
        verify(validator).verifyMarker(any(), anyString());
        verify(reportRepository).save(any());
    }
}
```

### DeleteReportServiceTest (Job)

```java
@ExtendWith(MockitoExtension.class)
class DeleteReportServiceTest {

    @Mock
    private MarkerReportRepository reportRepository;

    @Mock
    private ReportValidator validator;

    @Mock
    private DeleteReportProperty properties;

    @Mock
    private RabbitProducer producer;

    @InjectMocks
    private DeleteReportService service;

    @Test
    void execute() {
        when(reportRepository.findAll()).thenReturn(MarkerReportEntityStub.get());
        when(validator.markerAvailableForDeletion(any())).thenReturn(true);
        when(properties.getMessage()).thenReturn("message");
        when(properties.getReportRabbitUri()).thenReturn("uri");
        when(properties.getReportRabbitQueue()).thenReturn("queue");
        service.execute();
        verify(reportRepository).findAll();
        verify(validator).markerAvailableForDeletion(any());
        verify(reportRepository).delete(any());
        verify(properties).getMessage();
        verify(properties).getMessage();
        verify(properties).getReportRabbitUri();
        verify(properties).getReportRabbitQueue();
        verify(producer).sendMessage(any());
    }

    @Test
    void executeWithError() {
        String expectedError = "Algo inesperado aconteceu. Tente novamente daqui a pouco!";
        String expectedCause = "Erro ao executar delecao de marcadores: Erro ao deletar marcador no banco de dados: " +
                MarkerReportDTOStub.get() + " - Erro de conexao com o banco";
        when(reportRepository.findAll()).thenReturn(MarkerReportEntityStub.get());
        when(validator.markerAvailableForDeletion(any())).thenReturn(true);
        doThrow(new DataAccessException("Erro de conexao com o banco") {
        }).when(reportRepository).delete(any());
        ExceptionInternalError error = assertThrows(ExceptionInternalError.class, () -> service.execute());
        assertEquals(expectedError, error.getMessage());
        assertEquals(expectedCause, error.getCause().getMessage());
        verify(reportRepository).findAll();
        verify(validator).markerAvailableForDeletion(any());
        verify(reportRepository).delete(any());
    }
}
```
