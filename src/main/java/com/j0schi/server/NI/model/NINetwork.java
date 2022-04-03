package com.j0schi.server.NI.model;

import com.j0schi.server.NI.util.Utils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Универсальная нейронная сеть.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class NINetwork {

    private String name = "default_book_example";

    private String description = "Сеть без имени.";

    private String tableName = "no_name_ni_network";

    private List<NISample> samples = new ArrayList<NISample>();

    // Конвертирую пример из книги:
    private int INPUT_NEURONS = 4;
    private int HIDDEN_NEURONS = 3;
    private int OUTPUT_NEURONS = 4;

    // Смещение
    private int dest = 1;

    private int HIDDEN_LAYER_COUNT = 1;

    // Коэффициент обучения:
    private float LEARN_RATE = 0.2f;

    // Случайные веса:
    private float RAND_WEIGHT = 0f;

    private float RAND_MAX = 0.5f;

    // Входной данные от пользователя:
    private NISample inputSample = null;

    // Веса:
    // Вход скрытых ячеек(со смещением):
    private float[][] wih;

    // Вход выходных ячеек(со смещением):
    private float[][] who;

    // Активаторы:
    private float[] inputs;
    private float[] hidden;
    private float[] outputs;
    private float[] actual;

    // Ошибки:
    private float[] erro;
    private float[] errh;
    private float err = 0f;
    private int sum = 0;

    private boolean learn = false;

    //--------------------------------------- Utils:

    public NINetwork initialize(NISample sample){

        INPUT_NEURONS = sample.getLayer().get(0).getNeurons().size();
        HIDDEN_NEURONS = INPUT_NEURONS - dest;
        OUTPUT_NEURONS = sample.getLayer().get(1).getNeurons().size();

        // Веса
        // Вход скрытых ячеек(со смещением)
        wih = new float[INPUT_NEURONS + dest][HIDDEN_NEURONS];

        // Вход выходных ячеек(со смещением)
        who = new float[HIDDEN_NEURONS + dest][OUTPUT_NEURONS];

        // Активаторы
        inputs = new float[INPUT_NEURONS];
        hidden = new float[HIDDEN_NEURONS];
        outputs = new float[OUTPUT_NEURONS];
        actual = new float[OUTPUT_NEURONS];

        // Ошибки
        erro = new float[OUTPUT_NEURONS];
        errh = new float[HIDDEN_NEURONS];

        // Инициализировать генератор случайных чисел
        assignRandomWeights();
        return this;
    }

    /**
     * Выполнет обучение сети:
     * 100000
     */
    public NINetwork learn(int iterationCount){

        int iterations = 0;

        // Обучить сеть
        while(iterations < iterationCount)
        {
            for (int i = 0; i < samples.size(); i++)
            {
                NISample sample = samples.get(i);

                // тут подаем на входы и выходы "правильные значения"
                for(int j = 0; j < sample.getLayer().get(0).getNeurons().size(); j++){
                    inputs[j] = sample.getLayer().get(0).getNeurons().get(j).getValue();
                }

                for(int k = 0; k < sample.getLayer().get(1).getNeurons().size(); k++){
                    outputs[k] = sample.getLayer().get(1).getNeurons().get(k).getValue();
                }

                feedForward();

                err = 0.0f;

                // Квадратичная ошибка для каждого из выходов:
                for(int m = 0; m < sample.getLayer().get(1).getNeurons().size(); m++){
                    err += Math.sqrt((sample.getLayer().get(1).getNeurons().get(m).getValue() - actual[0]));
                }

                err = 0.5f * err;

                iterations++;

                // Выполняем обучение:
                backPropagate();
            }
        }
        return this;
    }

    // Прямое распространение
    private NINetwork feedForward() {
        int inp, hid, outs;
        float sum;

        // Вычислить вход в скрытый слой:
        for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
            sum = 0f;
            for (inp = 0; inp < INPUT_NEURONS; inp++) {
                sum += inputs[inp] * wih[inp][hid];
            }
            // Добавить смещение
            //for(int i = 0; i < dest; i++){
                sum += wih[INPUT_NEURONS][hid];
            //}
            hidden[hid] = sigmoid(sum);
        }

        // Вычислить вход в выходной слой:
        for (outs = 0; outs < OUTPUT_NEURONS; outs++) {
            sum = 0.0f;
            for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
                sum += hidden[hid] * who[hid][outs];
            }
            // Добавить смещение
            //for(int i = 0; i < dest; i++){
                sum += who[HIDDEN_NEURONS][outs];
            //}
            actual[outs] = sigmoid(sum);
        }
        return this;
    }

    // Обратное распространение(обучение)
    private NINetwork backPropagate() {
        int inp, hid, out;

        // Вычислить ошибку выходного слоя (шаг 3 для выходных ячеек)
        for (out = 0; out < OUTPUT_NEURONS; out++) {
            erro[out] = (outputs[out] - actual[out]) * sigmoidDerivative(actual[out]);
        }

        // Вычислить ошибку скрытого слоя (шаг 3 для скрытого слоя)
        for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
            errh[hid] = 0.0f;
            for (out = 0; out < OUTPUT_NEURONS; out++) {
                errh[hid] += erro[out] * who[hid][out];
            }
            errh[hid] *= sigmoidDerivative(hidden[hid]);
        }

        // Обновить веса для выходного слоя(шаг 4 для выходных ячеек)
        for (out = 0; out < OUTPUT_NEURONS; out++) {
            for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
                who[hid][out] += (LEARN_RATE * erro[out] * hidden[hid]);
            }
            // Обновить смещение
            //for(int i = 0; i < dest; i++) {
                who[HIDDEN_NEURONS][out] += (LEARN_RATE * erro[out]);
            //}
        }

        // Обновить веса для скрытого слоя (шаг 4 для скрытого слоя)
        for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
            for (inp = 0; inp < INPUT_NEURONS; inp++) {
                wih[inp][hid] += (LEARN_RATE * errh[hid] * inputs[inp]);
            }
            // Обновить смещение
            //for(int i = 0; i < dest; i++) {
                wih[INPUT_NEURONS][hid] += (LEARN_RATE * errh[hid]);
            //}
        }
        return this;
    }

    //Метод назначает случайные веса
    private NINetwork assignRandomWeights() {
        int hid, inp, out;

        // Назначаем случайные веса(по идее только первый раз)
        for (inp = 0; inp < INPUT_NEURONS + dest; inp++) {
            for (hid = 0; hid < HIDDEN_NEURONS; hid++) {
                RAND_WEIGHT = getRandomWEIGHT();
                wih[inp][hid] = RAND_WEIGHT;
            }
        }

        for (hid = 0; hid < HIDDEN_NEURONS + dest; hid++) {
            for (out = 0; out < OUTPUT_NEURONS; out++) {
                who[hid][out] = RAND_WEIGHT;
            }
        }
        return this;
    }

    // Получаем случайные веса
    private float getRandomWEIGHT() {
        return (float) Utils.randomFloat(-0.5, 0.5, 0.01);
    }

    // Значение функции сжатия ?
    private float sigmoid(float val) {
        return (float) (1.0f / (1.0f + Math.exp(-val)));
    }

    private float sigmoidDerivative(float val) {
        return (val * (1.0f - val));
    }

    //------------------------ Получение результатов(на вход подается пример с пустым выходным слоем):

    // Функция победитель получает все(по идее моя выборка также длжна работать):
    public int action(float[] vector) {
        int index, sel;
        float max;

        sel = 0;
        max = vector[sel];

        for (index = 1; index < OUTPUT_NEURONS; index++) {
            if (vector[index] > max) {
                max = vector[index];
                sel = index;
            }
        }
        return sel;
    }

    /**
     * Метод просчитывает и проставляет результаты в указанный пример:
     * @param sample
     * @return
     */
    public NISample getResult(NISample sample){

        for(int i = 0; i <  sample.getLayer().get(0).getNeurons().size(); i++){
            inputs[i] = sample.getLayer().get(0).getNeurons().get(i).getValue();
        }

        feedForward();

        for (int i = 0; i < actual.length; i++) {
            sample.getLayer().get(sample.getLayer().size()-1).getNeurons().get(i).setValue(actual[i]);
        }
        return sample;
    }
}
