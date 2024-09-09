package com.j0schi.server.vot.map;

import com.j0schi.server.NI.model.NILayer;
import com.j0schi.server.NI.model.NINetwork;
import com.j0schi.server.NI.model.NINeuron;
import com.j0schi.server.NI.model.NISample;
import com.j0schi.server.vot.map.property.MProperty;
import com.j0schi.server.vot.map.race.MRace;
import com.j0schi.server.vot.order.MOrder;
import com.j0schi.server.vot.utils.RandomUtil;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import lombok.Data;
import lombok.val;

/**
 * Основные параметры фракции (MFraction):
 * Population (Численность): Общая численность населения фракции. Включает всех граждан, солдат и рабочих. Чем больше население, тем выше производственные и военные возможности фракции, но это также требует больше ресурсов для поддержания.
 * Food (Пища): Количество доступных запасов пищи. Пища необходима для поддержания численности населения и войск. Нехватка пищи может приводить к голоду, снижению морали, потере численности и даже мятежам.
 * Materials (Материалы): Ресурсы, используемые для строительства и создания предметов. Чем больше материалов, тем больше фракция может строить (здания, укрепления, инфраструктуру) и производить предметы, оружие и броню.
 * Technology (Технологии): Уровень развития технологий в фракции. Влияет на эффективность производства, военные возможности и доступ к более сложным постройкам и предметам. Высокий уровень технологий может улучшать другие показатели, такие как производство пищи или материалов.
 * MilitaryPower (Военная мощь): Общий уровень военной силы фракции, который включает численность войск, качество их снаряжения и их боевую подготовку. Включает не только численность, но и огневую мощь, защитные возможности и боевой дух.
 * Defense (Обороноспособность): Уровень защиты фракции, включая фортификации, укрепления и вооружённые силы. Чем выше обороноспособность, тем сложнее врагам атаковать и захватить территории фракции.
 * DiplomaticInfluence (Дипломатическое влияние): Влияние фракции на другие фракции и государства. Высокий уровень дипломатического влияния позволяет заключать выгодные союзы, получать ресурсы через торговлю и избегать конфликтов.
 * Infrastructure (Инфраструктура): Уровень развития инфраструктуры фракции. Это включает дороги, мосты, здания, фермы и другие сооружения, которые поддерживают экономику и армию фракции. Хорошо развитая инфраструктура позволяет быстрее перемещать войска и ресурсы, а также увеличивает эффективность производства.

 * Экономические параметры:
 * Economy (Экономика): Общий экономический потенциал фракции, который зависит от уровня производства материалов, технологий и торговли. Более развитая экономика позволяет быстрее восполнять запасы ресурсов и развиваться.
 * ProductionRate (Производственные мощности): Скорость производства различных товаров и ресурсов (пища, материалы, оружие). Высокая производительность позволяет фракции быстрее развиваться и поддерживать войска.
 * ResourceBalance (Баланс ресурсов): Отношение между потреблением и производством ресурсов (пища, материалы). Если баланс положительный, фракция накапливает ресурсы; если отрицательный – начинаются дефициты.
 * Trade (Торговля): Способность фракции торговать с другими фракциями. Включает экспорт и импорт пищи, материалов и оружия. Торговля позволяет компенсировать нехватку определенных ресурсов и получить экономические выгоды.

 * Военные параметры:
 * RecruitmentRate (Скорость набора войск): Способность фракции набирать солдат для армии. Высокая скорость набора позволяет быстро восполнять потери армии.
 * Veterance (Опыт войск): Уровень боевого опыта войск. Ветераны могут быть более эффективными на поле боя, повышая общий боевой потенциал армии.
 * Logistics (Логистика): Способность фракции снабжать свои войска на больших расстояниях. Влияет на скорость передвижения и эффективность армии вдали от базы.

 * Специальные параметры:
 * Research (Исследования): Скорость, с которой фракция проводит исследования и разрабатывает новые технологии. Высокая скорость исследований позволяет фракции быстрее развиваться и получать преимущества.
 * Expansion (Экспансия): Способность фракции расширяться и захватывать новые территории. Влияет на рост численности, ресурсы и военную мощь.
 * Fortifications (Фортификации): Уровень защищенности ключевых точек фракции. Это могут быть стены, крепости и другие оборонительные сооружения, которые усложняют врагам захват территорий.

 * Доп параметры и абстракции для принятия решения:
 * Атакаованные врагом секции
 * Обнаружено отрядов противника
 * Есть не разведанные сектора
 * Есть потенциальные цели(потерянный сектор, ресурсная точка)
 */

