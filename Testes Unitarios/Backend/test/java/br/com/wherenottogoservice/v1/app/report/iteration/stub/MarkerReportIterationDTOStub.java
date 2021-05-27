package br.com.wherenottogoservice.v1.app.report.iteration.stub;

import br.com.wherenottogoservice.v1.app.report.iteration.dto.MarkerReportIterationDTO;

public class MarkerReportIterationDTOStub {

    public static MarkerReportIterationDTO get() {
        return MarkerReportIterationDTO.builder()
                .id("123456")
                .build();
    }
}