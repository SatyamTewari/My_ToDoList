package com.example.todolist.Class;

import com.example.todolist.Model.AllLists.AllLists;
import com.example.todolist.Model.AllTasks.AllTasks;
import com.example.todolist.Model.CreateNewList.CreateList;
import com.example.todolist.Model.CreateNewList.PostList;
import com.example.todolist.Model.CreateNewTask.CreateTask;
import com.example.todolist.Model.CreateNewTask.PostTask;
import com.example.todolist.Model.DelList.DelList;
import com.example.todolist.Model.DelList.DelListResponse;
import com.example.todolist.Model.DelTask.DelTask;
import com.example.todolist.Model.DelTask.DelTaskResponse;
import com.example.todolist.Model.ListId;
import com.example.todolist.Model.Login.Login;
import com.example.todolist.Model.Login.LoginRes;
import com.example.todolist.Model.TaskId;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRequest {
    public static String BASE_URL = "https://todo-list-poc-api.herokuapp.com";

    @GET("/allLists")
    Call<AllLists> getAllLists();

    @PUT("/list")
    Call<AllLists> getUpdatedLists(@Body ListId data);

    @POST("/list")
    Call<PostList> createList(@Body CreateList title);

    @POST("/login")
    Call<LoginRes> login(@Body Login cred);

    @HTTP(method = "DELETE", path = "/list", hasBody = true)
    Call<DelListResponse> deleteList(@Body DelList listId);

    // ---- for tasks -----

    @GET("/list/{listId}")
    Call<AllTasks> getAllTasks(@Path("listId") Integer id);

    @POST("/task")
    Call<PostTask> createTask(@Body CreateTask data);

    @PUT("/task")
    Call<AllTasks> getUpdatedTasks(@Body TaskId data);

    @HTTP(method = "DELETE", path = "/task", hasBody = true)
    Call<DelTaskResponse> deleteList(@Body DelTask taskId);
}
