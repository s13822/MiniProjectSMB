package com.example.miniprojectsmb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private SharedPreferences sp;
    private TextView tv;
    private Button listaBtn;
    private Button optionsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }

    // tworzenie shared preferences
    @Override
    protected void onStart() {
        super.onStart();
        //sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        int color = sp.getInt("color", 0x01060013);
        int font_size = sp.getInt("font_size", 14);
        setOptions(font_size, color);

    }

    public void listButtonClick(View view) {
        Intent optionIntent = new Intent( this, ListActivity.class);
        startActivity(optionIntent);
    }

    public void optionsButtonClick(View view) {
        Intent optionIntent = new Intent( this, OptionsActivity.class);
        startActivity(optionIntent);
    }

    private void setOptions(int font_size, int color){

        tv = findViewById(R.id.IdProductListTextView);
        listaBtn = findViewById(R.id.listButton);
        optionsBtn = findViewById(R.id.optionsButton);

        listaBtn.setBackgroundColor(color);
        optionsBtn.setBackgroundColor(color);
        tv.setTextColor(color);
        if(font_size > 0) {
            listaBtn.setTextSize(font_size);
            optionsBtn.setTextSize(font_size);
            tv.setTextSize(font_size);
        }

    }

}
