package com.j0schi.server.NI.model.entity;

import com.j0schi.server.NI.model.NISample;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class NINetworkEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description = "Сеть без имени.";

    private String tableName = "no_name_ninetwork";

    // Конвертирую пример из книги:
    private int INPUT_NEURONS;
    private int HIDDEN_NEURONS;
    private int OUTPUT_NEURONS;

    // Смещение
    private int dest = 1;

    private int HIDDEN_LAYER_COUNT = 1;

    // Коэффициент обучения:
    private float LEARN_RATE = 0.2f;

    // Случайные веса:
    private float RAND_WEIGHT = 0f;

    private float RAND_MAX = 0.5f;
}
