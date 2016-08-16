package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.widget.ProgressBar;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.model.holder.ViewHolderComponent;

public class ComponentEntry extends StoreEntry {
    public static final Creator<ComponentEntry> CREATOR;
    public static short PROGRESS_MAX;
    private boolean mIsDownloading;
    private boolean mIsMandatory;
    private int mProgress;
    private long mSize;
    private InstallStatus mStatus;
    private boolean mUpdateAvailable;

    /* renamed from: com.sygic.aura.store.data.ComponentEntry.1 */
    static class C16951 implements Creator<ComponentEntry> {
        C16951() {
        }

        public ComponentEntry createFromParcel(Parcel source) {
            return new ComponentEntry(source);
        }

        public ComponentEntry[] newArray(int size) {
            return new ComponentEntry[size];
        }
    }

    public enum InstallStatus {
        IsNone,
        IsNotInstalled,
        IsInstalled,
        IsPartiallyInstalled,
        IsUninstalled,
        IsUninstalling,
        IsCorrupted
    }

    static {
        PROGRESS_MAX = (short) 100;
        CREATOR = new C16951();
    }

    public ComponentEntry(String id, String title, String iconUrl, String langCode, int status, boolean isUpdateAvailable, boolean isMandatory, boolean isDownloading, int index) {
        super(id, title, iconUrl, index);
        this.mProgress = 0;
        this.mIsMandatory = false;
        this.mIsDownloading = false;
        this.mUpdateAvailable = false;
        this.mISO = langCode;
        this.mStatus = InstallStatus.values()[status];
        this.mUpdateAvailable = isUpdateAvailable;
        this.mIsMandatory = isMandatory;
        this.mIsDownloading = isDownloading;
    }

    public EViewType getType() {
        return EViewType.TYPE_COMPONENT;
    }

    public boolean isMandatory() {
        return this.mIsMandatory;
    }

    public boolean isInstalled() {
        return this.mStatus.equals(InstallStatus.IsInstalled);
    }

    public boolean isUpdateAvailable() {
        return this.mUpdateAvailable;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public boolean isDownloading() {
        return this.mIsDownloading;
    }

    public void stopDownloading(ViewHolderComponent holder) {
        this.mIsDownloading = false;
        if (holder != null) {
            holder.getProgressView().setIndeterminate(true);
        }
    }

    public void setDownloading(ViewHolderComponent holder) {
        this.mIsDownloading = true;
        showInstallNotification(holder, true);
    }

    public void showInstallNotification(ViewHolderComponent holder, boolean install) {
        if (holder != null) {
            holder.setActionState(false, install);
            holder.showProgressAnim(install);
        }
    }

    public void setProgress(ProgressBar progressView, short progress) {
        this.mProgress = progress;
        if (progressView != null) {
            if (this.mProgress > 0 && progressView.isIndeterminate()) {
                progressView.setIndeterminate(false);
            }
            progressView.setProgress(this.mProgress);
        }
    }

    public void setFinished(ViewHolderComponent holder) {
        this.mIsDownloading = false;
        this.mStatus = InstallStatus.IsInstalled;
        if (holder != null) {
            holder.setActionState(true, false);
            holder.showProgressAnim(false);
        }
    }

    public void setInstalled(InstallStatus status) {
        this.mStatus = status;
    }

    public String getSummary() {
        long size = getComponentSize();
        if (size > 0) {
            return ResourceManager.nativeFormatBytes(size);
        }
        return null;
    }

    public long getComponentSize() {
        if (this.mSize == 0) {
            this.mSize = ComponentManager.nativeGetComponentSize(getId(), getIndex());
        }
        return this.mSize;
    }

    public boolean install() {
        return ComponentManager.nativeInstall(getId(), getIndex());
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = (byte) 1;
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
        dest.writeInt(this.mProgress);
        dest.writeLong(this.mSize);
        if (this.mIsMandatory) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (this.mIsDownloading) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (!this.mUpdateAvailable) {
            b2 = (byte) 0;
        }
        dest.writeByte(b2);
    }

    protected ComponentEntry(Parcel in) {
        boolean z;
        boolean z2 = true;
        super(in);
        this.mProgress = 0;
        this.mIsMandatory = false;
        this.mIsDownloading = false;
        this.mUpdateAvailable = false;
        int tmpMStatus = in.readInt();
        this.mStatus = tmpMStatus == -1 ? null : InstallStatus.values()[tmpMStatus];
        this.mProgress = in.readInt();
        this.mSize = in.readLong();
        if (in.readByte() != null) {
            z = true;
        } else {
            z = false;
        }
        this.mIsMandatory = z;
        if (in.readByte() != null) {
            z = true;
        } else {
            z = false;
        }
        this.mIsDownloading = z;
        if (in.readByte() == null) {
            z2 = false;
        }
        this.mUpdateAvailable = z2;
    }
}
