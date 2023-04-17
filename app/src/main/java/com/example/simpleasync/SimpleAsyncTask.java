package com.example.simpleasync;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> progressBar;
    private String result;

    SimpleAsyncTask(TextView tv, ProgressBar pb) {
        mTextView = new WeakReference<>(tv);
        progressBar = new WeakReference<>(pb);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n * 10;
        int count = 0;
        while (count < 100) {
            try {
                Thread.sleep(s);
                count++;
                if (count <= 25) {
                    result = "Napping";
                } else if (count <= 50) {
                    result = "Dream";
                } else if (count <= 75) {
                    result = "Deep sleep";
                } else {
                    result = "Waking up";
                    }
                publishProgress(count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = "Awake at last after sleeping for " + s + " milliseconds!";
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        progressBar.get().setProgress(progress);
        mTextView.get().setText(result);
    }

    @Override
    protected void onPostExecute(Void result) {
        mTextView.get().setText(this.result);
        progressBar.get().setVisibility(View.GONE);
    }
}
