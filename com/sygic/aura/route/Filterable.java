package com.sygic.aura.route;

public interface Filterable {
    int getImageResId();

    int getTextResId();

    boolean hasLicence();

    boolean isFinished();

    void setFinished();
}
