package com.j0schi.server.NI;

import com.j0schi.server.NI.model.NILayer;
import com.j0schi.server.NI.model.NINetwork;
import com.j0schi.server.NI.model.NINeuron;
import com.j0schi.server.NI.model.NISample;
import com.j0schi.server.NI.service.NIService;
import com.j0schi.server.NI.util.SqLiteUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * com.j0schi.server.NI пример из книги:
 */
@AllArgsConstructor
@Component
public class NITest {

    public void test(NIService niService) throws SQLException, ClassNotFoundException {

        niService.initSchema();

        createTestContent(niService);

        //testNetwork(niService);
    }

    public static void testNetwork(NIService niService){
        NINetwork example_book_network = niService.getNINetworkByNetworkName("exampleBookNetwork".toLowerCase());

        NISample completed = null;

        // Пример использования:
        NISample wonderSample = new NISample();
        wonderSample.tableName = "wonder";
        wonderSample.description = "Пример когда следует применить поведение: wonder.";

        NILayer wonderInputLayer = new NILayer();

        ArrayList<NINeuron> input = new ArrayList<NINeuron>();

        NINeuron health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");

        NINeuron knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");

        NINeuron gun = new NINeuron(0);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");

        NINeuron enemy = new NINeuron(3);
        enemy.setDescription("Нейрон отражающий количество противников.");

        wonderInputLayer.getNeurons().add(health);
        wonderInputLayer.getNeurons().add(knifle);
        wonderInputLayer.getNeurons().add(gun);
        wonderInputLayer.getNeurons().add(enemy);

        //"Attack", "Run", "Wander", "Hide"

        NILayer wonderOuputLayer = new NILayer();
        wonderOuputLayer.setLayerType(2);

        NINeuron atack = new NINeuron(0);
        atack.setDescription("Действие : атака");

        NINeuron run = new NINeuron(0);
        run.setDescription("Действие : побег");

        NINeuron wander = new NINeuron(0);
        wander.setDescription("Действие : наблюдение");

        NINeuron hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        wonderOuputLayer.getNeurons().add(atack);
        wonderOuputLayer.getNeurons().add(run);
        wonderOuputLayer.getNeurons().add(wander);
        wonderOuputLayer.getNeurons().add(hide);

        wonderSample.getLayer().add(wonderInputLayer);
        wonderSample.getLayer().add(wonderOuputLayer);

        if(completed != null && completed.getLayer() != null && completed.getLayer().size() > 0) {
            completed = example_book_network.getResult(wonderSample);
            System.out.println(completed.getLayer().get(completed.getLayer().size() - 1).getMax().getDescription());
        }
    }

    /**
     * Метод генерит рандомный набор и выводит принятый сетью результат:
     * @param sample
     */
    public void networkTest(NISample sample){

    }

