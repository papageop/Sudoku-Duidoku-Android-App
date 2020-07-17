package com.example.sudoku;

import java.util.Random;

/**
 * Kλάση που υλοποιεί το παιχνίδι duidoku.
 * Δέχεται είσοδο από το χρήστη και ταυτόχρονα
 * παίζει και ο υπολογιστής, επιλέγοντας μια τυχαία
 * τιμή, που είναι επιτρεπόμενη, εως ότου κάποιος παίκτης
 * να μην μπορεί να κάνει κάποια κίνηση και να χάσει ή να
 * τελειώσει το παιχνίδι.
 */
public class Duidoku extends Puzzle {
    private boolean isPlayerTurn; //ελέγχει αν είναι η σειρά του παίκτη
    private Random rand; //γεννήτρια τυχαίων αριθμών για τον υπολογιστή

    /**
     * Constructor της κλάσης Duidoku. Αρχικοποιεί
     * τις μεταβλητές, τον πίνακα των τιμών του παιχνιδιού
     * και τον πίνακα που κρατάει τις άδειες θέσεις.
     * Επίσης θέτει αριθμό γραμμών και στηλών ίσο με 4.
     */
    public Duidoku()
    {

        rand=new Random();
        type=GameType.DUIDOKU;
        setRows(4);
        setColumns(4);
        board=new Cell[4][4];
        for (int i=0;i<16;i++) {
            Cell cell = new Cell(i / 4, i % 4);
            cell.setValue(0);
            this.board[i / 4][i % 4] = cell;
        }


        this.empty=new boolean[getRows()][getColumns()];
        for (int i=0;i<getRows();i++)
        {
            for (int j=0;j<getColumns();j++)
            {
                empty[i][j]=true;
            }
        }
        isPlayerTurn=true;

    }

    /**
     * Ελέγχει αν υπάρχει ο αριθμός στο κουτί 2x2 του πλέγματος που ανήκει.
     * @param x η συντεταγμένη στον άξονα x που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param y η συντεταγμένη στον άξονα y που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param value η τιμή που θέλουμε να ελέγξουμε
     * @return true αν υπάρχει αλλιώς false
     */
    public boolean inBox(int x,int y,int value)
    {
        int a=x/2;
        int b=y/2;
        int a1=a*2;
        int b1=b*2;
        for (int i=0;i<2;i++)
        {
            for (int j=0;j<2;j++)
            {
                if (board[a1+i][b1+j].getValue()==value)
                    return true;
            }
        }
        return false;
    }

    /**
     * Μέθοδος που ελέγχει αν μια κίνηση είναι έγκυρη, δηλαδή αν ο αριθμός είναι στο διάστημα [1,4],
     * αν δεν υπάρχει στην ίδια γραμμή, στήλη και κουτί.
     * @param x η συντεταγμένη στον άξονα x που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param y η συντεταγμένη στον άξονα y που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param number η τιμή που θέλουμε να ελέγξουμε
     * @return true αν είναι έγκυρη αλλιώς false
     */
    @Override
    public boolean validMove(int x,int y,int number)
    {

        return !super.inRow(x, y, number) && !super.inColumn(x, y, number) && !inBox(x, y, number) && super.inRange(x,y);

    }

    /**
     * Επιστρέφει ένα πίνακα με τις πιθανές τιμές για το
     * κελί στη θέση (x,y). Κληρονομείται από την κλάση Puzzle.
     * @param x η συντεταγμένη στον άξονα x που θέλουμε βοήθεια
     * @param y η συντεταγμένη στον άξονα y που θέλουμε βοήθεια
     * @return  πίνακας με τις πιθανές τιμές της λύσης
     */
    public int[] getHelp(int x,int y)
    {return super.getHelp(x,y); }





    /**
     * Μέθοδος που επιστρέφει ο κελί του πλέγματος του παιχνιδιού στη θέση (x,y).
     * @param x η συντεταγμένη στον άξονα x
     * @param y η συντεταγμένη στον άξονα y
     * @return  το κελί στη θέση (x,y)
     */
    public Cell getBoard(int x, int y)
    {return super.getBoard(x,y);}

    /**
     * Συνάρτηση που επιστρέφει αν ο πίνακας είναι κενός
     * και μπορεί να δεχτεί είσοδο από το χρήστη στη θέση
     * (x,y).
     * @param row η συντεταγμένη στον άξονα x
     * @param column η συντεταγμένη στον άξονα y
     * @return true αν είναι κενό αλλιώς false
     */
    public boolean isEmpty(int row,int column) {
        return super.isEmpty(row, column);
    }

    /**
     * Μέθοδος που δέχεται την είσοδο από το χρήστη και
     * αν είναι επιτρεπτεί τοποθετεί την τιμή number που δέχεται
     * από το χρήστη στη θέση (x,y). Στη συνέχεια, παίζει ο
     * computer User.
     * @param x η συντεταγμένη στον άξονα x
     * @param y η συντεταγμένη στον άξονα y
     * @param number ο αριθμός που δίνει ο χρήστης
     * @return true αν είναι σωστή είσοδος αλλιώς false
     */
    public boolean getUserInput(int x,int y,int number)
    {
        if(validMove(x, y, number) && isEmpty(x,y))
        {
            board[x][y].setValue(number);
            isPlayerTurn=false;
            empty[x][y]=false;
            return (computerInput()||true);
        }
        return false;

    }

    /**
     * Μέθοδος που τοποθετεί για το χρήστη computer ένα τυχαίο αποδεκτό
     * αριθμό στο παιχνίδι.
     * @return true αν ο υπολογιστής  βρει αποδεκτή είσοδο αλλιώς false
     */
    public boolean computerInput()
    {
        int value,x,y;

        if (!isOver())
        {
            do{
                value=rand.nextInt(4)+1;
                x=rand.nextInt(4);
                y=rand.nextInt(4);
            }while (!validMove(x,y,value) || !isEmpty(x,y));
            board[x][y].setValue(value);
            isPlayerTurn=true;
            empty[x][y]=false;
            return true;
        }
        return false;
    }

    /**
     * Μέθοδος που επιστρέφει αν το παιχνίδι έχει τελειώσει είτε
     * επειδή δεν υπάρχουν κενά κελιά, είτε επειδή δεν υπάρχουν
     * άλλες επιστρεπόμενες είσοδοι.
     * @return true αν το παιχνίδι τελείωσε αλλιώς false
     */
    @Override
    public boolean isOver()
    {
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                if (board[i][j].getValue()==0 && getHelp(i,j).length!=0 )
                    return false;


            }
        }
        return true;
    }

    /**
     * Συνάρτηση που επιστρέφει το πίνακα με τις άδειες θέσεις του παιχνιδιού.
     * @return ο πίνακας empty
     */
    public boolean[][] getEmpty()
    {
        return empty;
    }

    /**
     * Μέθοδος που επιστρέφει αν ο χρήστης νίκησε
     * ελέγχοντας αν είναι η σειρά του στο παιχνίδι.
     * @return true αν δεν είναι η σειρά του και τελείωσε το παιχνίδι αλλιώς false
     */
    public boolean userWins()
    {
        if (!isPlayerTurn)
            return true;
        return false;
    }

    /**
     * Συνάρτηση που ελέγχει αν υπάρχει ισοπαλία.
     * @return true αν έχουμε ισοπαλία και τελείωσε το παζλ αλλιώς false
     */
    public boolean isADraw()
    {
        boolean flag=true;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (getEmpty()[i][j]==true){
                    flag=false;
                    break;
                }}}
        if (isOver() && flag )
            return true;
        return false;
    }
}
