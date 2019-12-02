package com.example.miniprojectsmb;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import org.xml.sax.Parser;

import yuku.ambilwarna.AmbilWarnaDialog;

public class OptionsActivity extends Activity {

    SharedPreferences sp;
    private EditText etFontOption;
    private TextView CzcionkaTv;
    private TextView RozmiarTv;
    private Button kolorButton;
    private Button AcceptOptionButton;


    Button button;
    ConstraintLayout cstLayout;
    int colorPicker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sp = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenColorPickerDialog(false);

            }
        });
        AcceptOptionButton = findViewById(R.id.IdAcceptOptionButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setOptions(sp.getInt("font_size", 14), sp.getInt("color", 0x01060013));
    }

    private void OpenColorPickerDialog(boolean AlphaSupport) {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(OptionsActivity.this, colorPicker, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {
                colorPicker = color;
            }

            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

                Toast.makeText(OptionsActivity.this, "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();

    }



    public void SubmitButtonClick(View view) {

        SharedPreferences.Editor editor = sp.edit();
        editor.apply();
        EditText rozmiarET = findViewById(R.id.IdRozmiarOption);
        if( rozmiarET.getText().toString() != null ) {
            int fontSize = Integer.parseInt(rozmiarET.getText().toString());

            editor.putInt("font_size", fontSize);


        }
        editor.putInt("color", colorPicker);
        editor.apply();
        if(rozmiarET.getText().toString() != "") {
            setOptions(Integer.parseInt(rozmiarET.getText().toString()), colorPicker);
        }else{
            setOptions(14, colorPicker);
        }
    }

    public void setOptions(int font_size, int color){

        etFontOption = findViewById(R.id.IdRozmiarOption);
        CzcionkaTv = findViewById(R.id.czcionkaTextView);
        RozmiarTv = findViewById(R.id.rozmiarTextView);
        AcceptOptionButton = findViewById(R.id.IdAcceptOptionButton);
        kolorButton = findViewById(R.id.button);
        if(font_size != 0) {
            etFontOption.setTextSize(font_size);
            CzcionkaTv.setTextSize(font_size);
            RozmiarTv.setTextSize(font_size);
            AcceptOptionButton.setTextSize(font_size);
            kolorButton.setTextSize(font_size);
        }

        if(color != 0) {
            etFontOption.setTextColor(color);
            CzcionkaTv.setTextColor(color);
            RozmiarTv.setTextColor(color);
            AcceptOptionButton.setBackgroundColor(color);
            kolorButton.setBackgroundColor(color);
        }
    }

}