    public static void createTestContent(NIService niService) throws SQLException, ClassNotFoundException {

        NINetwork bookNetwork = new NINetwork();
        bookNetwork.setTableName("exampleBookNetwork".toLowerCase());
        bookNetwork.setDescription("Нейронная сеть по примеру из замечательной книги");

        //----------------------------------------- Wonder:

        NISample wonderSample = new NISample();
        wonderSample.tableName = "wonder";
        wonderSample.description = "Пример когда следует применить поведение: wonder.";

        NILayer wonderInputLayer = new NILayer();

        NINeuron health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");

        NINeuron knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");

        NINeuron gun = new NINeuron(0);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");

        NINeuron enemy = new NINeuron(0);
        enemy.setDescription("Нейрон отражающий количество противников.");

        wonderInputLayer.getNeurons().add(health);
        wonderInputLayer.getNeurons().add(knifle);
        wonderInputLayer.getNeurons().add(gun);
        wonderInputLayer.getNeurons().add(enemy);

        //"Attack", "Run", "Wander", "Hide"

        NILayer wonderOuputLayer = new NILayer();
        wonderOuputLayer.setLayerType(2);

        NINeuron atack = new NINeuron(0);
        atack.setDescription("Действие : атака");

        NINeuron run = new NINeuron(0);
        run.setDescription("Действие : побег");

        NINeuron wander = new NINeuron(1);
        wander.setDescription("Действие : наблюдение");

        NINeuron hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        wonderOuputLayer.getNeurons().add(atack);
        wonderOuputLayer.getNeurons().add(run);
        wonderOuputLayer.getNeurons().add(wander);
        wonderOuputLayer.getNeurons().add(hide);

        wonderSample.getLayer().add(wonderInputLayer);
        wonderSample.getLayer().add(wonderOuputLayer);

        //----------------------------------------- Wonder 2:

        NISample wonder2Sample = new NISample();
        wonder2Sample.setTableName("wonder2");
        wonder2Sample.setDescription("Пример когда следует применить поведение: wonder2.");

        NILayer wonder2InputLayer = new NILayer();

        health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");

        knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");

        gun = new NINeuron(0);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");

        enemy = new NINeuron(1);
        enemy.setDescription("Нейрон отражающий количество противников.");

        wonder2InputLayer.getNeurons().add(health);
        wonder2InputLayer.getNeurons().add(knifle);
        wonder2InputLayer.getNeurons().add(gun);
        wonder2InputLayer.getNeurons().add(enemy);

        //"Attack", "Run", "Wander", "Hide"

        NILayer wonder2OuputLayer = new NILayer();
        wonder2OuputLayer.setLayerType(2);

        atack = new NINeuron(0);
        atack.setDescription("Действие : атака");

        run = new NINeuron(0);
        run.setDescription("Действие : побег");

        wander = new NINeuron(1);
        wander.setDescription("Действие : наблюдение");

        hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        wonder2OuputLayer.getNeurons().add(atack);
        wonder2OuputLayer.getNeurons().add(run);
        wonder2OuputLayer.getNeurons().add(wander);
        wonder2OuputLayer.getNeurons().add(hide);

        wonder2Sample.getLayer().add(wonder2InputLayer);
        wonder2Sample.getLayer().add(wonder2OuputLayer);

        //----------------------------------------- Atack:

        NISample atackSample = new NISample();
        atackSample.setTableName("atack");
        atackSample.setDescription("Пример когда следует применить поведение: атака.");

        NILayer atackInputLayer = new NILayer();

        health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");

        knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");

        gun = new NINeuron(1);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");

        enemy = new NINeuron(1);
        enemy.setDescription("Нейрон отражающий количество противников.");

        atackInputLayer.getNeurons().add(health);
        atackInputLayer.getNeurons().add(knifle);
        atackInputLayer.getNeurons().add(gun);
        atackInputLayer.getNeurons().add(enemy);

        //"Attack", "Run", "Wander", "Hide"

        NILayer atackOuputLayer = new NILayer();
        atackOuputLayer.setLayerType(2);

        atack = new NINeuron(1);
        atack.setDescription("Действие : атака");

        run = new NINeuron(0);
        run.setDescription("Действие : побег");

        wander = new NINeuron(0);
        wander.setDescription("Действие : наблюдение");

        hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        atackOuputLayer.getNeurons().add(atack);
        atackOuputLayer.getNeurons().add(run);
        atackOuputLayer.getNeurons().add(wander);
        atackOuputLayer.getNeurons().add(hide);

        atackSample.getLayer().add(atackInputLayer);
        atackSample.getLayer().add(atackOuputLayer);

        //----------------------------------------- Atack 2:

        NISample atack2Sample = new NISample();
        atack2Sample.setTableName("atack2");
        atack2Sample.setDescription("Пример когда следует применить поведение: атака2.");

        NILayer atack2InputLayer = new NILayer();

        health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");

        knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");

        gun = new NINeuron(1);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");

        enemy = new NINeuron(2);
        enemy.setDescription("Нейрон отражающий количество противников.");

        atack2InputLayer.getNeurons().add(health);
        atack2InputLayer.getNeurons().add(knifle);
        atack2InputLayer.getNeurons().add(gun);
        atack2InputLayer.getNeurons().add(enemy);

        // "Attack", "Run", "Wander", "Hide"

        NILayer atack2OuputLayer = new NILayer();

        atack2OuputLayer.setLayerType(2);

        atack = new NINeuron(1);
        atack.setDescription("Действие : атака");

        run = new NINeuron(0);
        run.setDescription("Действие : побег");

        wander = new NINeuron(0);
        wander.setDescription("Действие : наблюдение");

        hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        atack2OuputLayer.getNeurons().add(atack);
        atack2OuputLayer.getNeurons().add(run);
        atack2OuputLayer.getNeurons().add(wander);
        atack2OuputLayer.getNeurons().add(hide);

        atack2Sample.getLayer().add(atack2InputLayer);
        atack2Sample.getLayer().add(atack2OuputLayer);

        bookNetwork.getSamples().add(wonderSample);
        bookNetwork.getSamples().add(wonder2Sample);
        bookNetwork.getSamples().add(atackSample);
        bookNetwork.getSamples().add(atack2Sample);

        // Теперь выполняем весь цикл обучения:
        bookNetwork.initialize(bookNetwork.getSamples().get(0));
        bookNetwork.learn(10000);

        niService.insertOrUpdateNINetwork(bookNetwork);

        //SqLiteUtil.insertNINetwork(bookNetwork);
    }

    public static ArrayList<NINetwork> readNINetwork() throws SQLException, ClassNotFoundException {

        ArrayList<String> allNetworkNames =  SqLiteUtil.selectAllNINetworkNameList();
        ArrayList<NINetwork> allNetwork = new ArrayList<NINetwork>();

        if(allNetworkNames != null && !allNetworkNames.isEmpty()) {
            for (int i = 0; i < allNetworkNames.size(); i++) {
                String nextNetworkName = allNetworkNames.get(i);
                NINetwork next = SqLiteUtil.selectNINetwork(nextNetworkName.toLowerCase());
                allNetwork.add(next);
            }
        }

        System.out.println(allNetwork);

        return allNetwork;
    }
}
