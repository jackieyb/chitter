package com.jterm.jaby.scarne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        ((Button)findViewById(R.id.playAgainButton)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public static final String USER_SCORE = "com.jterm.jaby.scarne.USER_SCORE";
    private void playerWins(){

        Intent intent = new Intent(this, WinActivity.class);
        intent.putExtra(USER_SCORE,String.valueOf(playerTotal + currentTurn));
        startActivity(intent);
        reset();
    }

}
