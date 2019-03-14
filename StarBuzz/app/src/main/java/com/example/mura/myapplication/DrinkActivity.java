package com.example.mura.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {

    public  static final String EXTRA_DRINKID = "drinkId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        //Cоздание курсора бд
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try{
            //открытие на чтение
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME","DESCRIPTION","IMAGE_RESOURCE_ID","FAVORITE"},
            "_id = ?",
            new String[]{Integer.toString(drinkId)},
            null,null,null);

            //переход к первой записи курсора
            if(cursor.moveToFirst()){

                //получение данных напитка
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3)==1);

                //хаполненеие названия напитка
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                //заполненеи описания
                TextView descr = (TextView) findViewById(R.id.description);
                descr.setText(descriptionText);

                //заполнение изображения
                ImageView photo = (ImageView) findViewById(R.id.photo);

                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //заполненеие флажка любимого напитка
                CheckBox favorite = (CheckBox) findViewById(R.id.fovorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        }catch (SQLException E){

            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }


    }


    //обновление бд по щелчку
    public void onFavoriteClicked(View view) {
        int drinkId = (Integer) getIntent().getExtras().get((EXTRA_DRINKID));
        new UpdateDrinkTsks().execute(drinkId);
    }


    private class UpdateDrinkTsks extends AsyncTask<Integer,Void,Boolean> {
        private ContentValues drinkValues;


        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkId = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            try{
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("DRINK",
                        drinkValues,
                        "_ID=?",
                        new String[]{Integer.toString(drinkId)});
                db.close();
                return true;
            }catch (SQLException e){
                return false;
            }
        }

        protected void onPreExecute(){
            CheckBox favorite = (CheckBox) findViewById(R.id.fovorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE",favorite.isChecked());
        }


        protected void onPostExecute(Boolean succes){
            if(!succes){
                Toast toast = Toast.makeText(DrinkActivity.this,"Database unavailable",Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

}


