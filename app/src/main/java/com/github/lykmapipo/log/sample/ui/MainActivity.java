package com.github.lykmapipo.log.sample.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.lykmapipo.log.Log;
import com.github.lykmapipo.log.sample.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: show common log scenario

        // simulate logs
        Button btnLogAction = findViewById(R.id.btnLogAction);
        btnLogAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send logs
                Log.v(new NullPointerException());
                Log.d(new NullPointerException());
                Log.i(new NullPointerException());
                Log.w(new NullPointerException());
                Log.e(new NullPointerException());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
