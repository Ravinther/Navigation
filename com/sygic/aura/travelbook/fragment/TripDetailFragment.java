package com.sygic.aura.travelbook.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.travelbook.Graph;
import com.sygic.aura.travelbook.TravelBookManager;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem.ETravelLogDataType;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;

public class TripDetailFragment extends AbstractScreenFragment {
    private TravelbookTrackLogItem mLog;

    public interface TripDetailCallback extends FragmentResultCallback {
        void onTripLogFavouriteChanged();
    }

    /* renamed from: com.sygic.aura.travelbook.fragment.TripDetailFragment.1 */
    class C17661 implements OnInvalidatedMenuListener {
        C17661() {
        }

        public void onMenuInvalidated(Menu menu) {
            TripDetailFragment.this.onPrepareOptionsMenu(menu);
        }
    }

    /* renamed from: com.sygic.aura.travelbook.fragment.TripDetailFragment.2 */
    class C17672 implements OnMenuItemClickListener {
        C17672() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return TripDetailFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.travelbook.fragment.TripDetailFragment.3 */
    class C17683 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$view;

        C17683(View view) {
            this.val$view = view;
        }

        public void onGlobalLayout() {
            this.val$view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            String noGraphDataMessage = null;
            if (TripDetailFragment.this.getActivity() != null) {
                noGraphDataMessage = ResourceManager.getCoreString(TripDetailFragment.this.getActivity(), 2131165991);
            }
            Graph altitudeGraph = (Graph) this.val$view.findViewById(2131624339);
            altitudeGraph.setXValueTextView((STextView) this.val$view.findViewById(2131624338));
            altitudeGraph.setNoDataMessage(noGraphDataMessage);
            altitudeGraph.load(TripDetailFragment.this.mLog.getIndex(), ETravelLogDataType.TYPE_ALTITUDE_DISTANCE);
            Graph speedGraph = (Graph) this.val$view.findViewById(2131624332);
            speedGraph.setXValueTextView((STextView) this.val$view.findViewById(2131624331));
            speedGraph.setNoDataMessage(noGraphDataMessage);
            speedGraph.load(TripDetailFragment.this.mLog.getIndex(), ETravelLogDataType.TYPE_SPEED_DISTANCE);
            Graph paceGraph = (Graph) this.val$view.findViewById(2131624346);
            paceGraph.setXValueTextView((STextView) this.val$view.findViewById(2131624345));
            paceGraph.setNoDataMessage(noGraphDataMessage);
            paceGraph.load(TripDetailFragment.this.mLog.getIndex(), ETravelLogDataType.TYPE_PACE_DISTANCE);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLog = (TravelbookTrackLogItem) getArguments().getParcelable("log_item");
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165999);
        toolbar.setOnMenuInvalidateListener(new C17661());
        toolbar.inflateMenu(2131755034);
        toolbar.setOnMenuItemClickListener(new C17672());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903137, container, false);
        ((STextView) view.findViewById(2131624311)).setCoreText(this.mLog.getStartAddress());
        ((STextView) view.findViewById(2131624313)).setCoreText(this.mLog.getEndAddress());
        ((STextView) view.findViewById(2131624315)).setCoreText(this.mLog.getStartTime() + " - " + this.mLog.getEndTime());
        ((STextView) view.findViewById(2131624317)).setCoreText(this.mLog.getDate());
        ((STextView) view.findViewById(2131624319)).setCoreText(this.mLog.getDuration());
        ((STextView) view.findViewById(2131624321)).setCoreText(this.mLog.getDistance());
        ((STextView) view.findViewById(2131624327)).setCoreText(this.mLog.getUpHill());
        ((STextView) view.findViewById(2131624329)).setCoreText(this.mLog.getDownHil());
        ((STextView) view.findViewById(2131624323)).setCoreText(this.mLog.getSpeed());
        ((STextView) view.findViewById(2131624325)).setCoreText(this.mLog.getPace());
        String[] altitudeMinMax = TravelBookManager.nativeGetMinMaxAvg(this.mLog.getIndex(), ETravelLogDataType.TYPE_ALTITUDE_DISTANCE);
        ((STextView) view.findViewById(2131624341)).setCoreText(altitudeMinMax[0]);
        ((STextView) view.findViewById(2131624343)).setCoreText(altitudeMinMax[1]);
        String[] speedMinMax = TravelBookManager.nativeGetMinMaxAvg(this.mLog.getIndex(), ETravelLogDataType.TYPE_SPEED_DISTANCE);
        ((STextView) view.findViewById(2131624334)).setCoreText(speedMinMax[0]);
        ((STextView) view.findViewById(2131624336)).setCoreText(speedMinMax[1]);
        String[] paceMinMax = TravelBookManager.nativeGetMinMaxAvg(this.mLog.getIndex(), ETravelLogDataType.TYPE_PACE_DISTANCE);
        ((STextView) view.findViewById(2131624348)).setCoreText(paceMinMax[0]);
        ((STextView) view.findViewById(2131624350)).setCoreText(paceMinMax[1]);
        initGraphs(view);
        return view;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 2131624699) {
            handleShowOnMap();
            return true;
        } else if (id != 2131624700) {
            return super.onOptionsItemSelected(item);
        } else {
            handleFavourite();
            return true;
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem likeItem = menu.findItem(2131624700);
        ResourceManager.setActionButtonIcon(getActivity(), likeItem, this.mLog.isFavourite() ? 2131166138 : 2131166815);
        if (this.mLog.isFavourite()) {
            likeItem.setTitle(ResourceManager.getCoreString(getActivity(), 2131166000));
        } else {
            likeItem.setTitle(ResourceManager.getCoreString(getActivity(), 2131165988));
        }
    }

    private void handleShowOnMap() {
        if (RouteManager.nativeExistValidRoute()) {
            new Builder(getActivity()).title(2131165996).body(2131165995).positiveButton(2131165994, null).build().show(getFragmentManager(), "cancel_route_dialog_tag");
            return;
        }
        MapControlsManager.nativeUnlockVehicle();
        Bundle args = new Bundle();
        args.putInt("log_index", this.mLog.getIndex());
        Fragments.replace(getActivity(), TripLogShowOnMapFragment.class, "fragment_show_on_map_tag", args);
    }

    private void handleFavourite() {
        this.mLog.setFavourite(!this.mLog.isFavourite());
        TravelBookManager.nativeSetTrackLogFavourite(this.mLog.getIndex(), this.mLog.isFavourite());
        this.mToolbar.invalidateMenu();
        ((TripDetailCallback) this.mResultCallback).onTripLogFavouriteChanged();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initGraphs(getView());
    }

    private void initGraphs(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new C17683(view));
    }
}
