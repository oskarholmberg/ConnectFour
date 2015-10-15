package Model;

/**
 * Created by Oskar on 2015-10-15.
 */
public class Player implements Comparable<Player>{

    private String name;
    private int wins;

    public Player(String name){
        this.name=name;
    }

    public void won(){
        wins++;
    }

    public int getNbrWins(){
        return wins;
    }

    public String getName(){
        return name;
    }

    public void setNbrWins(int wins){
        this.wins=wins;
    }

    public boolean equals(Object o){
        if(o instanceof Player){
            Player p = (Player) o;
            return p.getName().equals(name);
        }
        return false;
    }

    public int compareTo(Player p){
        if(wins < p.getNbrWins()) return 1;
        if(wins > p.getNbrWins()) return -1;
        else{
            return 0;
        }
    }
}
