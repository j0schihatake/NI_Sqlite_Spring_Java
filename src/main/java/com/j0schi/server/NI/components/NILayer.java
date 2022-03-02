package com.j0schi.server.NI.components;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class NILayer {

    private String networkName;

    public static final String INPUT_LAYER_PREFIX = "_input_layer";

    public static final String OUTPUT_LAYER_PREFIX = "_output_layer";

    private int layerType = 0;                                           // 0 - входной, 1 - скрытый, 2 - выходной

    private String tableName = "no_name_layer_table";

    private ArrayList<NINeuron> layer = new ArrayList<NINeuron>();


    //-------------------------------- Setter and Getter:

    public int getLayerType() {
        return layerType;
    }

    public void setLayerType(int layerType) {
        this.layerType = layerType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<NINeuron> getLayer() {
        return layer;
    }

    public void setLayer(ArrayList<NINeuron> layer) {
        this.layer = layer;
    }

    //-------------------------------- Constructors:

    public NILayer(){}

    public NILayer(String tableName, ArrayList<NINeuron> neurons){
        this.tableName = tableName;
        this.layer = neurons;
    }

    public NINeuron getMax(){
        NINeuron result = null;
        for(NINeuron neuron : getLayer()){
            if(result == null || result.value < neuron.value)
                result = neuron;
        }
        return result;
    }
}
