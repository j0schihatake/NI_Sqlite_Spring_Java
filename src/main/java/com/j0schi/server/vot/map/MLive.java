package com.j0schi.server.vot.map;

import com.j0schi.server.NI.model.*;
import com.j0schi.server.vot.generation.Sector;
import com.j0schi.server.vot.generation.Vector3;
import com.j0schi.server.vot.map.property.MProperty;
import com.j0schi.server.vot.order.MOrder;
import com.j0schi.server.vot.orders.Order;
import com.j0schi.server.vot.utils.RandomUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Основные физические параметры:
 * Health (Здоровье): Отвечает за количество очков жизни существа. Чем меньше здоровье, тем более осторожным или агрессивным может становиться существо.
 * Stamina (Выносливость): Влияет на скорость и продолжительность действий, таких как бег или атака. Существо может менять поведение, если выносливость на исходе (например, начинает отдыхать).
 * Strength (Сила): Определяет физическую мощь существа, влияет на силу атаки, способность переносить тяжести и т.д.
 * Agility (Ловкость): Влияет на скорость движения и уклонение от атак. Существо с высокой ловкостью может быть более активным, избегать столкновений или атак.
 * Armor (Броня): Уменьшает урон, получаемый существом. Повышенная броня может сделать существо более агрессивным, так как оно чувствует себя защищенным.
 * Speed (Скорость): Определяет, как быстро существо может перемещаться между секторами. При высокой скорости оно может избегать опасности или быстро догонять жертву.

 * Психологические/ментальные параметры:
 * Aggression (Агрессия): Влияет на склонность к атаке или защитному поведению. Чем выше агрессия, тем чаще существо будет выбирать атакующие действия.
 * Fear (Страх): Определяет, как часто существо будет избегать конфликтов. При высоком уровне страха существо может убегать или избегать врагов.
 * Curiosity (Любопытство): Влияет на поведение в отношении исследования новых территорий или взаимодействия с новыми объектами.
 * Intelligence (Интеллект): Определяет способность существа принимать сложные решения, решать головоломки, избегать ловушек или лучше управлять своими ресурсами.
 * Courage (Отвага): Чем выше отвага, тем больше существо готово рисковать в опасных ситуациях.
 * Focus (Концентрация): Влияет на точность действий, например, стрельбы или выполнения сложных задач. Низкий фокус может сделать существо менее эффективным в бою.
 * Loyalty (Преданность): Определяет степень привязанности к группе или союзникам. Высокая преданность может заставить существо помогать другим даже в опасных ситуациях.
 * Temper (Темперамент): Влияет на реакцию на раздражающие факторы. Высокий темперамент может привести к вспышкам гнева, а низкий – к спокойствию и рассудительности.

 * Социальные параметры:
 * Sociality (Социальность): Влияет на склонность к взаимодействию с другими существами. Высокая социальность может побуждать существо к объединению в группы.
 * Dominance (Доминирование): Определяет склонность существа к лидерству. Высокий уровень доминирования может заставить его требовать подчинения от других существ.
 * Empathy (Эмпатия): Влияет на способность существа понимать других и помогать им. Высокий уровень эмпатии может привести к тому, что существо будет защищать более слабых.

 * Специальные параметры:
 * Hunger (Голод): Влияет на мотивацию искать пищу. Чем больше голод, тем активнее существо будет искать еду.
 * Thirst (Жажда): Аналогично голоду, заставляет существо искать воду или другие источники жидкости.
 * Fatigue (Усталость): Влияет на необходимость отдыха. Чем выше усталость, тем больше времени существо будет проводить в поисках укрытия для сна.
 * Morale (Боевой дух): Влияет на готовность к бою. Низкий боевой дух может заставить существо избегать столкновений, даже если оно физически способно драться.

 * Параметры для создания характера существа:
 * Pacifism (Пацифизм): Существо, которое избегает насилия, предпочитает решать конфликты мирными способами.
 * Patience (Терпение): Влияет на способность существа выжидать удобного момента для атаки или других действий.
 * Vigilance (Бдительность): Существо с высокой бдительностью может быстро реагировать на изменение обстановки или неожиданные угрозы.
 * Resourcefulness (Находчивость): Влияет на способность находить выходы из трудных ситуаций, использовать окружающую среду для решения задач.

 * Параметры для реализации системы пороков:
 * Похоть - Lust
 * Чревоугодие - Gluttony
 * Жадность - Greed
 * Лень - Laziness
 * Гнев - Anger
 * Зависть - Envy

 * Доп параметры и абстракции для принятия решения:
 */

@Data
@NoArgsConstructor
public abstract class MLive
{
    public String id;
    public String name;
    public String description;

    private String raceId;                                                                                                  //  Ссылка на нашу расу
    private String squadId;                                                                                                 //  Ссылка на наш отряд
    private String fractionId;                                                                                              //  Ссылка на нашу фракцию

    // Хранение свойств сущности
    public LinkedHashMap<String, MProperty> properties = new LinkedHashMap<String, MProperty>();
    public ArrayList<Sector> mapPath = new ArrayList<>();

    public ArrayList<MSquad> squads;
    public ArrayList<MLive> lives;

    // Система приказов:
    public ArrayList<MOrder> orders = null;
    public MOrder activeOrder = null;

    public Sector activeSector = null;

    public boolean isNN = true;
    public boolean isDefault;
    public boolean isAllEnemies = false;

    public NINetwork network;
    public String networkKey;

