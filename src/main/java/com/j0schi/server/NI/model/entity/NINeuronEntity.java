package com.j0schi.server.NI.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class NINeuronEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private int layerId;

    private String networkName;

    private String sampleName;

    private int layerType;

    private String description = "Понятное пояснение что обозначает данный нейрон.";

    private float value;
}
