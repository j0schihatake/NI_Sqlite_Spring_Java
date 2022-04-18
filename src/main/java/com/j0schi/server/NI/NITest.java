package com.j0schi.server.NI;

import com.j0schi.server.NI.model.NILayer;
import com.j0schi.server.NI.model.NINetwork;
import com.j0schi.server.NI.model.NINeuron;
import com.j0schi.server.NI.model.NISample;
import com.j0schi.server.NI.db.service.NIService;
import com.j0schi.server.NI.db.util.SqLiteUtil;
import com.j0schi.server.NI.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * com.j0schi.server.NI пример из книги:
 */
@AllArgsConstructor
@Component
public class NITest {

    public void test(NIService niService) throws SQLException, ClassNotFoundException, InterruptedException {

        //niService.initSchema();

        testNetwork(createTestContent(niService));
    }

    public static void testNetwork(NINetwork niNetwork) throws InterruptedException {

        for(int i = 0; i < 30; i++){
            System.out.println("NINetwork." + niNetwork.getDescription() + " next();");
            System.out.println("");
            NISample nextRand = niNetwork.first();
            System.out.println("Ситуация следующая:");
            for(NINeuron neuron : nextRand.input().getNeurons()){
                neuron.setValue(Utils.getRandomNumber(neuron.minRandom, neuron.maxRandom));
                System.out.println(neuron.getDescription() + ":   " + neuron.getValue());
            }
            NISample resultSample = niNetwork.getResult(nextRand);
            int maxIndex = resultSample.output().getMax();
            NINeuron max = resultSample.output().getNeurons().get(maxIndex);
            System.out.println("Немного подумав принимаем решение: " + max.getDescription());
            Thread.sleep(5000);

        }

    }

    public static NINetwork createTestContent(NIService niService) throws SQLException, ClassNotFoundException {

        NINetwork bookNetwork = new NINetwork();
        bookNetwork.setTableName("exampleBookNetwork".toLowerCase());
        bookNetwork.setDescription("Нейронная сеть по примеру из замечательной книги");

        NISample nextSample = null;

        //----------------------------------------- Формируем первый пример:

        NISample firstSample = new NISample();
        firstSample.tableName = "wonder";
        firstSample.description = "Пример когда следует применить поведение: wonder.";

        NILayer wonderInputLayer = new NILayer();

        NINeuron health = new NINeuron(2);
        health.setDescription("Нейрон отражающий состояние здоровья.");
        health.minRandom = 0;
        health.maxRandom = 3;

        NINeuron knifle = new NINeuron(0);
        knifle.setDescription("Нейрон отражающий наличие ножа.");
        knifle.minRandom = 0;
        knifle.maxRandom = 2;

        NINeuron gun = new NINeuron(0);
        gun.setDescription("Нейрон отражающий наличие стрелкового оружия.");
        gun.minRandom = 0;
        gun.maxRandom = 2;

        NINeuron enemy = new NINeuron(0);
        enemy.setDescription("Нейрон отражающий количество противников.");
        enemy.minRandom = 0;
        enemy.maxRandom = 4;

        wonderInputLayer.getNeurons().add(health);
        wonderInputLayer.getNeurons().add(knifle);
        wonderInputLayer.getNeurons().add(gun);
        wonderInputLayer.getNeurons().add(enemy);

        //"Attack", "Run", "Wander", "Hide"

        NILayer firstOutputLayer = new NILayer();
        firstOutputLayer.setLayerType(2);

        NINeuron atack = new NINeuron(0);
        atack.setDescription("Действие : атака");

        NINeuron run = new NINeuron(0);
        run.setDescription("Действие : побег");

        NINeuron wander = new NINeuron(1);
        wander.setDescription("Действие : наблюдение");

        NINeuron hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        firstOutputLayer.getNeurons().add(atack);
        firstOutputLayer.getNeurons().add(run);
        firstOutputLayer.getNeurons().add(wander);
        firstOutputLayer.getNeurons().add(hide);

        firstSample.getLayer().add(wonderInputLayer);
        firstSample.getLayer().add(firstOutputLayer);

        bookNetwork.addSample(firstSample);

        // -------------------Далее через дублирование другие примеры при необходимости:

        // Пример 2:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(2);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(1);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 3:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(2);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(1);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(1);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 4:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(2);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(1);
        nextSample.input().getNeurons().get(3).setValue(2);

        nextSample.output().getNeurons().get(0).setValue(1);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 5:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(2);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(2);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 6:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(2);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(1);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 7:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(2);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(1);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 8:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(1);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(0);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(1);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 9:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(1);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 10:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(1);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(1);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(1);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 11:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(1);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(1);
        nextSample.input().getNeurons().get(3).setValue(2);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 12:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(1);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(2);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 13:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(1);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 14:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(0);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(0);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(1);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 15:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(0);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 16:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(0);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(1);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Пример 17:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(0);
        nextSample.input().getNeurons().get(1).setValue(0);
        nextSample.input().getNeurons().get(2).setValue(1);
        nextSample.input().getNeurons().get(3).setValue(2);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(1);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 18:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(0);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(2);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(1);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(0);
        bookNetwork.addSample(nextSample);

        // Пример 19:
        nextSample = firstSample.dublicate();
        nextSample.input().getNeurons().get(0).setValue(0);
        nextSample.input().getNeurons().get(1).setValue(1);
        nextSample.input().getNeurons().get(2).setValue(0);
        nextSample.input().getNeurons().get(3).setValue(1);

        nextSample.output().getNeurons().get(0).setValue(0);
        nextSample.output().getNeurons().get(1).setValue(0);
        nextSample.output().getNeurons().get(2).setValue(0);
        nextSample.output().getNeurons().get(3).setValue(1);
        bookNetwork.addSample(nextSample);

        // Теперь выполняем весь цикл обучения:
        bookNetwork.initialize(bookNetwork.getSamples().get(0));
        bookNetwork.learn(10000);

        //niService.insertOrUpdateNINetwork(bookNetwork);

        // SqLiteUtil.insertNINetwork(bookNetwork);

        return bookNetwork;
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
