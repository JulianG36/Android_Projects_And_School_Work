package com.example.julia.recycleview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoListFragment extends Fragment {
private RecyclerView recycleView;
private ToDoAdapter adapter;
private TodoList mTodoList;
private Button mButton;
    public ToDoListFragment() {
        // Required empty public constructor
        List<ToDoModel> toDoList = new ArrayList<ToDoModel>();
        toDoList.add(new ToDoModel("Porsche",1, false));
        toDoList.add(new ToDoModel("Volkswaggen", 2,false ));
        toDoList.add(new ToDoModel("BMW", 1, false));
        toDoList.add(new ToDoModel("Ferrari", 1, false));
        toDoList.add(new ToDoModel("Ducati", 99, false));
        ToDoAdapter toDoAdapter = new ToDoAdapter(toDoList);
        mTodoList.setToDoModels(toDoList);
        adapter = toDoAdapter;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        recycleView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mButton = (Button) v.findViewById(R.id.button);

        recycleView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        recycleView.setLayoutManager(layoutManager);


        // Inflate the layout for this fragment
        return v;
    }
    private class ToDoHolder extends RecyclerView.ViewHolder{
        public CheckBox chkCompleted;
        public TextView tvTitle;
        public TextView tvPriority;
        public ToDoHolder(View itemView){
            super(itemView);
            chkCompleted = itemView.findViewById(R.id.row_complete);
            tvTitle = itemView.findViewById(R.id.row_title);
            tvPriority = itemView.findViewById(R.id.row_priority);

        }

    }
    private class ToDoAdapter extends RecyclerView.Adapter<ToDoHolder>{
        private List<ToDoModel> adapterToDoList;
        public ToDoAdapter(List<ToDoModel> toDoList){
            adapterToDoList = toDoList;
        }
        public ToDoHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.to_do_row,parent,false);
            return new ToDoHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return adapterToDoList.size();
        }

        public void onBindViewHolder(ToDoHolder holder, int position){
            ToDoModel todoItem = adapterToDoList.get(position);
            holder.chkCompleted.setChecked(todoItem.getComplete());
            holder.tvTitle.setText(todoItem.getTitle());
            holder.tvPriority.setText(Integer.toString(todoItem.getPriority()));
        }


    }

}