    public float speed = 10f;                                                                                               // скорость перемещения объекта
    public float timeInCurrentSector = 0f;                                                                                  // время, проведенное в текущем секторе
    public float timeToNextSector = 0f;                                                                                     // время, необходимое для перехода в следующий сектор

    private int frameCounter = 0;
    private int framesToSkip = 3;                                                                                           // Сколько кадров пропускать

    public enum State
    {
        live,
        die,
        pause,
        move
    }

    public State state = State.pause;

    // Дефолтная реализация метода live
    public void live()
    {
        frameCounter++;
        if(frameCounter >= framesToSkip)
        {
            frameCounter = 0; // Сбрасываем счетчик

            // Обновляем все свойства
            for(val property : properties.values())
            {
                property.Update();
            }

            switch(state)
            {
                case live:
                    // Принятие решений на основе NINetwork.
                    if(orders.isEmpty() && activeOrder == null){

                    }else{
                        activeOrder.orderStateMachine();
                    }
                    break;
                case pause:
                    // Логика паузы
                    break;
                case die:
                    // Логика смерти
                    break;
            }
        }
    }

    public void init(){
        prepareNetwork();
    }

    public NINetwork prepareNetwork(){

        NINetwork network = new NINetwork();
        network.setTableName("exampleBookNetwork".toLowerCase());
        network.setDescription("Нейронная сеть по примеру из замечательной книги");

        //----------------------------------------- Формируем первый пример:

        NISample sample = new NISample();
        sample.tableName = "wonder";
        sample.description = "Пример когда следует применить поведение: wonder.";

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

        sample.getLayer().add(wonderInputLayer);
        sample.getLayer().add(firstOutputLayer);

        network.addSample(sample);
        // Теперь выполняем весь цикл обучения:
        network.initialize(network.getSamples().get(0));
        network.learn(10000);
        return network;
    }

    /**
     * Подготовка дефолтного списка параметров.
     * @return
     */
    public LinkedHashMap<String, MProperty> getDefaultProperties(){

        // this code need change to download from db
        LinkedHashMap<String, MProperty> result = new LinkedHashMap<String, MProperty>();

        MProperty property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Health";
        property.description = "Здоровье";
        property.maxValue = 100;
        property.value = 100;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 1;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Stamina";
        property.description = "Выносливость";
        property.maxValue = 100;
        property.value = 100;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Strength";
        property.description = "Сила";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Agility";
        property.description = "Ловкость";
        property.maxValue = 100;
        property.value = 100;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Armor";
        property.description = "Броня";
        property.maxValue = 100;
        property.value = 0;
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
        property.name = "Aggression";
        property.description = "Агрессия";
        property.maxValue = 100;
        property.value = 10;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Fear";
        property.description = "Страх";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Curiosity";
        property.description = "Любопытство";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Intelligence";
        property.description = "Интеллект";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Courage";
        property.description = "Отвага";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Focus";
        property.description = "Концентрация";
        property.maxValue = 100;
        property.value = 100;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Loyalty";
        property.description = "Преданность";
        property.maxValue = 100;
        property.value = 100;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Temper";
        property.description = "Темперамент";
        property.maxValue = 100;
        property.value = 100;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 2;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Socialite";
        property.description = "Социальность";
        property.maxValue = 50;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Dominance";
        property.description = "Доминирование";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Empathy";
        property.description = "Эмпатия";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 1;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Hunger";
        property.description = "Голод";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Thirst";
        property.description = "Жажда";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 1;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Fatigue";
        property.description = "Усталость";
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
        property.name = "Pacifism";
        property.description = "Пацифизм";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Patience";
        property.description = "Терпение";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Vigilance";
        property.description = "Бдительность";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Resourcefulness";
        property.description = "Находчивость";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        // 7 грехов:

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Lust";
        property.description = "Похоть";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Gluttony";
        property.description = "Чревоугодие";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Greed";
        property.description = "Жадность";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Laziness";
        property.description = "Лень";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Anger";
        property.description = "Гнев";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Envy";
        property.description = "Зависть";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        return result;
    }

    public LinkedHashMap<String, MOrder> getDefaultOrders(){

        LinkedHashMap<String, MOrder> result = new LinkedHashMap<>();

        return null;
    }

    /**
     * Метод для подготовки списка параметров который будет преобразован в нейронную сеть:
     * @return
     */
    public ArrayList<MProperty> prepareMetaState(){

        ArrayList<MProperty> result = new ArrayList<>();

        return result;
    }

    public List<Sector> findPath(Sector start, Sector goal) {
        // Используем статический AStarPathfinder для поиска пути
        return new ArrayList<Sector>();                                                                                     //AStarPathfinder.FindPath(start, goal, World.gameWorld.virtualMap);
    }

    // Метод для доступа к свойствам
    public MProperty getProperty(String name) {
        if(properties.containsKey(name))
        {
            return properties.get(name);
        }
        return null;
    }

    // Добавление юнита в отряд
    public void AddUnit(MLive unit){
        lives.add(unit);
        // При добавлении юнита переместим его в текущий сектор отряда
        unit.activeSector = activeSector;
    }

    // Удаление юнита из отряда
    public void RemoveUnit(MLive unit){
        lives.remove(unit);
    }

    // Устанавливаем сектор для всех юнитов (помимо стандартного поведения)
    public void updateSectorLives(Sector sector){
        if(lives != null && !lives.isEmpty()) {
            activeSector = sector;
            for (MLive unit : lives) {
                unit.activeSector = sector;
            }
        }
    }

    public LinkedHashMap<String, MProperty> getActualState(){
        return null;
    }
}
