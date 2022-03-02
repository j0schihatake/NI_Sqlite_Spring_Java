package com.j0schi.server.mapper;

import com.j0schi.server.NI.components.NINetwork;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NINetworkMapper implements RowMapper<NINetwork> {

    @Override
    public NINetwork mapRow(ResultSet resultSet, int i) throws SQLException {
        NINetwork result = new NINetwork();
        result.setDescription(resultSet.getString("description"));
        return result;
    }
}
