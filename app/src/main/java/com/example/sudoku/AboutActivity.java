package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Κλάση που αναπαριστά το Activity για τις οδηγίες του παιχνιδιού.
 * Οι οδηγίες βρίσκονται στο αρχείο help.txt στο φάκελο res/raw.
 */

public class AboutActivity extends AppCompatActivity {
    TextView textView; //το TextView που γίνεται εγγραφή των οδηγιών που διαβάζονται από το αρχείο

    /**
     * Μέθοδος που διαβάζει το αρχείο και εμφανίζει στο TextView τα περιεχόμενα.
     */
    public void loadInstructions() {

        BufferedReader input = null; //δημιουργεί ένα bufferedReader για το άνοιγμα και διάβασμα του αρχείου

        try {
            while (input == null){

                input = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.help)));

            }
            String line,line1="";//διαβάζει τη πρώτη γραμμή


            //διαβάζει τις γραμμές έως ότου να φτάσει το παζλ στη γραμμή number
            while ((line=input.readLine())!=null ) {
                line1+=line;

            }
            textView=(TextView)findViewById(R.id.text_view_instructions);
            textView.setText(line1);


        }catch (IOException e){System.err.println("No line in file");} //μήνυμα λάθος αν προκύψει σγάλμα με το αρχείο





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        loadInstructions();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_about);

        //listener για το bottom menu πο
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
}
