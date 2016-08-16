package android.support.v4.app;

import android.os.Build.VERSION;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransitionCompat21.EpicenterView;
import android.support.v4.app.FragmentTransitionCompat21.ViewRetriever;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import loquendo.tts.engine.TTSConst;

final class BackStackRecord extends FragmentTransaction implements BackStackEntry, Runnable {
    static final boolean SUPPORTS_TRANSITIONS;
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    Op mHead;
    int mIndex;
    final FragmentManagerImpl mManager;
    String mName;
    int mNumOp;
    int mPopEnterAnim;
    int mPopExitAnim;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    Op mTail;
    int mTransition;
    int mTransitionStyle;

    /* renamed from: android.support.v4.app.BackStackRecord.1 */
    class C00351 implements ViewRetriever {
        final /* synthetic */ Fragment val$inFragment;

        C00351(Fragment fragment) {
            this.val$inFragment = fragment;
        }

        public View getView() {
            return this.val$inFragment.getView();
        }
    }

    /* renamed from: android.support.v4.app.BackStackRecord.2 */
    class C00362 implements OnPreDrawListener {
        final /* synthetic */ Fragment val$inFragment;
        final /* synthetic */ boolean val$isBack;
        final /* synthetic */ Fragment val$outFragment;
        final /* synthetic */ View val$sceneRoot;
        final /* synthetic */ ArrayList val$sharedElementTargets;
        final /* synthetic */ Object val$sharedElementTransition;
        final /* synthetic */ TransitionState val$state;

        C00362(View view, Object obj, ArrayList arrayList, TransitionState transitionState, boolean z, Fragment fragment, Fragment fragment2) {
            this.val$sceneRoot = view;
            this.val$sharedElementTransition = obj;
            this.val$sharedElementTargets = arrayList;
            this.val$state = transitionState;
            this.val$isBack = z;
            this.val$inFragment = fragment;
            this.val$outFragment = fragment2;
        }

        public boolean onPreDraw() {
            this.val$sceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            if (this.val$sharedElementTransition != null) {
                FragmentTransitionCompat21.removeTargets(this.val$sharedElementTransition, this.val$sharedElementTargets);
                this.val$sharedElementTargets.clear();
                ArrayMap<String, View> namedViews = BackStackRecord.this.mapSharedElementsIn(this.val$state, this.val$isBack, this.val$inFragment);
                this.val$sharedElementTargets.add(this.val$state.nonExistentView);
                this.val$sharedElementTargets.addAll(namedViews.values());
                FragmentTransitionCompat21.addTargets(this.val$sharedElementTransition, this.val$sharedElementTargets);
                BackStackRecord.this.setEpicenterIn(namedViews, this.val$state);
                BackStackRecord.this.callSharedElementEnd(this.val$state, this.val$inFragment, this.val$outFragment, this.val$isBack, namedViews);
            }
            return true;
        }
    }

    /* renamed from: android.support.v4.app.BackStackRecord.3 */
    class C00373 implements OnPreDrawListener {
        final /* synthetic */ int val$containerId;
        final /* synthetic */ View val$sceneRoot;
        final /* synthetic */ TransitionState val$state;
        final /* synthetic */ Object val$transition;

        C00373(View view, TransitionState transitionState, int i, Object obj) {
            this.val$sceneRoot = view;
            this.val$state = transitionState;
            this.val$containerId = i;
            this.val$transition = obj;
        }

        public boolean onPreDraw() {
            this.val$sceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            BackStackRecord.this.excludeHiddenFragments(this.val$state, this.val$containerId, this.val$transition);
            return true;
        }
    }

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        Op next;
        int popEnterAnim;
        int popExitAnim;
        Op prev;
        ArrayList<Fragment> removed;

