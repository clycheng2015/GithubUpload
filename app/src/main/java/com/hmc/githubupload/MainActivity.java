package com.hmc.githubupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
