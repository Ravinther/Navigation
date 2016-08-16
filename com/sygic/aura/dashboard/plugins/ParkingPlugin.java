package com.sygic.aura.dashboard.plugins;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.poi.PoiDetailsInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.ParkingLocationDialogFragment;
import com.sygic.aura.route.ParkingLocationDialogFragment.ParkingDialogListener;
import com.sygic.aura.views.font_specials.STextView;

public class ParkingPlugin extends UserSettableDashPlugin {

    /* renamed from: com.sygic.aura.dashboard.plugins.ParkingPlugin.1 */
    class C11721 implements ParkingDialogListener {
        final /* synthetic */ DashboardFragment val$dashboardFragment;

        C11721(DashboardFragment dashboardFragment) {
            this.val$dashboardFragment = dashboardFragment;
        }

        public void onSaveLocation() {
            ParkingPlugin.this.showNoteDialog(this.val$dashboardFragment.getActivity());
        }

        public void onNavigateToCar() {
            ParkingPlugin.this.navigateToCar(this.val$dashboardFragment);
        }
    }

    /* renamed from: com.sygic.aura.dashboard.plugins.ParkingPlugin.2 */
    class C11732 implements OnClickListener {
        final /* synthetic */ EditText val$note;

        C11732(EditText editText) {
            this.val$note = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            NaviNativeActivity.hideKeyboard(this.val$note.getWindowToken());
        }
    }

    /* renamed from: com.sygic.aura.dashboard.plugins.ParkingPlugin.3 */
    class C11743 implements OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ LongPosition val$location;
        final /* synthetic */ EditText val$note;
        final /* synthetic */ String val$street;

        C11743(Context context, EditText editText, String str, LongPosition longPosition) {
            this.val$context = context;
            this.val$note = editText;
            this.val$street = str;
            this.val$location = longPosition;
        }

        public void onClick(DialogInterface dialog, int which) {
            String noteText;
            MemoItem item = MemoManager.nativeGetParking();
            if (item != null) {
                int itemId = (int) item.getId();
                MemoManager.nativeRemoveItem(this.val$context, (long) itemId);
                MapEventsReceiver.onMemoRemoved(MemoManager.nativeGetWidgetIDFromMemoID(itemId));
            }
            if (this.val$note.getText().toString().isEmpty()) {
                noteText = this.val$street;
            } else {
                noteText = this.val$note.getText().toString();
            }
            MemoManager.nativeAddPlugin(this.val$context, this.val$location, noteText.replace("\n", " "), EMemoType.memoParking);
            SToast.makeText(this.val$context, 2131165520, 0).show();
            NaviNativeActivity.hideKeyboard(this.val$note.getWindowToken());
        }
    }

    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeParking, EWidgetSize.widgetSizeHalfRow);
        }
        return new ParkingPlugin(widgetItem);
    }

    private ParkingPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    protected int getPluginImage() {
        return 2131034161;
    }

    public String getPluginLabel(Resources resources) {
        return ResourceManager.getCoreString(resources, 2131165379);
    }

    public void performAction(DashboardFragment dashboardFragment) {
        ParkingLocationDialogFragment.getInstance(getParkingDialogListener(dashboardFragment)).show(dashboardFragment.getFragmentManager(), "parking_location_dialog");
    }

    protected int addTitle() {
        return 0;
    }

    protected long addMemo(Context context) {
        MemoManager.nativeRemoveAllMemoByType(context, EMemoType.memoParking);
        return MemoManager.nativeAddPlugin(context, getLongPosition(), this.mWidgetItem.getName(), EMemoType.memoParking);
    }

    public ParkingDialogListener getParkingDialogListener(DashboardFragment dashboardFragment) {
        return new C11721(dashboardFragment);
    }

    private void navigateToCar(DashboardFragment dashboardFragment) {
        if (MemoManager.nativeGetParking().getLongPosition().isValid()) {
            navigate(dashboardFragment);
        }
    }

    private void showNoteDialog(Context context) {
        LongPosition location = PositionInfo.nativeGetLastValidPosition();
        if (location == null || !location.isValid()) {
            SToast.makeText(context, 2131165515, 0).show();
            return;
        }
        View view = LayoutInflater.from(context).inflate(2130903116, null);
        String street = PoiDetailsInfo.nativeGetAddressFromLongposition(location.toNativeLong());
        STextView streetView = (STextView) view.findViewById(2131624232);
        EditText note = (EditText) view.findViewById(2131624233);
        note.setHint(ResourceManager.getCoreString(context, 2131165516));
        streetView.setText(street);
        new Builder(context).view(view).positiveButton(2131165518, new C11743(context, note, street, location)).negativeButton(2131165351, new C11732(note)).build().showAllowingStateLoss("set_parking");
    }
}
