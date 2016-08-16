package android.support.v4.app;

import java.util.List;

public abstract class FragmentManager {

    public interface BackStackEntry {
        String getName();
    }

    public interface OnBackStackChangedListener {
        void onBackStackChanged();
    }

    public abstract void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);

    public abstract FragmentTransaction beginTransaction();

    public abstract boolean executePendingTransactions();

    public abstract Fragment findFragmentByTag(String str);

    public abstract BackStackEntry getBackStackEntryAt(int i);

    public abstract int getBackStackEntryCount();

    public abstract List<Fragment> getFragments();

    public abstract boolean isDestroyed();

    public abstract void popBackStack();

    public abstract void popBackStack(int i, int i2);

    public abstract void popBackStack(String str, int i);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(String str, int i);

    public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener);
}
