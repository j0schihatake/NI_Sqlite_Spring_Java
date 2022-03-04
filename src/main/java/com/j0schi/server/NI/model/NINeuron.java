package com.j0schi.server.NI.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder=true)
public class NINeuron {

    private int layerId;

    private String networkName;

    private String sampleName;

    private int layerType;

    private String description = "Понятное пояснение что обозначает данный нейрон.";

    private float value;

    // --------------------------------------- Constructors:

    public NINeuron(){}

    public NINeuron(float o){
        setValue(o);
    }

    public NINeuron(int o){
        setValue(o);
    }

    public NINeuron(boolean o){
        setValue(o);
    }

    // ---------------------------------------- Setters:

    // Значение всегда преобразуется во float;

    public void setValue(int o){
        this.value = o;
    }

    public void setValue(float o){
        this.value = o;
    }

    public void setValue(boolean o){
            this.value = !o ? 0 : 1;
    }
}
