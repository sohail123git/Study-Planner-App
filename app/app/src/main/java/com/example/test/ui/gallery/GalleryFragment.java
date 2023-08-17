package com.example.test.ui.gallery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.DataBaseHelper;
import com.example.test.EventActivity;
import com.example.test.EventModel;
import com.example.test.R;
import com.example.test.databinding.FragmentGalleryBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    private CompactCalendarView cv_calendar;
    private TextView tv_date_display;
    private String date;
    private Button btn_swipe_left,btn_swipe_right;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cv_calendar=(CompactCalendarView) root.findViewById(R.id.cv_calendar);

        tv_date_display=(TextView) root.findViewById(R.id.tv_date_display);
        btn_swipe_left=root.findViewById(R.id.btn_swipe_left);
        btn_swipe_right=root.findViewById(R.id.btn_swipe_right);

        btn_swipe_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv_calendar.scrollLeft();
            }
        });
        btn_swipe_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv_calendar.scrollRight();
            }
        });


        String oldString =java.text.DateFormat.getDateTimeInstance().format(new Date());
        Date currDate = null;
        try {
            currDate = new SimpleDateFormat("MMM d, yyyy K:m:s a").parse(oldString);
            date = new SimpleDateFormat("d/M/yyyy").format(currDate);
            tv_date_display.setText(new SimpleDateFormat("MMM yyyy").format(currDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        updateEventCount(date);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        List<EventModel> currentEvents = dataBaseHelper.getEvents();

        for (EventModel eventModel:currentEvents) {
            try {
                currDate = new SimpleDateFormat("d/M/yyyy").parse(eventModel.getDate());
                Event event;
                switch(eventModel.getEventType()){
                    case 0:
                        event=new Event(Color.GREEN,currDate.getTime());
                        cv_calendar.addEvent(event);
                        break;
                    case 1:
                        event=new Event(Color.YELLOW,currDate.getTime());
                        cv_calendar.addEvent(event);
                        break;
                    case 2:
                        event=new Event(Color.RED,currDate.getTime());
                        cv_calendar.addEvent(event);
                        break;
                    case 3:
                        event=new Event(Color.WHITE,currDate.getTime());
                        cv_calendar.addEvent(event);
                        break;
                    default:
                        break;
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

//        cv_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                date = String.valueOf(dayOfMonth) +"/"+ String.valueOf(month+1) +"/"+ String.valueOf(year);
//                updateEventCount(date);
//            }
//        });
        cv_calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = cv_calendar.getEvents(dateClicked);
                Log.d("compactcalendar", "Day was clicked: " + dateClicked + " with events " + events);
                date=new SimpleDateFormat("d/M/yyyy").format(dateClicked);
                updateEventCount(date);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d("compactcalendar", "Month was scrolled to: " + firstDayOfNewMonth);
                tv_date_display.setText(new SimpleDateFormat("MMM yyyy").format(firstDayOfNewMonth));
                date=new SimpleDateFormat("d/M/yyyy").format(firstDayOfNewMonth);
                updateEventCount(date);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void updateEventCount(String date){
//        Toast.makeText(getContext(),"updateCount",Toast.LENGTH_SHORT).show();

        View root = binding.getRoot();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());

        TextView tv_0=(TextView)(root.findViewById(R.id.tv_count0));
        tv_0.setText(Integer.toString(dataBaseHelper.getCount(0,date)));
        TextView tv_1=(TextView)(root.findViewById(R.id.tv_count1));
        tv_1.setText(Integer.toString(dataBaseHelper.getCount(1,date)));
        TextView tv_2=(TextView)(root.findViewById(R.id.tv_count2));
        tv_2.setText(Integer.toString(dataBaseHelper.getCount(2,date)));
        TextView tv_3=(TextView)(root.findViewById(R.id.tv_count3));
        tv_3.setText(Integer.toString(dataBaseHelper.getCount(3,date)));

    }
}