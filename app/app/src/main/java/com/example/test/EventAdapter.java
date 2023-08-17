package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.ui.home.HomeFragment;

import java.util.EventListener;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    public interface EventListener {
        void onEvent();
    }

    List<EventModel> eventList;
    Context context;
    EventListener listener;

    public EventAdapter(List<EventModel> eventList, Context context,EventListener listener) {
        this.eventList = eventList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_title.setText(eventList.get(position).getTitle());
        holder.tv_date.setText(eventList.get(position).getDate());
        holder.tv_time.setText(eventList.get(position).getTime());
        holder.tv_description.setText(eventList.get(position).getDescription());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventModel eventModel=new EventModel();
                int ID=eventList.get(holder.getAdapterPosition()).getId();
                eventModel.setId(ID);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                boolean b = dataBaseHelper.deleteOne(eventModel);

                listener.onEvent();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView tv_title,tv_description,tv_time,tv_date;
        Button btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_description = itemView.findViewById(R.id.tv_description);
            btn_delete = itemView.findViewById(R.id.btn_delete);


        }
    }
}
