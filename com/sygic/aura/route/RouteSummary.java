package com.sygic.aura.route;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.route.data.InstructionsItem;
import com.sygic.aura.search.model.data.ListItem;

public class RouteSummary {
    private static int mIntProgress;

    /* renamed from: com.sygic.aura.route.RouteSummary.1 */
    static class C15071 implements VoidCallback {
        C15071() {
        }

        public void getMethod() {
            RouteSummary.InitRouteSummary();
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.23 */
    static class AnonymousClass23 implements Callback<InstructionsItem> {
        final /* synthetic */ int val$index;

        AnonymousClass23(int i) {
            this.val$index = i;
        }

        public InstructionsItem getMethod() {
            return RouteSummary.GetAt(this.val$index);
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.24 */
    static class AnonymousClass24 implements Callback<Boolean> {
        final /* synthetic */ int val$index;

        AnonymousClass24(int i) {
            this.val$index = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(RouteSummary.AvoidInstruction(this.val$index));
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.2 */
    static class C15082 implements Callback<Long> {
        C15082() {
        }

        public Long getMethod() {
            return Long.valueOf(RouteSummary.GetFromStartDistance());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.3 */
    static class C15093 implements Callback<Long> {
        C15093() {
        }

        public Long getMethod() {
            return Long.valueOf(RouteSummary.GetTotalDistance());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.4 */
    static class C15104 implements Callback<Long> {
        C15104() {
        }

        public Long getMethod() {
            return Long.valueOf(RouteSummary.GetPassedDistance());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.5 */
    static class C15115 implements Callback<Long> {
        C15115() {
        }

        public Long getMethod() {
            return Long.valueOf(RouteSummary.GetRemainingDistance());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.6 */
    static class C15126 implements Callback<Integer> {
        C15126() {
        }

        public Integer getMethod() {
            return Integer.valueOf((int) RouteSummary.GetTotalTime());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.7 */
    static class C15137 implements Callback<Long> {
        C15137() {
        }

        public Long getMethod() {
            return Long.valueOf(RouteSummary.GetRemainingTime());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.8 */
    static class C15148 implements Callback<String> {
        C15148() {
        }

        public String getMethod() {
            return RouteSummary.GetStartPointText();
        }
    }

    /* renamed from: com.sygic.aura.route.RouteSummary.9 */
    static class C15159 implements Callback<String> {
        final /* synthetic */ int val$index;

        C15159(int i) {
            this.val$index = i;
        }

        public String getMethod() {
            return RouteSummary.GetWayPointText(this.val$index);
        }
    }

    protected static native boolean AvoidInstruction(int i);

    protected static native int CancelRoute();

    protected static native void CleanResults();

    protected static native InstructionsItem GetAt(int i);

    protected static native int GetComputingProgress();

    protected static native int GetCount();

    protected static native int GetCurrRouteIndex();

    protected static native ListItem[] GetEndPointSearchEntries();

    protected static native String GetEndPointText();

    protected static native long GetFromStartDistance();

    protected static native int GetIncidentsCount();

    protected static native long GetPassedDistance();

    protected static native long GetRemainingDistance();

    protected static native long GetRemainingTime();

    protected static native int GetRouteCount();

    protected static native int GetRouteWayPointsCount();

    protected static native ListItem[] GetStartPointSearchEntries();

    protected static native String GetStartPointText();

    protected static native long GetTotalDistance();

    protected static native long GetTotalTime();

    protected static native int GetTrafficDelay();

    protected static native String GetWayPointText(int i);

    protected static native void InitRouteSummary();

    protected static native boolean IsLastWaypointParking();

    protected static native boolean IsPedestrian();

    protected static native boolean IsViaPointOnRoute();

    protected static native void SkipViaPoint();

    public static void nativeInitRouteSummary() {
        logAction("nativeInitRouteSummary");
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C15071());
        }
    }

    public static long nativeGetFromStartDistance() {
        if (SygicProject.IS_PROTOTYPE) {
            return 5;
        }
        return ((Long) new ObjectHandler(new C15082()).execute().get(Long.valueOf(0))).longValue();
    }

    public static long nativeGetTotalDistance() {
        if (SygicProject.IS_PROTOTYPE) {
            return 15;
        }
        return ((Long) new ObjectHandler(new C15093()).execute().get(Long.valueOf(0))).longValue();
    }

    public static long nativeGetPassedDistance() {
        if (SygicProject.IS_PROTOTYPE) {
            return 5;
        }
        Long aLong = (Long) new ObjectHandler(new C15104()).execute().get(Long.valueOf(0));
        if (aLong != null) {
            return aLong.longValue();
        }
        return 0;
    }

    public static long nativeGetRemainingDistance() {
        if (SygicProject.IS_PROTOTYPE) {
            return 5;
        }
        Long aLong = (Long) new ObjectHandler(new C15115()).execute().get(Long.valueOf(0));
        if (aLong != null) {
            return aLong.longValue();
        }
        return 0;
    }

    public static int nativeGetTotalTime() {
        if (SygicProject.IS_PROTOTYPE) {
            return 123;
        }
        return ((Integer) new ObjectHandler(new C15126()).execute().get(Integer.valueOf(0))).intValue();
    }

    public static long nativeGetRemainingTime() {
        if (SygicProject.IS_PROTOTYPE) {
            return 101010;
        }
        return ((Long) new ObjectHandler(new C15137()).execute().get(Long.valueOf(0))).longValue();
    }

    public static String nativeGetStartPointText() {
        if (SygicProject.IS_PROTOTYPE) {
            return "Start";
        }
        return (String) new ObjectHandler(new C15148()).execute().get(null);
    }

    public static String nativeGetWayPointText(int index) {
        if (SygicProject.IS_PROTOTYPE) {
            return "Waypoint";
        }
        return (String) new ObjectHandler(new C15159(index)).execute().get(null);
    }

    public static String nativeGetEndPointText() {
        if (SygicProject.IS_PROTOTYPE) {
            return "End";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return RouteSummary.GetEndPointText();
            }
        }).execute().get(null);
    }

    public static ListItem[] nativeGetStartSearchEntries() {
        if (SygicProject.IS_PROTOTYPE) {
            return new ListItem[0];
        }
        return (ListItem[]) new ObjectHandler(new Callback<ListItem[]>() {
            public ListItem[] getMethod() {
                return RouteSummary.GetStartPointSearchEntries();
            }
        }).execute().get(new ListItem[0]);
    }

    public static ListItem[] nativeGetEndSearchEntries() {
        if (SygicProject.IS_PROTOTYPE) {
            return new ListItem[0];
        }
        return (ListItem[]) new ObjectHandler(new Callback<ListItem[]>() {
            public ListItem[] getMethod() {
                return RouteSummary.GetEndPointSearchEntries();
            }
        }).execute().get(new ListItem[0]);
    }

    public static int nativeGetIncidentsCount() {
        if (SygicProject.IS_PROTOTYPE) {
            return 13;
        }
        return ((Integer) new ObjectHandler(new Callback<Integer>() {
            public Integer getMethod() {
                return Integer.valueOf(RouteSummary.GetIncidentsCount());
            }
        }).execute().get(Integer.valueOf(0))).intValue();
    }

    public static int nativeGetTrafficDelay() {
        if (SygicProject.IS_PROTOTYPE) {
            return 5;
        }
        return ((Integer) new ObjectHandler(new Callback<Integer>() {
            public Integer getMethod() {
                return Integer.valueOf(RouteSummary.GetTrafficDelay());
            }
        }).execute().get(Integer.valueOf(0))).intValue();
    }

    public static void nativeCleanResults() {
        ObjectHandler.postAndWait(new VoidCallback() {
            public void getMethod() {
                RouteSummary.CleanResults();
            }
        });
    }

    public static int nativeGetRouteWayPointsCount() {
        if (SygicProject.IS_PROTOTYPE) {
            return 3;
        }
        return ((Integer) new ObjectHandler(new Callback<Integer>() {
            public Integer getMethod() {
                return Integer.valueOf(RouteSummary.GetRouteWayPointsCount());
            }
        }).execute().get(Integer.valueOf(0))).intValue();
    }

    public static boolean nativeIsLastWaypointParking() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(RouteSummary.IsLastWaypointParking());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeSkipViaPoint() {
        ObjectHandler.postAndWait(new VoidCallback() {
            public void getMethod() {
                RouteSummary.SkipViaPoint();
            }
        });
    }

    public static boolean nativeIsViaPointOnRoute() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(RouteSummary.IsViaPointOnRoute());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsPedestrian() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(RouteSummary.IsPedestrian());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static int nativeGetComputingProgress() {
        if (!SygicProject.IS_PROTOTYPE) {
            return ((Integer) new ObjectHandler(new Callback<Integer>() {
                public Integer getMethod() {
                    return Integer.valueOf(RouteSummary.GetComputingProgress());
                }
            }).execute().get(Integer.valueOf(0))).intValue();
        }
        mIntProgress += 7;
        return mIntProgress;
    }

    public static int nativeGetRouteSegmentsCount() {
        if (SygicProject.IS_PROTOTYPE) {
            return 50;
        }
        return ((Integer) new ObjectHandler(new Callback<Integer>() {
            public Integer getMethod() {
                return Integer.valueOf(RouteSummary.GetCount());
            }
        }).execute().get(Integer.valueOf(0))).intValue();
    }

    public static InstructionsItem nativeGetInstructionAt(int index) {
        if (!SygicProject.IS_PROTOTYPE) {
            return (InstructionsItem) new ObjectHandler(new AnonymousClass23(index)).execute().get(null);
        }
        return new InstructionsItem(index, "Instruction " + index, "10 km", 10, 12, 0, null, true);
    }

    public static boolean nativeAvoidInstruction(int index) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass24(index)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static int nativeGetCurrRouteIndex() {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Integer) new ObjectHandler(new Callback<Integer>() {
            public Integer getMethod() {
                return Integer.valueOf(RouteSummary.GetCurrRouteIndex());
            }
        }).execute().get(Integer.valueOf(0))).intValue();
    }

    public static int nativeGetRouteCount() {
        if (SygicProject.IS_PROTOTYPE) {
            return 1;
        }
        return ((Integer) new ObjectHandler(new Callback<Integer>() {
            public Integer getMethod() {
                return Integer.valueOf(RouteSummary.GetRouteCount());
            }
        }).execute().get(Integer.valueOf(0))).intValue();
    }

    public static void nativeCancelRoute() {
        logAction("nativeCancelRoute");
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteSummary.CancelRoute();
                }
            });
        }
    }

    private static void logAction(String methodNameMsg) {
    }
}
