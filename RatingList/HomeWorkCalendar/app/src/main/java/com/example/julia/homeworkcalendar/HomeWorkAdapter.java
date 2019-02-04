package com.example.julia.homeworkcalendar;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by julia on 4/18/2018.
 */

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MyHolder> {
    private final LayoutInflater inflater;
    private Cursor res;
    private Database db;
    private String TAG = "HomeWorkAdapter";
    public HomeWorkAdapter(Context context){
        inflater = LayoutInflater.from(context);
        db = new Database(context);
        res = db.getAllData();
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homework_rows,parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeWorkAdapter.MyHolder holder, final int position) {
        res.moveToPosition(position);
        boolean value = res.getInt(4) > 0;
        Log.i(TAG, "Recycler: " + position);
        holder.DueDate.setText(res.getString(5));
        holder.ClassAssigned.setText(res.getString(2));
        holder.Description.setText(res.getString(3));
        holder.reminder.setChecked(value);
        holder.AssignedDate.setText(res.getString(1));
        holder.deleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            //Used to delete a row when done, and also update the recycleView when it is removed.
            public void onClick(View view) {
                db.deleteRow(res.getInt(0));
                //updates data so that the res cursor in the fragment has the correct data after deletion
                res = db.getAllData();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return res.getCount();
    }
    class MyHolder extends  RecyclerView.ViewHolder{
        TextView DueDate;
        TextView Description;
        TextView AssignedDate;
        TextView ClassAssigned;
        CheckBox reminder;
        Button deleteRow;
        public MyHolder(View itemView){
            super(itemView);
            DueDate = itemView.findViewById(R.id.mDueDateRecycler);
            Description = itemView.findViewById(R.id.mDescriptionRecycler);
            AssignedDate = itemView.findViewById(R.id.mAssignedDateRecycler);
            ClassAssigned = itemView.findViewById(R.id.mClassAssignedRecycler);
            reminder = itemView.findViewById(R.id.mReminderRecycler);
            deleteRow = itemView.findViewById(R.id.mDeleteRow);
        }



    }
}
