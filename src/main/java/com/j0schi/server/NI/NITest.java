package NI;

import NI.model.NILayer;
import NI.model.NINetwork;
import NI.model.NINeuron;
import NI.model.NISample;
import NI.service.NIService;
import NI.util.SqLiteUtil;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * com.j0schi.server.NI пример из книги:
 */
@AllArgsConstructor
public class NITest {

    public void test(NIService niService) throws SQLException, ClassNotFoundException {

        createTestContent(niService);

        // ArrayList<NINetwork> all = readNINetwork();
        List<NINetwork> all = niService.getAllNINetwork();

        for(NINetwork network : all){
            network.initialize(network.getSamples().get(0));
            network.learn(10000);
        }

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

        wonderInputLayer.getLayer().add(health);
        wonderInputLayer.getLayer().add(knifle);
        wonderInputLayer.getLayer().add(gun);
        wonderInputLayer.getLayer().add(enemy);

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

        wonderOuputLayer.getLayer().add(atack);
        wonderOuputLayer.getLayer().add(run);
        wonderOuputLayer.getLayer().add(wander);
        wonderOuputLayer.getLayer().add(hide);

        wonderSample.inputLayer = wonderInputLayer;
        wonderSample.outputLayer = wonderOuputLayer;

        NISample completed = null;

        if(all!=null && !all.isEmpty()) {
            completed = all.get(0).calculateResult(wonderSample);
        }

        System.out.println(completed.outputLayer.getMax().getDescription());

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

        ArrayList<NINeuron> input = new ArrayList<NINeuron>();

        NINeuron health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");

        NINeuron knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");

        NINeuron gun = new NINeuron(0);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");

        NINeuron enemy = new NINeuron(0);
        enemy.setDescription("Нейрон отражающий количество противников.");

        wonderInputLayer.getLayer().add(health);
        wonderInputLayer.getLayer().add(knifle);
        wonderInputLayer.getLayer().add(gun);
        wonderInputLayer.getLayer().add(enemy);

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

        wonderOuputLayer.getLayer().add(atack);
        wonderOuputLayer.getLayer().add(run);
        wonderOuputLayer.getLayer().add(wander);
        wonderOuputLayer.getLayer().add(hide);

        wonderSample.setInputLayer(wonderInputLayer);
        wonderSample.setOutputLayer(wonderOuputLayer);


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

        wonder2InputLayer.getLayer().add(health);
        wonder2InputLayer.getLayer().add(knifle);
        wonder2InputLayer.getLayer().add(gun);
        wonder2InputLayer.getLayer().add(enemy);

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

        wonder2OuputLayer.getLayer().add(atack);
        wonder2OuputLayer.getLayer().add(run);
        wonder2OuputLayer.getLayer().add(wander);
        wonder2OuputLayer.getLayer().add(hide);

        wonder2Sample.setInputLayer(wonder2InputLayer);
        wonder2Sample.setOutputLayer(wonder2OuputLayer);

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

        atackInputLayer.getLayer().add(health);
        atackInputLayer.getLayer().add(knifle);
        atackInputLayer.getLayer().add(gun);
        atackInputLayer.getLayer().add(enemy);

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

        atackOuputLayer.getLayer().add(atack);
        atackOuputLayer.getLayer().add(run);
        atackOuputLayer.getLayer().add(wander);
        atackOuputLayer.getLayer().add(hide);

        atackSample.setInputLayer(atackInputLayer);
        atackSample.setOutputLayer(atackOuputLayer);

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

        atack2InputLayer.getLayer().add(health);
        atack2InputLayer.getLayer().add(knifle);
        atack2InputLayer.getLayer().add(gun);
        atack2InputLayer.getLayer().add(enemy);

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

        atack2OuputLayer.getLayer().add(atack);
        atack2OuputLayer.getLayer().add(run);
        atack2OuputLayer.getLayer().add(wander);
        atack2OuputLayer.getLayer().add(hide);

        atack2Sample.setInputLayer(atack2InputLayer);
        atack2Sample.setOutputLayer(atack2OuputLayer);

        bookNetwork.getSamples().add(wonderSample);
        bookNetwork.getSamples().add(wonder2Sample);
        bookNetwork.getSamples().add(atackSample);
        bookNetwork.getSamples().add(atack2Sample);

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
