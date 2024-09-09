package com.j0schi.server.vot.map.property;

import com.j0schi.server.vot.map.MLive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
public class OneTimeProperty extends MProperty {

    private boolean isActive = true;

    @Override
    public void Update() {
        if (bonus != 0 && isActive) {
            value += bonus;
            value = Math.max(0, Math.min(value, maxValue));
            isActive = false;  // После применения эффекта деактивируем его

            // Самоудаление из коллекции свойств
            removeFromProperties();
        }
    }

    // Метод для самоудаления свойства из коллекции
    private void removeFromProperties() {
        // Найдем объект владельца, предположим что коллекция properties принадлежит MLive
        // Воспользуемся механизмом рефлексии, чтобы получить доступ к полю properties
        try {
            MLive owner = getOwner();
            if (owner != null) {
                owner.properties.remove(name);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // Метод для получения ссылки на владельца (MLive), использует рефлексию
    private MLive getOwner() throws Exception {
        // Получаем стек вызовов и ищем объект класса MLive
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            Class<?> clazz = Class.forName(element.getClassName());
            if (MLive.class.isAssignableFrom(clazz)) {
                // Рефлексия для получения ссылки на MLive
                for (Object obj : clazz.getFields()) {
                    if (obj instanceof MLive) {
                        return (MLive) obj;
                    }
                }
            }
        }
        return null;
    }
}
