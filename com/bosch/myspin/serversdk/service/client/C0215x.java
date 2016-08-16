package com.bosch.myspin.serversdk.service.client;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import com.bosch.myspin.serversdk.utils.C0237f;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.ArrayList;

/* renamed from: com.bosch.myspin.serversdk.service.client.x */
public class C0215x {
    static final LogComponent f296a;
    private static C0215x f297c;
    ArrayList<View> f298b;
    private C0214a f299d;
    private OnHierarchyChangeListener f300e;

    /* renamed from: com.bosch.myspin.serversdk.service.client.x.a */
    class C0214a implements OnHierarchyChangeListener {
        final /* synthetic */ C0215x f295a;

        private C0214a(C0215x c0215x) {
            this.f295a = c0215x;
        }

        public void onChildViewAdded(View view, View view2) {
            Logger.logDebug(C0215x.f296a, "ViewManager.ViewHierarchyListener/onChildViewAdded => setLayerTypeRecursive ( " + view2 + " )");
            if (view2 instanceof ViewGroup) {
                this.f295a.m321a((ViewGroup) view2, this.f295a.f299d);
            }
            if (this.f295a.f300e != null) {
                this.f295a.f300e.onChildViewAdded(view, view2);
            }
            OnHierarchyChangeListener c = C0237f.m369a().m372c(view);
            if (c != null) {
                c.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            Logger.logDebug(C0215x.f296a, "ViewManager.ViewHierarchyListener/onChildViewRemoved");
            if (view2 instanceof ViewGroup) {
                this.f295a.m321a((ViewGroup) view2, null);
            }
            if (this.f295a.f300e != null) {
                this.f295a.f300e.onChildViewRemoved(view, view2);
            }
            OnHierarchyChangeListener c = C0237f.m369a().m372c(view);
            if (c != null) {
                c.onChildViewRemoved(view, view2);
            }
        }
    }

    static {
        f296a = LogComponent.UI;
        f297c = null;
    }

    private C0215x() {
        this.f298b = new ArrayList();
        this.f299d = new C0214a();
        this.f300e = null;
    }

    public static C0215x m320a() {
        if (f297c == null) {
            f297c = new C0215x();
        }
        return f297c;
    }

    public synchronized void m324a(View view) {
        if (view != null) {
            if (!this.f298b.contains(view)) {
                this.f298b.add(view);
                Logger.logDebug(f296a, "ViewManager/addCaptureView > views.size = [" + this.f298b.size() + "]");
                if (view instanceof ViewGroup) {
                    m321a((ViewGroup) view, this.f299d);
                }
            }
        }
        Logger.logWarning(f296a, "ViewManager/Not going to capture view: " + view);
    }

    public synchronized void m326b(View view) {
        if (view != null) {
            if (!this.f298b.contains(view)) {
                this.f298b.add(0, view);
                Logger.logDebug(f296a, "ViewManager/addCaptureView > views.size = [" + this.f298b.size() + "]");
                if (view instanceof ViewGroup) {
                    m321a((ViewGroup) view, this.f299d);
                }
            }
        }
        Logger.logWarning(f296a, "ViewManager/Not going to capture view: " + view);
    }

    public synchronized void m327c(View view) {
        if (view == null) {
            Logger.logWarning(f296a, "ViewManager/View parameter is null and will not be removed!");
        } else {
            this.f298b.remove(view);
            if (view instanceof ViewGroup) {
                m321a((ViewGroup) view, null);
            }
            Logger.logDebug(f296a, "ViewManager/removeCaptureView > views.size = [" + this.f298b.size() + "]");
        }
    }

    private void m321a(ViewGroup viewGroup, OnHierarchyChangeListener onHierarchyChangeListener) {
        viewGroup.setOnHierarchyChangeListener(onHierarchyChangeListener);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                m321a((ViewGroup) childAt, onHierarchyChangeListener);
            }
        }
    }

    public void m325a(OnHierarchyChangeListener onHierarchyChangeListener) {
        this.f300e = onHierarchyChangeListener;
    }
}
