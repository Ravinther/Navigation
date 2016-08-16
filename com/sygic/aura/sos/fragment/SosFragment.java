package com.sygic.aura.sos.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.poi.adapter.PoiAdapter;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.sos.EmergencyNumbersAdapter;
import com.sygic.aura.sos.data.EmergencyNumbers;
import com.sygic.aura.sos.data.EmergencyNumbers.Holder;
import com.sygic.aura.sos.view.SosSectionView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.ArrayList;
import java.util.List;

public class SosFragment extends AbstractScreenFragment implements OnClickListener, PoiFragmentResultCallback {
    private LongPosition mActualPosition;
    private PoiAdapter mAdapter;
    private EmergencyNumbers mEmergencyNumbers;
    private final SparseIntArray mMinDistances;
    private long mSearchObjRef;
    private final SparseArray<SosSectionView> mSectionViews;
    private final SparseIntArray mSections;
    private CategoryEnablingTask mTask;

    /* renamed from: com.sygic.aura.sos.fragment.SosFragment.1 */
    class C16831 extends SparseIntArray {
        C16831() {
            append(7, 2131624299);
            append(8, 2131624300);
            append(201, 2131624301);
            append(12, 2131624302);
        }
    }

    /* renamed from: com.sygic.aura.sos.fragment.SosFragment.2 */
    class C16842 implements DialogInterface.OnClickListener {
        final /* synthetic */ List val$numbers;

        C16842(List list) {
            this.val$numbers = list;
        }

        public void onClick(DialogInterface dialog, int which) {
            SosFragment.this.callNumber(((Holder) this.val$numbers.get(which)).getPhoneNumber());
        }
    }

    private class CategoryEnablingTask extends AsyncTask<Void, Integer, Void> {
        private final PoiAdapter mAdapter;
        private final SparseBooleanArray mDistancesSet;
        private int mLastCount;
        private final DataSetObserver mObserver;
        private boolean mReset;

        /* renamed from: com.sygic.aura.sos.fragment.SosFragment.CategoryEnablingTask.1 */
        class C16851 extends DataSetObserver {
            final /* synthetic */ SosFragment val$this$0;

            C16851(SosFragment sosFragment) {
                this.val$this$0 = sosFragment;
            }

            public void onChanged() {
                CategoryEnablingTask.this.reset();
            }

            public void onInvalidated() {
                CategoryEnablingTask.this.reset();
            }
        }

        public CategoryEnablingTask(PoiAdapter adapter) {
            this.mLastCount = 0;
            this.mDistancesSet = new SparseBooleanArray(SosFragment.this.mSections.size());
            this.mAdapter = adapter;
            this.mObserver = new C16851(SosFragment.this);
        }

        private synchronized void setStarted() {
            this.mReset = false;
        }

        private synchronized void reset() {
            this.mReset = true;
            this.mLastCount = 0;
            this.mDistancesSet.clear();
            notify();
        }

        private synchronized boolean isReset() {
            return this.mReset;
        }

        private boolean wantSetDistance(int groupId) {
            return SosFragment.this.mSections.indexOfKey(groupId) >= 0 && !this.mDistancesSet.get(groupId, false);
        }

        private void cancelLoading() {
            this.mAdapter.cancelPoiLoading();
            synchronized (this) {
                notify();
            }
        }

