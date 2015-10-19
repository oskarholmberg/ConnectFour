package Model;

import java.awt.event.MouseEvent;

/**
 * Created by oskar on 2015-10-16.
 */
public class HumanPlayer extends Player implements Comparable<Player>{

    public HumanPlayer(String name){
        super(name);
    }

    public boolean equals(Object o){
        if(o instanceof HumanPlayer){
            Player p = (HumanPlayer) o;
            return p.getName().equals(name);
        }
        return false;
    }

    @Override
    public void setDifficulty(int difficulty) {

    }
}
