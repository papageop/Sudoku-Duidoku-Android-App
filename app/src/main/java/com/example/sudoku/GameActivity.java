package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Κλάσης που αναπάριστά το Activity που περιέχει δύο κουμπιά με τις
 * επιλογές παιχνιδιών του χρήστη.
 */
public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_game);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_game:

                        startActivity(new Intent(getApplicationContext(),GameActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }


                return true;

            }
        });
    }

    /**
     * Μέθοδος που μεταβαίνει το χρήστη στο κλασικό παιχνίδι με το πάτημα του κουμπιού
     * @param view
     */
    public void sudokuButton(View view){
        startActivity(new Intent(getApplicationContext(),ClassicActivity.class));
    }

    /**
     * Μέθοδος που μεταβαίνει το χρήστη στο παιχνίδι duidoku με το πάτημα του κουμπιού
     * @param view
     */
    public void duidokuButton(View view){
        startActivity(new Intent(getApplicationContext(),DuidokuActivity.class));
    }
}
