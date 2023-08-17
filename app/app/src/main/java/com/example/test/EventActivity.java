package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.ui.home.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    EditText et_title,et_description,et_time;
    CalendarView cv_date;
    Button btn_add;
    String date;
    Spinner event_selector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent i = getIntent();
        int position=i.getExtras().getInt("position");


        btn_add = findViewById(R.id.btn_add);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        et_time = findViewById(R.id.et_time);
        cv_date = (CalendarView)findViewById(R.id.cv_date);
        event_selector = findViewById(R.id.event_selector);

//        setting up dropdown menu
        ArrayAdapter<String> eventTypeAdapter = new ArrayAdapter<String>(EventActivity.this,
                                                                            android.R.layout.simple_list_item_1,
                                                                            getResources().getStringArray(R.array.types_of_events));
        eventTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event_selector.setAdapter(eventTypeAdapter);
        event_selector.setSelection(position);

//        formatting current date
        String oldString =java.text.DateFormat.getDateTimeInstance().format(new Date());
        Date currDate = null;
        try {
            currDate = new SimpleDateFormat("MMM d, yyyy K:m:s a").parse(oldString);
            date = new SimpleDateFormat("d/M/yyyy").format(currDate);
            Log.d("currDate",date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("currDate",oldString);

        cv_date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = String.valueOf(dayOfMonth) +"/"+ String.valueOf(month+1) +"/"+ String.valueOf(year);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventModel em = new EventModel(-1, et_title.getText().toString(), date, et_time.getText().toString(),et_description.getText().toString(),event_selector.getSelectedItemPosition());

                DataBaseHelper dataBaseHelper = new DataBaseHelper(EventActivity.this);
                boolean success = dataBaseHelper.addOne(em);

                List<EventModel> allEvents= dataBaseHelper.getEvents();
//                Toast.makeText(EventActivity.this, allEvents.toString(), Toast.LENGTH_SHORT).show();


                EventActivity.this.finish();
            }
        });
    }
}