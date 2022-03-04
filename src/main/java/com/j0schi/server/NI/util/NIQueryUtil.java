package com.j0schi.server.NI.util;

import com.j0schi.server.NI.model.NILayer;
import com.j0schi.server.NI.model.NINetwork;
import com.j0schi.server.NI.model.NINeuron;
import com.j0schi.server.NI.model.NISample;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NIQueryUtil {

    //----------------------------------- UPDATE:
    public String updateNINetworkQuery(NINetwork niNetwork){
        StringBuilder query = new StringBuilder();
        query.append("update main.network set name = '")
                .append(niNetwork.getName())
                .append("', description = '")
                .append(niNetwork.getDescription())
                .append("', table_name = '")
                .append(niNetwork.getTableName())
                .append("', input_neurons = '")
                .append(niNetwork.getINPUT_NEURONS())
                .append("', hidden_neurons = '")
                .append(niNetwork.getHIDDEN_NEURONS())
                .append("', output_neurons = '")
                .append(niNetwork.getOUTPUT_NEURONS())
                .append("', dest = '")
                .append(niNetwork.getDest())
                .append("', hidden_layer_count = '")
                .append(niNetwork.getHIDDEN_LAYER_COUNT())
                .append("', learn_rate = '")
                .append(niNetwork.getLEARN_RATE())
                .append("' where name = '")
                .append(niNetwork.getName())
                .append("';");
        return query.toString();
    }

    public String updateNISampleQuery(NISample niSample){
        StringBuilder query = new StringBuilder();
        query.append("update main.sample set network_name = '")
                .append(niSample.getNetworkName())
                .append("', sample_name = '")
                .append(niSample.getSampleName())
                .append("', table_name = '")
                .append(niSample.getTableName())
                .append("', description = '")
                .append(niSample.getDescription())
                .append("' where network_name = '")
                .append(niSample.getNetworkName())
                .append("' and sample_name = '")
                .append(niSample.getSampleName())
                .append("';");
        return query.toString();
    }

    public String updateNILayerQuery(NILayer niLayer){
        StringBuilder query = new StringBuilder();
        query.append("update main.layer set network_name = '")
                .append(niLayer.getNetworkName())
                .append("', sample_name = '")
                .append(niLayer.getSampleName())
                .append("', layer_type = '")
                .append(niLayer.getLayerType())
                .append("', table_name = '")
                .append(niLayer.getTableName())
                .append("' where network_name = '")
                .append(niLayer.getNetworkName())
                .append("' and sample_name = '")
                .append(niLayer.getSampleName())
                .append("' and layer_id = '")
                .append(niLayer.getLayerId())
                .append("';");
        return query.toString();
    }

    public String updateNINeuronQuery(NINeuron niNeuron){
        StringBuilder query = new StringBuilder();
        query.append("update main.neuron set network_name = '")
                .append(niNeuron.getNetworkName())
                .append("', sample_name = '")
                .append(niNeuron.getSampleName())
                .append("', layer_type = '")
                .append(niNeuron.getLayerType())
                .append("', value = '")
                .append(niNeuron.getValue())
                .append("' where network_name = '")
                .append(niNeuron.getNetworkName())
                .append("' and sample_name = '")
                .append(niNeuron.getSampleName())
                .append("' and layer_id = '")
                .append(niNeuron.getLayerId())
                .append("';");
        return query.toString();
    }

    //----------------------------------- INSERT:

    public String insertNINetworkQuery(NINetwork network){
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

    public String insertNISampleQuery(NISample sample){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.sample ( network_name, sample_name, table_name, description) values (");
        query.append("'").append(sample.getNetworkName()).append("', ")
                .append("'").append(sample.getSampleName()).append("', ")
                .append("'").append(sample.getTableName()).append("', ")
                .append("'").append(sample.getDescription()).append("');");
        return query.toString();
    }

    public String insertNILayerQuery(NILayer layer){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.layer ( network_name, sample_name, layer_type, table_name) values (");
        query.append("'").append(layer.getNetworkName()).append("', ")
                .append("'").append(layer.getSampleName()).append("', ")
                .append("'").append(layer.getLayerType()).append("', ")
                .append("'").append(layer.getTableName()).append("');");
        return query.toString();
    }

    public String insertNINeuronQuery(NINeuron neuron){
        StringBuilder query = new StringBuilder();
        query.append("insert into main.neuron ( network_name, sample_name, layer_type, value) values (");
        query.append("'").append(neuron.getNetworkName()).append("', ")
                .append("'").append(neuron.getSampleName()).append("', ")
                .append("'").append(neuron.getLayerType()).append("', ")
                .append("'").append(neuron.getValue()).append("');");
        return query.toString();
    }

    //----------------------------------- SELECT:
    public String selectAllNINetworksQuery(){
        return new StringBuilder().append("select * from main.network;").toString();
    }

    public String selectAllNISampleQuery(){
        return new StringBuilder().append("select * from sample;").toString();
    }

    public String selectAllNILayerQuery(){
        return new StringBuilder().append("select * from layer;").toString();
    }

    public String selectAllNINeuronQuery(){
        return new StringBuilder().append("select * from neuron;").toString();
    }

    public String selectNINetworkByNetworkNameQuery(String networkName){
        return new StringBuilder().append("select * from main.network where network_name = '").append(networkName).append("';").toString();
    }

    public String selectAllNISampleByNetworkNameQuery(String networkName){
        return new StringBuilder().append("select * from sample where network_name = '").append(networkName).append("';").toString();
    }

    public String selectAllNISampleByNetworkNameAndSampleNameQuery(String networkName, String sampleName){
        return new StringBuilder().append("select * from sample where network_name = '").append(networkName).append("' and sample_name = ").append(sampleName).append("';").toString();
    }

    public String selectNILayerByNetworkNameQuery(String networkName, String sampleName, int layerType){
        return new StringBuilder().append("select * from layer where network_name = '").append(networkName).append("' and sample_name = ").append(sampleName).append("' and layer_type = '").append(layerType).append("';").toString();
    }

    public String selectAllNILayersByNetworkNameAndSampleNameQuery(String networkName, String sampleName){
        return new StringBuilder().append("select * from layer where network_name = '").append(networkName).append("' and sample_name = ").append(sampleName).append("';").toString();
    }

    public String selectAllNINeuronByNetworkNameAndLayerIdQuery(String networkName, String sampleName, int layerId){
        return new StringBuilder().append("select * from neuron where network_name = '").append(networkName).append("' and sample_name = '").append(sampleName).append("' and layer_id = '").append(layerId).append("';").toString();
    }

}
