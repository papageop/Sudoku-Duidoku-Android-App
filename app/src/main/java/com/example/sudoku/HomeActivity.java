package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Κλάση που αναπαριστά το Activity για την αρχική σελίδα όπου ο χρήστης εισάγει
 * ψευδώνυμο για να παίξει στη συνέχεια κάποιο παιχνίδι
 */

public class HomeActivity extends AppCompatActivity {

    public TextView responseBox;// TextView όπου εκτυπώνεται η απάντηση δηλάδη αν υπάρχει ο χρήστης
    // ή όχι και είναι κάποιος χρήστης συνδεδεμένος με κάποιο ψευδώνυμο ήδη
    EditText textBox; //EditText όπου γράφει ο χρήστης ως input ένα ψευδώνυμο
    //Button click; //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
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
        responseBox = findViewById(R.id.responseView);
        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        String username=sharedPreferences.getString("username",null);
        if (username != null) {
            responseBox.setText("Current nickname playing: "+username);
        }
        else{
            responseBox.setText("No nickname. Please give a nickname to play the game!");
        }
        textBox=findViewById(R.id.textView);
        //click=findViewById(R.id.search);
    }

    /**
     * OnClick μέθοδος για ADD button
     */
    public void newUser (View view) {
        DataTable dbHandler = new DataTable(this, null, null, 1);
        String username = textBox.getText().toString();
        if (!username.equals("") ){ //αν ο χρήστης έχει πληκτρολογήσει κάτι
            User found = dbHandler.findUser(textBox.getText().toString());//ψάξε στη βάση το χρήστη
            if (found == null){ //αν δεν υπάρχει ο χρήστης δημιούργησε ένα νέο User με το ψευδόνυμο που δόθηκε
                User user = new User(textBox.getText().toString());
                dbHandler.addUser(user);
                textBox.setText("");
                responseBox.setText("Username "+user.getNickname()+" stored in database.");
            }
            else {//αλλιώς αν υπάρχει ήδη εκτύπωσε μήνυμα
                responseBox.setText("Username already stored in database.");
            }
        }
    }

    /**
     * Μέθοδος που αποθηκεύει στα Shared Preferences κάποιο username για να έχουν πρόσβαση τα
     * όλα τα Activities
     * @param username
     */
    public void saveInfo(String username)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",username);
        editor.commit();
    }
    //OnClick μέθοδος για το Search button
    public void lookupUser (View view) {
        DataTable dbHandler = new DataTable(this, null, null, 1);
        User user = dbHandler.findUser(textBox.getText().toString()); //ψάξε το χρήστη στη βάση
        if (user != null) { //αν υπάρχει κάποιος χρήστης με το ψευδώνυμο που δόθηκε σύνδεσε τον στο παιχνίδι
            responseBox.setText("Welcome "+user.getNickname()+"!\n Number of wins: "+user.getWins()+"\n Number of losses: "+user.getLosses()+"\n Previous classic played: "+user.getCurrentClassic()+"\n Choose a game to play");

            saveInfo(user.getNickname());//αποθήκευσε το όνομα του χρήστη στα Shared Preferences

        } else {//αλλιώς εμφάνισε ότι δε βρέθηκε χρήστης
            responseBox.setText("No match");
        }
    }
}