        protected void onPreExecute() {
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mAdapter.query("");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected java.lang.Void doInBackground(java.lang.Void... r13) {
            /*
            r12 = this;
            r11 = 1;
            r10 = 0;
        L_0x0002:
            r12.setStarted();
            r7 = r12.mAdapter;
            r0 = r7.getCount();
            if (r0 == 0) goto L_0x0011;
        L_0x000d:
            r7 = r12.mLastCount;
            if (r7 < r0) goto L_0x0041;
        L_0x0011:
            monitor-enter(r12);
            r8 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
            r12.wait(r8);	 Catch:{ InterruptedException -> 0x0030 }
            monitor-exit(r12);	 Catch:{ all -> 0x003e }
        L_0x0018:
            r7 = r12.isReset();
            if (r7 != 0) goto L_0x0002;
        L_0x001e:
            r7 = r12.mAdapter;
            r7 = r7.isFinished();
            if (r7 != 0) goto L_0x002e;
        L_0x0026:
            r7 = r12.mAdapter;
            r7 = r7.isCanceled();
            if (r7 == 0) goto L_0x0002;
        L_0x002e:
            r7 = 0;
            return r7;
        L_0x0030:
            r2 = move-exception;
            r7 = "SOS Fragment";
            r8 = r2.getMessage();	 Catch:{ all -> 0x003e }
            r9 = 0;
            com.sygic.aura.helper.CrashlyticsHelper.logException(r7, r8, r2, r9);	 Catch:{ all -> 0x003e }
            monitor-exit(r12);	 Catch:{ all -> 0x003e }
            goto L_0x002e;
        L_0x003e:
            r7 = move-exception;
            monitor-exit(r12);	 Catch:{ all -> 0x003e }
            throw r7;
        L_0x0041:
            r4 = r12.mLastCount;
        L_0x0043:
            if (r4 >= r0) goto L_0x0053;
        L_0x0045:
            r7 = r12.isReset();
            if (r7 == 0) goto L_0x0056;
        L_0x004b:
            r7 = r12.mAdapter;
            r7 = r7.getCount();
            if (r4 >= r7) goto L_0x0056;
        L_0x0053:
            r12.mLastCount = r0;
            goto L_0x0018;
        L_0x0056:
            r7 = r12.mAdapter;
            r5 = r7.getItem(r4);
            r5 = (com.sygic.aura.search.model.data.PoiListItem) r5;
            if (r5 == 0) goto L_0x00af;
        L_0x0060:
            r3 = r5.getGroupId();
            r6 = r5.getPoiDistance();
            r1 = r12.wantSetDistance(r3);
            if (r1 == 0) goto L_0x00af;
        L_0x006e:
            r7 = r12.mDistancesSet;
            r7.put(r3, r11);
            r7 = com.sygic.aura.sos.fragment.SosFragment.this;
            r7 = r7.mMinDistances;
            r8 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
            r7 = r7.get(r3, r8);
            if (r6 >= r7) goto L_0x009d;
        L_0x0082:
            r7 = com.sygic.aura.sos.fragment.SosFragment.this;
            r7 = r7.mMinDistances;
            r7.put(r3, r6);
            r7 = 2;
            r7 = new java.lang.Integer[r7];
            r8 = java.lang.Integer.valueOf(r3);
            r7[r10] = r8;
            r8 = java.lang.Integer.valueOf(r6);
            r7[r11] = r8;
            r12.publishProgress(r7);
        L_0x009d:
            r7 = r12.mDistancesSet;
            r7 = r7.size();
            r8 = com.sygic.aura.sos.fragment.SosFragment.this;
            r8 = r8.mSections;
            r8 = r8.size();
            if (r7 == r8) goto L_0x0053;
        L_0x00af:
            r4 = r4 + 1;
            goto L_0x0043;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.sos.fragment.SosFragment.CategoryEnablingTask.doInBackground(java.lang.Void[]):java.lang.Void");
        }

        protected void onProgressUpdate(Integer... values) {
            for (int i = 0; i < values.length - 1; i += 2) {
                SosSectionView sectionView = (SosSectionView) SosFragment.this.mSectionViews.get(values[0].intValue());
                sectionView.setDistance(values[1].intValue());
                sectionView.setEnabled(true);
            }
        }

        protected void onPostExecute(Void aVoid) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
    }

    public SosFragment() {
        this.mSections = new C16831();
        this.mSectionViews = new SparseArray(this.mSections.size());
        this.mMinDistances = new SparseIntArray(this.mSections.size());
        this.mActualPosition = LongPosition.Invalid;
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165925);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PositionInfo.nativeHasLastValidPosition()) {
            this.mActualPosition = PositionInfo.nativeGetLastValidPosition();
            this.mSearchObjRef = this.mLocationQuery.initCoreSearch(5, this.mActualPosition, 0);
            this.mAdapter = new PoiAdapter(getActivity(), this.mLocationQuery, this.mSearchObjRef, null);
            this.mTask = new CategoryEnablingTask(this.mAdapter);
            AsyncTaskHelper.execute(this.mTask);
        }
        this.mEmergencyNumbers = ResourceManager.nativeGetLocalEmergencyNumbers();
    }

    public void onDestroy() {
        if (this.mActualPosition.isValid()) {
            this.mLocationQuery.destroySearchObjectRef(this.mSearchObjRef);
        }
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903132, container, false);
        STextView txtStreet = (STextView) view.findViewById(2131624296);
        STextView txtCoords = (STextView) view.findViewById(2131624297);
        if (this.mActualPosition.isValid()) {
            String coordinates = ResourceManager.nativeFormatPosition(this.mActualPosition.getX(), this.mActualPosition.getY());
            txtStreet.setText(PositionInfo.nativeGetPositionString(getActivity(), this.mActualPosition));
            txtCoords.setText(coordinates);
        } else {
            txtStreet.setCoreText(2131165942);
            txtCoords.setCoreText(2131165942);
        }
        STextView txtCall = (STextView) view.findViewById(2131624298);
        txtCall.setCompoundDrawablesWithIntrinsicBounds(FontDrawable.inflate(getResources(), 2131034293), null, null, null);
        txtCall.setOnClickListener(this);
        for (int i = 0; i < this.mSections.size(); i++) {
            SosSectionView sectionView = (SosSectionView) view.findViewById(this.mSections.valueAt(i));
            this.mSectionViews.put(this.mSections.keyAt(i), sectionView);
            sectionView.setEnabled(false);
            sectionView.setOnClickListener(this);
        }
        return view;
    }

    public void onDestroyView() {
        if (this.mActualPosition.isValid()) {
            this.mTask.cancelLoading();
        }
        super.onDestroyView();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == 2131624298) {
            if (!this.mEmergencyNumbers.hasNumber()) {
                SToast.makeText(getActivity(), 2131165922, 1).show();
            } else if (this.mEmergencyNumbers.onlyOneNumber()) {
                callNumber(this.mEmergencyNumbers.getOnlyNumber());
            } else {
                pickNumberAndCall();
            }
        } else if (id == 2131624299) {
            showNearbyPois(ResourceManager.getCoreString(getResources(), 2131165920), 7);
        } else if (id == 2131624300) {
            showNearbyPois(ResourceManager.getCoreString(getResources(), 2131165924), 8);
        } else if (id == 2131624301) {
            showNearbyPois(ResourceManager.getCoreString(getResources(), 2131165919), 201);
        } else if (id == 2131624302) {
            showNearbyPois(ResourceManager.getCoreString(getResources(), 2131165923), 12);
        }
    }

    private void callNumber(String phoneNumber) {
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNumber)));
    }

    private void pickNumberAndCall() {
        List<Holder> numbers = getNumberHolders(this.mEmergencyNumbers);
        new Builder(getActivity()).title(2131165915).listAdapter(new EmergencyNumbersAdapter(getActivity(), numbers), new C16842(numbers)).build().showAllowingStateLoss("dialog_select_number");
    }

    private void showNearbyPois(String title, int categoryId) {
        this.mTask.cancelLoading();
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, title);
        bundle.putParcelable(PoiFragment.ARG_DATA, this.mActualPosition);
        bundle.putInt(PoiFragment.ARG_GROUP_ID, categoryId);
        manager.addFragment(PoiFragment.class, title, true, this, bundle);
    }

    private List<Holder> getNumberHolders(EmergencyNumbers emergencyNumbers) {
        List<Holder> result = new ArrayList();
        if (!TextUtils.isEmpty(emergencyNumbers.getAmbulance())) {
            Holder ambulanceHolder = new Holder();
            ambulanceHolder.setTextId(2131165916);
            ambulanceHolder.setIconId(2131034294);
            ambulanceHolder.setPhoneNumber(emergencyNumbers.getAmbulance());
            result.add(ambulanceHolder);
        }
        if (!TextUtils.isEmpty(emergencyNumbers.getPolice())) {
            Holder policeHolder = new Holder();
            policeHolder.setTextId(2131165918);
            policeHolder.setIconId(2131034296);
            policeHolder.setPhoneNumber(emergencyNumbers.getPolice());
            result.add(policeHolder);
        }
        if (!TextUtils.isEmpty(emergencyNumbers.getFiremen())) {
            Holder firemenHolder = new Holder();
            firemenHolder.setTextId(2131165917);
            firemenHolder.setIconId(2131034295);
            firemenHolder.setPhoneNumber(emergencyNumbers.getFiremen());
            result.add(firemenHolder);
        }
        return result;
    }

    public static SosFragment getSosFragment(FragmentManager fragmentManager) {
        return (SosFragment) fragmentManager.findFragmentByTag("fragment_sos_tag");
    }

    public boolean dismissOnFinish() {
        return true;
    }

    public void onPoiFragmentResult(PoiListItem result) {
        if (result == null) {
            this.mTask = new CategoryEnablingTask(this.mAdapter);
            AsyncTaskHelper.execute(this.mTask);
            return;
        }
        performHomeAction();
        Bundle poiData = new Bundle();
        poiData.putLong(PoiDetailFragment.POI_ID, result.getPoiId());
        poiData.putString(PoiDetailFragment.POI_TITLE, result.getDisplayName());
        poiData.putParcelable(PoiDetailFragment.POI_SEL, result.getMapSel());
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            manager.addFragment(PoiDetailFragment.class, "fragment_poi_detail_tag", true, (FragmentResultCallback) manager, poiData);
        }
    }
}
