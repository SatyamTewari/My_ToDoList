
package com.example.todolist.Model;

import com.example.todolist.Model.AllTasks.Datum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListId {

    @SerializedName("listId")
    @Expose
    private Integer listId;

    public ListId(Integer listId){
        this.listId = listId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }
}
