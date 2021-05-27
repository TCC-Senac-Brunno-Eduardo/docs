package br.com.wherenottogoservice.v1.app.report.delete.service;

import br.com.wherenottogoservice.v1.app.report.database.MarkerReportRepository;
import br.com.wherenottogoservice.v1.app.report.delete.property.DeleteReportProperty;
import br.com.wherenottogoservice.v1.app.report.stub.MarkerReportDTOStub;
import br.com.wherenottogoservice.v1.app.report.stub.MarkerReportEntityStub;
import br.com.wherenottogoservice.v1.app.report.validator.ReportValidator;
import br.com.wherenottogoservice.v1.common.exception.ExceptionInternalError;
import br.com.wherenottogoservice.v1.rabbitmq.producer.RabbitProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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