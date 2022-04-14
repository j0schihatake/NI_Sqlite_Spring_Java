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

    public NILayer clone(){
        NILayer clone = new NILayer();
        clone.setLayerType(this.getLayerType());
        clone.setLayerId(this.getLayerId());
        clone.setNetworkName(this.getNetworkName());
        clone.setTableName(this.getTableName());
        clone.setSampleName(this.getSampleName());
        clone.setNeurons(this.getNeurons());
        return clone;
    }

    //--------------------------------------- Utils:

    public NINeuron getMax(){
        NINeuron result = null;
        for(NINeuron neuron : this.getNeurons()){
            if(result == null || result.getValue() < neuron.getValue())
                result = neuron;
        }
        return result;
    }

    public NILayer getRandomValue(){
        NILayer result = clone();
        for (NINeuron neuron: result.getNeurons()) {
            //neuron.setValue();
        }
        return result;
    }
}
