package br.com.wherenottogoservice.v1.app.report.stub;

import br.com.wherenottogoservice.v1.app.report.dto.MarkerReportDTO;
import br.com.wherenottogoservice.v1.app.report.dto.MarkerReportIterationDTO;

import java.util.Collections;
import java.util.List;

public class MarkerReportDTOStub {

    public static MarkerReportDTO get() {
        return MarkerReportDTO.builder()
                .city("city")
                .description("description")
                .latitude(1D)
                .longitude(1D)
                .id("1")
                .title("title")
                .iteration(MarkerReportIterationDTO.builder()
                        .like(24)
                        .dislike(2)
                        .strike(0)
                        .build())
                .build();
    }

    public static List<MarkerReportDTO> getList() {
        return Collections.singletonList(MarkerReportDTO.builder()
                .city("city")
                .description("description")
                .latitude(1D)
                .longitude(1D)
                .id("1")
                .title("title")
                .iteration(MarkerReportIterationDTO.builder()
                        .like(24)
                        .dislike(2)
                        .strike(0)
                        .build())
                .build());
    }
}