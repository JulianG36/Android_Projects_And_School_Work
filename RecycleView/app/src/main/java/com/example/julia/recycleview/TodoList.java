package com.example.julia.recycleview;

import java.util.List;

/**
 * Created by julia on 3/22/2018.
 */

public class TodoList {
    private List<ToDoModel> toDoModels;
    private ToDoModel toDoModel;

    public List<ToDoModel> getToDoModels() {
        return toDoModels;
    }

    public void setToDoModels(List<ToDoModel> toDoModels) {
        this.toDoModels = toDoModels;
    }

    public ToDoModel getToDoModel() {
        return toDoModel;
    }

    public void setToDoModel(ToDoModel toDoModel) {
        this.toDoModel = toDoModel;
    }
    public List<ToDoModel> addToDoModel(ToDoModel toDoModel){
        toDoModels.add(toDoModel);
        return toDoModels;
    }
}
