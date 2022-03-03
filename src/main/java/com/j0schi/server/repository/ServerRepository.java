package com.j0schi.server.repository;

import com.j0schi.server.mapper.Vector3Mapper;
import com.j0schi.server.model.Vector3;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServerRepository {

    private final JdbcTemplate jdbcTemplate;

    public Boolean execute(String query){
        Boolean success = false;
        try{
            System.out.println("Query ok " + query);
            success = true;
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return success;
    }

    //--------------------------------------Vector3
    public Vector3 getVector3(String query){
        Vector3 vector3 = null;
        try{
            vector3 = jdbcTemplate.queryForObject(query, new Vector3Mapper());
            System.out.println("Query ok " + query);
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return vector3;
    }

    public List<Vector3> getVector3List(String query){
        List<Vector3> vector3List = null;
        try{
            vector3List = jdbcTemplate.query(query, new Vector3Mapper());
            System.out.println("Query ok " + query);
        }catch(Exception ex){
            System.out.println("Query error " + query);
            System.out.println(ex.getMessage());
        }
        return vector3List;
    }
}
