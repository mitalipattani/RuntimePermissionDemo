package demo.mitali.com.runtimepermissiondemo;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    Button check_permission, request_permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check_permission = (Button)findViewById(R.id.btn_check);
        request_permission = (Button)findViewById(R.id.btn_request);
        check_permission.setOnClickListener(this);
        request_permission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id)
        {
            case R.id.btn_check:
                if(checkPermission())
                {
                    Toast.makeText(this, "" +
                            "PERMISSION IS ALREADY GRANTED", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "" +
                            "PERMISSION IS NOT GRANTED, REQUEST THE PERMISSION", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_request:
                 if(!checkPermission())
                 {
                     requestPermission();
                 }
                 else
                 {
                     Toast.makeText(this, "" +
                             "PERMISSION IS ALREADY GRANTED", Toast.LENGTH_SHORT).show();
                 }

                break;
        }
    }
    private  boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(
                getApplicationContext(),
                CAMERA
                );
        boolean return_result;
        if(result == PackageManager
                .PERMISSION_GRANTED  ) {
             return_result = true;
        }
        else
            return_result = false;
        return return_result;
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0) {
            boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (camera_accepted) {
                Toast.makeText(MainActivity.this,
                        "Permission is Granted, Acess Camera",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Permission is Denied,Cannot Acess Camera",
                        Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(CAMERA)) {
                        //creates a dialogue
                        showMessage("YOU NEED TO ALLOW ACCESS FOR CAMERA",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermission();
                                        }
                                    }
                                });
                    }
                }
            }
        }
    }
        //check for sdk>=23 if yes than show the dialog box
        private void showMessage(String explanation,
       DialogInterface.OnClickListener listner){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(explanation)
                .setPositiveButton("OK", listner)
                .setNegativeButton("CANCEL",null)
                .create()
                .show();
    }
    }

