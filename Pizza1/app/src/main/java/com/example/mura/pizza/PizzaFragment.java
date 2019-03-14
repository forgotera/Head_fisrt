package com.example.mura.pizza;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaFragment extends Fragment {


    public PizzaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView pizzaRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_pizza,container,false);

        //название пиццы добавляются в массив строк
        String[] pizzaNames = new String[Pizza.pizzas.length];
        for (int i = 0; i < pizzaNames.length; i++){

            pizzaNames[i] = Pizza.pizzas[i].getName();
        }


        //изображения добавляются в массив с int
        int[] pizzaImages = new int[Pizza.pizzas.length];
        for(int i = 0; i<pizzaImages.length; i++){
            pizzaImages[i] = Pizza.pizzas[i].getImageRecourceId();
        }

        //передаем массивы адаптеру
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames,pizzaImages);
        pizzaRecycler.setAdapter(adapter);

        //выводим список ввиде таблицы
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        pizzaRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(),activity_pizza_details.class);
                intent.putExtra(activity_pizza_details.EXTRA_PIZZA_ID,position);
                getActivity().startActivity(intent);
            }
        });
        return  pizzaRecycler;

    }

}
