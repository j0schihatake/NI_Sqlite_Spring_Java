package com.j0schi.server.NI.util;
import com.j0schi.server.NI.components.NILayer;
import com.j0schi.server.NI.components.NINetwork;
import com.j0schi.server.NI.components.NINeuron;
import com.j0schi.server.NI.components.NISample;

import java.sql.*;
import java.util.ArrayList;

public class SqLiteUtil {

    /**
     * conn.Conn();
     * 		conn.CreateDB();
     * 		conn.WriteDB();
     * 		conn.ReadDB();
     * conn.CloseDB();
     */

    public static final String DEFAULT_DB_NAME = "ExampleForBook";

    public static final String TOTAL_TABLE_NAME = "networks";

    public static final String DEFAULT_LAYER_NAME = "_layers";

    /**
     * Версия БД версия DB чтобы не потерять примеры при изменениях
     */
    public static int bdVersion = 0;

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    /**
     * START connections to db.
     * @param dbName
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void conn(String dbName) throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".s3db");

        System.out.println("База Подключена!");
    }

    /**
     * CLOSE connections to db.
     * @throws SQLException
     */
    public static void closeDB() throws SQLException
    {
        if(conn!=null)
            conn.close();

        if(statmt!=null)
            statmt.close();

        if(resSet!=null)
            resSet.close();

        System.out.println("Соединения закрыты.");
    }

    //-----------------------------------------------------NILayer:

