package Model;


/**
 * Created by Oskar on 2015-10-06.
 */
public class RedToken implements Token {

    public int getShape(){
        return GlobalVariables.RED;
    }

    @Override
    public boolean equals(Token t) {
        return t.getShape()==GlobalVariables.RED;
    }
}
