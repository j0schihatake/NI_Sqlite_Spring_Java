package com.j0schi.server.vot;

import com.j0schi.server.vot.generation.DungeonGenerator;
import com.j0schi.server.vot.generation.Sector;
import com.j0schi.server.vot.generation.SectorType;
import com.j0schi.server.vot.generation.Vector3Int;
import com.j0schi.server.vot.map.AStarPathFinder;
import com.j0schi.server.vot.map.MFraction;
import com.j0schi.server.vot.map.MLive;
import com.j0schi.server.vot.utils.RandomUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class World {

    public ConcurrentHashMap<Vector3Int, Sector> virtualMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Vector3Int, Sector> staticSectors = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, MFraction> fractionsMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, MLive> livesMap = new ConcurrentHashMap<>();

    public static World gameWorld;

    public DungeonGenerator generator;

    public AStarPathFinder pathFinder;

    public enum WorldState{
        init, live, end
    }

    /**
     * Мировой цикл:
     */
    public void stateMachine(){

        WorldState state = WorldState.init;

        do{
            switch (state){
                case init:
                    log.info("Первоначальная настройка игрового мира.");
                    init();
                    state = WorldState.live;
                    log.info("Переход к итерациям мира.");
                    break;
                case live:
                    live();
                    break;
                case end:
                    break;
                default:
                    state = WorldState.end;
            }

        }while(state != WorldState.end);
    }

    public void init(){

        gameWorld = this;

        generator = new DungeonGenerator();
        virtualMap = generator.fullRegenerate();

        pathFinder = new AStarPathFinder();

        prepareMLives(new WorldConfig());
    }

    public void prepareMLives(WorldConfig config){

        for(Sector sector : virtualMap.values()){
            if(sector.getType() == SectorType.STATIC)
                staticSectors.put(sector.position, sector);
        }

        // Первоначальное создание фракций:
        MFraction fraction = new MFraction();
        fraction.isNN = false;
        fraction.name = "Player";
        fraction.fractionId = RandomUtil.getThreadLocalRandomString(10);
        fraction.properties = fraction.getDefaultProperties();
        fractionsMap.put(fraction.fractionId, fraction);

        fraction = new MFraction();
        fraction.name = "Mechanics";
        fraction.fractionId = RandomUtil.getThreadLocalRandomString(10);
        fraction.properties = fraction.getDefaultProperties();
        fractionsMap.put(fraction.fractionId, fraction);

        fraction = new MFraction();
        fraction.isAllEnemies = true;
        fraction.name = "DarkMutants";
        fraction.fractionId = RandomUtil.getThreadLocalRandomString(10);
        fraction.properties = fraction.getDefaultProperties();
        fractionsMap.put(fraction.fractionId, fraction);

        for(MFraction nextFraction : fractionsMap.values()){
            nextFraction.init();
        }
    }

    public void live(){
        for (MFraction fraction: fractionsMap.values()) {
            fraction.live();
        }
    }

    //  Анализ и формирование оценки для отдачи приказов:

    public List<Vector3Int> getNeighborPositions(Vector3Int position) {
        List<Vector3Int> neighbors = new ArrayList<>();

        // Добавляем соседей по шести направлениям
        neighbors.add(new Vector3Int(position.getX() + 1, position.getY(), position.getZ())); // вправо
        neighbors.add(new Vector3Int(position.getX() - 1, position.getY(), position.getZ())); // влево
        neighbors.add(new Vector3Int(position.getX(), position.getY(), position.getZ() + 1)); // вперед
        neighbors.add(new Vector3Int(position.getX(), position.getY(), position.getZ() - 1)); // назад
        neighbors.add(new Vector3Int(position.getX(), position.getY() + 1, position.getZ())); // вверх
        neighbors.add(new Vector3Int(position.getX(), position.getY() - 1, position.getZ())); // вниз

        return neighbors;
    }

    public Sector findNearestSector(Vector3Int targetPosition) {
        Sector nearestSector = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Map.Entry<Vector3Int, Sector> entry : virtualMap.entrySet()) {
            Vector3Int sectorPosition = entry.getKey();
            Sector sector = entry.getValue();

            // Вычисляем расстояние между текущей позицией сектора и целевой позицией
            double distance = sectorPosition.distance(targetPosition);

            // Если текущее расстояние меньше, чем текущее минимальное, обновляем ближайший сектор
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestSector = sector;
            }
        }

        return nearestSector;                                                                                               // Возвращаем ближайший сектор
    }

    public Sector findFreeNearestSector(Vector3Int targetPosition) {
        Sector nearestSector = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Map.Entry<Vector3Int, Sector> entry : virtualMap.entrySet()) {
            Vector3Int sectorPosition = entry.getKey();
            Sector sector = entry.getValue();

            // Вычисляем расстояние между текущей позицией сектора и целевой позицией
            double distance = sectorPosition.distance(targetPosition);

            // Если текущее расстояние меньше, чем текущее минимальное, обновляем ближайший сектор
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestSector = sector;
            }
        }

        return nearestSector;                                                                                               // Возвращаем ближайший сектор
    }

    public Sector findNearestSectorBFS(Vector3Int startPosition, Vector3Int targetPosition) {
        Set<Vector3Int> visited = new HashSet<>();
        Queue<Vector3Int> queue = new LinkedList<>();
        queue.add(startPosition);
        visited.add(startPosition);

        Sector nearestSector = virtualMap.get(startPosition);
        double nearestDistance = startPosition.distance(targetPosition);

        while (!queue.isEmpty()) {
            Vector3Int currentPos = queue.poll();

            // Получаем текущий сектор
            Sector currentSector = virtualMap.get(currentPos);
            if (currentSector == null) {
                continue; // Пропускаем если сектор не существует
            }

            // Рассчитываем расстояние от текущего сектора до целевой позиции
            double currentDistance = currentPos.distance(targetPosition);

            // Если текущий сектор ближе, обновляем ближайший сектор
            if (currentDistance < nearestDistance) {
                nearestDistance = currentDistance;
                nearestSector = currentSector;
            }

            // Добавляем всех соседей в очередь, если они не посещены
            for (Vector3Int neighborPos : getNeighborPositions(currentPos)) {
                if (!visited.contains(neighborPos) && virtualMap.containsKey(neighborPos)) {
                    visited.add(neighborPos);
                    queue.add(neighborPos);
                }
            }
        }

        return nearestSector;
    }
}
