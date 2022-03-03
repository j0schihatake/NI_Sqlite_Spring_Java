package NI.mapper;

import NI.model.NISample;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NISampleMapper implements RowMapper<NISample> {

    @Override
    public NISample mapRow(ResultSet resultSet, int i) throws SQLException {
        NISample result = new NISample();
        result.setPk(resultSet.getInt("id"));
        result.setNetworkName(resultSet.getString("network_name"));
        result.setSampleName(resultSet.getString("sample_name"));
        result.setTableName(resultSet.getString("table_name"));
        result.setDescription(resultSet.getString("description"));
        return result;
    }
}