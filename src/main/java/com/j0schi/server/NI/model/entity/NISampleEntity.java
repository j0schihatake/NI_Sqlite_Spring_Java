package com.j0schi.server.NI.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class NISampleEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String networkName;

    private String sampleName;

    public String tableName = "test_nisample";

    public String description = "Набор входных данных + заданных выходных данных(пример).";
}
