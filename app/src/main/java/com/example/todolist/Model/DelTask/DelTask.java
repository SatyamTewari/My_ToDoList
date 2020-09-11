
package com.example.todolist.Model.DelTask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelTask {

    @SerializedName("taskId")
    @Expose
    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

}
