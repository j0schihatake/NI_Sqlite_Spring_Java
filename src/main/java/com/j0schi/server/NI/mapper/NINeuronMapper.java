package com.j0schi.server.NI.mapper;

import com.j0schi.server.NI.model.NINeuron;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NINeuronMapper implements RowMapper<NINeuron> {

    @Override
    public NINeuron mapRow(ResultSet resultSet, int i) throws SQLException {
        NINeuron result = new NINeuron();
        result.setPk(resultSet.getInt("id"));
        result.setLayerId(resultSet.getInt("layer_id"));
        result.setNetworkName(resultSet.getString("network_name"));
        result.setSampleName(resultSet.getString("sample_name"));
        result.setLayerType(resultSet.getInt("layer_type"));
        result.setValue(Float.parseFloat(resultSet.getString("value")));
        return result;
    }
}
