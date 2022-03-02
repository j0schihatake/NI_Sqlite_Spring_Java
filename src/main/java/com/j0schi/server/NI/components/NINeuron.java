package com.j0schi.server.NI.components;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NINeuron {

    public String description = "Понятное пояснение что обозначает данный нейрон.";

    public float value;

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

    //Значение всегда преобразуется во float;

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