        Op() {
        }
    }

    public class TransitionState {
        public EpicenterView enteringEpicenterView;
        public ArrayList<View> hiddenFragmentViews;
        public ArrayMap<String, String> nameOverrides;
        public View nonExistentView;

        public TransitionState() {
            this.nameOverrides = new ArrayMap();
            this.hiddenFragmentViews = new ArrayList();
            this.enteringEpicenterView = new EpicenterView();
        }
    }

    static {
        SUPPORTS_TRANSITIONS = VERSION.SDK_INT >= 21;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mName != null) {
            sb.append(" ");
            sb.append(this.mName);
        }
        sb.append("}");
        return sb.toString();
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        dump(prefix, writer, true);
    }

    public void dump(String prefix, PrintWriter writer, boolean full) {
        if (full) {
            writer.print(prefix);
            writer.print("mName=");
            writer.print(this.mName);
            writer.print(" mIndex=");
            writer.print(this.mIndex);
            writer.print(" mCommitted=");
            writer.println(this.mCommitted);
            if (this.mTransition != 0) {
                writer.print(prefix);
                writer.print("mTransition=#");
                writer.print(Integer.toHexString(this.mTransition));
                writer.print(" mTransitionStyle=#");
                writer.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (!(this.mEnterAnim == 0 && this.mExitAnim == 0)) {
                writer.print(prefix);
                writer.print("mEnterAnim=#");
                writer.print(Integer.toHexString(this.mEnterAnim));
                writer.print(" mExitAnim=#");
                writer.println(Integer.toHexString(this.mExitAnim));
            }
            if (!(this.mPopEnterAnim == 0 && this.mPopExitAnim == 0)) {
                writer.print(prefix);
                writer.print("mPopEnterAnim=#");
                writer.print(Integer.toHexString(this.mPopEnterAnim));
                writer.print(" mPopExitAnim=#");
                writer.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (!(this.mBreadCrumbTitleRes == 0 && this.mBreadCrumbTitleText == null)) {
                writer.print(prefix);
                writer.print("mBreadCrumbTitleRes=#");
                writer.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                writer.print(" mBreadCrumbTitleText=");
                writer.println(this.mBreadCrumbTitleText);
            }
            if (!(this.mBreadCrumbShortTitleRes == 0 && this.mBreadCrumbShortTitleText == null)) {
                writer.print(prefix);
                writer.print("mBreadCrumbShortTitleRes=#");
                writer.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                writer.print(" mBreadCrumbShortTitleText=");
                writer.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (this.mHead != null) {
            writer.print(prefix);
            writer.println("Operations:");
            String innerPrefix = prefix + "    ";
            Op op = this.mHead;
            int num = 0;
            while (op != null) {
                String cmdStr;
                switch (op.cmd) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                        cmdStr = "NULL";
                        break;
                    case TTSConst.TTSMULTILINE /*1*/:
                        cmdStr = "ADD";
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        cmdStr = "REPLACE";
                        break;
                    case TTSConst.TTSUNICODE /*3*/:
                        cmdStr = "REMOVE";
                        break;
                    case TTSConst.TTSXML /*4*/:
                        cmdStr = "HIDE";
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        cmdStr = "SHOW";
                        break;
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                        cmdStr = "DETACH";
                        break;
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                        cmdStr = "ATTACH";
                        break;
                    default:
                        cmdStr = "cmd=" + op.cmd;
                        break;
                }
                writer.print(prefix);
                writer.print("  Op #");
                writer.print(num);
                writer.print(": ");
                writer.print(cmdStr);
                writer.print(" ");
                writer.println(op.fragment);
                if (full) {
                    if (!(op.enterAnim == 0 && op.exitAnim == 0)) {
                        writer.print(prefix);
                        writer.print("enterAnim=#");
                        writer.print(Integer.toHexString(op.enterAnim));
                        writer.print(" exitAnim=#");
                        writer.println(Integer.toHexString(op.exitAnim));
                    }
                    if (!(op.popEnterAnim == 0 && op.popExitAnim == 0)) {
                        writer.print(prefix);
                        writer.print("popEnterAnim=#");
                        writer.print(Integer.toHexString(op.popEnterAnim));
                        writer.print(" popExitAnim=#");
                        writer.println(Integer.toHexString(op.popExitAnim));
                    }
                }
                if (op.removed != null && op.removed.size() > 0) {
                    for (int i = 0; i < op.removed.size(); i++) {
                        writer.print(innerPrefix);
                        if (op.removed.size() == 1) {
                            writer.print("Removed: ");
                        } else {
                            if (i == 0) {
                                writer.println("Removed:");
                            }
                            writer.print(innerPrefix);
                            writer.print("  #");
                            writer.print(i);
                            writer.print(": ");
                        }
                        writer.println(op.removed.get(i));
                    }
                }
                op = op.next;
                num++;
            }
        }
    }

    public BackStackRecord(FragmentManagerImpl manager) {
        this.mAllowAddToBackStack = true;
        this.mIndex = -1;
        this.mManager = manager;
    }

    void addOp(Op op) {
        if (this.mHead == null) {
            this.mTail = op;
            this.mHead = op;
        } else {
            op.prev = this.mTail;
            this.mTail.next = op;
            this.mTail = op;
        }
        op.enterAnim = this.mEnterAnim;
        op.exitAnim = this.mExitAnim;
        op.popEnterAnim = this.mPopEnterAnim;
        op.popExitAnim = this.mPopExitAnim;
        this.mNumOp++;
    }

    public FragmentTransaction add(Fragment fragment, String tag) {
        doAddOp(0, fragment, tag, 1);
        return this;
    }

    public FragmentTransaction add(int containerViewId, Fragment fragment, String tag) {
        doAddOp(containerViewId, fragment, tag, 1);
        return this;
    }

    private void doAddOp(int containerViewId, Fragment fragment, String tag, int opcmd) {
        fragment.mFragmentManager = this.mManager;
        if (tag != null) {
            if (fragment.mTag == null || tag.equals(fragment.mTag)) {
                fragment.mTag = tag;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + tag);
            }
        }
        if (containerViewId != 0) {
            if (fragment.mFragmentId == 0 || fragment.mFragmentId == containerViewId) {
                fragment.mFragmentId = containerViewId;
                fragment.mContainerId = containerViewId;
            } else {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + containerViewId);
            }
        }
        Op op = new Op();
        op.cmd = opcmd;
        op.fragment = fragment;
        addOp(op);
    }

    public FragmentTransaction replace(int containerViewId, Fragment fragment, String tag) {
        if (containerViewId == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        doAddOp(containerViewId, fragment, tag, 2);
        return this;
    }

    public FragmentTransaction remove(Fragment fragment) {
        Op op = new Op();
        op.cmd = 3;
        op.fragment = fragment;
        addOp(op);
        return this;
    }

    public FragmentTransaction detach(Fragment fragment) {
        Op op = new Op();
        op.cmd = 6;
        op.fragment = fragment;
        addOp(op);
        return this;
    }

    public FragmentTransaction attach(Fragment fragment) {
        Op op = new Op();
        op.cmd = 7;
        op.fragment = fragment;
        addOp(op);
        return this;
    }

    public FragmentTransaction setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
        this.mEnterAnim = enter;
        this.mExitAnim = exit;
        this.mPopEnterAnim = popEnter;
        this.mPopExitAnim = popExit;
        return this;
    }

    public FragmentTransaction addToBackStack(String name) {
        if (this.mAllowAddToBackStack) {
            this.mAddToBackStack = true;
            this.mName = name;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    void bumpBackStackNesting(int amt) {
        if (this.mAddToBackStack) {
            if (FragmentManagerImpl.DEBUG) {
                Log.v("FragmentManager", "Bump nesting in " + this + " by " + amt);
            }
            for (Op op = this.mHead; op != null; op = op.next) {
                if (op.fragment != null) {
                    Fragment fragment = op.fragment;
                    fragment.mBackStackNesting += amt;
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v("FragmentManager", "Bump nesting of " + op.fragment + " to " + op.fragment.mBackStackNesting);
                    }
                }
                if (op.removed != null) {
                    for (int i = op.removed.size() - 1; i >= 0; i--) {
                        Fragment r = (Fragment) op.removed.get(i);
                        r.mBackStackNesting += amt;
                        if (FragmentManagerImpl.DEBUG) {
                            Log.v("FragmentManager", "Bump nesting of " + r + " to " + r.mBackStackNesting);
                        }
                    }
                }
            }
        }
    }

    public int commit() {
        return commitInternal(false);
    }

    public int commitAllowingStateLoss() {
        return commitInternal(true);
    }

    int commitInternal(boolean allowStateLoss) {
        if (this.mCommitted) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "Commit: " + this);
            dump("  ", null, new PrintWriter(new LogWriter("FragmentManager")), null);
        }
        this.mCommitted = true;
        if (this.mAddToBackStack) {
            this.mIndex = this.mManager.allocBackStackIndex(this);
        } else {
            this.mIndex = -1;
        }
        this.mManager.enqueueAction(this, allowStateLoss);
        return this.mIndex;
    }

    public void run() {
        if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "Run: " + this);
        }
        if (!this.mAddToBackStack || this.mIndex >= 0) {
            bumpBackStackNesting(1);
            TransitionState state = null;
            if (SUPPORTS_TRANSITIONS) {
                SparseArray<Fragment> firstOutFragments = new SparseArray();
                SparseArray<Fragment> lastInFragments = new SparseArray();
                calculateFragments(firstOutFragments, lastInFragments);
                state = beginTransition(firstOutFragments, lastInFragments, false);
            }
            int transitionStyle = state != null ? 0 : this.mTransitionStyle;
            int transition = state != null ? 0 : this.mTransition;
            Op op = this.mHead;
            while (op != null) {
                int enterAnim = state != null ? 0 : op.enterAnim;
                int exitAnim = state != null ? 0 : op.exitAnim;
                Fragment f;
                switch (op.cmd) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        f = op.fragment;
                        f.mNextAnim = enterAnim;
                        this.mManager.addFragment(f, false);
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        f = op.fragment;
                        if (this.mManager.mAdded != null) {
                            for (int i = 0; i < this.mManager.mAdded.size(); i++) {
                                Fragment old = (Fragment) this.mManager.mAdded.get(i);
                                if (FragmentManagerImpl.DEBUG) {
                                    Log.v("FragmentManager", "OP_REPLACE: adding=" + f + " old=" + old);
                                }
                                if (f == null || old.mContainerId == f.mContainerId) {
                                    if (old == f) {
                                        f = null;
                                        op.fragment = null;
                                    } else {
                                        if (op.removed == null) {
                                            op.removed = new ArrayList();
                                        }
                                        op.removed.add(old);
                                        old.mNextAnim = exitAnim;
                                        if (this.mAddToBackStack) {
                                            old.mBackStackNesting++;
                                            if (FragmentManagerImpl.DEBUG) {
                                                Log.v("FragmentManager", "Bump nesting of " + old + " to " + old.mBackStackNesting);
                                            }
                                        }
                                        this.mManager.removeFragment(old, transition, transitionStyle);
                                    }
                                }
                            }
                        }
                        if (f == null) {
                            break;
                        }
                        f.mNextAnim = enterAnim;
                        this.mManager.addFragment(f, false);
                        break;
                    case TTSConst.TTSUNICODE /*3*/:
                        f = op.fragment;
                        f.mNextAnim = exitAnim;
                        this.mManager.removeFragment(f, transition, transitionStyle);
                        break;
                    case TTSConst.TTSXML /*4*/:
                        f = op.fragment;
                        f.mNextAnim = exitAnim;
                        this.mManager.hideFragment(f, transition, transitionStyle);
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        f = op.fragment;
                        f.mNextAnim = enterAnim;
                        this.mManager.showFragment(f, transition, transitionStyle);
                        break;
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                        f = op.fragment;
                        f.mNextAnim = exitAnim;
                        this.mManager.detachFragment(f, transition, transitionStyle);
                        break;
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                        f = op.fragment;
                        f.mNextAnim = enterAnim;
                        this.mManager.attachFragment(f, transition, transitionStyle);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                }
                op = op.next;
            }
            this.mManager.moveToState(this.mManager.mCurState, transition, transitionStyle, true);
            if (this.mAddToBackStack) {
                this.mManager.addBackStackState(this);
                return;
            }
            return;
        }
        throw new IllegalStateException("addToBackStack() called after commit()");
    }

    private static void setFirstOut(SparseArray<Fragment> fragments, Fragment fragment) {
        if (fragment != null) {
            int containerId = fragment.mContainerId;
            if (containerId != 0 && !fragment.isHidden() && fragment.isAdded() && fragment.getView() != null && fragments.get(containerId) == null) {
                fragments.put(containerId, fragment);
            }
        }
    }

    private void setLastIn(SparseArray<Fragment> fragments, Fragment fragment) {
        if (fragment != null) {
            int containerId = fragment.mContainerId;
            if (containerId != 0) {
                fragments.put(containerId, fragment);
            }
        }
    }

    private void calculateFragments(SparseArray<Fragment> firstOutFragments, SparseArray<Fragment> lastInFragments) {
        if (this.mManager.mContainer.hasView()) {
            for (Op op = this.mHead; op != null; op = op.next) {
                switch (op.cmd) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        setLastIn(lastInFragments, op.fragment);
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        Fragment f = op.fragment;
                        if (this.mManager.mAdded != null) {
                            for (int i = 0; i < this.mManager.mAdded.size(); i++) {
                                Fragment old = (Fragment) this.mManager.mAdded.get(i);
                                if (f == null || old.mContainerId == f.mContainerId) {
                                    if (old == f) {
                                        f = null;
                                    } else {
                                        setFirstOut(firstOutFragments, old);
                                    }
                                }
                            }
                        }
                        setLastIn(lastInFragments, f);
                        break;
                    case TTSConst.TTSUNICODE /*3*/:
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    case TTSConst.TTSXML /*4*/:
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        setLastIn(lastInFragments, op.fragment);
                        break;
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                        setLastIn(lastInFragments, op.fragment);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void calculateBackFragments(SparseArray<Fragment> firstOutFragments, SparseArray<Fragment> lastInFragments) {
        if (this.mManager.mContainer.hasView()) {
            for (Op op = this.mHead; op != null; op = op.next) {
                switch (op.cmd) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        if (op.removed != null) {
                            for (int i = op.removed.size() - 1; i >= 0; i--) {
                                setLastIn(lastInFragments, (Fragment) op.removed.get(i));
                            }
                        }
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    case TTSConst.TTSUNICODE /*3*/:
                        setLastIn(lastInFragments, op.fragment);
                        break;
                    case TTSConst.TTSXML /*4*/:
                        setLastIn(lastInFragments, op.fragment);
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                        setLastIn(lastInFragments, op.fragment);
                        break;
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                        setFirstOut(firstOutFragments, op.fragment);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public TransitionState popFromBackStack(boolean doStateMove, TransitionState state, SparseArray<Fragment> firstOutFragments, SparseArray<Fragment> lastInFragments) {
        if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "popFromBackStack: " + this);
            dump("  ", null, new PrintWriter(new LogWriter("FragmentManager")), null);
        }
        if (SUPPORTS_TRANSITIONS) {
            if (state == null) {
                if (!(firstOutFragments.size() == 0 && lastInFragments.size() == 0)) {
                    state = beginTransition(firstOutFragments, lastInFragments, true);
                }
            } else if (!doStateMove) {
                setNameOverrides(state, this.mSharedElementTargetNames, this.mSharedElementSourceNames);
            }
        }
        bumpBackStackNesting(-1);
        int transitionStyle = state != null ? 0 : this.mTransitionStyle;
        int transition = state != null ? 0 : this.mTransition;
        Op op = this.mTail;
        while (op != null) {
            int popEnterAnim = state != null ? 0 : op.popEnterAnim;
            int popExitAnim = state != null ? 0 : op.popExitAnim;
            Fragment f;
            switch (op.cmd) {
                case TTSConst.TTSMULTILINE /*1*/:
                    f = op.fragment;
                    f.mNextAnim = popExitAnim;
                    this.mManager.removeFragment(f, FragmentManagerImpl.reverseTransit(transition), transitionStyle);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    f = op.fragment;
                    if (f != null) {
                        f.mNextAnim = popExitAnim;
                        this.mManager.removeFragment(f, FragmentManagerImpl.reverseTransit(transition), transitionStyle);
                    }
                    if (op.removed == null) {
                        break;
                    }
                    for (int i = 0; i < op.removed.size(); i++) {
                        Fragment old = (Fragment) op.removed.get(i);
                        old.mNextAnim = popEnterAnim;
                        this.mManager.addFragment(old, false);
                    }
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    f = op.fragment;
                    f.mNextAnim = popEnterAnim;
                    this.mManager.addFragment(f, false);
                    break;
                case TTSConst.TTSXML /*4*/:
                    f = op.fragment;
                    f.mNextAnim = popEnterAnim;
                    this.mManager.showFragment(f, FragmentManagerImpl.reverseTransit(transition), transitionStyle);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    f = op.fragment;
                    f.mNextAnim = popExitAnim;
                    this.mManager.hideFragment(f, FragmentManagerImpl.reverseTransit(transition), transitionStyle);
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    f = op.fragment;
                    f.mNextAnim = popEnterAnim;
                    this.mManager.attachFragment(f, FragmentManagerImpl.reverseTransit(transition), transitionStyle);
                    break;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    f = op.fragment;
                    f.mNextAnim = popEnterAnim;
                    this.mManager.detachFragment(f, FragmentManagerImpl.reverseTransit(transition), transitionStyle);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
            }
            op = op.prev;
        }
        if (doStateMove) {
            this.mManager.moveToState(this.mManager.mCurState, FragmentManagerImpl.reverseTransit(transition), transitionStyle, true);
            state = null;
        }
        if (this.mIndex >= 0) {
            this.mManager.freeBackStackIndex(this.mIndex);
            this.mIndex = -1;
        }
        return state;
    }

    public String getName() {
        return this.mName;
    }

    private TransitionState beginTransition(SparseArray<Fragment> firstOutFragments, SparseArray<Fragment> lastInFragments, boolean isBack) {
        int i;
        TransitionState state = new TransitionState();
        state.nonExistentView = new View(this.mManager.mActivity);
        boolean anyTransitionStarted = false;
        for (i = 0; i < firstOutFragments.size(); i++) {
            if (configureTransitions(firstOutFragments.keyAt(i), state, isBack, firstOutFragments, lastInFragments)) {
                anyTransitionStarted = true;
            }
        }
        for (i = 0; i < lastInFragments.size(); i++) {
            int containerId = lastInFragments.keyAt(i);
            if (firstOutFragments.get(containerId) == null && configureTransitions(containerId, state, isBack, firstOutFragments, lastInFragments)) {
                anyTransitionStarted = true;
            }
        }
        if (anyTransitionStarted) {
            return state;
        }
        return null;
    }

    private static Object getEnterTransition(Fragment inFragment, boolean isBack) {
        if (inFragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition(isBack ? inFragment.getReenterTransition() : inFragment.getEnterTransition());
    }

    private static Object getExitTransition(Fragment outFragment, boolean isBack) {
        if (outFragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition(isBack ? outFragment.getReturnTransition() : outFragment.getExitTransition());
    }

    private static Object getSharedElementTransition(Fragment inFragment, Fragment outFragment, boolean isBack) {
        if (inFragment == null || outFragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition(isBack ? outFragment.getSharedElementReturnTransition() : inFragment.getSharedElementEnterTransition());
    }

    private static Object captureExitingViews(Object exitTransition, Fragment outFragment, ArrayList<View> exitingViews, ArrayMap<String, View> namedViews, View nonExistentView) {
        if (exitTransition != null) {
            return FragmentTransitionCompat21.captureExitingViews(exitTransition, outFragment.getView(), exitingViews, namedViews, nonExistentView);
        }
        return exitTransition;
    }

    private ArrayMap<String, View> remapSharedElements(TransitionState state, Fragment outFragment, boolean isBack) {
        ArrayMap namedViews = new ArrayMap();
        if (this.mSharedElementSourceNames != null) {
            FragmentTransitionCompat21.findNamedViews(namedViews, outFragment.getView());
            if (isBack) {
                namedViews.retainAll(this.mSharedElementTargetNames);
            } else {
                namedViews = remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, namedViews);
            }
        }
        if (isBack) {
            if (outFragment.mEnterTransitionCallback != null) {
                outFragment.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, namedViews);
            }
            setBackNameOverrides(state, namedViews, false);
        } else {
            if (outFragment.mExitTransitionCallback != null) {
                outFragment.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, namedViews);
            }
            setNameOverrides(state, namedViews, false);
        }
        return namedViews;
    }

    private boolean configureTransitions(int containerId, TransitionState state, boolean isBack, SparseArray<Fragment> firstOutFragments, SparseArray<Fragment> lastInFragments) {
        View sceneRoot = (ViewGroup) this.mManager.mContainer.findViewById(containerId);
        if (sceneRoot == null) {
            return false;
        }
        Fragment inFragment = (Fragment) lastInFragments.get(containerId);
        Fragment outFragment = (Fragment) firstOutFragments.get(containerId);
        Object enterTransition = getEnterTransition(inFragment, isBack);
        Object sharedElementTransition = getSharedElementTransition(inFragment, outFragment, isBack);
        Object exitTransition = getExitTransition(outFragment, isBack);
        if (enterTransition == null && sharedElementTransition == null && exitTransition == null) {
            return false;
        }
        ArrayMap<String, View> namedViews = null;
        ArrayList<View> sharedElementTargets = new ArrayList();
        if (sharedElementTransition != null) {
            namedViews = remapSharedElements(state, outFragment, isBack);
            sharedElementTargets.add(state.nonExistentView);
            sharedElementTargets.addAll(namedViews.values());
            SharedElementCallback callback = isBack ? outFragment.mEnterTransitionCallback : inFragment.mEnterTransitionCallback;
            if (callback != null) {
                callback.onSharedElementStart(new ArrayList(namedViews.keySet()), new ArrayList(namedViews.values()), null);
            }
        }
        ArrayList<View> exitingViews = new ArrayList();
        exitTransition = captureExitingViews(exitTransition, outFragment, exitingViews, namedViews, state.nonExistentView);
        if (!(this.mSharedElementTargetNames == null || namedViews == null)) {
            View epicenterView = (View) namedViews.get(this.mSharedElementTargetNames.get(0));
            if (epicenterView != null) {
                if (exitTransition != null) {
                    FragmentTransitionCompat21.setEpicenter(exitTransition, epicenterView);
                }
                if (sharedElementTransition != null) {
                    FragmentTransitionCompat21.setEpicenter(sharedElementTransition, epicenterView);
                }
            }
        }
        ViewRetriever viewRetriever = new C00351(inFragment);
        if (sharedElementTransition != null) {
            prepareSharedElementTransition(state, sceneRoot, sharedElementTransition, inFragment, outFragment, isBack, sharedElementTargets);
        }
        ArrayList<View> enteringViews = new ArrayList();
        ArrayMap<String, View> renamedViews = new ArrayMap();
        Object transition = FragmentTransitionCompat21.mergeTransitions(enterTransition, exitTransition, sharedElementTransition, isBack ? inFragment.getAllowReturnTransitionOverlap() : inFragment.getAllowEnterTransitionOverlap());
        if (transition != null) {
            FragmentTransitionCompat21.addTransitionTargets(enterTransition, sharedElementTransition, sceneRoot, viewRetriever, state.nonExistentView, state.enteringEpicenterView, state.nameOverrides, enteringViews, renamedViews, sharedElementTargets);
            excludeHiddenFragmentsAfterEnter(sceneRoot, state, containerId, transition);
            FragmentTransitionCompat21.excludeTarget(transition, state.nonExistentView, true);
            excludeHiddenFragments(state, containerId, transition);
            FragmentTransitionCompat21.beginDelayedTransition(sceneRoot, transition);
            FragmentTransitionCompat21.cleanupTransitions(sceneRoot, state.nonExistentView, enterTransition, enteringViews, exitTransition, exitingViews, sharedElementTransition, sharedElementTargets, transition, state.hiddenFragmentViews, renamedViews);
        }
        if (transition != null) {
            return true;
        }
        return false;
    }

    private void prepareSharedElementTransition(TransitionState state, View sceneRoot, Object sharedElementTransition, Fragment inFragment, Fragment outFragment, boolean isBack, ArrayList<View> sharedElementTargets) {
        sceneRoot.getViewTreeObserver().addOnPreDrawListener(new C00362(sceneRoot, sharedElementTransition, sharedElementTargets, state, isBack, inFragment, outFragment));
    }

    private void callSharedElementEnd(TransitionState state, Fragment inFragment, Fragment outFragment, boolean isBack, ArrayMap<String, View> namedViews) {
        SharedElementCallback sharedElementCallback = isBack ? outFragment.mEnterTransitionCallback : inFragment.mEnterTransitionCallback;
        if (sharedElementCallback != null) {
            sharedElementCallback.onSharedElementEnd(new ArrayList(namedViews.keySet()), new ArrayList(namedViews.values()), null);
        }
    }

    private void setEpicenterIn(ArrayMap<String, View> namedViews, TransitionState state) {
        if (this.mSharedElementTargetNames != null && !namedViews.isEmpty()) {
            View epicenter = (View) namedViews.get(this.mSharedElementTargetNames.get(0));
            if (epicenter != null) {
                state.enteringEpicenterView.epicenter = epicenter;
            }
        }
    }

    private ArrayMap<String, View> mapSharedElementsIn(TransitionState state, boolean isBack, Fragment inFragment) {
        ArrayMap namedViews = mapEnteringSharedElements(state, inFragment, isBack);
        if (isBack) {
            if (inFragment.mExitTransitionCallback != null) {
                inFragment.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, namedViews);
            }
            setBackNameOverrides(state, namedViews, true);
        } else {
            if (inFragment.mEnterTransitionCallback != null) {
                inFragment.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, namedViews);
            }
            setNameOverrides(state, namedViews, true);
        }
        return namedViews;
    }

    private static ArrayMap<String, View> remapNames(ArrayList<String> inMap, ArrayList<String> toGoInMap, ArrayMap<String, View> namedViews) {
        if (namedViews.isEmpty()) {
            return namedViews;
        }
        ArrayMap<String, View> remappedViews = new ArrayMap();
        int numKeys = inMap.size();
        for (int i = 0; i < numKeys; i++) {
            View view = (View) namedViews.get(inMap.get(i));
            if (view != null) {
                remappedViews.put(toGoInMap.get(i), view);
            }
        }
        return remappedViews;
    }

    private ArrayMap<String, View> mapEnteringSharedElements(TransitionState state, Fragment inFragment, boolean isBack) {
        ArrayMap<String, View> namedViews = new ArrayMap();
        View root = inFragment.getView();
        if (root == null || this.mSharedElementSourceNames == null) {
            return namedViews;
        }
        FragmentTransitionCompat21.findNamedViews(namedViews, root);
        if (isBack) {
            return remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, namedViews);
        }
        namedViews.retainAll(this.mSharedElementTargetNames);
        return namedViews;
    }

    private void excludeHiddenFragmentsAfterEnter(View sceneRoot, TransitionState state, int containerId, Object transition) {
        sceneRoot.getViewTreeObserver().addOnPreDrawListener(new C00373(sceneRoot, state, containerId, transition));
    }

    private void excludeHiddenFragments(TransitionState state, int containerId, Object transition) {
        if (this.mManager.mAdded != null) {
            for (int i = 0; i < this.mManager.mAdded.size(); i++) {
                Fragment fragment = (Fragment) this.mManager.mAdded.get(i);
                if (!(fragment.mView == null || fragment.mContainer == null || fragment.mContainerId != containerId)) {
                    if (!fragment.mHidden) {
                        FragmentTransitionCompat21.excludeTarget(transition, fragment.mView, false);
                        state.hiddenFragmentViews.remove(fragment.mView);
                    } else if (!state.hiddenFragmentViews.contains(fragment.mView)) {
                        FragmentTransitionCompat21.excludeTarget(transition, fragment.mView, true);
                        state.hiddenFragmentViews.add(fragment.mView);
                    }
                }
            }
        }
    }

    private static void setNameOverride(ArrayMap<String, String> overrides, String source, String target) {
        if (source != null && target != null && !source.equals(target)) {
            for (int index = 0; index < overrides.size(); index++) {
                if (source.equals(overrides.valueAt(index))) {
                    overrides.setValueAt(index, target);
                    return;
                }
            }
            overrides.put(source, target);
        }
    }

    private static void setNameOverrides(TransitionState state, ArrayList<String> sourceNames, ArrayList<String> targetNames) {
        if (sourceNames != null) {
            for (int i = 0; i < sourceNames.size(); i++) {
                setNameOverride(state.nameOverrides, (String) sourceNames.get(i), (String) targetNames.get(i));
            }
        }
    }

    private void setBackNameOverrides(TransitionState state, ArrayMap<String, View> namedViews, boolean isEnd) {
        int count = this.mSharedElementTargetNames == null ? 0 : this.mSharedElementTargetNames.size();
        for (int i = 0; i < count; i++) {
            String source = (String) this.mSharedElementSourceNames.get(i);
            View view = (View) namedViews.get((String) this.mSharedElementTargetNames.get(i));
            if (view != null) {
                String target = FragmentTransitionCompat21.getTransitionName(view);
                if (isEnd) {
                    setNameOverride(state.nameOverrides, source, target);
                } else {
                    setNameOverride(state.nameOverrides, target, source);
                }
            }
        }
    }

    private void setNameOverrides(TransitionState state, ArrayMap<String, View> namedViews, boolean isEnd) {
        int count = namedViews.size();
        for (int i = 0; i < count; i++) {
            String source = (String) namedViews.keyAt(i);
            String target = FragmentTransitionCompat21.getTransitionName((View) namedViews.valueAt(i));
            if (isEnd) {
                setNameOverride(state.nameOverrides, source, target);
            } else {
                setNameOverride(state.nameOverrides, target, source);
            }
        }
    }
}
