package Model;

/**
 * Created by Oskar on 2015-10-15.
 */
public abstract class Player implements Comparable<Player>{

    protected String name;
    protected int wins;

    public Player(String name){
        this.name=name;
    }

    /**
     * Increments the number of times this Player has won
     */
    public void won(){
        wins++;
    }

    /**
     * @return, amount of times this player has won.
     */
    public int getNbrWins(){
        return wins;
    }

    /**
     * @return, Player name.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the number of wins this player has.
     * @param wins, number of wins.
     */
    public void setNbrWins(int wins){
        this.wins=wins;
    }

    public abstract boolean equals(Object o);

    public abstract void setDifficulty(int difficulty);

    public int compareTo(Player p){
        if(wins < p.getNbrWins()) return 1;
        if(wins > p.getNbrWins()) return -1;
        else{
            return 0;
        }
    }
}
