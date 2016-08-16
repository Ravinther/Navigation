package com.sygic.aura.favorites.model;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.favorites.FavoritesAdapter;
import com.sygic.aura.favorites.FavoritesAdapter.Mode;
import com.sygic.aura.favorites.fragment.FavoritesFragmentInterface;
import com.sygic.aura.fragments.InputDialogFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.keyboard.FocusableKeyboard.OnKeyboardVisibilityListener;
import com.sygic.aura.keyboard.FocusableKeyboardView;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.ContactListItem;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.RecentListItem;
import com.sygic.aura.search.model.data.RouteListItem;
import com.sygic.aura.views.ButtonScrollListView;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.font_specials.SEditText;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.TreeSet;
import loquendo.tts.engine.TTSConst;

public class PagerFavoritesFragment extends ListFragment implements DropListener, LoadingStateListener {
    private ActionMode mContextActionMode;
    private STextView mEmptyTextView;
    private FavoritesFragmentInterface mFragmentInterface;
    private FocusableKeyboardView mKeyboardView;
    private OnKeyboardVisibilityListener mKeyboardVisibilityListener;
    private ListView mListView;
    private Mode mMode;
    private EditText mSearch;
    private SmartProgressBar mSmartProgressBar;

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.1 */
    class C12001 implements TextWatcher {
        C12001() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((FavoritesAdapter) PagerFavoritesFragment.this.getListAdapter()).query(s);
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.2 */
    class C12012 implements OnEditorActionListener {
        C12012() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 2) {
                return false;
            }
            ListView listView = PagerFavoritesFragment.this.getListView();
            if (listView.getCount() > 0 && v.length() > 0) {
                NaviNativeActivity.hideKeyboard(v.getWindowToken());
                int firstItemPosition = 0;
                if (PagerFavoritesFragment.this.mMode == Mode.MODE_CONTACTS && listView.getChildCount() > 1) {
                    firstItemPosition = 1;
                }
                PagerFavoritesFragment.this.onListItemClick(listView, listView.getChildAt(firstItemPosition), firstItemPosition, 0);
            }
            return true;
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.3 */
    class C12023 implements MultiChoiceModeListener {
        private final TreeSet<Integer> mSelectedItems;

        C12023() {
            this.mSelectedItems = new TreeSet();
        }

        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if (checked) {
                this.mSelectedItems.add(Integer.valueOf(position));
            } else {
                this.mSelectedItems.remove(Integer.valueOf(position));
            }
            mode.invalidate();
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            PagerFavoritesFragment.this.mContextActionMode = mode;
            PagerFavoritesFragment.this.getActivity().getMenuInflater().inflate(2131755011, menu);
            menu.removeItem(2131624672);
            if (PagerFavoritesFragment.this.mMode == Mode.MODE_RECENT) {
                menu.removeItem(2131624670);
            }
            if (PagerFavoritesFragment.this.mMode == Mode.MODE_BOOKMARKS) {
                menu.removeItem(2131624669);
            }
            if (!InCarConnection.isInCarConnected()) {
                ((DragSortListView) PagerFavoritesFragment.this.mListView).setDragEnabled(false);
            }
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            boolean z = true;
            MenuItem renameItem = menu.findItem(2131624670);
            if (renameItem != null) {
                boolean z2;
                if (this.mSelectedItems.size() == 1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                renameItem.setVisible(z2);
            }
            MenuItem recentToFavoritesItem = menu.findItem(2131624669);
            if (recentToFavoritesItem != null) {
                if (this.mSelectedItems.size() != 1) {
                    z = false;
                }
                recentToFavoritesItem.setVisible(z);
            }
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == 2131624671) {
                for (Integer intValue : this.mSelectedItems.descendingSet()) {
                    PagerFavoritesFragment.this.delete(intValue.intValue());
                }
                PagerFavoritesFragment.this.mFragmentInterface.requestRefresh(PagerFavoritesFragment.this.mMode);
                this.mSelectedItems.clear();
                mode.finish();
                return true;
            } else if (id == 2131624670) {
                PagerFavoritesFragment.this.rename(((Integer) this.mSelectedItems.first()).intValue());
                this.mSelectedItems.clear();
                mode.finish();
                return true;
            } else if (id != 2131624669) {
                return false;
            } else {
                PagerFavoritesFragment.this.addToFavorites(((Integer) this.mSelectedItems.first()).intValue());
                this.mSelectedItems.clear();
                mode.finish();
                return true;
            }
        }

