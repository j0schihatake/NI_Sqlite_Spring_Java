package com.j0schi.server.vot.orders;

import com.j0schi.server.vot.generation.Vector3Int;

public abstract class Order
{
    public Npc npc;

    //public GameObject orderObject;

    public Vector3Int start;

    public Vector3Int end;

    public String type;

    public int priority;

    /**
     *  Модель состояний приказов(Order):
     */
    public orderState state;
    public enum orderState {
        awake,
        start,
        update,
        fixedUpdate,
        added,
        passed,
        failed,
        complete,
    }

    public boolean isActual() {
        boolean isActual = false;
        switch(state) {
            case added:
                isActual = true;
                break;
            case passed:
            case failed:
            case complete:
                isActual = false;
                break;
        }
        return isActual;
    }

    public abstract void orderAwakeMethod();

    public abstract void orderStartMethod();

    public abstract void orderUpdateMethod();

    public abstract void orderLastUpdateMethod();

    public abstract void orderFixedUpdateMethod();

    /**
     *  Метод описывает изменение состояния ордера.
     */
    public abstract void orderStateMachine();
}
