package com.j0schi.server.NI.components;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

/**
 * NISample - пример входной набор данных + заданный(известный) результат.
 */
@Data
@ToString
public class NISample {

    private String networkName;

    public static final String SAMPLE_PREFIX = "_samples";

    public String tableName = "test_nisample";

    public String description = "Набор входных данных + заданных выходных данных(пример).";

    public NILayer inputLayer = null;

    public NILayer outputLayer = null;

    //--------------------------------------- Constructors:

    public NISample(){}

    public NISample(String networkName, ArrayList<NINeuron> input, ArrayList<NINeuron> output){

        this.tableName = networkName + SAMPLE_PREFIX;

        this.inputLayer = new NILayer();
        this.outputLayer = new NILayer();

        inputLayer.setLayer(input);
        outputLayer.setLayer(output);
        outputLayer.setLayerType(2);
    }

    //--------------------------------------- Utils:

    public NISample cloneNISample(NISample sample, String description_new_sample){

        NISample returned = new NISample();

        returned.inputLayer = new NILayer();

        returned.outputLayer = new NILayer();

        returned.description = description_new_sample;

        // Клонируем каждый входной нейрон:
        for(int i = 0; i < sample.inputLayer.getLayer().size(); i++){
            NINeuron next = sample.inputLayer.getLayer().get(i);
            NINeuron nextClone = new NINeuron(next.value);
            returned.inputLayer.getLayer().add(nextClone);
        }

        // Клонируем каждый выходной нейрон:
        for(int j = 0; j < sample.outputLayer.getLayer().size(); j++){
            NINeuron next = sample.outputLayer.getLayer().get(j);
            NINeuron nextClone = new NINeuron(next.value);
            returned.outputLayer.getLayer().add(nextClone);
        }

        return returned;
    }
}
