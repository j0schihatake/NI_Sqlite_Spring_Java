package com.j0schi.server.NI.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * NISample - пример входной набор данных + заданный(известный) результат.
 */
@Data
@ToString
public class NISample {

    public static final String SAMPLE_PREFIX = "_samples";

    private int pk;

    private String networkName;

    private String sampleName;

    public String tableName = "test_nisample";

    public String description = "Набор входных данных + заданных выходных данных(пример).";

    public NILayer inputLayer = null;

    public NILayer outputLayer = null;

    public List<NILayer> layer = new ArrayList<>();

    //--------------------------------------- Constructors:

    public NISample(){}

    public NISample(String networkName, ArrayList<NINeuron> input, ArrayList<NINeuron> output){

        this.tableName = networkName + SAMPLE_PREFIX;

        this.inputLayer = new NILayer();
        this.outputLayer = new NILayer();

        inputLayer.setNeurons(input);
        outputLayer.setNeurons(output);
        outputLayer.setLayerType(2);
    }

    //--------------------------------------- Utils:

    public NISample cloneNISample(NISample sample, String description_new_sample){

        NISample returned = new NISample();

        returned.inputLayer = new NILayer();

        returned.outputLayer = new NILayer();

        returned.description = description_new_sample;

        // Клонируем каждый входной нейрон:
        for(int i = 0; i < sample.inputLayer.getNeurons().size(); i++){
            NINeuron next = sample.inputLayer.getNeurons().get(i);
            NINeuron nextClone = new NINeuron(next.getValue());
            returned.inputLayer.getNeurons().add(nextClone);
        }

        // Клонируем каждый выходной нейрон:
        for(int j = 0; j < sample.outputLayer.getNeurons().size(); j++){
            NINeuron next = sample.outputLayer.getNeurons().get(j);
            NINeuron nextClone = new NINeuron(next.getValue());
            returned.outputLayer.getNeurons().add(nextClone);
        }

        return returned;
    }
}
