package com.j0schi.server.NI.mapper;

import com.j0schi.server.NI.model.NILayer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NILayerMapper implements RowMapper<NILayer> {

    @Override
    public NILayer mapRow(ResultSet resultSet, int i) throws SQLException {
        NILayer result = new NILayer();
        result.setLayerId(resultSet.getInt("layer_id"));
        result.setNetworkName(resultSet.getString("network_name"));
        result.setSampleName(resultSet.getString("sample_name"));
        result.setLayerType(resultSet.getInt("layer_type"));
        result.setTableName(resultSet.getString("table_name"));
        return result;
    }
}
