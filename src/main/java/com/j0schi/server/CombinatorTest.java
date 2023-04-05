package com.j0schi.server;

import java.util.*;

public class CombinatorTest {

    /**
     *     https://habr.com/ru/articles/515352/
     */
    public static void main(String[] args) {
        Map<String, List<String>> source = Map.of(
                "model", Arrays.asList("audy", "bmw", "toyota", "vw"),
                "color", Arrays.asList("red", "green", "blue", "yellow", "pink"),
                "engine", Arrays.asList("diesel", "gasoline", "hybrid"),
                "transmission", Arrays.asList("manual", "auto", "robot")
        );

        Combinator<String, String> combinator = new Combinator<>(source);
        List<Map<String, String>> result = combinator.makeCombinations();

        for(Map variant : result){
            System.out.println(variant);
        }
    }

    public static class Combinator<K,V> {

        //Тут в виде ассоциативного массива хранятся исходные данные
        private Map<K, List<V>> sources;

        //Итератор для перебора параметров. В нашем случае это обязательно
        //ListIterator, т.к. потребуется вызывать метод previous
        private ListIterator<K> keysIterator;

        //Счетчик текущего положения в итерации для каждого параметра
        //где ключ - имя параметра, а значение - текущая позиция в наборе элементов
        private Map<K, Integer> counter;

        //Тут будут храниться итоговые наборы
        private List<Map<K,V>> result;


        public Combinator(Map<K, List<V>> sources) {
            this.sources = sources;
            counter = new HashMap<>();
            keysIterator = new ArrayList<>(sources.keySet())
                    .listIterator();
        }

        //Этот метод вызываем для генерации набора
        public List<Map<K,V>> makeCombinations() {
            result = new ArrayList<>();
            //Запускаем перебор параметров
            loop();
            return result;
        }

        private void loop(){
            //Проверяем, есть ли еще параметры в источнике
            if(keysIterator.hasNext()){

                //Сдвигаем счетчик вперед
                K key = keysIterator.next();

                //Активируем набор элементов (указываем в счетчике,
                //что находимся на первом элементе набора)
                counter.put(key, 0);


                //Перебираем элементы набора
                while(counter.get(key) < sources.get(key).size()){
                    //Рекурсивно вызываем метод loop чтобы активировать следующий набор элементов
                    loop();

                    //Сдвигаем счетчик элементов набора
                    counter.put(key, counter.get(key) + 1);
                }

                //Если мы уже перебрали элементы набора - сдвигаем итератор параметров назад
                keysIterator.previous();
            }
            else{
                //Если параметров в источнике нет, т.е. мы активировали все наборы попеременно
                //заполняем очередной итоговый набор
                fill();
            }
        }

        //В этом методе наполняем очередной итоговый набор
        private void fill() {
            Map<K,V> variant = new HashMap<>();

            //Перебираем все параметры
            for(K key : sources.keySet()){
                //Получаем значение текущего элемента в наборе
                Integer position = counter.get(key);

                //Вставляем в итоговый набор
                variant.put(key, sources.get(key).get(position));
            }

            result.add(variant);
        }

    }
}
