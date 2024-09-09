package com.j0schi.server.vot.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FractionRelationship {

    private int trust = 50;                                                                                                 // Уровень доверия (0-100)
    private int hostility = 0;                                                                                              // Уровень враждебности (0-100)
    private boolean tradeAgreement = false;                                                                                 // Торговое соглашение
    private boolean militaryAlliance = false;                                                                               // Военный альянс
    private RelationshipState state = RelationshipState.Neutral;                                                            // Состояние отношений

    // Метод для изменения доверия
    public void changeTrust(int amount) {
        trust = Math.max(0, Math.min(trust + amount, 100));
    }

    // Метод для изменения враждебности
    public void changeHostility(int amount) {
        hostility = Math.max(0, Math.min(hostility + amount, 100));
    }
}

