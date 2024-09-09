package com.j0schi.server.vot.map.entity;

import com.j0schi.server.vot.map.MLive;
import com.j0schi.server.vot.map.MSquad;
import lombok.Data;

@Data
public class MVacation {

    public MSquad activeFraction = null;

    public MLive activeUnit = null;
}
