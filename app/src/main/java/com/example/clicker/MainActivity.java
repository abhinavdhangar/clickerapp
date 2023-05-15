package com.example.clicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAccessibilityServicePermission();
    }

    public void checkAccessibilityServicePermission() {
        int access = 0;
        try{
            access = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e){
            e.printStackTrace();
            //put a Toast
        }
        if (access == 0) {
            Intent myIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
        }
    }
}