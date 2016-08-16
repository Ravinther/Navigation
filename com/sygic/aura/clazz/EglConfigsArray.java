package com.sygic.aura.clazz;

import com.sygic.aura.utils.Pair;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;

public class EglConfigsArray {
    private ArrayList<Pair<Integer, EGLConfig>> arr;

    public EglConfigsArray(EGLConfig[] arrEgl) {
        this.arr = null;
        if (arrEgl != null && arrEgl.length > 0) {
            this.arr = new ArrayList();
            for (int i = 0; i < arrEgl.length; i++) {
                this.arr.add(i, new Pair(Integer.valueOf(i), arrEgl[i]));
            }
        }
    }

    protected int get(int index) {
        return ((Integer) ((Pair) this.arr.get(index)).getFirst()).intValue();
    }

    public EGLConfig getConfig(int index) {
        return (EGLConfig) ((Pair) this.arr.get(index)).getSecond();
    }

    public int size() {
        return this.arr.size();
    }
}
