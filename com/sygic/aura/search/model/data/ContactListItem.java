package com.sygic.aura.search.model.data;

import android.text.TextUtils;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.plugins.ContactDashPlugin;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.LocalizedStringComparator;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import java.util.Comparator;

public class ContactListItem extends FavoritesItem {
    private static Comparator<? super ListItem> mComparator;
    private static boolean mCompareByFullName;
    private final String[] mAddressDescriptions;
    private final long[] mAddressLocations;
    private boolean mIsHeader;

    /* renamed from: com.sygic.aura.search.model.data.ContactListItem.1 */
    static class C15751 implements Comparator<ListItem> {
        private LocalizedStringComparator mLocalizedStringComparator;

        C15751() {
            this.mLocalizedStringComparator = LocalizedStringComparator.getInstance(SettingsManager.nativeGetSelectedLanguage());
        }

        public int compare(ListItem lListItem, ListItem rListItem) {
            return this.mLocalizedStringComparator.compare(((ContactListItem) lListItem).getComparableStringRepr(), ((ContactListItem) rListItem).getComparableStringRepr());
        }
    }

    static {
        mCompareByFullName = true;
    }

    public ContactListItem(String strName, String strExtName, MapSelection mapSel, int iconType, long id, String[] addressDescriptions, long[] addressLocations) {
        super(strName, strExtName, mapSel, id, iconType);
        this.mAddressDescriptions = addressDescriptions;
        this.mAddressLocations = addressLocations;
    }

    public ContactListItem(String strName, boolean isHeader) {
        super(strName, null, null, 0, 0);
        this.mAddressDescriptions = null;
        this.mAddressLocations = null;
        this.mIsHeader = isHeader;
    }

    public String getContactName() {
        return mCompareByFullName ? getDisplayName() : getExtName();
    }

    public char getInitial() {
        if (TextUtils.isEmpty(getContactName())) {
            return '*';
        }
        return Character.toUpperCase(getContactName().charAt(0));
    }

    public MapSelection getMapSel() {
        if (this.mMapSel != null) {
            this.mMapSel.setPosition(new LongPosition(this.mAddressLocations[this.mRequestedAddressIndex]));
        }
        return super.getMapSel();
    }

    public String toString() {
        return getContactName();
    }

    protected int getIconResId() {
        return 0;
    }

    public String[] getAddressDescriptions() {
        return this.mAddressDescriptions;
    }

    protected long[] getAddressLocations() {
        return this.mAddressLocations;
    }

    public static void setCompareByFullName(boolean compareByFullName) {
        mCompareByFullName = compareByFullName;
    }

    public static Comparator<? super ListItem> getComparator(boolean compareByFullName) {
        mCompareByFullName = compareByFullName;
        if (mComparator == null) {
            mComparator = new C15751();
        }
        return mComparator;
    }

    public String getAddressSummary() {
        if (!hasSummary()) {
            return null;
        }
        if (hasOnlyOneAddress()) {
            return getAddressDescriptions()[0];
        }
        return String.format(ResourceManager.getCoreString(SygicHelper.getActivity(), 2131165421), new Object[0]);
    }

    public DashboardPlugin createDashPlugin() {
        return ContactDashPlugin.newInstance(null);
    }

    public boolean isHeader() {
        return this.mIsHeader;
    }

    private String getComparableStringRepr() {
        if (mCompareByFullName || !hasExtName()) {
            return getDisplayName();
        }
        return getExtName();
    }
}
