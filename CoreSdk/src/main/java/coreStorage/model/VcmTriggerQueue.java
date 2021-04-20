package coreStorage.model;

public class VcmTriggerQueue {
    boolean triggering;
    VcmTriggerEvent amEvent;
    VcmTriggerEvent pmEvent;
    VcmTriggerEvent triggeringEvent;

    public boolean isTriggering() {
        return this.triggering;
    }

    public void setTriggering(boolean isTriggering) {
        this.triggering = isTriggering;
    }

    public VcmTriggerEvent getAmEvent() {
        return this.amEvent;
    }

    public void setAmEvent(VcmTriggerEvent amEvent) {
        this.amEvent = amEvent;
    }

    public VcmTriggerEvent getPmEvent() {
        return this.pmEvent;
    }

    public void setPmEvent(VcmTriggerEvent pmEvent) {
        this.pmEvent = pmEvent;
    }

    public VcmTriggerEvent getTriggeringEvent() {
        return this.triggeringEvent;
    }

    public void setTriggeringStruct(VcmTriggerEvent triggeringEvent) {
        this.triggeringEvent = triggeringEvent;
    }
}

