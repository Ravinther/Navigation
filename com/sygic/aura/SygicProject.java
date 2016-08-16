package com.sygic.aura;

public class SygicProject {
    public static final boolean IS_PROTOTYPE;
    public static final boolean IS_TEST;

    static {
        ProjectsConsts.setBoolean(7, false);
        IS_PROTOTYPE = SygicConsts.IS_PROTOTYPE;
        IS_TEST = SygicConsts.IS_TEST;
    }

    public static void setConsts() {
        ProjectsConsts.setBoolean(4, true);
        ProjectsConsts.setBoolean(6, true);
        ProjectsConsts.setString(10, "Sygic");
        ProjectsConsts.setString(15, "4WRQC7FHNFXZFM8KGSS8");
    }
}
