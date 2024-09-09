package com.j0schi.server.vot.generation;

import com.j0schi.server.vot.map.MFraction;
import com.j0schi.server.vot.map.MLive;
import com.j0schi.server.vot.map.MSquad;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class Sector {

    public Vector3Int position = new Vector3Int(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public String sectorTemplateName = null;
    public String sectorName;

    public Map<Vector3Int, Integer> voxelMap = null;

    //  Информация о живом в данном секторе:
    public ArrayList<MFraction> sectorFractions = new ArrayList<>();
    public ArrayList<MSquad> sectorSquads = new ArrayList<>();
    public ArrayList<MLive> sectorLives = new ArrayList<>();

    // Переменные для ключа окружения:
    public boolean forwardN;
    public boolean backN;
    public boolean leftN;
    public boolean rightN;
    public boolean upN;
    public boolean downN;
    public boolean forwardLeftN;
    public boolean backLeftN;
    public boolean forwardRightN;
    public boolean backRightN;

    /*
     * Строковое представление соеденительной плоскости:
     */
    private String forwardKey;
    private String backwardKey;
    private String leftKey;
    private String rightKey;
    private String upKey;
    private String downKey;

    private SectorType type;

    public String neighborKey = null;

    public Sector(int x, int y, int z, SectorType type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.position = new Vector3Int(x, y, z);
        this.type = type;
    }

    public Sector(Vector3Int position, SectorType type) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
        this.position = position;
        this.type = type;
    }

    public void setType(SectorType type) {
        this.type = type;
    }

    public void load(String templateName) {
        this.sectorTemplateName = templateName.toLowerCase();
    }

    public Map<Vector3Int, Integer> getSector() {
        return null;//SaveLoadManager.getInstance().loadEditorChunk(sectorTemplateName);
    }

    /**
     * Обновляем описание наличия соседей секции
     */
    public void updateNeighbourInfo(Vector3Int sectorPos, ConcurrentHashMap<Vector3Int, Sector> virtualMap) {
        forwardN = virtualMap.containsKey(sectorPos.add(Vector3Int.forward()));
        backN = virtualMap.containsKey(sectorPos.add(Vector3Int.back()));
        leftN = virtualMap.containsKey(sectorPos.add(Vector3Int.left()));
        rightN = virtualMap.containsKey(sectorPos.add(Vector3Int.right()));
        upN = virtualMap.containsKey(sectorPos.add(Vector3Int.up()));
        downN = virtualMap.containsKey(sectorPos.add(Vector3Int.down()));

        forwardLeftN = virtualMap.containsKey(sectorPos.add(Vector3Int.forward()).add(Vector3Int.left()));
        forwardRightN = virtualMap.containsKey(sectorPos.add(Vector3Int.forward()).add(Vector3Int.right()));
        backLeftN = virtualMap.containsKey(sectorPos.add(Vector3Int.back()).add(Vector3Int.left()));
        backRightN = virtualMap.containsKey(sectorPos.add(Vector3Int.back()).add(Vector3Int.right()));
    }

    public void updateNeighbourInfo(ConcurrentHashMap<Vector3Int, Sector> virtualMap) {
        forwardN = virtualMap.containsKey(position.add(Vector3Int.forward()));
        backN = virtualMap.containsKey(position.add(Vector3Int.back()));
        leftN = virtualMap.containsKey(position.add(Vector3Int.left()));
        rightN = virtualMap.containsKey(position.add(Vector3Int.right()));
        upN = virtualMap.containsKey(position.add(Vector3Int.up()));
        downN = virtualMap.containsKey(position.add(Vector3Int.down()));

        forwardLeftN = virtualMap.containsKey(position.add(Vector3Int.forward()).add(Vector3Int.left()));
        forwardRightN = virtualMap.containsKey(position.add(Vector3Int.forward()).add(Vector3Int.right()));
        backLeftN = virtualMap.containsKey(position.add(Vector3Int.back()).add(Vector3Int.left()));
        backRightN = virtualMap.containsKey(position.add(Vector3Int.back()).add(Vector3Int.right()));
    }

    public String getNeighborKey() {
        return (forwardN + "," + backN + "," + leftN + "," + rightN + "," + upN + "," + downN).toLowerCase();
    }

    public void updateNeighborBooleans() {

        String[] values = neighborKey != null ? neighborKey.split(",") : new String[0];

        if (values.length != 6) {
            System.err.println("Invalid key format. The key should have exactly 6 boolean values separated by commas.");
            return;
        }

        forwardN = Boolean.parseBoolean(values[0]);
        backN = Boolean.parseBoolean(values[1]);
        leftN = Boolean.parseBoolean(values[2]);
        rightN = Boolean.parseBoolean(values[3]);
        upN = Boolean.parseBoolean(values[4]);
        downN = Boolean.parseBoolean(values[5]);

        forwardLeftN = forwardN && leftN;
        backLeftN = backN && leftN;
        forwardRightN = forwardN && rightN;
        backRightN = backN && rightN;
    }

    public String getSectorName(){
        StringBuilder builder = new StringBuilder();
        switch(type){
            case STATIC:
                return "S" + position.x + "|" + position.y + "|" + position.z;
            case DYNAMIC:
                return "D" + position.x + "|" + position.y + "|" + position.z;
        }
        return null;
    }

    public static Vector3Int parseSectorNameToVector3Int(String sectorName) {
        String[] parts = sectorName.substring(1).split("\\|");

        if (parts.length != 3) {
            System.err.println("Неправильный формат имени сектора: " + sectorName);
            return new Vector3Int(0, 0, 0); // Возвращаем Vector3Int(0, 0, 0) в случае ошибки
        }

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);

        return new Vector3Int(x, y, z);
    }
}

