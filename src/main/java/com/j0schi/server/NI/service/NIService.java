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
        return niRepository.getNINeurons(niQueryUtil.selectAllNINeuronByNetworkNameAndLayerId(networkName, sampleName, layerId));
    }

    //------------------------------ NILayer
    public List<NILayer> getAllNILayerByNetworkNameAndSampleName(String networkName, String sampleName){
        return niRepository.getNILayers(niQueryUtil.selectAllNILayersByNetworkNameAndSampleName(networkName, sampleName));
    }

    //------------------------------ NISample
    public List<NISample> getAllNISampleByNetworkName(String networkName){
        return niRepository.getNISamples(niQueryUtil.selectAllNISampleByNetworkName(networkName));
    }

    //------------------------------ NINetwork

    public NINetwork getNINetworkByNetworkName(String networkName){
        NINetwork network = niRepository.getNINetwork(niQueryUtil.selectNINetworkByNetworkName(networkName));
        assert network != null;

        List<NISample> samples = getAllNISampleByNetworkName(networkName);
        assert samples != null;

        for(NISample sample: samples){
            List<NILayer> layers = getAllNILayerByNetworkNameAndSampleName(network.getName(), sample.getSampleName());
            assert layers != null;
            for(NILayer layer: layers){
                List<NINeuron> neurons = getAllNINeuronByNetworkNameAndSampleNameAndLayerId(network.getName(), sample.getSampleName(), layer.getLayerId());
                layer.setNeurons(neurons);
            }
            sample.setLayer(layers);
        }
        return network;
    }

    public List<NINetwork> getAllNINetwork(){

        List<NINetwork> result = niRepository.getNINetworks(niQueryUtil.selectAllNINetworks());
        assert result != null;

        for(NINetwork network: result){
            List<NISample> samples = niRepository.getNISamples(niQueryUtil.selectAllNISampleByNetworkName(network.getName()));
            assert samples != null;
            for(NISample sample: samples){
                List<NILayer> layers = niRepository.getNILayers(niQueryUtil.selectAllNILayersByNetworkNameAndSampleName(network.getName(), sample.getSampleName()));
                assert layers != null;
                for(NILayer layer: layers){
                    List<NINeuron> neurons = niRepository.getNINeurons(niQueryUtil.selectAllNINeuronByNetworkNameAndLayerId(network.getName(), sample.getSampleName(), layer.getLayerId()));
                    layer.setNeurons(neurons);
                }
                sample.setLayer(layers);
            }
            network.setSamples(samples);
        }
        return result;
    }

    //------------------------------------------------ INSERT:
    @Transactional
    public boolean insertOrUpdateNINetwork(NINetwork niNetwork){
        boolean result = false;

        result = niRepository.execute(niQueryUtil.insertNINetwork(niNetwork));

        assert niNetwork.getSamples() != null;
        for(NISample sample: niNetwork.getSamples()){
            assert result != true;
            result = niRepository.execute(niQueryUtil.insertNISample(sample));
            assert sample.getLayer() != null;
            for(NILayer layer: sample.getLayer()){
                assert result != true;
                result = niRepository.execute(niQueryUtil.insertNILayer(layer));
                assert layer.getNeurons() != null;
                for(NINeuron neuron: layer.getNeurons()){
                    assert result != true;
                    result = niRepository.execute(niQueryUtil.insertNINeuron(neuron));
                }
            }
        }

        return result;
    }
}