    /**
     * CREATE new NILayer table
     */
    public static void createNILayerTable(String layerTableName) throws SQLException {

        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        try {
            statmt = conn.createStatement();

            StringBuilder builder = new StringBuilder();
            builder.append("CREATE TABLE if not exists '");
            builder.append(layerTableName);
            builder.append("' (");
            builder.append("'id' INTEGER PRIMARY KEY AUTOINCREMENT,");
            builder.append("'sample_table_name' Text,");
            builder.append("'layer_type' Text,");
            builder.append("'description' Text,");
            builder.append("'value' Text);");

            statmt.execute(builder.toString());
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Layer saved from " + layerTableName);
    }

    /**
     * INSERT new NILayer
     * @param layer
     * @throws SQLException
     */
    public static void insertNILayer(String sampleName, NILayer layer) throws SQLException
    {

        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        try {
            createNILayerTable(layer.getTableName());

            for (int i = 0; i < layer.getLayer().size(); i++) {
                StringBuilder builder = new StringBuilder();
                NINeuron next = layer.getLayer().get(i);
                builder.append("INSERT INTO ");
                builder.append(layer.getTableName());
                builder.append("(");
                builder.append("'sample_table_name', ");
                builder.append("'layer_type', ");
                builder.append("'description', ");
                builder.append("'value'");
                builder.append(") VALUES (");
                builder.append("'" + sampleName + "',");
                builder.append("'" + layer.getLayerType() + "',");
                builder.append("'" + next.description + "',");
                builder.append("'" + next.value + "');");

                statmt = conn.createStatement();
                statmt.execute(builder.toString());
            }
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Layer добавлен в " + layer.getTableName());
    }

    /**
     * SELECT NILayer
     */
    public static NILayer readNILayer(String tableName, String layerTableName, String type) throws SQLException {

        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        NILayer resultLayer = null;

        try {

            resultLayer = new NILayer();

            statmt = conn.createStatement();
            resSet = statmt.executeQuery("SELECT * FROM " + layerTableName + " WHERE sample_table_name = '" + tableName + "' AND layer_type = " + type + ";");

            while (resSet.next()) {
                int id = resSet.getInt("id");
                String description = resSet.getString("description");
                String value = resSet.getString("value");

                NINeuron nextNeuron = new NINeuron();
                nextNeuron.description = description;
                nextNeuron.value = Float.parseFloat(value);

                if (!resultLayer.getLayer().contains(nextNeuron))
                    resultLayer.getLayer().add(nextNeuron);
            }
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Layer loaded from " + layerTableName);

        return resultLayer;
    }

    //-----------------------------------------------------NISample:

    /**
     * CREATE new table NISample
     * @param sampleTableName
     * @throws SQLException
     */
    public static void createNISampleTable(String sampleTableName) throws SQLException {

        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        try {
            statmt = conn.createStatement();

            StringBuilder builder = new StringBuilder();
            builder.append("CREATE TABLE if not exists '");
            builder.append(sampleTableName);
            builder.append("' (");
            builder.append("'id' INTEGER PRIMARY KEY AUTOINCREMENT, ");
            builder.append("'description' Text, ");
            builder.append("'network_name' Text, ");
            builder.append("'table_name' Text, ");
            builder.append("'input_layer_table_name' Text, ");
            builder.append("'output_layer_table_name' Text);");

            statmt.execute(builder.toString());
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Create table saved from Sample.");
    }

    /**
     * INSERT new NISample
     * @param sample
     * @throws SQLException
     */
    public static void insertNISample(String sampleTableName, NISample sample) throws SQLException
    {
        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        try {
            sample.inputLayer.setTableName(sampleTableName + DEFAULT_LAYER_NAME);
            //sample.inputLayer.setTableName(sampleTableName + NILayer.INPUT_LAYER_PREFIX);
            sample.outputLayer.setTableName(sampleTableName + DEFAULT_LAYER_NAME);
            //sample.outputLayer.setTableName(sampleTableName + NILayer.OUTPUT_LAYER_PREFIX);
            sample.outputLayer.setLayerType(2);

            String fullSampleTableName = sampleTableName + NISample.SAMPLE_PREFIX;

            createNISampleTable(fullSampleTableName);

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO '");
            builder.append(fullSampleTableName);
            builder.append("' (");
            builder.append("'description', ");
            builder.append("'network_name', ");
            builder.append("'table_name', ");
            builder.append("'input_layer_table_name', ");
            builder.append("'output_layer_table_name' ");
            builder.append(") VALUES (");
            builder.append("'" + sample.description + "', ");
            builder.append("'" + sampleTableName + "', ");
            builder.append("'" + sample.tableName + "', ");
            builder.append("'" + sample.inputLayer.getTableName() + "', ");
            builder.append("'" + sample.outputLayer.getTableName() + "'");
            builder.append(");");

            statmt.execute(builder.toString());

            // Сохранение нейронов по слоям:
            insertNILayer(sample.tableName, sample.inputLayer);
            insertNILayer(sample.tableName, sample.outputLayer);
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Добавлен новый пример NISample в таблицу " + sample.tableName);
    }

    /**
     * SELECT NISample
     * @return
     * @throws SQLException
     */
    public static ArrayList<NISample> selectNISamplesList(String networkTableName) throws SQLException
    {
        ArrayList<NISample> result = new ArrayList<NISample>();

        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        try{
            resSet = statmt.executeQuery("SELECT * FROM " + networkTableName.toLowerCase() + NISample.SAMPLE_PREFIX + " WHERE network_name = '" + networkTableName.toLowerCase() + "';");

            while (resSet.next()) {
                NISample nextSample = new NISample();
                NILayer inputLayer = new NILayer();
                NILayer outputLayer = new NILayer();

                nextSample.description = resSet.getString("description");
                nextSample.tableName = resSet.getString("table_name");
                inputLayer.setTableName(resSet.getString("input_layer_table_name"));
                outputLayer.setTableName(resSet.getString("output_layer_table_name"));

                nextSample.inputLayer = readNILayer(nextSample.tableName, inputLayer.getTableName(), inputLayer.getLayerType() + "");
                nextSample.outputLayer = readNILayer(nextSample.tableName, outputLayer.getTableName(), outputLayer.getLayerType() + "");

                result.add(nextSample);
            }
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Загружен новый пример NISample из базы.");

        return result;
    }

    //----------------------------------------------- NetworksTotal:

    /**
     * CREATE new Networks table
     * @throws SQLException
     */
    public static void createNINetworksTable() throws SQLException, ClassNotFoundException {

        Statement statmt = conn.createStatement();
        ResultSet resSet = null;

        try {

            statmt = conn.createStatement();

            StringBuilder builder = new StringBuilder();
            builder.append("CREATE TABLE if not exists '");
            builder.append(TOTAL_TABLE_NAME);
            builder.append("' (");
            builder.append("'id' INTEGER PRIMARY KEY AUTOINCREMENT,");
            builder.append("'table_name' Text, ");
            builder.append("'description' Text, ");
            builder.append("'hidden_layer_count' Text);");

            statmt.execute(builder.toString());
        }finally {
            if(resSet!=null)
                resSet.close();
            if(statmt!=null)
                statmt.close();
        }

        System.out.println("Глобальная таблица наименований сетей создана или уже существует.");
    }

    /**
     * Insert NINetwork to total table
     * @param network
     * @throws SQLException
     */
    public static void insertNINetwork(NINetwork network) throws SQLException, ClassNotFoundException {

        conn(DEFAULT_DB_NAME);

        createNINetworksTable();

        statmt = conn.createStatement();

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO '");
        builder.append(TOTAL_TABLE_NAME);
        builder.append("' (");
        builder.append("'table_name', ");
        builder.append("'description', ");
        builder.append("'hidden_layer_count') VALUES (");
        builder.append("'" + network.tableName + "',");
        builder.append("'" + network.description + "',");
        builder.append("'" + network.HIDDEN_LAYER_COUNT + "'");
        builder.append(");");

        statmt.execute(builder.toString());

        for(int i =0; i < network.samples.size(); i++){
            NISample nextSample = network.samples.get(i);
            insertNISample(network.tableName, nextSample);
        }

        closeDB();

        System.out.println("NINetwork insert successful.");
    }

    public static NINetwork selectNINetwork(String networkName) throws SQLException, ClassNotFoundException {

        conn(DEFAULT_DB_NAME);

        NINetwork network = new NINetwork();

        statmt = conn.createStatement();

        resSet = statmt.executeQuery("SELECT * FROM " + TOTAL_TABLE_NAME + " WHERE table_name = '" + networkName + "';");

        while(resSet.next())
        {
            network.tableName = resSet.getString("table_name");
            network.description = resSet.getString("description");
            network.HIDDEN_LAYER_COUNT = Integer.parseInt(resSet.getString("hidden_layer_count"));
        }

        ArrayList<NISample> samples = selectNISamplesList(networkName);

        network.samples = samples;

        closeDB();

        System.out.println("NINetwork " + networkName + " загружена.");

        return network;
    }

    public static ArrayList<String> selectAllNINetworkNameList() throws SQLException, ClassNotFoundException {

        conn(DEFAULT_DB_NAME);

        ArrayList<String> result = new ArrayList<String>();

        statmt = conn.createStatement();

        resSet = statmt.executeQuery("SELECT * FROM " + TOTAL_TABLE_NAME + ";");

        while(resSet.next())
        {
            String nextName = resSet.getString("table_name");
            result.add(nextName);
        }

        closeDB();

        System.out.println("Список имен имеющихся сетей загружен.");

        return result;
    }

    //------------------------------------------  Utilits:

}
