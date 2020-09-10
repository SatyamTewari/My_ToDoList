
package com.example.todolist.Model.CreateNewList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateList {

    @SerializedName("title")
    @Expose
    private String title;

    public CreateList(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
