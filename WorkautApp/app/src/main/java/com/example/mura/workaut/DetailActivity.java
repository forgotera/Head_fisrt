package com.example.mura.workaut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    public  static  final  String EXTRA_WORKAUT_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WorkautDetailFragment fragment = (WorkautDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        int workautId = (int) getIntent().getExtras().get(EXTRA_WORKAUT_ID);
        fragment.setWorkoutId(workautId);
    }
}
