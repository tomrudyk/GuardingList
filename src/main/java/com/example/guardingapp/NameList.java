 package com.example.guardingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 public class NameList extends AppCompatActivity implements ReplaceNameDialog
        .ReplaceNameDialogListener {

    Button add,delete, replace, remove;
    EditText addname;
    String read = "";
    String name;
    TextView namelist;
    Scanner w = new Scanner(System.in);
    String[] p = new String[0];
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);

        addname = findViewById(R.id.EditName);
        add = findViewById(R.id.AddNameBtn);
        delete = findViewById(R.id.DeleteAllBtn);
        replace = findViewById(R.id.ReplaceBtn);
        remove = findViewById(R.id.RemoveBtn);
        namelist = findViewById(R.id.TheNameList);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Names", Context.MODE_PRIVATE);
        read = sp.getString("StringY", "");
        namelist.setText(read);
        namelist.setMovementMethod(new ScrollingMovementMethod());



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = addname.getText().toString();
                if (!name.isEmpty()) {
                    read = read + "\n "+" " + name;

                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("StringY", read);
                    editor.commit();
                    Toast.makeText(NameList.this, "Name Added", Toast.LENGTH_SHORT).show();
                    namelist.setText(" " + read + " " + " ");

                    addname.setText("");

                    addname.requestFocus();
                    addname.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); /// Leaves Keyboard OPEN
                            keyboard.showSoftInput(addname, 0);
                        }
                    }, 200);

                }
                else{
                    Toast.makeText(NameList.this, "Name Not Found", Toast.LENGTH_SHORT).show();
                }
            }



        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               YesNoDialog("Delete All Names", "delete");
            }
        });


        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        remove.setOnClickListener(v -> {
            YesNoDialog("Remove: " +addname.getText().toString(),"remove");
        });

        addname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                    add.performClick();
                }
                return true;
            }
        }); /// Done == Enter - keyboard Listener


    }
        public void openDialog(){
            ReplaceNameDialog replaceNameDialog = new ReplaceNameDialog();
            replaceNameDialog.show(getSupportFragmentManager(), "ReplaceNameDialog");
        }


    @Override
    public void applyText(String ReplaceName) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Names", Context.MODE_PRIVATE);
        read = sp.getString("StringY", "");
        String name = addname.getText().toString() ;
        if(!name.isEmpty()&&isContain(read,name)) {
            read = read.replaceAll("\\b"+name+"\\b", ReplaceName );
            Toast.makeText(NameList.this, "Name Changed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(NameList.this, "Old Name Was Not Found", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("StringY", read);
        editor.commit();
        namelist.setText(read);
    }

    public void YesNoDialog(String text, String activity){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to "+text+"?")
                .setMessage("")
                .setPositiveButton("Yes", null)
                .setNegativeButton("Cancel", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity.toLowerCase().equals("remove")){
                    SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Names", Context.MODE_PRIVATE);
                    read = sp1.getString("StringY", "");
                    String name = addname.getText().toString() ;
                    if (isContain(read,name)&&(!name.isEmpty())) {
                        read = read.replaceAll("\\b"+name+"\\b", "");
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putString("StringY", read);
                        editor.commit();
                        namelist.setText(read);
                        Toast.makeText(NameList.this, "Name Removed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NameList.this, "Name Was Not Found", Toast.LENGTH_SHORT).show();
                    }

                }
                if ((activity.toLowerCase().equals("delete"))){
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("Names", Context.MODE_PRIVATE);
                    read = "";
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("StringY", read);
                    editor.commit();
                    Toast.makeText(NameList.this, "Deleted", Toast.LENGTH_SHORT).show();
                    namelist.setText(read);
                }


                addname.setText("");
                dialog.dismiss();
            }
        });

    }

    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }


}