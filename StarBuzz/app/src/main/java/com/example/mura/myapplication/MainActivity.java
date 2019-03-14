package com.example.mura.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor favoriteCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupOptionsListView();
        setupFavoriteListView();

    }

    //заполнение листа любимых напитков и обработка нажатий
    private void setupFavoriteListView() {
        ListView listFovorites = (ListView) findViewById(R.id.list_favorites);
        try{
            //используя помощник устанавливаем курсов
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db =starbuzzDatabaseHelper.getReadableDatabase();
            favoriteCursor = db.query("DRINK",
                    new String[]{"_ID,NAME"},
                    "FAVORITE = 1",
                    null,null,null,null);

            //добавляемкурсор в адаптер
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(MainActivity.this,
                    android.R.layout.activity_list_item,
                    favoriteCursor,
                    new String[]{"NAME"},
                    new int[] {android.R.id.text1},0);
            listFovorites.setAdapter(favoriteAdapter);
        }catch (SQLException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();

        }

        //переход к напитку при нажатии
        listFovorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRestart(){
        super.onRestart();
        //создаем новую версию курсора
        Cursor newCursor = db.query("DRINK",
                new String[]{"_ID","NAME"},
                "FAVORITE = 1",
                null,null,null,null);

        ListView listfovarites = (ListView) findViewById(R.id.list_favorites);
        CursorAdapter adapter = (CursorAdapter) listfovarites.getAdapter();
        //заменяем старый курсор на новый
        adapter.changeCursor(newCursor);
        favoriteCursor = newCursor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteCursor.close();
        db.close();
    }

    private void setupOptionsListView() {
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    Intent intent = new Intent(MainActivity.this,DrinkCategoryActivity.class);
                    startActivity(intent);

                }
            }
        };
        ListView listView = (ListView) findViewById(R.id.list_option);
        listView.setOnItemClickListener(itemClickListener);

    }


}
