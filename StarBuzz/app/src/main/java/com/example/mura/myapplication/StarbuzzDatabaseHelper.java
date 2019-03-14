package com.example.mura.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 10;

    StarbuzzDatabaseHelper(Context context){

        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,2,DB_VERSION);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE DRINK("
                    + "_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT,"
                    + "DESCRIPTION TEXT,"
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(db,"Latte","Espresso and steamed milk",R.drawable.latte);
            insertDrink(db,"Cappucino","Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
            insertDrink(db,"Fitter","Our best drip coffe",R.drawable.filter);
        }if(oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERCI");

        }
        if(newVersion == 3){
            db.execSQL("DROP TABLE DRINK;");
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(db,"Latte","Espresso and steamed milk",R.drawable.latte);
            insertDrink(db,"Cappucino","Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
            insertDrink(db,"Fitter","Our best drip coffe",R.drawable.filter);
        }
        if(newVersion == 4){
            db.execSQL("DROP TABLE DRINK;");
        }
        if(newVersion == 5){
            db.execSQL("DROP TABLE DRINK;");
        }
        if(newVersion == 6){
            db.execSQL("DROP TABLE DRINK;");
        }
        if(newVersion == 7){
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(db,"Latte","Espresso and steamed milk",R.drawable.latte);
            insertDrink(db,"Cappucino","Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
            insertDrink(db,"Fitter","Our best drip coffe",R.drawable.filter);
        }
        if(newVersion == 8){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");

        }
        if(newVersion == 9 ){
            db.execSQL("ALTER TABLE DRINK DROP COLUMN FAVORITE");
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }
        if(newVersion == 10){
            db.execSQL("DROP TABLE DRINK;");
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(db,"Latte","Espresso and steamed milk",R.drawable.latte);
            insertDrink(db,"Cappucino","Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
            insertDrink(db,"Fitter","Our best drip coffe",R.drawable.filter);
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }

    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int recourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME",name);
        drinkValues.put("DESCRIPTION",description);
        drinkValues.put("IMAGE_RESOURCE_ID",recourceId);
        db.insert("DRINK",null,drinkValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion,newVersion);
    }
}
