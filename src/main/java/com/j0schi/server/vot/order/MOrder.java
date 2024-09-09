package com.j0schi.server.vot.order;

import lombok.Data;

@Data
public class MOrder {

    public String id;

    public String name;

    public MOrder smallOrder;

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

    public void orderStateMachine(){}
}
