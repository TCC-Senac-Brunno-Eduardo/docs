package br.com.wherenottogoservice.v1.app.report.iteration.service;

import br.com.wherenottogoservice.v1.app.report.database.MarkerReportRepository;
import br.com.wherenottogoservice.v1.app.report.delete.service.DeleteReportService;
import br.com.wherenottogoservice.v1.app.report.iteration.property.ReportIterationProperty;
import br.com.wherenottogoservice.v1.app.report.iteration.stub.MarkerReportIterationDTOStub;
import br.com.wherenottogoservice.v1.app.report.iteration.validator.ReportIterationValidator;
import br.com.wherenottogoservice.v1.app.report.stub.MarkerReportDTOStub;
import br.com.wherenottogoservice.v1.app.report.stub.MarkerReportEntityStub;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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