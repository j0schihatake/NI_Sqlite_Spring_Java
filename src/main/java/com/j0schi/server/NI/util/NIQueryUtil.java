package NI.util;

import NI.model.NILayer;
import NI.model.NINetwork;
import NI.model.NINeuron;
import NI.model.NISample;

public class NIQueryUtil {

    //----------------------------------- INSERT:

    public String insertNINetwork(NINetwork network){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.network ( name, description, table_name, input_neurons, hidden_neurons, output_neurons, dest, hidden_layer_count, learn_rate) values (");
        query.append("'").append(network.getName()).append("', ")
                .append("'").append(network.getDescription()).append("', ")
                .append("'").append(network.getTableName()).append("', ")
                .append("'").append(network.getINPUT_NEURONS()).append("', ")
                .append("'").append(network.getHIDDEN_NEURONS()).append("', ")
                .append("'").append(network.getOUTPUT_NEURONS()).append("', ")
                .append("'").append(network.getDest()).append("', ")
                .append("'").append(network.getHIDDEN_LAYER_COUNT()).append("', ")
                .append("'").append(network.getLEARN_RATE()).append("');");
        return query.toString();
    }

    public String insertNISample(NISample sample){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.sample ( network_name, sample_name, table_name, description) values (");
        query.append("'").append(sample.getNetworkName()).append("', ")
                .append("'").append(sample.getSampleName()).append("', ")
                .append("'").append(sample.getTableName()).append("', ")
                .append("'").append(sample.getDescription()).append("');");
        return query.toString();
    }

    public String insertNILayer(NILayer layer){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.layer ( network_name, sample_name, layer_type, table_name) values (");
        query.append("'").append(layer.getNetworkName()).append("', ")
                .append("'").append(layer.getSampleName()).append("', ")
                .append("'").append(layer.getLayerType()).append("', ")
                .append("'").append(layer.getTableName()).append("');");
        return query.toString();
    }

    public String insertNINeuron(NINeuron neuron){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.layer ( network_name, sample_name, layer_type, value) values (");
        query.append("'").append(neuron.getNetworkName()).append("', ")
                .append("'").append(neuron.getSampleName()).append("', ")
                .append("'").append(neuron.getLayerType()).append("', ")
                .append("'").append(neuron.getValue()).append("');");
        return query.toString();
    }

    //----------------------------------- SELECT:
    public String selectAllNINetworks(){
        return new StringBuilder().append("select * from network;").toString();
    }

    public String selectAllNISample(){
        return new StringBuilder().append("select * from sample;").toString();
    }

    public String selectAllNILayer(){
        return new StringBuilder().append("select * from layer;").toString();
    }

    public String selectAllNINeuron(){
        return new StringBuilder().append("select * from neuron;").toString();
    }

    public String selectNINetworkByNetworkName(String networkName){
        return new StringBuilder().append("select * from network where network_name = '").append(networkName).append("';").toString();
    }

    public String selectAllNISampleByNetworkName(String networkName){
        return new StringBuilder().append("select * from sample where network_name = '").append(networkName).append("';").toString();
    }

    public String selectAllNISampleByNetworkNameAndSampleName(String networkName, String sampleName){
        return new StringBuilder().append("select * from sample where network_name = '").append(networkName).append("' and sample_name = ").append(sampleName).append("';").toString();
    }

    public String selectNILayerByNetworkName(String networkName, String sampleName, int layerType){
        return new StringBuilder().append("select * from layer where network_name = '").append(networkName).append("' and sample_name = ").append(sampleName).append("' and layer_type = '").append(layerType).append("';").toString();
    }

    public String selectAllNILayersByNetworkNameAndSampleName(String networkName, String sampleName){
        return new StringBuilder().append("select * from layer where network_name = '").append(networkName).append("' and sample_name = ").append(sampleName).append("';").toString();
    }

    public String selectAllNINeuronByNetworkNameAndLayerId(String networkName, String sampleName, int layerId){
        return new StringBuilder().append("select * from neuron where network_name = '").append(networkName).append("' and sample_name = '").append(sampleName).append("' and layer_id = '").append(layerId).append("';").toString();
    }

}
