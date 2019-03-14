package com.example.mura.workaut;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements WorkautListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public  void itemClicked(long id){
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null){
            WorkautDetailFragment detailFragment =new WorkautDetailFragment();
            //add fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            detailFragment.setWorkoutId(id);
            ft.replace(R.id.fragment_container,detailFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();


        }else{
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKAUT_ID, (int) id);
            startActivity(intent);
        }
    }
}
