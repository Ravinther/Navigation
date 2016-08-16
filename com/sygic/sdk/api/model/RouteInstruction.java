package com.sygic.sdk.api.model;

import android.os.Bundle;

public class RouteInstruction {
    public int lDistanceToNextTurn;
    public int lNextTurnX;
    public int lNextTurnY;
    public int nInstruction;
    public int nRoundaboutExitIndex;
    private String strExitIndex;

    public static Bundle writeBundle(RouteInstruction instr) {
        if (instr == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("nextTurnX", instr.getNextTurnX());
        b.putInt("nextTurny", instr.getNextTurnY());
        b.putInt("distanceNextTurn", instr.getDistanceToNextTurn());
        b.putInt("instruction", instr.getInstruction());
        b.putInt("roundExitIndex", instr.getRoundaboutExitIndex());
        b.putString("exitIndex", instr.getExitIndex());
        return b;
    }

    public String getExitIndex() {
        return this.strExitIndex;
    }

    public int getNextTurnX() {
        return this.lNextTurnX;
    }

    public int getNextTurnY() {
        return this.lNextTurnY;
    }

    public int getDistanceToNextTurn() {
        return this.lDistanceToNextTurn;
    }

    public int getInstruction() {
        return this.nInstruction;
    }

    public int getRoundaboutExitIndex() {
        return this.nRoundaboutExitIndex;
    }

    public String toString() {
        return "RouteInstruction [NextTurnX=" + this.lNextTurnX + ", NextTurnY=" + this.lNextTurnY + ", DistanceToNextTurn=" + this.lDistanceToNextTurn + ", Instruction=" + this.nInstruction + ", RoundaboutExitIndex=" + this.nRoundaboutExitIndex + ", ExitIndex=" + this.strExitIndex + "]";
    }
}
