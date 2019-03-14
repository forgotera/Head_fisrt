package com.example.mura.mymessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSendMessage(View view){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        EditText text = (EditText) findViewById(R.id.message);
        intent.putExtra("message",text.getText().toString());
        String choseTitle = getString(R.string.chooser);
        Intent chosenIntent = Intent.createChooser(intent,choseTitle);
        startActivity(chosenIntent);

    }
}
