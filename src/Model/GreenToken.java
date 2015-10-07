package Model;

/**
 * Created by Oskar on 2015-10-06.
 */
public class GreenToken implements Token {

    public int getShape(){
        return GlobalVariables.GREEN;
    }

    @Override
    public boolean equals(Token t) {
        return t.getShape()==GlobalVariables.GREEN;
    }
}
