package com.vividsolutions.jts.geomgraph.index;

public class SweepLineEvent implements Comparable {
    private int deleteEventIndex;
    Object edgeSet;
    private int eventType;
    private SweepLineEvent insertEvent;
    private Object obj;
    private double xValue;

    public SweepLineEvent(Object edgeSet, double x, SweepLineEvent insertEvent, Object obj) {
        this.edgeSet = edgeSet;
        this.xValue = x;
        this.insertEvent = insertEvent;
        this.eventType = 1;
        if (insertEvent != null) {
            this.eventType = 2;
        }
        this.obj = obj;
    }

    public boolean isInsert() {
        return this.insertEvent == null;
    }

    public boolean isDelete() {
        return this.insertEvent != null;
    }

    public SweepLineEvent getInsertEvent() {
        return this.insertEvent;
    }

    public int getDeleteEventIndex() {
        return this.deleteEventIndex;
    }

    public void setDeleteEventIndex(int deleteEventIndex) {
        this.deleteEventIndex = deleteEventIndex;
    }

    public Object getObject() {
        return this.obj;
    }

    public int compareTo(Object o) {
        SweepLineEvent pe = (SweepLineEvent) o;
        if (this.xValue < pe.xValue) {
            return -1;
        }
        if (this.xValue > pe.xValue) {
            return 1;
        }
        if (this.eventType < pe.eventType) {
            return -1;
        }
        if (this.eventType > pe.eventType) {
            return 1;
        }
        return 0;
    }
}
