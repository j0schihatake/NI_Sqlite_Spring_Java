package com.j0schi.server.vot.map.property;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MProperty {

    public String id;                                                                                                       // Уникальный id
    public String name;
    public String description;

    public int value;
    public int bonus;                                                                                                       // Величина изменения при каждом вызове live
    public int maxValue = 100;                                                                                              // Предел по умолчанию

    public MProperty(String name, int initialValue, int bonus, int maxValue)
    {
        this.name = name;
        this.value = initialValue;
        this.bonus = bonus;
        this.maxValue = maxValue;
    }

    // Метод для обновления значения свойства
    public void Update()
    {
        if(bonus != 0)
        {
            value += bonus;
            // Ограничиваем значение, если оно должно быть в определенном диапазоне
            value = Math.max(0, Math.min(value, maxValue));
        }
    }

    // Метод для изменения бонуса
    public void SetBonus(int bonus)
    {
        bonus = bonus;
    }
}
