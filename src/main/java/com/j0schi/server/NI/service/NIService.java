package com.j0schi.server.NI.service;

import com.j0schi.server.NI.model.NILayer;
import com.j0schi.server.NI.model.NINetwork;
import com.j0schi.server.NI.model.NINeuron;
import com.j0schi.server.NI.model.NISample;
import com.j0schi.server.NI.repository.NIRepository;
import com.j0schi.server.NI.util.NIQueryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NIService {

    private final NIRepository niRepository;
    private final NIQueryUtil niQueryUtil;

    //------------------------------ NINeuron
    public List<NINeuron> getAllNINeuronByNetworkNameAndSampleNameAndLayerId(String networkName, String sampleName, int layerId){
        return niRepository.getNINeurons(niQueryUtil.selectAllNINeuronByNetworkNameAndLayerIdQuery(networkName, sampleName, layerId));
    }

    //------------------------------ NILayer
    public List<NILayer> getAllNILayerByNetworkNameAndSampleName(String networkName, String sampleName){
        return niRepository.getNILayers(niQueryUtil.selectAllNILayersByNetworkNameAndSampleNameQuery(networkName, sampleName));
    }

    //------------------------------ NISample
    public List<NISample> getAllNISampleByNetworkName(String networkName){
        return niRepository.getNISamples(niQueryUtil.selectAllNISampleByNetworkNameQuery(networkName));
    }

    //------------------------------ NINetwork

    public NINetwork getNINetworkByNetworkName(String networkName){
        NINetwork network = niRepository.getNINetwork(niQueryUtil.selectNINetworkByNetworkNameQuery(networkName));

        List<NISample> samples = getAllNISampleByNetworkName(networkName);

        if(samples != null) {
            for (NISample sample : samples) {
                List<NILayer> layers = getAllNILayerByNetworkNameAndSampleName(network.getName(), sample.getSampleName());
                if (layers != null) {
                    for (NILayer layer : layers) {
                        List<NINeuron> neurons = getAllNINeuronByNetworkNameAndSampleNameAndLayerId(network.getName(), sample.getSampleName(), layer.getLayerId());
                        layer.setNeurons(neurons);
                    }
                }
                sample.setLayer(layers);
            }
        }
        return network;
    }

    public List<NINetwork> getAllNINetwork(){

        List<NINetwork> result = niRepository.getNINetworks(niQueryUtil.selectAllNINetworksQuery());

        if(result != null) {
            for (NINetwork network : result) {
                List<NISample> samples = niRepository.getNISamples(niQueryUtil.selectAllNISampleByNetworkNameQuery(network.getName()));
                if(samples != null) {
                    for (NISample sample : samples) {
                        List<NILayer> layers = niRepository.getNILayers(niQueryUtil.selectAllNILayersByNetworkNameAndSampleNameQuery(network.getName(), sample.getSampleName()));
                        if(layers != null) {
                            for (NILayer layer : layers) {
                                List<NINeuron> neurons = niRepository.getNINeurons(niQueryUtil.selectAllNINeuronByNetworkNameAndLayerIdQuery(network.getName(), sample.getSampleName(), layer.getLayerId()));
                                layer.setNeurons(neurons);
                            }
                        }
                        sample.setLayer(layers);
                    }
                }
                network.setSamples(samples);
            }
        }
        return result;
    }

    //------------------------------------------------ INSERT:
    @Transactional
    public boolean insertOrUpdateNINetwork(NINetwork niNetwork){

        boolean result = false;
        String action = "insert";

        if(niNetwork!=null) {

            NINetwork prevNetworkState = niRepository.getNINetwork(niQueryUtil.selectNINetworkByNetworkNameQuery(niNetwork.getName()));

            if (prevNetworkState == null)
                action = "update";

            switch (action) {
                case "insert":
                    result = niRepository.execute(niQueryUtil.insertNINetworkQuery(niNetwork));

                    if(niNetwork.getSamples() != null && niNetwork.getSamples().size()>0) {
                        for (NISample sample : niNetwork.getSamples()) {
                            result = niRepository.execute(niQueryUtil.insertNISampleQuery(sample));
                            if (sample.getLayer() != null && sample.getLayer().size()>0) {
                                for (NILayer layer : sample.getLayer()) {
                                    result = niRepository.execute(niQueryUtil.insertNILayerQuery(layer));
                                    if(layer.getNeurons()!=null && layer.getNeurons().size()>0) {
                                        for (NINeuron neuron : layer.getNeurons()) {
                                            result = niRepository.execute(niQueryUtil.insertNINeuronQuery(neuron));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "update":
                    result = niRepository.execute(niQueryUtil.updateNINetworkQuery(niNetwork));

                    if(niNetwork.getSamples() != null && niNetwork.getSamples().size()>0) {
                        for (NISample sample : niNetwork.getSamples()) {
                            result = niRepository.execute(niQueryUtil.updateNISampleQuery(sample));
                            if (sample.getLayer() != null && sample.getLayer().size()>0) {
                                for (NILayer layer : sample.getLayer()) {
                                    result = niRepository.execute(niQueryUtil.updateNILayerQuery(layer));
                                    if(layer.getNeurons()!=null && layer.getNeurons().size()>0) {
                                        for (NINeuron neuron : layer.getNeurons()) {
                                            result = niRepository.execute(niQueryUtil.updateNINeuronQuery(neuron));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
        }
        return result;
    }
}