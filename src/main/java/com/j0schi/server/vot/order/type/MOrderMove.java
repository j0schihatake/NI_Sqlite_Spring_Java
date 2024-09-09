package com.j0schi.server.vot.order.type;

import com.j0schi.server.vot.World;
import com.j0schi.server.vot.generation.Sector;
import com.j0schi.server.vot.generation.Vector3;
import com.j0schi.server.vot.map.AStarPathFinder;
import com.j0schi.server.vot.map.MLive;
import com.j0schi.server.vot.order.MOrder;
import lombok.Data;

@Data
public class MOrderMove extends MOrder {

    public Sector current;
    public Sector target;

    public MLive owner;

    @Override
    public void orderStateMachine(){

        switch (state){
            case start:
                owner.mapPath = AStarPathFinder.findPath(current, target, World.gameWorld.virtualMap);
                state = orderState.update;
                break;

            case update:
                moveAlongPath();
                break;

            case complete:
            case passed:
                owner.activeOrder = null;
                owner.orders.remove(this);
                owner.state = MLive.State.live;                                                                         // Путь пройден или отменен, возвращаемся в состояние live
                break;
        }
    }

    public void moveAlongPath() {

        // Если путь пуст, возвращаемся в состояние live
        if (owner.mapPath.isEmpty()) {
            owner.state = MLive.State.live;
            return;
        }

        // Обновляем время, проведенное в текущем секторе
        owner.timeInCurrentSector += 0.01f;

        // Проверяем, если юнит пробыл в текущем секторе больше времени, чем требуется для перехода
        if (owner.timeInCurrentSector >= owner.timeToNextSector) {

            owner.activeSector = owner.mapPath.remove(0);
            owner.timeInCurrentSector = 0;
            owner.updateSectorLives(owner.activeSector);

            // Проверяем, есть ли еще секторы в пути
            if (!owner.mapPath.isEmpty()) {
                calculateTimeToNextSector();                                                                            // Рассчитываем время до следующего сектора
            } else {
                state = orderState.complete;
            }
        }
    }

    public void calculateTimeToNextSector() {
        if(owner.mapPath.size() > 0)
        {
            Sector nextSector = owner.mapPath.get(0);
            float distance = Vector3.distance(owner.activeSector.position.toVector3(), nextSector.position.toVector3());
            owner.timeToNextSector = distance / owner.speed;
        }
    }
}
