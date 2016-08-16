package com.amazon.device.iap.internal.p001b;

import com.amazon.android.Kiwi;
import com.amazon.android.framework.task.command.AbstractCommandTask;
import com.amazon.android.licensing.LicenseFailurePromptContentMapper;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.amazon.device.iap.internal.b.i */
public abstract class KiwiCommand extends AbstractCommandTask {
    private static final String f11a;
    private final KiwiRequest f12b;
    private final String f13c;
    private final String f14d;
    private final String f15e;
    private final Map<String, Object> f16f;
    private final LicenseFailurePromptContentMapper f17g;
    private boolean f18h;
    private KiwiCommand f19i;
    private KiwiCommand f20j;
    private boolean f21k;

    static {
        f11a = KiwiCommand.class.getSimpleName();
    }

    public KiwiCommand(KiwiRequest kiwiRequest, String str, String str2) {
        this.f17g = new LicenseFailurePromptContentMapper();
        this.f21k = false;
        this.f12b = kiwiRequest;
        this.f13c = kiwiRequest.m29c().toString();
        this.f14d = str;
        this.f15e = str2;
        this.f16f = new HashMap();
        this.f16f.put("requestId", this.f13c);
        this.f16f.put("sdkVersion", "2.0.0");
        this.f18h = true;
        this.f19i = null;
        this.f20j = null;
    }

    public KiwiCommand m18a(boolean z) {
        this.f21k = z;
        return this;
    }

    public void m19a(KiwiCommand kiwiCommand) {
        this.f19i = kiwiCommand;
    }

    public void m22b(KiwiCommand kiwiCommand) {
        this.f20j = kiwiCommand;
    }

    protected void m20a(String str, Object obj) {
        this.f16f.put(str, obj);
    }

    protected KiwiRequest m21b() {
        return this.f12b;
    }

    protected void m23b(boolean z) {
        this.f18h = z;
    }

    public void a_() {
        Kiwi.addCommandToCommandTaskPipeline(this);
    }
}
