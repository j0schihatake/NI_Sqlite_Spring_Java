package com.j0schi.server.services;

import com.j0schi.server.NI.NITest;
import com.j0schi.server.NI.service.NIService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ServerService {

    //------------------------------ Services
    private final NIService niService;

    //------------------------------ Other
    private final NITest niTest;

    //------------------------------ Main thread
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws SQLException, ClassNotFoundException {
        System.out.println("Start main code.");
        niTest.test(niService);
    }

}