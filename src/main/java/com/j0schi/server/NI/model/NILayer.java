package NI.model;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class NILayer {

    private int pk;

    private int layerId;                                                // порядковый номер слоя слева направо: 0.1.2...

    public static final String INPUT_LAYER_PREFIX = "_input_layer";

    public static final String OUTPUT_LAYER_PREFIX = "_output_layer";

    private String networkName;

    private String sampleName;

    private int layerType = 0;                                           // 0 - входной, 1 - скрытый, 2 - выходной

    private String tableName = "no_name_layer_table";

    private List<NINeuron> layer = new ArrayList<>();

    //-------------------------------- Constructors:

    public NILayer(){}

    public NILayer(String tableName, ArrayList<NINeuron> neurons){
        this.tableName = tableName;
        this.layer = neurons;
    }

    //--------------------------------------- Utils:

    public NINeuron getMax(){
        NINeuron result = null;
        for(NINeuron neuron : getLayer()){
            if(result == null || result.getValue() < neuron.getValue())
                result = neuron;
        }
        return result;
    }
}
