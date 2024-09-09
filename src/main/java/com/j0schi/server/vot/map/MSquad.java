package com.j0schi.server.vot.map;

import com.j0schi.server.vot.generation.Sector;
import com.j0schi.server.vot.map.property.MProperty;
import com.j0schi.server.vot.utils.RandomUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Основные параметры отряда (MSquad):
 * Size (Численность): Количество бойцов в отряде. Чем больше отряд, тем больше он может выполнять задач одновременно, но может быть медленнее в реакции.
 * Morale (Боевой дух): Средний уровень боевого духа в отряде. Влияет на готовность вступать в бой или продолжать выполнение сложных приказов.
 * Speed (Скорость): Средняя скорость перемещения отряда. Определяется как среднее значение скорости всех бойцов в отряде.
 * Firepower (Огневая мощь): Влияет на эффективность отряда в бою. Это может быть среднее значение всех показателей урона бойцов, а также наличие тяжелого вооружения.
 * Armor (Броня): Среднее значение защиты отряда. Отряд с высокой броней может принимать больше ударов, прежде чем получит урон.
 * Range (Дальность атаки): Максимальная дистанция, на которой отряд может эффективно действовать.
 * Stealth (Скрытность): Умение отряда скрываться от противника, что может быть полезно для разведки или внезапных атак.

 * Психологические параметры отряда:
 * Aggression (Агрессия): Общая склонность отряда к атакующим действиям. Высокая агрессия может означать, что отряд чаще выбирает наступательные тактики.
 * Fear (Страх): Общая склонность отряда к избеганию боевых действий. При высоком страхе отряд может попытаться избежать конфликта или отказаться от выполнения приказов.
 * Loyalty (Преданность): Готовность отряда следовать приказам командира даже в трудных условиях.

 * Специальные параметры:
 * Supplies (Снабжение): Наличие у отряда боеприпасов, еды, воды и других ресурсов. Недостаток снабжения может резко снизить эффективность отряда.
 */

@Data
@NoArgsConstructor
public class MSquad extends MLive{

    @Override
    public LinkedHashMap<String, MProperty> getDefaultProperties(){

        LinkedHashMap<String, MProperty> result = new LinkedHashMap<String, MProperty>();

        MProperty property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Size";
        property.description = "Размер отряда";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Morale";
        property.description = "Боевой дух";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Speed";
        property.description = "Скорость";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Firepower";
        property.description = "Огневая мощ";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Stealth";
        property.description = "Скрытность";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Aggression";
        property.description = "Агрессивность";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Fear";
        property.description = "Страх";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Loyalty";
        property.description = "Преданность";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Supplies";
        property.description = "Снабжение";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Range";
        property.description = "Дистанционная атака";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "MeleeRange";
        property.description = "Дистанционная атака";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        return result;
    }
}