package Control;

import Model.GreenToken;
import Model.Player;
import Model.RedToken;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Oskar", new RedToken()));
        players.add(new Player("Kevin", new GreenToken()));
        GameSession gs = new GameSession(players);
        gs.run();
    }
}
