package com.example.guardingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Contacts;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button NameListActivity = findViewById(R.id.NameList);
        Button KeepingTime = findViewById(R.id.listmaker2);
        Button EndingTime = findViewById(R.id.listmaker1);
        Button KeepAndPeople = findViewById(R.id.listmaker3);
        EditText sh = findViewById(R.id.startH);
        EditText sm = findViewById(R.id.startM);
        EditText eh = findViewById(R.id.endH);
        EditText em = findViewById(R.id.endM);
        EditText people = findViewById(R.id.people);
        EditText keep = findViewById(R.id.keepTime);


        EndingTime.setEnabled(false);
        KeepingTime.setEnabled(false);
        KeepAndPeople.setEnabled(false);



        SharedPreferences sp = getApplicationContext().getSharedPreferences("Names", Context.MODE_PRIVATE);
        SharedPreferences NameList = getApplicationContext().getSharedPreferences("List", Context.MODE_PRIVATE);


        people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String peopleInput = people.getText().toString().trim();
                String keepInput = keep.getText().toString().trim();
                String InfoSh = sh.getText().toString().trim();
                String InfoEh = eh.getText().toString().trim();
                String InfoSm = sm.getText().toString().trim();
                String InfoEm = em.getText().toString().trim();

                boolean peopleInput2 = !peopleInput.isEmpty();
                boolean keepInput2 = !keepInput.isEmpty();
                boolean InfoSh2 = !InfoSh.isEmpty();
                boolean InfoSm2 = !InfoSm.isEmpty();
                boolean InfoEh2 = !InfoEh.isEmpty();
                boolean InfoEm2 = !InfoEm.isEmpty();
                boolean TimeCheckInfo = InfoEh2&&InfoEm2&&InfoSh2&&InfoSm2;
                EndingTime.setEnabled(peopleInput2&&TimeCheckInfo);
                KeepAndPeople.setEnabled(peopleInput2&& keepInput2);


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        }); /// Button Lock
        keep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String peopleInput = people.getText().toString().trim();
                String keepInput = keep.getText().toString().trim();
                String InfoSh = sh.getText().toString().trim();
                String InfoEh = eh.getText().toString().trim();
                String InfoSm = sm.getText().toString().trim();
                String InfoEm = em.getText().toString().trim();

                boolean peopleInput2 = !peopleInput.isEmpty();
                boolean keepInput2 = !keepInput.isEmpty();
                boolean InfoSh2 = !InfoSh.isEmpty();
                boolean InfoSm2 = !InfoSm.isEmpty();
                boolean InfoEh2 = !InfoEh.isEmpty();
                boolean InfoEm2 = !InfoEm.isEmpty();
                boolean TimeCheckInfo = InfoEh2&&InfoEm2&&InfoSh2&&InfoSm2;
                KeepingTime.setEnabled(keepInput2&&TimeCheckInfo);
                KeepAndPeople.setEnabled(peopleInput2&& keepInput2);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); /// Button Lock


        NameListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeActivity();

            }
        });

        KeepingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int keep3 = Integer.parseInt(keep.getText().toString());
                int sH = Integer.parseInt(sh.getText().toString());
                int eH = Integer.parseInt(eh.getText().toString());
                int sM = Integer.parseInt(sm.getText().toString());
                int eM = Integer.parseInt(em.getText().toString());
                int p3 = 0;
                double checkP = 0;
                boolean TimeRight = true;

                if (eH >= 24 || eM >= 60 || sH >= 24 || sM >= 60) {
                    Toast.makeText(MainActivity.this, "Time is not likely", Toast.LENGTH_SHORT).show();
                    TimeRight=false;
                }
                if (TimeRight) {
                    if (sH < eH) {
                        p3 = (((eH - sH) * 60) + eM - sM) / keep3;
                    }
                    if (eH < sH) {
                        p3 = (((eH + (24 - sH)) * 60) + eM - sM) / keep3;
                    }
                    if (eH == sH) {
                        p3 = (eM - sM) / keep3;
                    }


                    String List = RNames(sH, sM, keep3, p3, eH, eM,false);
                    SharedPreferences.Editor editor = NameList.edit();

                    editor.putString("List1", List);
                    editor.commit();

                    changeActivityToViewList();
                }

            }
        });


        EndingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = Integer.parseInt(people.getText().toString());
                int sH = Integer.parseInt(sh.getText().toString());
                int eH = Integer.parseInt(eh.getText().toString());
                int sM = Integer.parseInt(sm.getText().toString());
                int eM = Integer.parseInt(em.getText().toString());
                int min, km = 0, kh = 0;
                boolean TimeRight=true;

                if (eH >= 24 || eM >= 60 || sH >= 24 || sM >= 60) {
                    Toast.makeText(MainActivity.this, "Time is not likely", Toast.LENGTH_SHORT).show();
                    TimeRight=false;
                }
                if(TimeRight) {
                    min = 0;
                    if (eH > sH) {
                        min = ((eH * 60) + eM) - ((sH * 60) + sM);
                    }
                    if (eH < sH) {
                        min = 24 * 60 - (((sH * 60) + sM) - ((eH * 60) + eM));
                    }
                    if (eH == sH) {
                        min = eM - sM;
                    }

                    int keep = min / p;


                    String List = RNames(sH, sM, keep, p, eH, eM,false);

                    SharedPreferences.Editor editor = NameList.edit();

                    editor.putString("List1", List);
                    editor.commit();
                    changeActivityToViewList();
                }


            }
        });

        KeepAndPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int keep3 = Integer.parseInt(keep.getText().toString());
                int p = Integer.parseInt(people.getText().toString());
                int sH = Integer.parseInt(sh.getText().toString());
                int sM = Integer.parseInt(sm.getText().toString());

                String List= RNames(sH,sM,keep3,p,0,0,true);
                SharedPreferences.Editor editor = NameList.edit();

                editor.putString("List1", List);
                editor.commit();
                changeActivityToViewList();
            }
        });

    }


    public void changeActivity() {
        Intent intent = new Intent(this, NameList.class);
        startActivity(intent);
    }

    public void changeActivityToViewList() {
        Intent intent = new Intent(this, ViewList.class);
        startActivity(intent);
    }


    public String RNames(int h, int m, int min, int p, int eth1, int etm1,boolean FlagKeepPeople) {
        Random rd = new Random();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Names", Context.MODE_PRIVATE);
        String read = sp.getString("StringY", "");
        String List = "";
        if (read.isEmpty()||read==null){
            read = "NoNamesFound ";
        }

        Scanner w = new Scanner(read);
        int n = 0;
        String[] peopleNames = new String[read.length()];
        while (w.hasNext()) {
            String a = w.next();
            peopleNames[n] = a;
            n++;
        }

        String[] peopleNames2 = new String[n]; /// String[] with Right Names lenght - No nulls
        for (int i = 0; i < n ; i++) {
            peopleNames2[i] = peopleNames[i];
        }

        int[] TrueFalsePeople = new int[n];
        for (int i = 0; i < n ; i++) {
            TrueFalsePeople[i] = 0;
        }


        TrueFalsePeople = checkNames(TrueFalsePeople, n);
        int em = m;
        int eh = h;

        for (int i = 0; i < p; i++) {
            TrueFalsePeople = checkNames(TrueFalsePeople, n);
            if (m >= 60) {
                m = m - 60;
                h++;
            }

            int r = rd.nextInt(n);
            int c = 0;

            if (TrueFalsePeople[r] == 1) {
                i--;
                c = 1;
            }

            em = m;
            eh = h;
            int min2 = min;
            if (c == 0) {
                while (min >= 60) {
                    min = min - 60;
                    eh = eh + 1;

                }

                if (em >= 60) {
                    eh++;
                    em = em - 60;
                }
                if (eh >= 24) {
                    eh = eh - 24;
                }

            }

            if (TrueFalsePeople[r] == 0) {
                em = em + min;
                min = min2;
                List = List + print2(peopleNames2[r], h, m, eh, em);
                TrueFalsePeople[r] = 1;

            }
            if (em >= 60) {
                eh++;
                em = em - 60;
            }
            if (eh >= 24) {
                eh = eh - 24;
            }
            h = eh;
            m = em;

        }

        if ((eh != eth1 || em != etm1)&&FlagKeepPeople==false) {
                List = List + "\n";
                List = List + print2("Hole ", eh, em, eth1, etm1);

            }
        ///Make Hole - FlagKeepPople Means that Btn is Keep+People - then dont need a Hole


        return (List);
    }

    public String print2(String s, int h, int m, int eh, int em) {
        if (em >= 60) {
            em = em - 60;
            eh++;
        }

        return (s + " " + h + ":" + m + "-" + eh + ":" + em + "\n");

    }

    public int[] checkNames(int[] TrueFalsePeople, int n) {
        boolean t = true;
        for (int i = 0; i < n ; i++) {
            if (TrueFalsePeople[i] == 0) {
                t = false;
            }
        }
        if (t) {
            for (int i = 0; i < n ; i++) {
                TrueFalsePeople[i] = 0;
            }
        }

        return (TrueFalsePeople);
    }

}



