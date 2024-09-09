package com.j0schi.server.vot.map.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MEntity {

    /**
     * Неодушевленный обьект игрового мира
     */
    public ArrayList<MVacation> vacations = new ArrayList<>();
}
