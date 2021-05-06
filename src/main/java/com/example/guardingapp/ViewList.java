package com.example.guardingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        SharedPreferences NameList = getApplicationContext().getSharedPreferences("List", Context.MODE_PRIVATE);

        TextView ListOfNames = findViewById(R.id.textView);
        Button back = findViewById(R.id.back2);
        Button copy = findViewById(R.id.copy);

        ListOfNames.setMovementMethod(new ScrollingMovementMethod());

        ListOfNames.setText(NameList.getString("List1", " "));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView", ListOfNames.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ViewList.this, "Copied", Toast.LENGTH_SHORT).show();;
            }
        });
    }
}