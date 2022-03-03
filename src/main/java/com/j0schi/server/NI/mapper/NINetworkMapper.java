package NI.mapper;

import NI.model.NINetwork;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NINetworkMapper implements RowMapper<NINetwork> {

    @Override
    public NINetwork mapRow(ResultSet resultSet, int i) throws SQLException {
        NINetwork result = new NINetwork();
        result.setPk(resultSet.getInt("id"));
        result.setName(resultSet.getString("name"));
        result.setDescription(resultSet.getString("description"));
        result.setTableName(resultSet.getString("table_name"));
        result.setINPUT_NEURONS(resultSet.getInt("input_neurons"));
        result.setHIDDEN_NEURONS(resultSet.getInt("hidden_neurons"));
        result.setOUTPUT_NEURONS(resultSet.getInt("output_neurons"));
        result.setDest(resultSet.getInt("dest"));
        result.setHIDDEN_LAYER_COUNT(resultSet.getInt("hidden_layer_count"));
        result.setLEARN_RATE(resultSet.getInt("learn_rate"));
        return result;
    }
}
