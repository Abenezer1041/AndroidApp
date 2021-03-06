package com.example.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

import static android.app.DatePickerDialog.OnDateSetListener;

public class MainActivity extends AppCompatActivity implements OnDateSetListener{
    private EditText userText;
    private EditText passWord;
    private EditText fullName;
    private EditText emailText,urdescription,workText;
    Button signUp;
    private Button mDatebtn,dButton;
    private TextView mDateText;
    boolean isNameValid, isEmailValid;
    boolean isAllFieldsChecked = true;
    int ageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userText=findViewById(R.id.editUser);
        emailText=findViewById(R.id.editTextEmail);
        fullName=findViewById(R.id.editTextName);
        passWord=findViewById(R.id.password);
        signUp=findViewById(R.id.regester);
        urdescription=findViewById(R.id.mainDescription);
        workText=findViewById(R.id.occupationInput);
        mDatebtn=findViewById(R.id.datePickerButton);
        mDateText=findViewById(R.id.datetText);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //isAllFieldsChecked=SetValidation();

                if(SetValidation()==true && mDateText.length()!=0){
                    String username = userText.getText().toString();
                    String fullname=fullName.getText().toString();
                    String urjob=workText.getText().toString();
                    String urinfo= urdescription.getText().toString();

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("usernames", username);
                    intent.putExtra("age", ageText);
                    intent.putExtra("fullname",fullname);
                    intent.putExtra("work",urjob);
                    intent.putExtra("description",urinfo);
                    //intent.putExtra("fullname",fullName.getText().toString());
                    //intent.putExtra("age",)
                    startActivity(intent);
                }
            }
        });
        mDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });
    }



    public boolean SetValidation() {
        if (userText.length() <6) {
            userText.setError("Must have 6 characters");
           return false;
        }

       if(fullName.length()<8){
           fullName.setError("Must have 8 characters");
           return false;

        }
       String emailinput = emailText.getText().toString();
//String user=userText.getText().toString();
       if (emailinput.length()==0 || !Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
            //Toast.makeText(this, "Email is Valiade", Toast.LENGTH_SHORT).show();
           emailText.setError("Email is inValid");
           return false;
        }

       if (passWord.length() <7) {
         passWord.setError("Password must have 8 or more characters");
           return false;
      }

       if (mDateText.length()==0){
            mDateText.setError("need birth date");
            return false;
       }
       if(workText.length()==0){
           workText.setError("Cant leave this empty");
           return false;

      }
      if (urdescription .length()==0){
           urdescription.setError("cant leave this empty");
           return false;
      }

        // return true.
        return true;

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        //TextView textView = (TextView) findViewById(R.id.datetText);
        mDateText.setText(currentDateString);
        boolean goodage =true;

        LocalDate userday = LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        LocalDate todaydate = LocalDate.now();
        int age = Period.between(todaydate, userday).getYears();

        ageText=(calculateAge(c.getTimeInMillis()));
        if (ageText<18|| mDateText.getText().toString().length()==0){
            mDateText.setError("you need to be 18 or older");

        }
        else{
           mDateText.setError(null);

       }

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);

        //intent.putExtra("age",)


    }
    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }

        return age;

    }

}