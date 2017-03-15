package com.example.andware.tetravex;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LeaderboardActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks;
    Button newButton, viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText2);
        editSurname = (EditText)findViewById(R.id.editText3);
        editMarks = (EditText)findViewById(R.id.editText4);
        newButton = (Button)findViewById(R.id.buttontest);
        viewAll = (Button)findViewById(R.id.view_all);
        AddData();
        viewAll();
    }

    public void AddData(){
        newButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.intsertData(editName.getText().toString(),
                                editSurname.getText().toString(),
                                editMarks.getText().toString());

                        if (isInserted){
                            Toast.makeText(LeaderboardActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(LeaderboardActivity.this, "It didn't work", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void viewAll(){
        viewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0){
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Id : "+ res.getString(0)+"\n");
                            buffer.append("Name : "+ res.getString(1)+"\n");
                            buffer.append("Surname : "+ res.getString(2)+"\n");
                            buffer.append("Marks : "+ res.getString(3)+"\n\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
