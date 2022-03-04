package com.j0schi.server.NI.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class NILayerEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private int layerId;                                                // порядковый номер слоя слева направо: 0.1.2...

    private String networkName;

    private String sampleName;

    private int layerType = 0;                                           // 0 - входной, 1 - скрытый, 2 - выходной

    private String tableName = "no_name_layer_table";
}
