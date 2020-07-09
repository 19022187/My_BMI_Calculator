package sg.edu.rp.c346.id19022187.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCal, btnReset;
    TextView tvDate, tvBMI, tvBMIResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My BMI Calculator");
        etHeight = findViewById(R.id.editTextHeight);
        etWeight = findViewById(R.id.editTextWeight);
        btnCal = findViewById(R.id.buttonCal);
        btnReset = findViewById(R.id.buttonReset);
        tvBMI = findViewById(R.id.textViewBMI);
        tvDate = findViewById(R.id.textViewDate);

        //Enhancement
        tvBMIResult = findViewById(R.id.textViewResult);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor edit = prefs.edit();
                edit.clear();
                edit.commit();
            }
        });

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                Float BMI = weight/(height*height);
                Calendar now = Calendar.getInstance();
                String dateTime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        now.get(Calendar.MONTH+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                if (BMI < 18.5){
                    tvBMIResult.setText("You are underweight");
                }
                else if (BMI >= 18.5 && BMI <= 24.9){
                    tvBMIResult.setText("Yor BMI is normal");
                }
                else if (BMI >= 25 && BMI <= 29.9){
                    tvBMIResult.setText("You are overweight");
                }
                else if (BMI > 30){
                    tvBMIResult.setText("You are obese");
                }
                else{
                    tvBMIResult.setText("Calculation error");
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Save data into SharedPreferences
        String dateTimeString = tvDate.getText().toString();
        float bmi = Float.parseFloat(tvBMI.getText().toString());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("datetime", dateTimeString);
        edit.putFloat("lastBMIValue", bmi);
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Retrieve data from sharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String dateTimeString = prefs.getString("datetime", "");
        float lastBMIvalue = prefs.getFloat("lastBMIValue", 0);
        tvDate.setText(dateTimeString);
        tvBMI.setText(lastBMIvalue + "");
    }
}