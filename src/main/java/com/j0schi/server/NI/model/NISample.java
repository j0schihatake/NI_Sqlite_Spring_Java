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

    public NISample dublicate(){

        NISample dublicate = new NISample();
        dublicate.tableName = tableName;
        dublicate.description = description;
        dublicate.sampleName = sampleName;
        dublicate.networkName = networkName;

        ArrayList<NILayer> dublicateLayers = new ArrayList<NILayer>();
        for(int i = 0; i < layer.size(); i++){
            dublicate.layer.add(layer.get(i).dublicate());
        }
        return dublicate;
    }

    public NILayer input(){
        return layer.get(0);
    }

    public NILayer output(){
        return layer.get(1);
    }
}
