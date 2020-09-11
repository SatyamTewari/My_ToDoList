
package com.example.todolist.Model.CreateNewTask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTask {

    @SerializedName("listId")
    @Expose
    private Integer listId;

    @SerializedName("title")
    @Expose
    private String title;


    public CreateTask(Integer listId, String title){
        this.listId = listId;
        this.title = title;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
