package com.etnet.coresdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.etnet.coresdk.api.ApiFields;

public class SdkDemo extends AppCompatActivity {
    public final ApiFields apiFields = new ApiFields();

    private static final String TAG = "mySDKTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sdktest);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "You clicked module button!!");
            }
        });
    }

}


