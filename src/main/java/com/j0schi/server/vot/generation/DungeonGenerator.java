package com.j0schi.server.vot.generation;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DungeonGenerator{
    public int staticSectorsCount = 10;
    public int additionalPathsCount = 10;
    public int maxDynamicSectors = 50;
    public int maxAttempts = 1000;

    public int positionRangeXMin = -5;
    public int positionRangeXMax = 6;
    public int positionRangeYMin = -2;
    public int positionRangeYMax = 3;
    public int positionRangeZMin = -5;
    public int positionRangeZMax = 6;

    public int sectorScale = 10;
    public int minConnections = 1;
    public int maxConnections = 5;

    public ConcurrentHashMap<Vector3Int, Sector> virtualMap = new ConcurrentHashMap<>();

    public byte getBox(Vector3 pos) {
        return 0;
    }

    public Map<Vector3Int, Integer> generateMap(Vector3Int chunkPosition) {
        mapInit();

        if (virtualMap.containsKey(chunkPosition)) {
            Sector sector = virtualMap.get(chunkPosition);
            if (sector.voxelMap == null) {
                // Подгружаем данные из базы или файла (эмуляция)
                sector.voxelMap = loadEditorChunk(sector.sectorTemplateName);
            }
            return sector.voxelMap;
        }
        return null;
    }

    private Map<Vector3Int, Integer> loadEditorChunk(String sectorTemplateName) {
        // Здесь должна быть реализация загрузки сектора (например, из базы данных)
        return new HashMap<>();
    }

    public void mapInit() {
        if (virtualMap == null) {
            virtualMap = fullRegenerate();
            sectionInitialization(virtualMap);
        }
    }

    public void sectionInitialization(ConcurrentHashMap<Vector3Int, Sector> virtualMap) {
        for (Vector3Int pos : virtualMap.keySet()) {
            Sector next = virtualMap.get(pos);
            next.updateNeighbourInfo(pos, virtualMap);
            if (next.sectorTemplateName == null) {
                /*
                // Пример загрузки случайного сектора, используя ключ соседей (эмуляция):
                ChunkEntity entity = loadRandomChunkByNeighborKey(pos, virtualMap);
                next.sectorTemplateName = entity.chunkTemplate;
                next.neighborKey = entity.neighborKey;
                next.ForwardKey = entity.forward;
                next.BackwardKey = entity.back;
                next.LeftKey = entity.left;
                next.RightKey = entity.right;
                next.UpKey = entity.up;
                next.DownKey = entity.down;
                next.updateNeighborBooleans();
                 */
            }
        }
    }

    //private ChunkEntity loadRandomChunkByNeighborKey(Vector3Int pos, ConcurrentHashMap<Vector3Int, Sector> virtualMap) {
        // Заглушка для демонстрации
    //    return new ChunkEntity("random_template", "neighborKey", "forward", "back", "left", "right", "up", "down");
    //}

    public ConcurrentHashMap<Vector3Int, Sector> fullRegenerate() {
        return generateSimple();
    }

    public ConcurrentHashMap<Vector3Int, Sector> generateSimple() {
        ConcurrentHashMap<Vector3Int, Sector> virtualMap = new ConcurrentHashMap<>();
        List<Vector3Int> staticPositions = new ArrayList<>();

        for (Vector3Int position : getRandomPositions(staticSectorsCount)) {
            addSector(position, SectorType.STATIC);
            staticPositions.add(position);
        }

        connectStaticSectors(staticPositions, virtualMap);

        for (Vector3Int position : getRandomPositions(maxDynamicSectors)) {
            if (!virtualMap.containsKey(position) && isPositionConnected(position, virtualMap)) {
                addSector(position, SectorType.DYNAMIC);
            }
        }

        ensureConnectivity(virtualMap);

        for(Sector sector : virtualMap.values()){
            sector.updateNeighbourInfo(virtualMap);
            sector.updateNeighborBooleans();
        }

        return virtualMap;
    }

    void addSector(Vector3Int position, SectorType type) {
        virtualMap.put(position, new Sector(position, type));
    }

    private List<Vector3Int> getRandomPositions(int count) {
        Set<Vector3Int> positions = new HashSet<>();
        Random random = new Random();

        while (positions.size() < count) {
            int x = random.nextInt(positionRangeXMax - positionRangeXMin + 1) + positionRangeXMin;
            int y = random.nextInt(positionRangeYMax - positionRangeYMin + 1) + positionRangeYMin;
            int z = random.nextInt(positionRangeZMax - positionRangeZMin + 1) + positionRangeZMin;
            positions.add(new Vector3Int(x, y, z));
        }

        return new ArrayList<>(positions);
    }

    private boolean isPositionConnected(Vector3Int position, ConcurrentHashMap<Vector3Int, Sector> virtualMap) {
        Vector3Int[] directions = {
                Vector3Int.left(), Vector3Int.right(),
                Vector3Int.forward(), Vector3Int.back(),
                Vector3Int.up(), Vector3Int.down()
        };

        for (Vector3Int direction : directions) {
            if (virtualMap.containsKey(position.add(direction))) {
                return true;
            }
        }
        return false;
    }

    private void ensureConnectivity(ConcurrentHashMap<Vector3Int, Sector> map) {
        // Реализация обеспечения связности карты
    }

    private void connectStaticSectors(List<Vector3Int> positions, ConcurrentHashMap<Vector3Int, Sector> map) {
        Random random = new Random();

        for (Vector3Int start : positions) {
            int connections = random.nextInt(maxConnections - minConnections + 1) + minConnections;
            for (int i = 0; i < connections; i++) {
                Vector3Int end = positions.get(random.nextInt(positions.size()));
                if (!start.equals(end)) {
                    createPath(start, end, map);
                }
            }
        }
    }

    private void createPath(Vector3Int start, Vector3Int end, ConcurrentHashMap<Vector3Int, Sector> map) {
        Vector3Int current = start;

        while (!current.equals(end)) {
            Vector3Int direction = getDirection(current, end);
            current = current.add(direction);
            if (!map.containsKey(current)) {
                map.put(current, new Sector(current, SectorType.DYNAMIC));
            }
        }
    }

    private Vector3Int getDirection(Vector3Int from, Vector3Int to) {
        int dx = Integer.compare(to.x, from.x);
        int dy = Integer.compare(to.y, from.y);
        int dz = Integer.compare(to.z, from.z);

        if (dx != 0) return new Vector3Int(dx, 0, 0);
        if (dy != 0) return new Vector3Int(0, dy, 0);
        return new Vector3Int(0, 0, dz);
    }

    public static Map<Vector3Int, Sector> loadVirtualMap() {
        StringBuilder json = new StringBuilder("{}");

        // Парсим JSON строку
        JsonReader reader = Json.createReader(new StringReader(json.toString()));
        JsonObject jsonObject = reader.readObject();
        reader.close();

        Map<Vector3Int, Sector> virtualMap = new HashMap<>();

        JsonArray entriesArray = jsonObject.getJsonArray("entries");

        for (int i = 0; i < entriesArray.size(); i++) {
            JsonObject entry = entriesArray.getJsonObject(i);

            JsonObject position = entry.getJsonObject("position");
            int x = position.getInt("x");
            int y = position.getInt("y");
            int z = position.getInt("z");

            JsonObject sector = entry.getJsonObject("sector");
            int id = sector.getInt("Id");
            String name = sector.getString("Name");

            Vector3Int vector = new Vector3Int(x, y, z);
            Sector sec = new Sector();

            virtualMap.put(vector, sec);
        }

        // Выводим результат
        for (Map.Entry<Vector3Int, Sector> entry : virtualMap.entrySet()) {
            System.out.println("Key: " + entry.getKey());
            System.out.println("Value: " + entry.getValue());
        }
        return virtualMap;
    }
}