        public void onDestroyActionMode(ActionMode mode) {
            this.mSelectedItems.clear();
            PagerFavoritesFragment.this.mContextActionMode = null;
            if (!InCarConnection.isInCarConnected()) {
                ((DragSortListView) PagerFavoritesFragment.this.mListView).setDragEnabled(PagerFavoritesFragment.this.mMode == Mode.MODE_BOOKMARKS);
            }
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.4 */
    class C12034 implements OnClickListener {
        final /* synthetic */ FavoritesItem val$contactListItem;

        C12034(FavoritesItem favoritesItem) {
            this.val$contactListItem = favoritesItem;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.val$contactListItem.setRequestedAddress(which);
            PagerFavoritesFragment.this.mFragmentInterface.finish(this.val$contactListItem);
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.5 */
    class C12045 implements DialogFragmentClickListener {
        final /* synthetic */ FavoritesItem val$selectedItem;

        C12045(FavoritesItem favoritesItem) {
            this.val$selectedItem = favoritesItem;
        }

        public void onPositiveButtonClicked(Editable text) {
            String strNewName = text.toString();
            if (this.val$selectedItem instanceof RouteListItem) {
                int oldNameLen = this.val$selectedItem.getDisplayName().length();
                strNewName = strNewName.length() > oldNameLen ? strNewName.substring(0, oldNameLen) : PagerFavoritesFragment.padRight(strNewName, oldNameLen);
            }
            PagerFavoritesFragment.this.mFragmentInterface.onRenameItem(this.val$selectedItem.getMemoId(), strNewName);
            PagerFavoritesFragment.this.mFragmentInterface.requestRefresh(PagerFavoritesFragment.this.mMode);
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.6 */
    class C12056 implements DialogFragmentClickListener {
        final /* synthetic */ LongPosition val$longPos;
        final /* synthetic */ int val$poiCategory;

        C12056(LongPosition longPosition, int i) {
            this.val$longPos = longPosition;
            this.val$poiCategory = i;
        }

        public void onPositiveButtonClicked(Editable text) {
            MemoManager.nativeAddFavorite(PagerFavoritesFragment.this.getActivity(), this.val$longPos, text.toString(), this.val$poiCategory);
            PagerFavoritesFragment.this.mFragmentInterface.requestRefresh(Mode.MODE_BOOKMARKS);
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.7 */
    class C12067 implements OnMenuItemClickListener {
        C12067() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            FavoritesAdapter adapter = (FavoritesAdapter) PagerFavoritesFragment.this.getListAdapter();
            boolean sortFullname = menuItem.getItemId() == 2131624667;
            adapter.saveSortPrefferences(sortFullname);
            adapter.recreateHeaders();
            adapter.sort(ContactListItem.getComparator(sortFullname));
            return true;
        }
    }

    /* renamed from: com.sygic.aura.favorites.model.PagerFavoritesFragment.8 */
    static /* synthetic */ class C12078 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode;

        static {
            $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[Mode.MODE_BOOKMARKS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[Mode.MODE_RECENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[Mode.MODE_CONTACTS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected static PagerFavoritesFragment newInstance(FavoritesFragmentInterface fragmentInterface, LocationQuery locationQuery, int position, boolean isFirstLoaded) {
        PagerFavoritesFragment favoritesFragment = new PagerFavoritesFragment();
        FavoritesAdapter listAdapter = new FavoritesAdapter(fragmentInterface.getContext(), locationQuery, position);
        favoritesFragment.setFavoritesFragmentInterface(fragmentInterface);
        favoritesFragment.setListAdapter(listAdapter);
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putBoolean("first_loaded", isFirstLoaded);
        favoritesFragment.setArguments(args);
        return favoritesFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903096, container, false);
        this.mSearch = (SEditText) view.findViewById(2131624470);
        this.mSearch.requestFocus();
        this.mSearch.addTextChangedListener(new C12001());
        this.mSearch.setOnEditorActionListener(new C12012());
        Bundle args = getArguments();
        this.mMode = Mode.values()[args.getInt("position")];
        this.mEmptyTextView = (STextView) view.findViewById(2131624165);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        switch (C12078.$SwitchMap$com$sygic$aura$favorites$FavoritesAdapter$Mode[this.mMode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                this.mSmartProgressBar.setEmptyTextAndImageRes(2131165911, 2131034303);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mSmartProgressBar.setEmptyTextAndImageRes(2131165912, 2131034304);
                break;
            case TTSConst.TTSUNICODE /*3*/:
                this.mSmartProgressBar.setEmptyTextAndImageRes(2131165910, 2131034302);
                break;
        }
        if (InCarConnection.isInCarConnected()) {
            this.mListView = (ButtonScrollListView) view.findViewById(16908298);
            ((ButtonScrollListView) this.mListView).initButtonScroll(view, 2131624136, 2131624138);
            this.mKeyboardView = (FocusableKeyboardView) view.findViewById(2131624371);
            this.mKeyboardVisibilityListener = InCarConnection.handleKeyboardChanges((ButtonScrollListView) this.mListView);
        } else {
            this.mListView = (DragSortListView) view.findViewById(16908298);
            ((DragSortListView) this.mListView).setDropListener(this);
        }
        this.mListView.setTag(this.mMode);
        this.mListView.setFastScrollEnabled(this.mMode.equals(Mode.MODE_CONTACTS));
        if (VERSION.SDK_INT >= 11) {
            this.mListView.setFastScrollAlwaysVisible(this.mMode.equals(Mode.MODE_CONTACTS));
        }
        if (!this.mMode.equals(Mode.MODE_CONTACTS)) {
            if (VERSION.SDK_INT >= 11) {
                initMultichoiceList();
            } else {
                registerForContextMenu(this.mListView);
            }
        }
        if (this.mMode.equals(Mode.MODE_BOOKMARKS) && !InCarConnection.isInCarConnected()) {
            ((DragSortListView) this.mListView).setDragEnabled(true);
        }
        FavoritesAdapter adapter = (FavoritesAdapter) getListAdapter();
        adapter.registerFavouritieLoadedListener(this);
        if (args.getBoolean("first_loaded")) {
            adapter.reinitializeCoreSearch();
            this.mFragmentInterface.onFirstFragmentCreated(this);
        }
        return view;
    }

    @TargetApi(11)
    private void initMultichoiceList() {
        this.mListView.setChoiceMode(3);
        this.mListView.setMultiChoiceModeListener(new C12023());
    }

    public void onDestroyView() {
        super.onDestroyView();
        ((FavoritesAdapter) getListAdapter()).unregisterFavouritieLoadedListener(this);
        ((FavoritesAdapter) getListAdapter()).destroySearchObjectReference();
        InCarConnection.unhandleKeyboardChanges(this.mKeyboardVisibilityListener);
    }

    private void setFavoritesFragmentInterface(FavoritesFragmentInterface fragmentInterface) {
        this.mFragmentInterface = fragmentInterface;
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        FavoritesItem selectedItem = (FavoritesItem) listView.getItemAtPosition(position);
        if (selectedItem.hasOnlyOneAddress()) {
            this.mFragmentInterface.finish(selectedItem);
        } else {
            showPickerDialog(selectedItem);
        }
    }

    private void showPickerDialog(FavoritesItem contactListItem) {
        new Builder(getActivity()).title(2131165425).simpleListAdapter(contactListItem.getAddressDescriptions(), new C12034(contactListItem)).build().showAllowingStateLoss("dialog_address_picker");
    }

    private boolean delete(int position) {
        FavoritesItem selectedItem = (FavoritesItem) this.mListView.getItemAtPosition(position);
        if (selectedItem == null || !this.mFragmentInterface.onDeleteItem(selectedItem)) {
            return false;
        }
        if (InCarConnection.isInCarConnected()) {
            ((FavoritesAdapter) this.mListView.getAdapter()).deleteAtPosition(position);
        } else {
            ((FavoritesAdapter) this.mListView.getInputAdapter()).deleteAtPosition(position);
        }
        return true;
    }

    private void deleteAll() {
        for (int i = this.mListView.getCount() - 1; i >= 0; i--) {
            delete(i);
        }
        this.mFragmentInterface.requestRefresh(this.mMode);
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", new Object[]{s});
    }

    private void rename(int position) {
        FavoritesItem selectedItem = (FavoritesItem) this.mListView.getItemAtPosition(position);
        InputDialogFragment.newInstance(getActivity(), 2131165424, selectedItem.getDisplayName().trim(), new C12045(selectedItem)).show(getFragmentManager(), "rename_favourite");
    }

    private void addToFavorites(int position) {
        ListItem selectedItem = (ListItem) this.mListView.getItemAtPosition(position);
        LongPosition longPos = selectedItem.getLongPosition();
        int poiCategory = selectedItem instanceof RecentListItem ? ((RecentListItem) selectedItem).getPoiCategory() : 0;
        if (MemoManager.nativeIsItemFavorite(longPos)) {
            SToast.makeText(getActivity(), 2131165401, 1).show();
        } else {
            InputDialogFragment.newInstance(getActivity(), 2131165400, selectedItem.getDisplayName(), new C12056(longPos, poiCategory)).showDialog();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(2131755011, menu);
        if (this.mMode == Mode.MODE_RECENT) {
            menu.removeItem(2131624670);
        }
        if (this.mMode == Mode.MODE_BOOKMARKS) {
            menu.removeItem(2131624669);
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (!getUserVisibleHint()) {
            return super.onContextItemSelected(item);
        }
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == 2131624671) {
            if (!delete(info.position)) {
                return true;
            }
            this.mFragmentInterface.requestRefresh(this.mMode);
            return true;
        } else if (id == 2131624672) {
            deleteAll();
            return true;
        } else if (id == 2131624670) {
            rename(info.position);
            return true;
        } else if (id != 2131624669) {
            return super.onContextItemSelected(item);
        } else {
            addToFavorites(info.position);
            return true;
        }
    }

    @TargetApi(11)
    public void closeContextActionBar() {
        if (VERSION.SDK_INT >= 11 && this.mContextActionMode != null) {
            this.mContextActionMode.finish();
        }
    }

    public void onLoadingStarted() {
        this.mSmartProgressBar.start();
    }

    public void onFirstNonEmptyTick() {
        this.mSmartProgressBar.stopAndCrossfadeWith(this.mListView);
    }

    public void onLoadingFinished(boolean isAdapterEmpty) {
        boolean isUserFiltering;
        boolean isDbEmpty;
        boolean z = true;
        if (TextUtils.isEmpty(this.mSearch.getText())) {
            isUserFiltering = false;
        } else {
            isUserFiltering = true;
        }
        if (!isAdapterEmpty || isUserFiltering) {
            isDbEmpty = false;
        } else {
            isDbEmpty = true;
        }
        if (!isAdapterEmpty) {
            this.mEmptyTextView.setVisibility(8);
            this.mSmartProgressBar.setVisibility(8);
        } else if (isUserFiltering) {
            this.mEmptyTextView.setVisibility(0);
            this.mSmartProgressBar.setVisibility(8);
        } else {
            this.mEmptyTextView.setVisibility(8);
            this.mSmartProgressBar.setVisibility(0);
            this.mSmartProgressBar.showEmpty();
        }
        EditText editText = this.mSearch;
        if (isDbEmpty) {
            z = false;
        }
        editText.setEnabled(z);
        if (isDbEmpty) {
            ((View) this.mSearch.getParent()).setVisibility(8);
        } else {
            ((View) this.mSearch.getParent()).setVisibility(0);
        }
    }

    public void onLoadingError() {
    }

    public void drop(int from, int to) {
        if (from != to) {
            this.mFragmentInterface.onReorderItem(((FavoritesItem) this.mListView.getItemAtPosition(from)).getMemoId(), ((FavoritesItem) this.mListView.getItemAtPosition(to)).getMemoId());
            this.mFragmentInterface.requestRefresh(this.mMode);
        }
    }

    public void onUpdateToolbar(SToolbar toolbar) {
        if (this.mMode != Mode.MODE_CONTACTS) {
            setToolbarItemsVisible(toolbar, false);
        } else if (toolbar.getMenu().size() <= 0) {
            toolbar.inflateMenu(2131755010);
            toolbar.setOnMenuItemClickListener(new C12067());
        } else {
            setToolbarItemsVisible(toolbar, true);
        }
    }

    private void setToolbarItemsVisible(SToolbar toolbar, boolean visible) {
        for (int i = 0; i < toolbar.getMenu().size(); i++) {
            toolbar.getMenu().getItem(i).setVisible(visible);
        }
    }

    public void setupKeyboard() {
        InCarConnection.registerKeyboard(this.mKeyboardView, this.mSearch);
    }
}
