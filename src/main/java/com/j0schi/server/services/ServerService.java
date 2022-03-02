package com.j0schi.server.services;

import com.j0schi.server.NI.NITest;
import com.j0schi.server.NI.components.NINetwork;
import com.j0schi.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws SQLException, ClassNotFoundException {
        System.out.println("hello world, I have just started up");
        NITest.test();
    }

    public Boolean pullNINetwork(NINetwork network){
        StringBuilder query = new StringBuilder();
        query.append("insert into network ");
        return serverRepository.execute(query.toString());
    }

    public NINetwork getNINetwork(String networkName){
        return serverRepository.getNINetworkByNetworkName(networkName);
    }

    public List<NINetwork> getAllNINetwork(){
        return serverRepository.getAllNINetwork();
    }

}