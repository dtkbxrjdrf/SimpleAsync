package com.example.simpleasync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Web extends AppCompatActivity {

    private EditText mUrlInput;
    private Button mSearchButton;
    private TextView mTextSource;
    private Spinner mProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mUrlInput = findViewById(R.id.urlInput);
        mSearchButton = findViewById(R.id.searchButton);
        mTextSource = findViewById(R.id.textSource);
        mProtocol = findViewById(R.id.protocol);

        String[] protocol = {"http://", "https://"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, protocol);
        Spinner spinner = (Spinner) mProtocol;
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String protocol = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    String protocol = mProtocol.getSelectedItem().toString();
                    String url = protocol + mUrlInput.getText().toString();
                    WebPageLoader webPageLoader = new WebPageLoader(mTextSource, url);
                    webPageLoader.startLoading();
                } else {
                    Snackbar.make(v, "Перевірте підключення до Інтернету та повторіть спробу", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}