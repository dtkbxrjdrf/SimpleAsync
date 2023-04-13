package com.example.simpleasync;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebPageLoader extends AsyncTaskLoader<String> {
    private WeakReference<TextView> mTextSource;
    private String mUrl;

    public WebPageLoader(TextView mTextSource, String url) {
        super(mTextSource.getContext());
        this.mTextSource = new WeakReference<>(mTextSource);
        this.mUrl = url;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(@Nullable String data) {
        super.deliverResult(data);
        if (mTextSource.get() != null) {
            mTextSource.get().setText(data);
        }
    }
}
