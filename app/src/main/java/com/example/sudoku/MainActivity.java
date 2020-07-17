package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Κλάση που αναπαριστά τη Main Activity της εφαρμογής και
 * από την οποία ξεκινάει. Περιέχει ένα TextView που καλωσορίζει τους χρήστες
 * και ένα κουμπί για να μπορούν να εισέλθουν στο κύριο κομμάτι της εφαρμογής
 * για να προσθέσουν ψευδώνυμο αλλα και να παίξουν κάποιο από  τα δύο παιχνίδια
 */

public class MainActivity extends AppCompatActivity {

    Button click; //το κουμπί της αρχικής οθόνης
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click=findViewById(R.id.button);

    }

    /**
     * Συνάρτηση που χρησιμεύει ως listener όταν πατηθεί το κουμπί και μεταβαίνει στη κύρια
     * εφαρμογή.
     * @param view
     */
    public void onClickButton(View view){
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));

    }

}
