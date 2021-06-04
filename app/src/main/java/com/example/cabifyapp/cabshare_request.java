package com.example.cabifyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.internal.SharedResourceHolder;


public class cabshare_request extends AppCompatActivity {
    private static final String TAG = "cabshare_request";
    final Context context = this;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Spinner spinner1;
    Button bt_post,btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime,et_name,et_from,et_to,et_phone;
    TextView tv_03;
    private int mYear, mMonth, mDay, mHour, mMinute;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabshare_request);
        addItemsOnSpinner1();

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        et_name=(EditText)findViewById(R.id.et_name);
        et_from=(EditText)findViewById(R.id.et_from);
        et_to=(EditText)findViewById(R.id.et_to);
        et_phone=(EditText)findViewById(R.id.et_phone);
        bt_post=(Button)findViewById(R.id.bt_post);
        tv_03 = (TextView)findViewById(R.id.tv_03);

        mFirebaseAuth = FirebaseAuth.getInstance();
        btnDatePicker.setOnClickListener(this::onClick);
        btnTimePicker.setOnClickListener(this::onClick);
        String myemail = mFirebaseAuth.getCurrentUser().getEmail();

        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= et_name.getText().toString();
                String from= et_from.getText().toString();
                String to= et_to.getText().toString();
                String phone= et_phone.getText().toString();
                String tDate= txtDate.getText().toString();
                String tTime= txtTime.getText().toString();
                String Avail = spinner1.getSelectedItem().toString();
                Map<String, Object> request = new HashMap<>();
                request.put("email",myemail);
                request.put("name",name);
                request.put("from",from);
                request.put("to",to);
                request.put("phone",phone);
                request.put("tDate",tDate);
                request.put("tTime",tTime);
                request.put("Avail",Avail);

                AlertDialog.Builder build = new AlertDialog.Builder(context).setTitle("Alert").
                        setMessage("Are you ready to post your request?").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.print("Request is posted");
                                db.collection("Requests").document(myemail+from)
                                        .set(request)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " );
                                                Toast.makeText(cabshare_request.this,"Successfully posted",Toast.LENGTH_SHORT).show();                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });
                                Intent intent = new Intent(cabshare_request.this, Home.class);
                                startActivity(intent);
                            }
                        }).
                        setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = build.create();
                alertDialog.show();
            }
        });

    }
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Enter Seat Availability");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }
}