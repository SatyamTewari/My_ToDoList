package com.example.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Activity.ListScreenActivity;
import com.example.todolist.Activity.TaskScreenActivity;
import com.example.todolist.Class.ApiRequest;
import com.example.todolist.Class.RetrofitAdapter;
import com.example.todolist.Model.AllLists.AllLists;
import com.example.todolist.Model.DelList.DelList;
import com.example.todolist.Model.DelList.DelListResponse;
import com.example.todolist.Model.ListId;
import com.example.todolist.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllListAdapter extends RecyclerView.Adapter<AllListAdapter.MyViewHolder> {

    AllLists allLists;
    Context context;

    public AllListAdapter(@NonNull Context context, AllLists allLists) {
        this.context = context;
        this.allLists = allLists;
    }

    Retrofit retrofit = RetrofitAdapter.getinstance(ApiRequest.BASE_URL, context);
    private ApiRequest retrofitCall = retrofit.create(ApiRequest.class);

    public void setAllLists(AllLists allLists){
        this.allLists = allLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listTitle.setText(allLists.getData().get(position).getTitle());
        if(allLists.getData().get(position).getCompleted())
            holder.done.setImageResource(R.drawable.checked);
        else
            holder.done.setImageResource(R.drawable.unchecked);
    }

    @Override
    public int getItemCount() {
        return allLists.getData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView listTitle;
        RelativeLayout listItem;
        ImageView done, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.tv_list_item);
            listItem = itemView.findViewById(R.id.rl_list_item);
            done = itemView.findViewById(R.id.iv_list_item_done);
            delete = itemView.findViewById(R.id.iv_list_item_delete);

            listTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent((ListScreenActivity)context, TaskScreenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("listId",allLists.getData().get(getAdapterPosition()).getId());
                    bundle.putString("list_title",allLists.getData().get(getAdapterPosition()).getTitle());
                    i.putExtras(bundle);
                    ((ListScreenActivity)context).startActivity(i);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DelList delList = new DelList();
                    delList.setListId(allLists.getData().get(getAdapterPosition()).getId());
                    try{
                    Call<DelListResponse> call = retrofitCall.deleteList(delList);
                    call.enqueue(new Callback<DelListResponse>() {
                        @Override
                        public void onResponse(Call<DelListResponse> call, Response<DelListResponse> response) {
                            if(response.isSuccessful()){
                                Log.e("Response",""+response.isSuccessful());
                                Toast.makeText(context, "Successfully deleted List", Toast.LENGTH_LONG).show();
                                allLists.getData().remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<DelListResponse> call, Throwable t) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((ListScreenActivity)context).getListPutCall(getAdapterPosition());
                    ListId data = new ListId(allLists.getData().get(getAdapterPosition()).getId());
                    try {
                        Call<AllLists> call = retrofitCall.getUpdatedLists(data);
                        call.enqueue(new Callback<AllLists>() {
                            @Override
                            public void onResponse(Call<AllLists> call, Response<AllLists> response) {
                                allLists = response.body();
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<AllLists> call, Throwable t) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
