package com.etnet.nsscoresdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.etnet.coresdk.SdkDemo;

import com.etnet.coresdk.api.ApiFields;

public class MainActivity extends AppCompatActivity {
    ApiFields a = new ApiFields();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SdkDemo.class);
                startActivity(intent);
            }
        });


    }
}