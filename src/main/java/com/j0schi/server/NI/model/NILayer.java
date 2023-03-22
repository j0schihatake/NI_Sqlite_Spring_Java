package com.j0schi.server.NI.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder=true)
public class NILayer {

    private int layerId;                                                // порядковый номер слоя слева направо: 0.1.2...

    private String networkName;

    private String sampleName;

    private int layerType = 0;                                           // 0 - входной, 1 - скрытый, 2 - выходной

    private String tableName = "no_name_layer_table";

    private List<NINeuron> neurons = new ArrayList<>();

    //-------------------------------- Constructors:

    public NILayer(String tableName, ArrayList<NINeuron> neurons){
        this.tableName = tableName;
        this.neurons = neurons;
    }

    public NILayer dublicate(){
        NILayer dublicateLayer = new NILayer();
        dublicateLayer.setLayerType(this.getLayerType());
        dublicateLayer.setLayerId(this.getLayerId());
        dublicateLayer.setNetworkName(this.getNetworkName());
        dublicateLayer.setTableName(this.getTableName());
        dublicateLayer.setSampleName(this.getSampleName());
        for(int i = 0; i < neurons.size(); i++){
            dublicateLayer.neurons.add(neurons.get(i).dublicate());
        }
        return dublicateLayer;
    }

    //--------------------------------------- Utils:

    public int getMax(){
        NINeuron resultNeuron = null;
        int result = 0;
        for(int i = 0; i < this.getNeurons().size(); i++ ){
            NINeuron neuron  = this.getNeurons().get(i);
            if(resultNeuron == null || resultNeuron.getValue() < neuron.getValue()) {
                resultNeuron = neuron;
                result = i;
            }
        }
        return result;
    }

    public NILayer getRandomValue(){
        NILayer result = dublicate();
        for (NINeuron neuron: result.getNeurons()) {
            //neuron.setValue();
        }
        return result;
    }
}
