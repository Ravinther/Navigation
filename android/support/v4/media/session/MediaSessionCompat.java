package android.support.v4.media.session;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;

public class MediaSessionCompat {

    public static final class QueueItem implements Parcelable {
        public static final Creator<QueueItem> CREATOR;
        private final MediaDescriptionCompat mDescription;
        private final long mId;

        /* renamed from: android.support.v4.media.session.MediaSessionCompat.QueueItem.1 */
        static class C00571 implements Creator<QueueItem> {
            C00571() {
            }

            public QueueItem createFromParcel(Parcel p) {
                return new QueueItem(null);
            }

            public QueueItem[] newArray(int size) {
                return new QueueItem[size];
            }
        }

        private QueueItem(Parcel in) {
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel(in);
            this.mId = in.readLong();
        }

        public void writeToParcel(Parcel dest, int flags) {
            this.mDescription.writeToParcel(dest, flags);
            dest.writeLong(this.mId);
        }

        public int describeContents() {
            return 0;
        }

        static {
            CREATOR = new C00571();
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }
    }

    static final class ResultReceiverWrapper implements Parcelable {
        public static final Creator<ResultReceiverWrapper> CREATOR;
        private ResultReceiver mResultReceiver;

        /* renamed from: android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper.1 */
        static class C00581 implements Creator<ResultReceiverWrapper> {
            C00581() {
            }

            public ResultReceiverWrapper createFromParcel(Parcel p) {
                return new ResultReceiverWrapper(p);
            }

            public ResultReceiverWrapper[] newArray(int size) {
                return new ResultReceiverWrapper[size];
            }
        }

        ResultReceiverWrapper(Parcel in) {
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(in);
        }

        static {
            CREATOR = new C00581();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            this.mResultReceiver.writeToParcel(dest, flags);
        }
    }

    public static final class Token implements Parcelable {
        public static final Creator<Token> CREATOR;
        private final Object mInner;

        /* renamed from: android.support.v4.media.session.MediaSessionCompat.Token.1 */
        static class C00591 implements Creator<Token> {
            C00591() {
            }

            public Token createFromParcel(Parcel in) {
                Object readParcelable;
                if (VERSION.SDK_INT >= 21) {
                    readParcelable = in.readParcelable(null);
                } else {
                    readParcelable = in.readStrongBinder();
                }
                return new Token(readParcelable);
            }

            public Token[] newArray(int size) {
                return new Token[size];
            }
        }

        Token(Object inner) {
            this.mInner = inner;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            if (VERSION.SDK_INT >= 21) {
                dest.writeParcelable((Parcelable) this.mInner, flags);
            } else {
                dest.writeStrongBinder((IBinder) this.mInner);
            }
        }

        static {
            CREATOR = new C00591();
        }
    }
}
