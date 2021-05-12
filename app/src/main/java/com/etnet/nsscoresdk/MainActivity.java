package com.etnet.nsscoresdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.etnet.coresdk.SdkDemo;

import com.etnet.coresdk.api.ApiFields;
import com.etnet.coresdk.coreController.ProcessorController;
import com.etnet.coresdk.coreModel.NssPacket;
import com.etnet.coresdk.coreModel.Processor;
import com.etnet.coresdk.coreModel.ProcessorInfo;
import com.etnet.coresdk.coreModel.User;
import com.etnet.coresdk.coreNetwork.NssConnection;
import com.etnet.coresdk.network.ConnectOptions;
import com.etnet.coresdk.nssCoreService.DataCoreUtil;
import com.etnet.coresdk.nssCoreService.NssMain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ApiFields a = new ApiFields();
    NssConnection nssConnection = new NssConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NssMain _nssMain = new NssMain();

//                final DataCoreUtil dataCoreUtil = new DataCoreUtil(_nssMain);


//                nssConnection.connect(new ConnectOptions(new User("user name", "web token", "nss token"),"US", "test url", false));
//                Intent intent = new Intent(MainActivity.this, SdkDemo.class);
//                startActivity(intent);
            }
        });

    }
}