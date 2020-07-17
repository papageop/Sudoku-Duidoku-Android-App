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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Kλάση που υλοποιεί το Activity για το παιχνίδι Classic Sudoku.
 */
public class ClassicActivity extends AppCompatActivity {

    private GridView gridView; //GridView για εμφάνιση του πίνακα του παιχνιδιού
    String[] arr; //πίνακας των τιμών που θα εμφανιστούν στο χρήστη
    private Spinner mSpinner; //Spinner με τις επιλογές του χρήστη
    Classic classic; //αντικείμενο Classic που αντιπροσωπεύει τη λογική του σουντόκου
    Button helpButton; //το κουμπί για βοήθεια
    TextView textHelp; //ΤextView όπου εμφανίζονται οι πιθανές λυσεις για κάποιο κουτί

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic2);
        classic=new Classic();

        helpButton=(Button)findViewById(R.id.button_help);
        textHelp=(TextView)findViewById(R.id.text_help);
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
        arr=new String[81];
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.classic_array)));
        DataTable dbHandler= new DataTable(this, null, null, 1);
        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        String username=sharedPreferences.getString("username",null);
        User user = dbHandler.findUser(username);
        if(user==null){ //αν ο χρήστης είναι ανώνυμος φόρτωσε τυχαία ένα παιχνίδι
            Random random=new Random();
            loadPuzzle(random.nextInt(10)+1);
        }
        else{
            loadPuzzle(user.getCurrentClassic());
        }

        //αρχικοποίηση του πίνακα arr που θα εμφανίστούν οι τιμές του στο χρήστη
        for (int i = 0;i<9;i++){
            for (int j=0;j<9;j++){
                if(classic.board[i][j].getValue()!=0) {
                    arr[i * 9 + j] = "" + classic.board[i][j].getValue();
                }
                else{
                    arr[i * 9 + j] = "";
                }

            }
        }

        //Toast.makeText(this,""+classic.board[0][0].getValue(),Toast.LENGTH_SHORT).show();
        final GridAdapter gadapter=new GridAdapter(this,arr,classic);
        gridView.setAdapter(gadapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) { //listener για τα κουτάκια του πίνακα του παιχνιδιού
                helpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { //listener για το κουμπί help
                        int[] temp=classic.getHelp(position/9,position%9);
                        String s="";
                        for (int i=0;i<temp.length;i++){
                            s+=temp[i]+", ";
                        }
                        textHelp.setText(s);
                    }
                });
                //Toast.makeText(ClassicActivity.this,"Clicked" +position+" "+position/9+" "+position%9, Toast.LENGTH_SHORT).show();
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    //Do something when the item is selected
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        //getting label name of the selected spinner
                        String message = adapterView.getItemAtPosition(i).toString();
                        if(message.equals("-")){ //αν πατηθεί το κουμπί του Spinner "-" μη κάνεις τίποτα

                        }
                        else{
                        Integer integer=new Integer(message);

                        if(classic.getUserInput(position/9,position%9,integer))//αν ο αριθμός στη θέση αυτή είναι έγκυρος
                        {

                                gadapter.array[position]=""+classic.board[position/9][position%9].getValue();
                            gadapter.notifyDataSetChanged();
                        }

                        else{//η κίνηση δεν είναι έγκυρη
                            Toast.makeText(ClassicActivity.this,"Invalid Move", Toast.LENGTH_SHORT).show();
                        }
                        if(classic.isOver()) //αν έχει τελειώσει το παζλ
                        {
                            updateUser();
                        }
                        if(classic.wrongSolution()){ //αν δεν έχει τελειώσει το παιχνίδι αλλά υπάρχουν κενά κελιά με μη έγκυρες κινήσεις
                            //τότε η λύση είναι λάθος
                            Toast.makeText(ClassicActivity.this,"Wrong Solution", Toast.LENGTH_SHORT).show();
                        }

                    }}

                    //may keep blank
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                });
                mSpinner.setAdapter(new ArrayAdapter<String>(ClassicActivity.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.classic_array)));

            }

        });



    }

    /**
     * Μέθοδος που φορτώνει ένα παζλ από το αρχείο games.txt. Αν ο χρήστης είναι ανώνυμος φορτώνεται ένα τυχαίο παιχνίδι
     * αλλιώς φορτώνεται το τρέχον παιχνίδι του χρήστη.
     * @param number
     */
    public void loadPuzzle(int number) {
        classic.setId(number);
        BufferedReader input = null; //δημιουργεί ένα bufferedReader για το άνοιγμα και διάβασμα του αρχείου

        try {
            while (input == null){

                input = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.games)));

            }
            String fileRead=input.readLine(); //διαβάζει τη πρώτη γραμμή

            int count=1;
            //διαβάζει τις γραμμές έως ότου να φτάσει το παζλ στη γραμμή number
            while (fileRead!=null && count!=number) {
                fileRead=input.readLine();
                count++;
            }

            String[] tokenize = fileRead.split(" "); //αγνοεί τα κενά


            String str;
            int num;
            for (int i=0;i<tokenize.length;i++) {
                // θεωρούμε ότι το αρχείο είναι σωστά γραμμένο με κενά ανάμεσα στο στις τιμές των κελιών
                // and make temporary variables for the three types of data
                str = tokenize[i];
                num=Integer.parseInt(str);
                Cell cell=new Cell(i/9,i%9); //φορτώνει ένα κελί με τιμή num
                cell.setValue(num);
                classic.board[i/9][i%9]=cell;

            }
            //Toast.makeText(this,""+classic.board[0][0].getValue(),Toast.LENGTH_SHORT).show();
            classic.setEmpty();

        }catch (IOException e){System.err.println("No line in file");} //μήνυμα λάθος αν προκύψει σγάλμα με το αρχείο





    }
    /**
      *Μέθοδος που ανανεώνει τα στοιχεία του χρήστη στη βάση μόλις ολοκληρώσει ένα παιχνίδι
      */
    public void updateUser () {
        DataTable dbHandler = new DataTable(this, null, null, 1);
        SharedPreferences sharedPreferences=getSharedPreferences("shared",MODE_PRIVATE);
        String username=sharedPreferences.getString("username",null);

        if(username!=null){  //αν έχει δοθεί κάποιο ψευδώνυμο
            User user = dbHandler.findUser(username);
                user.setCurrentClassic(user.getCurrentClassic()+1);
            dbHandler.update(user);}

    }
}
