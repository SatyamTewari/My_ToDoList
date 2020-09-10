
package com.example.todolist.Model.DelList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelList {

    @SerializedName("listId")
    @Expose
    private Integer listId;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

}
