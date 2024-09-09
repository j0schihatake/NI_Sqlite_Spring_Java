package com.j0schi.server.vot.orders;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс NPC на верхнем уровне стыкует NN с другими различными системами.
 * Например patch через разные order, или другие ордер.
 * Так-же именно он выполняет, старт, прерывание и поиск новых order.
 */
public class Npc{
    //  -------------------------   Animation:

    //public Animator animator;

    //  -------------------------   Controller:

    //public NpcController controller;

    //  -------------------------   Path:

    //private Path path;

    //public PathClient client;

    //  -------------------------   OrderSystem:

    public List<Order> orders = new ArrayList<>();

    public Order activeOrder;

    public Order prevOrder;

    public NpcState state;
    public enum NpcState
    {
        die,
        live
    }

    public boolean isMonoBehaviour = false;

    public void Start()
    {
        //path = Path.Instance;
        //controller = this.gameObject.GetComponent<NpcController>();

        defaultInit();
    }

    public void Update()
    {
        switch(state) {
            case die:
                break;
            case live:

                if(activeOrder != null && (activeOrder.state == Order.orderState.passed ||
                        activeOrder.state == Order.orderState.failed ||
                        activeOrder.state == Order.orderState.complete))
                {
                    prevOrder = activeOrder;
                    activeOrder = null;
                }

                if(orders.size()> 0)
                {
                    if(activeOrder == null)
                    {
                        activeOrder = orders.get(orders.size() - 1);
                        activeOrder.orderAwakeMethod();
                        activeOrder.orderStartMethod();
                    }
                }

                if(activeOrder != null)
                {
                    activeOrder.orderUpdateMethod();
                    activeOrder.orderStateMachine();
                }

                break;
        }
    }

    public void LateUpdate()
    {
        switch(state) {
            case die:
                break;
            case live:

                if(activeOrder != null)
                {
                    activeOrder.orderLastUpdateMethod();
                }

                break;
        }
    }

    public void FixedUpdate()
    {
        switch(state) {
            case die:
                break;
            case live:

                if(activeOrder != null)
                {
                    activeOrder.orderFixedUpdateMethod();
                }

                break;
        }
    }

    /**
     *  Для перехода от Monobehavior к коду:
     */
    public void defaultInit() {

        // Если реализации через MonoBehaviour:
        if(isMonoBehaviour)
        {
            //this.gameObject.AddComponent<Plane3DFilter>();
            //this.gameObject.AddComponent<PathClient>();

            //client = GetComponent<PathClient>();

            //Plane3DFilter filter = GetComponent<Plane3DFilter>();

            //client.filters.Add(filter);
        }
        else
        {
            // Если не MonoBehaviour:

            // Создаем новый обьект представления компонента поиска пути для данного Npc
            //client = new PathClient();

            // Настройка доступных данному Unit-у фильтров поиска
            // (характеризует доступные способы перемещения)
            //client.filters = new List<PathFilter>();
            //PathFilter next = new Plane3DFilter();
            //client.filters.Add(next);
        }
        // Мировые кординаты местоположения данного Npc.
        //client.start = new Vector3Int(3,2,31);
        //client.alghoritm = Path.AlgorithmType.ASTAR;
        //client.pathState = PathClient.PathState.waitOrder;
    }
}