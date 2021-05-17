package com.ladila.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ladila.SecondActivity;
import com.ladila.project.API.APIClient;
import com.ladila.project.API.ApiInterface;
import com.ladila.project.Pojo.ClsApiData;
import com.ladila.project.Pojo.DBPojo.DBResult;
import com.ladila.project.Pojo.Result;
import com.ladila.project.RoomDataBase.MyDao;
import com.ladila.project.RoomDataBase.MyDataBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnClickListener {
    ApiInterface apiInterface;
    List<Result> results = new ArrayList<>();
    List<DBResult> dbResults = new ArrayList<>();
    MyAdapter adapter;
    MyDao myDao;
    RecyclerView myRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRv = findViewById(R.id.myRv);
        myRv.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("First Screen");

        MyDataBase db = Room
                .databaseBuilder(MainActivity.this, MyDataBase.class,"database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        myDao = db.myDao();
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
        dbResults = myDao.getAll();
        if(dbResults != null){
            adapter = new MyAdapter(dbResults,MainActivity.this,MainActivity.this);
            myRv.setAdapter(adapter);
        }else {
            Toast.makeText(MainActivity.this, "list is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(DBResult dbResult) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("myObj",dbResult);
        startActivity(intent);
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                Call<ClsApiData> call = apiInterface.getAPIData("7e8f60e325cd06e164799af1e317d7a7");

                call.enqueue(new Callback<ClsApiData>() {
                    @Override
                    public void onResponse(Call<ClsApiData> call, Response<ClsApiData> response) {
                       if(response.isSuccessful()){
                           ClsApiData clsApiData = response.body();
                           Log.d("Check", "onResponse: "+new Gson().toJson(clsApiData));
                            results = clsApiData.getResults();
                           if(results != null){
                                for (Result result : results){
                                    myDao.addResult(new DBResult(result.getAdult(),
                                            result.getBackdropPath(),
                                           // result.getGenreIds(),
                                            result.getId(),
                                            result.getOriginalLanguage(),
                                            result.getOriginalTitle(),
                                            result.getOverview(),
                                            result.getPopularity(),
                                            result.getPosterPath(),
                                            result.getReleaseDate(),
                                            result.getTitle(),
                                            result.getVideo(),
                                            result.getVoteAverage(),
                                            result.getVoteCount()
                                            ));

                                }

                               Log.d("Check", "onPostExecute:  myDao.getAll() if "+new Gson().toJson( myDao.getAll()));
                               dbResults = myDao.getAll();
                           }
                       }
                    }

                    @Override
                    public void onFailure(Call<ClsApiData> call, Throwable t) {
                        Log.d("Check", "onResponse: "+t.getMessage());

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
//            setAdapter();
//            if(dbResults != null){
//                adapter = new MyAdapter(dbResults,MainActivity.this);
//                myRv.setAdapter(adapter);
//            }else {
//                Toast.makeText(MainActivity.this, "list is null", Toast.LENGTH_SHORT).show();
//            }

            Log.d("Check", "onPostExecute:  myDao.getAll() "+new Gson().toJson( myDao.getAll()));
           ;
        }
    }

}