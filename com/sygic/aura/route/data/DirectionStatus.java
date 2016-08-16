package com.sygic.aura.route.data;

public class DirectionStatus {
    public transient int[] arrPrimaryChars;
    public transient int[] arrSecondaryChars;
    public transient boolean bValidPrimary;
    public transient boolean bValidSecondary;
    public transient int nCommandPrimary;
    public transient int nCommandSecondary;
    public transient int nDistance;
    public transient int nRbExitPrimary;
    public transient int nRbExitSecondary;

    private DirectionStatus(boolean bValidPrimary, int nCommandPrimary, int nRbExitPrimary, int[] arrPrimaryChars, boolean bValidSecondary, int nCommandSecondary, int nRbExitSecondary, int[] arrSecondaryChars, int nDistance) {
        this.bValidPrimary = bValidPrimary;
        this.nCommandPrimary = nCommandPrimary;
        this.nRbExitPrimary = nRbExitPrimary;
        this.arrPrimaryChars = arrPrimaryChars;
        this.bValidSecondary = bValidSecondary;
        this.nCommandSecondary = nCommandSecondary;
        this.nRbExitSecondary = nRbExitSecondary;
        this.arrSecondaryChars = arrSecondaryChars;
        this.nDistance = nDistance;
    }
}
