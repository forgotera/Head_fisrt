package com.example.mura.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BeerExpert beerExpert = new BeerExpert();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickFindBeer(View view){
        //получить ссылку на TextView
        TextView brands =  (TextView) findViewById(R.id.brands);

        //получить ссылку на spinner
        Spinner colors = (Spinner) findViewById(R.id.color);

        //получить выриант выбранный в спинер
        String color = String.valueOf(colors.getSelectedItem());

        //получить рекомендации от beerexpert
        List<String> brandList = (beerExpert.getBrands(color));
        StringBuilder brandsFormatted = new StringBuilder();
        for( String brand : brandList){
            brandsFormatted.append(brand).append('\n');
        }
        brands.setText(brandsFormatted);
    }
}
