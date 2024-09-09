package com.j0schi.server.vot.map.race;

import com.j0schi.server.vot.map.property.MProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Data
@Slf4j
public class MRace {

    public String id;

    public String name;

    public HashMap<String, MProperty> properties = new HashMap<String, MProperty>();
}
