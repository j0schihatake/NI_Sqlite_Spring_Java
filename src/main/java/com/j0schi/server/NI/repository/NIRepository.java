package com.j0schi.server.NI.repository;

import com.j0schi.server.NI.mapper.NILayerMapper;
import com.j0schi.server.NI.mapper.NINeuronMapper;
import com.j0schi.server.NI.mapper.NISampleMapper;
import com.j0schi.server.NI.model.NILayer;
import com.j0schi.server.NI.model.NINetwork;
import com.j0schi.server.NI.mapper.NINetworkMapper;
import com.j0schi.server.NI.model.NINeuron;
import com.j0schi.server.NI.model.NISample;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NIRepository {

    private final JdbcTemplate jdbcTemplate;

    public Boolean execute(String query){
        Boolean success = false;
        try{
            jdbcTemplate.execute(query);
            System.out.println("Query ok " + query);
            success = true;
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return success;
    }

    //-------------------------------NINetwork
    public NINetwork getNINetwork(String query){
        NINetwork result = null;
        try{
            result = jdbcTemplate.queryForObject(query, new NINetworkMapper());
            System.out.println("Query ok " + query);
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public List<NINetwork> getNINetworks(String query) {
        List<NINetwork> result = null;
        try {
            result = jdbcTemplate.query(query, new NINetworkMapper());
            System.out.println("Query ok " + query);
        } catch (Exception ex) {
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    //---------------------------------NISample
    public NISample getNISample(String query){
        NISample result = null;
        try{
            result = jdbcTemplate.queryForObject(query, new NISampleMapper());
            System.out.println("Query ok " + query);
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public List<NISample> getNISamples(String query) {
        List<NISample> result = null;
        try {
            result = jdbcTemplate.query(query, new NISampleMapper());
            System.out.println("Query ok " + query);
        } catch (Exception ex) {
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    //---------------------------------NILayer
    public NILayer getNILayer(String query){
        NILayer result = null;
        try{
            result = jdbcTemplate.queryForObject(query, new NILayerMapper());
            System.out.println("Query ok " + query);
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public List<NILayer> getNILayers(String query) {
        List<NILayer> result = null;
        try {
            result = jdbcTemplate.query(query, new NILayerMapper());
            System.out.println("Query ok " + query);
        } catch (Exception ex) {
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    //---------------------------------NINeuron
    public NINeuron getNINeuron(String query){
        NINeuron result = null;
        try{
            result = jdbcTemplate.queryForObject(query, new NINeuronMapper());
            System.out.println("Query ok " + query);
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public List<NINeuron> getNINeurons(String query) {
        List<NINeuron> result = null;
        try {
            result = jdbcTemplate.query(query, new NINeuronMapper());
            System.out.println("Query ok " + query);
        } catch (Exception ex) {
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
