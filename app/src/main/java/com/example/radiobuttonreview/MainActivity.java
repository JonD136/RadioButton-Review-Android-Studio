package com.example.radiobuttonreview;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textName = (EditText) findViewById(R.id.editTextName);
        final TextView textGPA = (EditText) findViewById(R.id.editTextGPA);

        final TextView opTextView = (TextView) findViewById(R.id.outputTextView);
        final RadioGroup radiobtn = (RadioGroup) findViewById(R.id.radiobuttonGRP);

        Button pressMe = (Button) findViewById(R.id.btnGo);

        //making it have 1 number before the decimal and taking in 2 decimal points after the decimal
        // use this website to help me figure it out: https://stackoverflow.com/questions/48753337/android-edittext-two-decimal-places

        textGPA.setFilters(new InputFilter[]{
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 1, afterDecimal = 2;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = textGPA.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.";
                        } else if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        } else {
                            temp = temp.substring(temp.indexOf(".") + 1);
                            if (temp.length() > afterDecimal) {
                                return "";
                            }
                        }

                        return super.filter(source, start, end, dest, dstart, dend);
                    }
                }
        });
        //end of code for decimal


        // clicking button
        pressMe.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {


                                           //ensuring name is not one space long and
                                           // the GPA is between 0.00 to 4.00 and
                                           //one of the radio button is checked
                                           if ( radiobtn.getCheckedRadioButtonId() == -1 || TextUtils.isEmpty(textName.getText().toString()) || TextUtils.isEmpty(textGPA.getText().toString())) {

                                               Toast.makeText(MainActivity.this, "Fill in the blanks!", Toast.LENGTH_LONG).show();
                                               return;

                                           } else {

                                               //setting output TextView to visible
                                               opTextView.setVisibility(View.VISIBLE);

                                               //Getting the editText Name
                                               String strTextName = textName.getText().toString();

                                               // taking the string and turning it to an integer
                                               double numGPA = Double.parseDouble(textGPA.getText().toString());

                                               //reading the selected radio button for gender
                                               //getting the index of the clicked radio button
                                               int selectedIndex = radiobtn.getCheckedRadioButtonId();

                                               //lets associate the logical radiobutton with this index
                                               final RadioButton selectedRB = (RadioButton) findViewById(selectedIndex);

                                               //the value of the selected radio button is
                                               String selectedGender = selectedRB.getText().toString();
                                               System.out.println(selectedGender);

                                               if (numGPA >= 0.00 && numGPA <= 4.00) {

                                                   if (selectedGender != null) {

                                                       //displaying me message in the output Textview outTV
                                                       //depending on the GPA
                                                       if (numGPA >= 3.0)
                                                           opTextView.setText(strTextName + " you are " + selectedGender +
                                                                   " and you will be considered for the job");
                                                       else
                                                           opTextView.setText(strTextName + " you are " + selectedGender +
                                                                   " and sorry you will not be considered for the job");

                                                   } else
                                                       Toast.makeText(MainActivity.this,
                                                               "Please check one of the gender choices",
                                                               Toast.LENGTH_LONG).show();
                                               } else
                                                   Toast.makeText(MainActivity.this,
                                                           "GPA must be between 0.00 and 4.00",
                                                           Toast.LENGTH_LONG).show();
                                           }


                                       }
                                   }
        );

    }
}