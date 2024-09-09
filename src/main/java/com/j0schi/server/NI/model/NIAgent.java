package com.j0schi.server.NI.model;

import com.j0schi.server.vot.generation.Sector;
import com.j0schi.server.vot.generation.Vector3Int;
import com.j0schi.server.vot.map.MFraction;
import lombok.Data;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class NIAgent {
    public ArrayList<NINeuron> getWorldMeta(ConcurrentHashMap<Vector3Int, Sector> virtualMap){
        return null;
    }

    public ArrayList<NINeuron> getSectorMeta(Sector sector){
        return null;
    }

    public ArrayList<NINeuron> getMFractionMeta(ConcurrentHashMap<Vector3Int, Sector> virtualMap, MFraction fraction){
        return null;
    }

    public ArrayList<NINeuron> getMSquadMeta(ConcurrentHashMap<Vector3Int, Sector> virtualMap){
        return null;
    }

    public ArrayList<NINeuron> getMLiveMeta(){
        return null;
    }
}
