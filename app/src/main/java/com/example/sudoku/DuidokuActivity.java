package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Κλάση που αναπαριστά το Αctivity για το παιχνίδι duidoku
 */
public class DuidokuActivity extends AppCompatActivity {
    private GridView gridView;
    String[] arr;
    private Spinner mSpinner;
    Duidoku duidoku;
    Button helpButton; //κουμπί για να εμφανιστεί βοήθεια για κάποιο κελί
    TextView textHelp; //εδώ εμφανίζεται η βοήθεια
    TextView player; //εμφανίζει τους ποντους του παίκτη
    TextView computer; //εμφανίζει τους ποντους του υπολογιστή
    int playerPoints; //οι πόντοι του παίκτη
    int computerPoints; //οι πόντοι του υπολογιστή
    GridAdapter gadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duidoku);
        duidoku=new Duidoku();
        helpButton=(Button)findViewById(R.id.button_help);
        textHelp=(TextView)findViewById(R.id.text_help);
        player=(TextView)findViewById(R.id.text_view_p1);
        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        String username=sharedPreferences.getString("username",null);
        if (username==null){
            player.setText("User: 0");
        }else{
        player.setText(username+": 0");}
        computer=(TextView)findViewById(R.id.text_view_p2);
        computer.setText("Computer: "+0);
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
        gridView=(GridView)findViewById(R.id.grid);
        arr=new String[16];
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.duidoku_array)));

        for (int i = 0;i<4;i++){
            for (int j=0;j<4;j++){
                arr[i*4+j]="";


            }
        }


        gadapter=new GridAdapter(this,arr);
        gridView.setAdapter(gadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                helpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int[] temp=duidoku.getHelp(position/4,position%4);
                        StringBuilder s= new StringBuilder();
                        for (int i=0;i<temp.length;i++){
                            s.append(temp[i]).append(", ");
                        }

                        textHelp.setText(s.toString());
                    }
                });
                //Toast.makeText(DuidokuActivity.this,"Clicked" +position, Toast.LENGTH_SHORT).show();
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    //Do something when the item is selected
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String message = adapterView.getItemAtPosition(i).toString();
                        if(message.equals("-")){

                        }
                        else{
                        //getting label name of the selected spinner

                        int integer= Integer.parseInt(message);
                        //showing in Toast selected item name
                        //Toast.makeText(DuidokuActivity.this,"Clicked" +position+ message, Toast.LENGTH_SHORT).show();

                        if(duidoku.getUserInput(position/4,position%4,integer))
                        {//αν ο αριθμός στη θέση position είναι έγκυρος
                            for (int k=0;k<16;k++){
                                //arr[i]=""+duidoku.board[k/4][k%4].getValue();
                                if(duidoku.board[k/4][k%4].getValue()!=0) {
                                    gadapter.array[k] = "" + duidoku.board[k / 4][k % 4].getValue();
                                }
                                else{
                                    gadapter.array[k] = "";
                                }

                            }

                            gadapter.notifyDataSetChanged();



                        }
                        else{//αν δεν είναι έγκυρος εμφάνισε μήνυμα λάθους
                            Toast.makeText(DuidokuActivity.this,"Invalid Move", Toast.LENGTH_SHORT).show();
                        }
                        if (duidoku.isOver() ){//αν το παιχνίδι έχει ττελειώσει
                            if(duidoku.userWins()) {//αν νίκησε ο χρηστης
                                {
                                    Toast.makeText(DuidokuActivity.this, "You win", Toast.LENGTH_SHORT).show();



                                }
                            }
                            else if(duidoku.isADraw()){//αν είναι ισοπαλία
                                Toast.makeText(DuidokuActivity.this, "This is a draw", Toast.LENGTH_SHORT).show();
                            }
                            else{//αν κέρδισε ο υπολογιστής
                                Toast.makeText(DuidokuActivity.this, "You lose", Toast.LENGTH_SHORT).show();
                            }
                            updateUser();
                        }
                        }


                    }

                    //may keep blank
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                });

                mSpinner.setAdapter(new ArrayAdapter<String>(DuidokuActivity.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.duidoku_array)));

            }

        });



    }

    /**
     * Μέθοδος που ανανεώνει στη βάση τη νίκη ή την ήττα του χρήστη στο παιχνίδι
     */
    public void updateUser () {
        DataTable dbHandler = new DataTable(this, null, null, 1);
        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        String username=sharedPreferences.getString("username",null);

        if(!duidoku.isADraw()){//αν δεν είναι είναι ισοπαλία
        if(username!=null){ //αν δεν είναι ανώνυμος ο χρήστης
            User user = dbHandler.findUser(username); //ψάξε το χρήστη στη βάση

        if (duidoku.userWins()) { //αν κέρδισε ο χρήστης αύξησε τους πόντους νίκης του
            playerPoints++;
            user.addWin();
            player.setText(user.getNickname()+":"+playerPoints);



        } else { //αλλιώς αύξησε τους πόντους του υπολογιστή και πρόσθεσε την ήττα του χρήστη
            computerPoints++;
            user.addLoss();
            computer.setText("Computer:"+computerPoints);
        }
        dbHandler.update(user);//ανανέωσε τα στοιχεία του χρήστη στη βάση
        }
        else{//αν ο χρήστης είναι ανώνυμος
            if (duidoku.userWins()) {//αν κέρδισε ο χρήστης αύξησε τους πόντους του
                playerPoints++;
                player.setText("User:"+playerPoints);



            } else {//αλλιώς αύξησε τους πόντους του υπολογιστή
                computerPoints++;
                computer.setText("Computer:"+computerPoints);
            }
        }}
    }

    /**
     * OnClick μέθοδος για το RESET Button που μηδενίζει το πίνακα
     * @param view
     */
    public void reset(View view){
        duidoku=new Duidoku();
        for (int k=0;k<16;k++){
            //arr[i]=""+duidoku.board[k/4][k%4].getValue();
            gadapter.array[k]="";

        }

        gadapter.notifyDataSetChanged();
    }

}
