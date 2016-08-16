package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.C0001R;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPresenter;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

public class NavigationMenuPresenter implements MenuPresenter, OnItemClickListener {
    private NavigationMenuAdapter mAdapter;
    private Callback mCallback;
    private LinearLayout mHeader;
    private ColorStateList mIconTintList;
    private int mId;
    private Drawable mItemBackground;
    private LayoutInflater mLayoutInflater;
    private MenuBuilder mMenu;
    private NavigationMenuView mMenuView;
    private int mPaddingSeparator;
    private int mPaddingTopDefault;
    private ColorStateList mTextColor;

    private class NavigationMenuAdapter extends BaseAdapter {
        private final ArrayList<NavigationMenuItem> mItems;
        private ColorDrawable mTransparentIcon;
        private boolean mUpdateSuspended;

        NavigationMenuAdapter() {
            this.mItems = new ArrayList();
            prepareMenuItems();
        }

        public int getCount() {
            return this.mItems.size();
        }

        public NavigationMenuItem getItem(int position) {
            return (NavigationMenuItem) this.mItems.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getViewTypeCount() {
            return 3;
        }

        public int getItemViewType(int position) {
            NavigationMenuItem item = getItem(position);
            if (item.isSeparator()) {
                return 2;
            }
            if (item.getMenuItem().hasSubMenu()) {
                return 1;
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            NavigationMenuItem item = getItem(position);
            switch (getItemViewType(position)) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    if (convertView == null) {
                        convertView = NavigationMenuPresenter.this.mLayoutInflater.inflate(C0001R.layout.design_navigation_item, parent, false);
                    }
                    NavigationMenuItemView itemView = (NavigationMenuItemView) convertView;
                    itemView.setIconTintList(NavigationMenuPresenter.this.mIconTintList);
                    itemView.setTextColor(NavigationMenuPresenter.this.mTextColor);
                    itemView.setBackgroundDrawable(NavigationMenuPresenter.this.mItemBackground != null ? NavigationMenuPresenter.this.mItemBackground.getConstantState().newDrawable() : null);
                    itemView.initialize(item.getMenuItem(), 0);
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    if (convertView == null) {
                        convertView = NavigationMenuPresenter.this.mLayoutInflater.inflate(C0001R.layout.design_navigation_item_subheader, parent, false);
                    }
                    ((TextView) convertView).setText(item.getMenuItem().getTitle());
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (convertView == null) {
                        convertView = NavigationMenuPresenter.this.mLayoutInflater.inflate(C0001R.layout.design_navigation_item_separator, parent, false);
                    }
                    convertView.setPadding(0, item.getPaddingTop(), 0, item.getPaddingBottom());
                    break;
            }
            return convertView;
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public boolean isEnabled(int position) {
            return getItem(position).isEnabled();
        }

        public void notifyDataSetChanged() {
            prepareMenuItems();
            super.notifyDataSetChanged();
        }

        private void prepareMenuItems() {
            if (!this.mUpdateSuspended) {
                this.mItems.clear();
                int currentGroupId = -1;
                int currentGroupStart = 0;
                boolean currentGroupHasIcon = false;
                int totalSize = NavigationMenuPresenter.this.mMenu.getVisibleItems().size();
                for (int i = 0; i < totalSize; i++) {
                    MenuItemImpl item = (MenuItemImpl) NavigationMenuPresenter.this.mMenu.getVisibleItems().get(i);
                    if (item.hasSubMenu()) {
                        SubMenu subMenu = item.getSubMenu();
                        if (subMenu.hasVisibleItems()) {
                            if (i != 0) {
                                this.mItems.add(NavigationMenuItem.separator(NavigationMenuPresenter.this.mPaddingSeparator, 0));
                            }
                            this.mItems.add(NavigationMenuItem.of(item));
                            boolean subMenuHasIcon = false;
                            int subMenuStart = this.mItems.size();
                            int size = subMenu.size();
                            for (int j = 0; j < size; j++) {
                                MenuItem subMenuItem = subMenu.getItem(j);
                                if (subMenuItem.isVisible()) {
                                    if (!(subMenuHasIcon || subMenuItem.getIcon() == null)) {
                                        subMenuHasIcon = true;
                                    }
                                    this.mItems.add(NavigationMenuItem.of((MenuItemImpl) subMenuItem));
                                }
                            }
                            if (subMenuHasIcon) {
                                appendTransparentIconIfMissing(subMenuStart, this.mItems.size());
                            }
                        }
                    } else {
                        int groupId = item.getGroupId();
                        if (groupId != currentGroupId) {
                            currentGroupStart = this.mItems.size();
                            currentGroupHasIcon = item.getIcon() != null;
                            if (i != 0) {
                                currentGroupStart++;
                                this.mItems.add(NavigationMenuItem.separator(NavigationMenuPresenter.this.mPaddingSeparator, NavigationMenuPresenter.this.mPaddingSeparator));
                            }
                        } else if (!(currentGroupHasIcon || item.getIcon() == null)) {
                            currentGroupHasIcon = true;
                            appendTransparentIconIfMissing(currentGroupStart, this.mItems.size());
                        }
                        if (currentGroupHasIcon && item.getIcon() == null) {
                            item.setIcon(17170445);
                        }
                        this.mItems.add(NavigationMenuItem.of(item));
                        currentGroupId = groupId;
                    }
                }
            }
        }

        private void appendTransparentIconIfMissing(int startIndex, int endIndex) {
            for (int i = startIndex; i < endIndex; i++) {
                MenuItem item = ((NavigationMenuItem) this.mItems.get(i)).getMenuItem();
                if (item.getIcon() == null) {
                    if (this.mTransparentIcon == null) {
                        this.mTransparentIcon = new ColorDrawable(17170445);
                    }
                    item.setIcon(this.mTransparentIcon);
                }
            }
        }

        public Bundle createInstanceState() {
            Bundle state = new Bundle();
            ArrayList<Integer> checkedItems = new ArrayList();
            Iterator i$ = this.mItems.iterator();
            while (i$.hasNext()) {
                MenuItemImpl menuItem = ((NavigationMenuItem) i$.next()).getMenuItem();
                if (menuItem != null && menuItem.isChecked()) {
                    checkedItems.add(Integer.valueOf(menuItem.getItemId()));
                }
            }
            state.putIntegerArrayList("android:menu:checked", checkedItems);
            return state;
        }

        public void restoreInstanceState(Bundle state) {
            ArrayList<Integer> checkedItems = state.getIntegerArrayList("android:menu:checked");
            if (checkedItems != null) {
                this.mUpdateSuspended = true;
                Iterator i$ = this.mItems.iterator();
                while (i$.hasNext()) {
                    MenuItemImpl menuItem = ((NavigationMenuItem) i$.next()).getMenuItem();
                    if (menuItem != null && checkedItems.contains(Integer.valueOf(menuItem.getItemId()))) {
                        menuItem.setChecked(true);
                    }
                }
                this.mUpdateSuspended = false;
                prepareMenuItems();
            }
        }

        public void setUpdateSuspended(boolean updateSuspended) {
            this.mUpdateSuspended = updateSuspended;
        }
    }

    private static class NavigationMenuItem {
        private final MenuItemImpl mMenuItem;
        private final int mPaddingBottom;
        private final int mPaddingTop;

        private NavigationMenuItem(MenuItemImpl item, int paddingTop, int paddingBottom) {
            this.mMenuItem = item;
            this.mPaddingTop = paddingTop;
            this.mPaddingBottom = paddingBottom;
        }

        public static NavigationMenuItem of(MenuItemImpl item) {
            return new NavigationMenuItem(item, 0, 0);
        }

        public static NavigationMenuItem separator(int paddingTop, int paddingBottom) {
            return new NavigationMenuItem(null, paddingTop, paddingBottom);
        }

        public boolean isSeparator() {
            return this.mMenuItem == null;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }

        public MenuItemImpl getMenuItem() {
            return this.mMenuItem;
        }

        public boolean isEnabled() {
            return (this.mMenuItem == null || this.mMenuItem.hasSubMenu() || !this.mMenuItem.isEnabled()) ? false : true;
        }
    }

    public void initForMenu(Context context, MenuBuilder menu) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mMenu = menu;
        Resources res = context.getResources();
        this.mPaddingTopDefault = res.getDimensionPixelOffset(C0001R.dimen.navigation_padding_top_default);
        this.mPaddingSeparator = res.getDimensionPixelOffset(C0001R.dimen.navigation_separator_vertical_padding);
    }

