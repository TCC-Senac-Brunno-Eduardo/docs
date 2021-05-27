package br.com.wherenottogoservice.v1.app.report.stub;

import br.com.wherenottogoservice.v1.app.report.database.entity.MarkerReportEntity;
import br.com.wherenottogoservice.v1.app.report.database.entity.MarkerReportIterationEntity;

import java.util.Collections;
import java.util.List;

public class MarkerReportEntityStub {

    public static List<MarkerReportEntity> get() {
        return Collections.singletonList(MarkerReportEntity.builder()
                .city("city")
                .description("description")
                .latitude(1D)
                .longitude(1D)
                .id("1")
                .title("title")
                .iteration(MarkerReportIterationEntity.builder()
                        .like(24)
                        .dislike(2)
                        .strike(0)
                        .build())
                .build());
    }

    public static MarkerReportEntity getObject() {
        return MarkerReportEntity.builder()
                .city("city")
                .description("description")
                .latitude(1D)
                .longitude(1D)
                .id("1")
                .title("title")
                .iteration(MarkerReportIterationEntity.builder()
                        .like(24)
                        .dislike(2)
                        .strike(0)
                        .build())
                .build();
    }
}