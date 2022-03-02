package com.j0schi.server.mapper;

import com.j0schi.server.model.Vector3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class Vector3Mapper implements RowMapper<Vector3> {

    @Override
    public Vector3 mapRow(ResultSet resultSet, int i){
        Vector3 result = new Vector3();
        return result;
    }
}