@Data
@NoArgsConstructor
public class MFraction extends MLive {

    public String fractionId;

    public HashMap<String, MRace> fractionRaces = new HashMap<>();                                                          //  Содержит описания и свойства конкретных моделей доступных данной фракции.
    public HashMap<String, MSquad> fractionSquads = new HashMap<>();
    public HashMap<String, MLive> fractionUnit = new HashMap<>();

    public ArrayList<MLive> freeLives = new ArrayList<>();                                                                  //  Список свободных юнитов

    // Отношения с другими фракциями
    public HashMap<String, FractionRelationship> relationships = new HashMap<>();

    // Метод для установки отношений с другой фракцией
    public void setRelationship(String otherFractionId, FractionRelationship relationship) {
        relationships.put(otherFractionId, relationship);
    }

    // Получение отношений с другой фракцией
    public FractionRelationship getRelationship(String otherFractionId) {
        return relationships.getOrDefault(otherFractionId, new FractionRelationship());
    }

    // Пример метода для объявления войны
    public void declareWar(String otherFractionId) {
        FractionRelationship relationship = getRelationship(otherFractionId);
        relationship.setHostility(relationship.getHostility() + 50);                                                        // Повышаем враждебность
        relationship.setTrust(relationship.getTrust() - 30);                                                                // Понижаем доверие
        relationship.setState(RelationshipState.Hostile);                                                                   // Устанавливаем враждебное состояние
    }

    // Заключение союза
    public void formAlliance(String otherFractionId) {
        FractionRelationship relationship = getRelationship(otherFractionId);
        relationship.setTrust(relationship.getTrust() + 50);                                                                // Повышаем доверие
        relationship.setHostility(relationship.getHostility() - 20);                                                        // Понижаем враждебность
        relationship.setState(RelationshipState.Friendly);                                                                  // Устанавливаем дружественное состояние
        relationship.setMilitaryAlliance(true);                                                                             // Формируем военный альянс
    }

    @Override
    public void init(){

        // Необходимо подготовить нейронную сеть:
        NINetwork network = prepareNetwork();
    }

    public NINetwork prepareNetwork(){

        NINetwork network = new NINetwork();
        network.setTableName("fraction".toLowerCase());
        network.setDescription("Нейронная сеть для принятий решения фракциями.");

        //----------------------------------------- Формируем первый пример:

        NISample sample = new NISample();
        sample.tableName = "white";
        sample.description = "Пример когда следует применить поведение: ожидание.";

        NILayer inputLayer = new NILayer();

        for(MProperty property : properties.values()){
            NINeuron neuron = new NINeuron(property.value);
            neuron.setDescription(property.description);
            neuron.minRandom = 0;
            neuron.maxRandom = property.maxValue;

            inputLayer.getNeurons().add(neuron);
        }

        //"Attack", "Run", "Wander", "Hide"

        NILayer outputLayer = new NILayer();
        outputLayer.setLayerType(2);

        NINeuron atack = new NINeuron(0);
        atack.setDescription("Действие : атака");

        NINeuron run = new NINeuron(0);
        run.setDescription("Действие : побег");

        NINeuron wander = new NINeuron(1);
        wander.setDescription("Действие : наблюдение");

        NINeuron hide = new NINeuron(0);
        hide.setDescription("Действие : спрятаться");

        outputLayer.getNeurons().add(atack);
        outputLayer.getNeurons().add(run);
        outputLayer.getNeurons().add(wander);
        outputLayer.getNeurons().add(hide);

        sample.getLayer().add(inputLayer);
        sample.getLayer().add(outputLayer);

        network.addSample(sample);
        // Теперь выполняем весь цикл обучения:
        network.initialize(network.getSamples().get(0));
        network.learn(10000);
        return network;
    }

