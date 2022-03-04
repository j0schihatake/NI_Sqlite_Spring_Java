package com.j0schi.server.NI.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NISample - пример входной набор данных + заданный(известный) результат.
 */
@Data
@ToString
@Builder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class NISample {

    public static final String SAMPLE_PREFIX = "_samples";

    private String networkName;

    private String sampleName;

    public String tableName = "test_nisample";

    public String description = "Набор входных данных + заданных выходных данных(пример).";

    public List<NILayer> layer = new ArrayList<>();
}
