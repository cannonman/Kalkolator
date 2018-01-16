package io.github.wint3rmute.kalkolator;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class data_upload extends AppCompatActivity {

    NumberPicker percentage ;
    NumberPicker liters ;
    Switch scaleSwitch ;
    TextView LitersAndMililiters;
    DatePicker datePicker;

    boolean isMililiters;

    int day = 0, month = 0, year = 0;

    float litersAmound, percentageAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_upload);

        initUI();
        FirebaseApp.initializeApp(getApplicationContext());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert("saving");
                saveData();
            }
        });
    }

    void alert(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void initUI()
    {

        LitersAndMililiters = findViewById(R.id.litersTextView);
        datePicker = findViewById(R.id.datePicker);

        percentage = findViewById(R.id.percentage);
        liters = findViewById(R.id.liters);
        scaleSwitch = findViewById(R.id.scaleSwitch);

        liters.setMinValue(0);
        liters.setMaxValue(100);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int n_year, int n_month, int n_dayOfMonth) {
                year = n_year;
                month = n_month;
                day = n_dayOfMonth;
             }
        });

        /*
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                datePicker.setVisibility(View.GONE);
                alert(i + " " + i1 + " " + i2);
            }
        });
        */

        String [] litersValues = new String[101];
        LitersAndMililiters.setText("Liters");
        for (int i = 0; i < 101; i++) {
            litersValues[i] = Float.toString( i*0.5f);
        }
        liters.setDisplayedValues(litersValues);


        percentage.setMinValue(0);
        percentage.setMaxValue(200);

        //percentage, this wont change
        String [] percentageValues = new String[201];
        for (int i = 0; i < 201; i++) {
            percentageValues[i] = Float.toString( i*0.5f );
        }
        percentage.setDisplayedValues(percentageValues);

        scaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isMililiters = b;

                String [] litersValues = new String[101];


                if (b == true) //zmieniamy l na ml
                {
                    LitersAndMililiters.setText("Mililiters");
                    for (int i = 0; i < 101; i++) {
                        litersValues[i] = Integer.toString( i*10 );
                    }

                }
                else
                {
                    LitersAndMililiters.setText("Liters");
                    for (int i = 0; i < 101; i++) {
                        litersValues[i] = Float.toString( i*0.5f);
                    }
                }

                liters.setDisplayedValues(litersValues);

            }
        });

    }

    void saveData()
    {

        //alert( liters.getDisplayedValues()[liters.getValue()]);

        litersAmound = Float.parseFloat( liters.getDisplayedValues()[liters.getValue()]);
        percentageAmount = Float.parseFloat( percentage.getDisplayedValues()[percentage.getValue()] );

        //if(day != 0 && month !=0 && year != 0 && litersAmound !=0 && percentageAmount != 0)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String UID = user.getUid();

            DatabaseReference userAdress = FirebaseDatabase.getInstance().getReference("/users/" + UID);

            if(!isMililiters)
                litersAmound*=1000;

            DrinkLogObject drinkLogObject = new DrinkLogObject(day + "-" + (month + 1) + "-" + year, litersAmound, percentageAmount);
            userAdress.child(drinkLogObject.getDate()).setValue(drinkLogObject);

        }//else{
           // alert("some shit is wrong");
       // }
    }
}
