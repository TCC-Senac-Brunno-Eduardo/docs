package br.com.wherenottogoservice.v1.app.report.service;

import br.com.wherenottogoservice.v1.app.report.database.MarkerReportRepository;
import br.com.wherenottogoservice.v1.app.report.dto.MarkerReportDTO;
import br.com.wherenottogoservice.v1.app.report.property.ReportProperty;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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