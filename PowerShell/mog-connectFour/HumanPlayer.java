import java.util.Scanner;

public class HumanPlayer extends ConnectFourPlayer {
    public HumanPlayer( String playerName, char difficulty, boolean automatic ) {
        super(playerName, '\0', false);
    }

    @Override
    public void makeMove(ConnectFourGame game) {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        int move;
        while (!done) {
            System.out.print("Please select a column [1-7]: ");
            move = in.nextInt();
            done = game.dropPiece(move-1);
            if (done) System.out.println(getPlayerName() + " has made a move in column " + move);
            else System.out.println("Invalid move, column full or not available.");
        }
    }
}
