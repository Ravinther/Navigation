package com.sygic.aura.helper.loading;

import android.os.AsyncTask;
import android.view.View;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.loading.loadable.LazyPoiListItemWrapper;
import com.sygic.aura.search.model.data.PoiListItem;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LazyLoading {
    private static final ThreadPoolExecutor mPool;

    public interface LazyLoadable {
        Object execute();

        long getIdentifier();

        <R extends View> void setResult(Object obj, R r);
    }

    public static class TaskCreator<S extends LazyLoadable> {
        private final S mSource;

        /* renamed from: com.sygic.aura.helper.loading.LazyLoading.TaskCreator.1 */
        class C12811 extends AsyncTask<Void, Void, Object> {
            final /* synthetic */ View val$receiverView;

            C12811(View view) {
                this.val$receiverView = view;
            }

            protected Object doInBackground(Void... params) {
                return TaskCreator.this.mSource.execute();
            }

            protected void onPostExecute(Object result) {
                TaskCreator.this.mSource.setResult(result, this.val$receiverView);
            }
        }

        public TaskCreator(S sourceItem) {
            this.mSource = sourceItem;
        }

        public <R extends View> void into(R receiverView) {
            AsyncTask task = new C12811(receiverView);
            synchronized (this.mSource) {
                receiverView.setTag(Long.valueOf(this.mSource.getIdentifier()));
            }
            AsyncTaskHelper.execute(LazyLoading.mPool, task);
        }
    }

    static {
        mPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(13);
    }

    public static TaskCreator loadFor(PoiListItem sourceItem) {
        return loadFor(LazyPoiListItemWrapper.of(sourceItem));
    }

    private static <S extends LazyLoadable> TaskCreator loadFor(S sourceItem) {
        return new TaskCreator(sourceItem);
    }
}
