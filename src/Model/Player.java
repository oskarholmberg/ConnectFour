package Model;

/**
 * Created by Oskar on 2015-10-06.
 */
public class Player {

    private String name;
    private Token t;
    private int wins = 0;

    public Player(String name, Token t){
        this.name=name;
        this.t = t;
    }

    public String getName(){
        return name;
    }

    public Token getToken(){
        return t;
    }

    public void won(){
        wins++;
    }
}
