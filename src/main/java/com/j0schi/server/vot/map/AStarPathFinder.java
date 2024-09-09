package com.j0schi.server.vot.map;

import com.j0schi.server.vot.generation.Sector;
import com.j0schi.server.vot.generation.Vector3Int;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AStarPathFinder {

    public static ArrayList<Sector> findPath(Sector start, Sector goal, ConcurrentHashMap<Vector3Int, Sector> virtualMap) {


        SortedSet<SectorNode> openSet = new TreeSet<>(new SectorNodeComparer());
        Map<Sector, Sector> cameFrom = new HashMap<>();
        Map<Sector, Float> gScore = new HashMap<>();
        Map<Sector, Float> fScore = new HashMap<>();

        SectorNode startNode = new SectorNode(start, 0f, heuristicCostEstimate(start, goal));
        openSet.add(startNode);
        gScore.put(start, 0f);
        fScore.put(start, startNode.getFScore());

        while (!openSet.isEmpty()) {
            SectorNode currentNode = openSet.first();
            openSet.remove(currentNode);

            Sector currentSector = currentNode.getSector();
            if (currentSector.equals(goal)) {
                return reconstructPath(cameFrom, currentSector);
            }

            for (Sector neighbor : getNeighbors(currentSector, virtualMap)) {
                float tentativeGScore = gScore.get(currentSector) + currentSector.getPosition().distance(neighbor.getPosition());
                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, currentSector);
                    gScore.put(neighbor, tentativeGScore);
                    float fScoreNeighbor = tentativeGScore + heuristicCostEstimate(neighbor, goal);
                    fScore.put(neighbor, fScoreNeighbor);

                    SectorNode neighborNode = new SectorNode(neighbor, tentativeGScore, fScoreNeighbor);
                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return null; // Путь не найден
    }

    private static ArrayList<Sector> reconstructPath(Map<Sector, Sector> cameFrom, Sector current) {
        ArrayList<Sector> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(0, current);
        }
        return totalPath;
    }

    private static float heuristicCostEstimate(Sector start, Sector goal) {
        return start.getPosition().distance(goal.getPosition());
    }

    private static ArrayList<Sector> getNeighbors(Sector sector, ConcurrentHashMap<Vector3Int, Sector> virtualMap) {
        ArrayList<Sector> neighbors = new ArrayList<>();

        tryAddNeighbor(sector, virtualMap, neighbors, sector.getForwardKey(), new Vector3Int(1, 0, 0));
        tryAddNeighbor(sector, virtualMap, neighbors, sector.getBackwardKey(), new Vector3Int(-1, 0, 0));
        tryAddNeighbor(sector, virtualMap, neighbors, sector.getLeftKey(), new Vector3Int(0, 0, -1));
        tryAddNeighbor(sector, virtualMap, neighbors, sector.getRightKey(), new Vector3Int(0, 0, 1));
        tryAddNeighbor(sector, virtualMap, neighbors, sector.getUpKey(), new Vector3Int(0, 1, 0));
        tryAddNeighbor(sector, virtualMap, neighbors, sector.getDownKey(), new Vector3Int(0, -1, 0));

        return neighbors;
    }

    private static void tryAddNeighbor(Sector currentSector, ConcurrentHashMap<Vector3Int, Sector> virtualMap, List<Sector> neighbors, String directionKey, Vector3Int direction) {
        if (directionKey != null && !directionKey.isEmpty()) {
            Vector3Int neighborPosition = currentSector.getPosition().add(direction);
            Sector neighborSector = virtualMap.get(neighborPosition);
            if (neighborSector != null) {
                String oppositeKey = getOppositeDirectionKey(direction);
                if (isValidTransition(neighborSector, oppositeKey)) {
                    neighbors.add(neighborSector);
                }
            }
        }
    }

    private static String getOppositeDirectionKey(Vector3Int direction) {
        if (direction.equals(new Vector3Int(1, 0, 0))) return "BackwardKey";
        if (direction.equals(new Vector3Int(-1, 0, 0))) return "ForwardKey";
        if (direction.equals(new Vector3Int(0, 0, 1))) return "LeftKey";
        if (direction.equals(new Vector3Int(0, 0, -1))) return "RightKey";
        if (direction.equals(new Vector3Int(0, 1, 0))) return "DownKey";
        if (direction.equals(new Vector3Int(0, -1, 0))) return "UpKey";
        return null;
    }

    private static boolean isValidTransition(Sector sector, String key) {
        switch (key) {
            case "ForwardKey": return sector.getForwardKey() != null && !sector.getForwardKey().isEmpty();
            case "BackwardKey": return sector.getBackwardKey() != null && !sector.getBackwardKey().isEmpty();
            case "LeftKey": return sector.getLeftKey() != null && !sector.getLeftKey().isEmpty();
            case "RightKey": return sector.getRightKey() != null && !sector.getRightKey().isEmpty();
            case "UpKey": return sector.getUpKey() != null && !sector.getUpKey().isEmpty();
            case "DownKey": return sector.getDownKey() != null && !sector.getDownKey().isEmpty();
            default: return false;
        }
    }

    private static class SectorNode {
        private final Sector sector;
        private final float gScore;
        private final float fScore;

        public SectorNode(Sector sector, float gScore, float fScore) {
            this.sector = sector;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        public Sector getSector() {
            return sector;
        }

        public float getFScore() {
            return fScore;
        }

        public float getGScore() {
            return gScore;
        }
    }

    private static class SectorNodeComparer implements Comparator<SectorNode> {
        @Override
        public int compare(SectorNode x, SectorNode y) {
            int compare = Float.compare(x.getFScore(), y.getFScore());
            if (compare == 0) {
                compare = Float.compare(x.getGScore(), y.getGScore());
            }
            return compare;
        }
    }

    /**
     * Рассчитывает время, необходимое юниту для перемещения из текущего сектора в новый сектор.
     *
     * @param currentSector  текущий сектор
     * @param newSector      новый сектор
     * @param speed          скорость юнита (единицы расстояния в секунду)
     * @return время, необходимое для перемещения (в секундах)
     */
    public static double calculateTravelTime(Sector currentSector, Sector newSector, double speed) {
        // Получаем позиции секторов
        Vector3Int currentPosition = currentSector.getPosition();
        Vector3Int newPosition = newSector.getPosition();

        // Вычисляем расстояние между секторами
        double distance = currentPosition.distance(newPosition);

        // Рассчитываем время
        if (speed > 0) {
            return distance / speed;
        } else {
            throw new IllegalArgumentException("Скорость должна быть больше 0");
        }
    }
}
