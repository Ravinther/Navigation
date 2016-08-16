package com.sygic.aura.route.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.RouteAvoidUnableListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteManager.AvoidType;
import com.sygic.aura.route.fragment.RouteComputeProgressFragment;
import loquendo.tts.engine.TTSConst;

public class AvoidsFragment extends RouteComputeProgressFragment implements RouteAvoidUnableListener, RouteEventsListener {
    private AvoidsAdapter mAvoidsAdapter;

    /* renamed from: com.sygic.aura.route.overview.AvoidsFragment.1 */
    static /* synthetic */ class C15371 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$route$RouteManager$AvoidType;

        static {
            $SwitchMap$com$sygic$aura$route$RouteManager$AvoidType = new int[AvoidType.values().length];
            try {
                $SwitchMap$com$sygic$aura$route$RouteManager$AvoidType[AvoidType.TYPE_TOLL_ROADS_UNABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$RouteManager$AvoidType[AvoidType.TYPE_MOTORWAYS_UNABLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$RouteManager$AvoidType[AvoidType.TYPE_UNPAVED_UNABLE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$RouteManager$AvoidType[AvoidType.TYPE_FERRIES_UNABLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteEventsReceiver.registerRouteAvoidUnableListener(this);
        loadNavigationData();
    }

    public void onDestroy() {
        RouteEventsReceiver.unregisterRouteAvoidUnableListener(this);
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(2130903097, container, false);
        this.mAvoidsAdapter = new AvoidsAdapter(getActivity(), this.mRouteNavigateData);
        this.mRouteNavigateData.registerObserver(this.mAvoidsAdapter);
        ((ListView) view.findViewById(2131624167)).setAdapter(this.mAvoidsAdapter);
        return view;
    }

    protected int getToolbarTitle() {
        return 2131165320;
    }

    protected int getToolbarMenu() {
        return 0;
    }

    public void onDestroyView() {
        this.mRouteNavigateData.unregisterObserver(this.mAvoidsAdapter);
        super.onDestroyView();
    }

    public void onRouteComputeFinishedAll() {
        super.onRouteComputeFinishedAll();
        this.mRouteNavigateData.setCountryAvoidsArray(RouteManager.nativeGetRouteAvoids());
    }

    public void onAvoidUnable(AvoidType eAvoidType) {
        SToast.makeText(getActivity(), getUnableAvoidResId(eAvoidType), 1).show();
    }

    public static int getUnableAvoidResId(AvoidType eAvoidType) {
        switch (C15371.$SwitchMap$com$sygic$aura$route$RouteManager$AvoidType[eAvoidType.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131165623;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131165621;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131165625;
            case TTSConst.TTSXML /*4*/:
                return 2131165619;
            default:
                return 0;
        }
    }
}
