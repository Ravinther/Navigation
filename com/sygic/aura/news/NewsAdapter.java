package com.sygic.aura.news;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sygic.base.C1799R;
import java.lang.ref.WeakReference;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    private static class PicassoCallBack implements Callback {
        private final WeakReference<ImageView> mImageView;
        private final WeakReference<ProgressBar> mProgressBar;

        public PicassoCallBack(ProgressBar progressBar, ImageView imageView) {
            this.mProgressBar = new WeakReference(progressBar);
            this.mImageView = new WeakReference(imageView);
        }

        public void onSuccess() {
            ImageView imageView = (ImageView) this.mImageView.get();
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            ProgressBar progressBar = (ProgressBar) this.mProgressBar.get();
            if (progressBar != null) {
                progressBar.setVisibility(8);
            }
        }

        public void onError() {
            ImageView imageView = (ImageView) this.mImageView.get();
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            ProgressBar progressBar = (ProgressBar) this.mProgressBar.get();
            if (progressBar != null) {
                progressBar.setVisibility(8);
            }
        }
    }

    private class ViewHolder {
        private final ImageView imgImage;
        private final ProgressBar progressBar;
        private final TextView tvDescription;
        private final TextView tvTitle;

        private ViewHolder(View rootView) {
            this.tvTitle = (TextView) rootView.findViewById(C1799R.id.title);
            this.tvDescription = (TextView) rootView.findViewById(C1799R.id.description);
            this.imgImage = (ImageView) rootView.findViewById(C1799R.id.image);
            this.progressBar = (ProgressBar) rootView.findViewById(C1799R.id.progress);
        }

        protected void update(NewsItem item) {
            this.tvTitle.setText(Html.fromHtml(item.getTitle()));
            this.tvDescription.setText(Html.fromHtml(item.getDescription()));
            Picasso.with(NewsAdapter.this.getContext()).load(item.getImageSourceLink()).into(this.imgImage, new PicassoCallBack(this.progressBar, this.imgImage));
        }
    }

    public NewsAdapter(Context context, List<NewsItem> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        NewsItem item = (NewsItem) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(2130903193, parent, false);
            viewHolder = new ViewHolder(convertView, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.update(item);
        return convertView;
    }
}