    private int frameCounter = 0;
    private int framesToSkip = 3;

    @Override
    public void live()
    {
        frameCounter++;
        if(frameCounter >= framesToSkip)
        {
            frameCounter = 0;                                                                                               // Сбрасываем счетчик

            for(val property : properties.values())                                                                         // Обновляем все свойства
            {
                property.Update();
            }

            switch(state) {
                case live:
                    // Принятие решений на основе NINetwork.
                    if(orders.isEmpty() || activeOrder == null){
                        //  Собираем текущие обновленные данные
                        //  Запрашиваем у сети совет
                        //  Преобразуем его в mOrder

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

    @Override
    public LinkedHashMap<String, MProperty> getDefaultProperties(){

        LinkedHashMap<String, MProperty> result = new LinkedHashMap<String, MProperty>();

        MProperty property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Population";
        property.description = "Численность";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Food";
        property.description = "Пища";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Materials";
        property.description = "Материалы";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Technology";
        property.description = "Технологии";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "MilitaryPower";
        property.description = "Военная мощ";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Defense";
        property.description = "Обороноспособность";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "DiplomaticInfluence";
        property.description = "Дипломатия";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Infrastructure";
        property.description = "Инфраструктура";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Economy";
        property.description = "Экономика";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "ProductionRate";
        property.description = "Производство";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "ResourceBalance";
        property.description = "Баланс ресурсов";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Trade";
        property.description = "Торговля";
        property.maxValue = 100;
        property.value = 10;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "RecruitmentRate";
        property.description = "Скорость набора войск";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Veterancy";
        property.description = "Скорость приобретения опыта";
        property.maxValue = 100;
        property.value = 50;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Logistics";
        property.description = "Логистика";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Research";
        property.description = "Исследования";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Expansion";
        property.description = "Экспансия";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Fortifications";
        property.description = "Фортификация";
        property.maxValue = 100;
        property.value = 0;
        result.put(property.id, property);

        // Дополнительные свойства

        return result;
    }

    @Override
    public LinkedHashMap<String, MOrder> getDefaultOrders(){

        /**
         * Возможный набор приказов(реакций)
         * white - ожидание(накапливание ресурсов)

         Отряды и управление:
         * create -                         создать new Squad
         * sendToSector -                   отдать приказ: отправить отряд в секцию
         * sendToProtectSector -            отдать приказ: удерживать сектор между нами и врагом(ближайший к врагу)
         * extractResources -               приказать отряду добывать ресурсы(из текущего сектора в котором отряд)
         * whiteAndProtect  -               ожидать и защищать(оставаться на месте, в текущем секторе)
         * searchAndDestroy -               найти и уничтожить(импровизировать, отдать управление сети отряда)

         Производство:
         * buildResourcePoint -             постройть точку для производства ресурсов(как на базе, так и на точках)
         * buildFortification -             построить заставу или фортификации

         Дипломатия:
         * warDeclaration       -           объявление войны
         * requestToAlly        -           предложение альянса
         * requestResources     -           запрос ресурсов у союзника(через караван)
         * requestProtection    -           запрос поддержки союзника
         */

        LinkedHashMap<String, MOrder> result = new LinkedHashMap<String, MOrder>();

        return result;
    }

    /**
     * Актуальное состояние свойств игрового мира:
     * Индивидуален для данной сущности.
     * @return
     */
    @Override
    public LinkedHashMap<String, MProperty> getActualState(){

        LinkedHashMap<String, MProperty> result = new LinkedHashMap<>();

        for (MProperty prop: properties.values()) {
            result.put(prop.id, prop);
        }

        MProperty property = new MProperty();
        property.bonus = 0;
        property.id = RandomUtil.getThreadLocalRandomString(10);
        property.name = "Population";
        property.description = "Численность";
        property.maxValue = 100;
        property.value = 0;

        return result;
    }
}
