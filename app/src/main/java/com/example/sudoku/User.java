package com.example.sudoku;

/**
 * Κλάση που αναπαριστά το χρήστη της εφαρμογής. Ο χρήστης μπορεί να
 * προσθέσει ένα ψευδώνυμο και να αποθηκεύεται στη βάση το
 * τρέχον παιχνίδι κλασικό σουντόκου, οι νίκες και οι ήττες στο duidoku.
 */

public class User  {
    private int id;
    private String nickname; //το ψευδώνυμο του χρήστη
    private int wins; //οι νίκες του χρήστη στο παιχνίδι duidoku
    private int losses; //οι ήττες του χρήστη στο παιχνίδι duidoku
    private int currentClassic; //το τρέχον κλασικό παιχνίδι που έπαιξε

    /**
     * Κατασκευαστής της κλάσης User με παράμετρο το
     * ψυδώνυμο του χρήστη. Αρχικοποιεί τις υπόλοιπες
     * μεταβλητές στο μηδέν.
     * @param nickname
     */
    public User(String nickname)
    {
        this.nickname=nickname;
        this.wins=0;
        this.losses=0;
        this.currentClassic=1;


    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     *Setter για το πεδίο wins.
     * Θέτει τιμή μεγαλύτερη ίση του μηδέν ίση με
     * τη παραμετρό.
     * @param wins οι νίκες που είχε ο χρήστης στο duidoku
     */
    public void setWins(int wins)
    {
        if (wins>=0)
            this.wins=wins;
    }

    /**
     *Setter για το πεδίο losses.
     * Θέτει τιμή μεγαλύτερη ίση του μηδέν ίση με
     * τη παραμετρό.
     * @param losses οι ήττες που είχε ο χρήστης στο duidoku
     */
    public void setLosses(int losses)
    {
        if (losses>=0) //πρέπει οι ήττες να είναι θετικός ακέραιος αριθμός
            this.losses=losses;
    }
    /**
     *Setter για το πεδίο nickname.
     * Θέτει τιμή ίση με tη παραμετρό.
     * @param nickname το ψυδώνυμο που θέλει να δώσει ο χρήστης
     */
    public void setNickname(String nickname)
    {
        this.nickname=nickname;
    }

    /**
     * Προσθέτει μια νίκη στο χρήστη
     * σε περίπτωση που κερδίσει στο παιχνίδι
     * duidoku
     */
    public void addWin()
    {
        this.wins++;
    }

    /**
     * Προσθέτει μια ήττα στο χρήστη
     * σε περίπτωση που χάσει στο παιχνίδι
     * duidoku
     */
    public void addLoss()
    {
        this.losses++;
    }

    /**
     * Θέτει το τρέχον κλασικό σουντόκου
     * που έπαιξε ο χρήστης για να μην
     * εμφανιστεί ξανά. Δέχεται παράμετρο
     * τον αριθμό του προηγούμενου παιχνιδιού
     * που πρέπει να ανήκει στο διάστημα (0,10]
     * @param currentClassic το τρέχον παιχνίδι για το κλασικό
     */
    public void setCurrentClassic(int currentClassic)
    {
        if(currentClassic>0 && currentClassic<=10)
            this.currentClassic=currentClassic;
    }



    /**
     * Επιστρέφει το ψευδώνυμου του χρήστη.
     * @return το ψευδώνυμο του χρήστη
     */
    public String getNickname()
    {
        return this.nickname;
    }

    /**
     * Επιστρέφει τις νίκες του χρήστη
     * @return οι νίκες του χρήστη στο duidoku
     */
    public int getWins()
    {
        return this.wins;
    }

    /**
     * Επιστρέφει τις ήττες του χρήστη
     * @return οι ήττες του χρήστη duidoku
     */
    public int getLosses()
    {
        return this.losses;
    }

    /**Επιστρέφει το τρέχον κλασικό παιχνίδι
     * που έπαιξε ο χρήστης
     * @return το τρέχον κλασικό παιχνίδι που έπαιξε
     */
    public int getCurrentClassic()
    {
        return this.currentClassic;
    }

}