    public MenuView getMenuView(ViewGroup root) {
        if (this.mMenuView == null) {
            this.mMenuView = (NavigationMenuView) this.mLayoutInflater.inflate(C0001R.layout.design_navigation_menu, root, false);
            if (this.mAdapter == null) {
                this.mAdapter = new NavigationMenuAdapter();
            }
            this.mHeader = (LinearLayout) this.mLayoutInflater.inflate(C0001R.layout.design_navigation_item_header, this.mMenuView, false);
            this.mMenuView.addHeaderView(this.mHeader);
            this.mMenuView.setAdapter(this.mAdapter);
            this.mMenuView.setOnItemClickListener(this);
        }
        return this.mMenuView;
    }

    public void updateMenuView(boolean cleared) {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menu, allMenusAreClosing);
        }
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
        return false;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Parcelable onSaveInstanceState() {
        Bundle state = new Bundle();
        if (this.mMenuView != null) {
            SparseArray<Parcelable> hierarchy = new SparseArray();
            this.mMenuView.saveHierarchyState(hierarchy);
            state.putSparseParcelableArray("android:menu:list", hierarchy);
        }
        if (this.mAdapter != null) {
            state.putBundle("android:menu:adapter", this.mAdapter.createInstanceState());
        }
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle state = (Bundle) parcelable;
        SparseArray<Parcelable> hierarchy = state.getSparseParcelableArray("android:menu:list");
        if (hierarchy != null) {
            this.mMenuView.restoreHierarchyState(hierarchy);
        }
        Bundle adapterState = state.getBundle("android:menu:adapter");
        if (adapterState != null) {
            this.mAdapter.restoreInstanceState(adapterState);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int positionInAdapter = position - this.mMenuView.getHeaderViewsCount();
        if (positionInAdapter >= 0) {
            this.mMenu.performItemAction(this.mAdapter.getItem(positionInAdapter).getMenuItem(), this, 0);
        }
    }

    public View inflateHeaderView(int res) {
        View view = this.mLayoutInflater.inflate(res, this.mHeader, false);
        addHeaderView(view);
        return view;
    }

    public void addHeaderView(View view) {
        this.mHeader.addView(view);
        this.mMenuView.setPadding(0, 0, 0, this.mMenuView.getPaddingBottom());
    }

    public ColorStateList getItemTintList() {
        return this.mIconTintList;
    }

    public void setItemIconTintList(ColorStateList tint) {
        this.mIconTintList = tint;
    }

    public ColorStateList getItemTextColor() {
        return this.mTextColor;
    }

    public void setItemTextColor(ColorStateList textColor) {
        this.mTextColor = textColor;
    }

    public Drawable getItemBackground() {
        return this.mItemBackground;
    }

    public void setItemBackground(Drawable itemBackground) {
        this.mItemBackground = itemBackground;
    }

    public void setUpdateSuspended(boolean updateSuspended) {
        if (this.mAdapter != null) {
            this.mAdapter.setUpdateSuspended(updateSuspended);
        }
    }
}
