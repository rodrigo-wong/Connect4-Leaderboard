import java.util.Random;

public class RandomAI extends ConnectFourPlayer {
    public RandomAI(String player_name, char difficulty, boolean automaticPlayer) {
        super( player_name, difficulty, automaticPlayer);
    }

    public void makeMove(ConnectFourGame game ) {
        // Trivial game play. Replace with minimax.
        // Demonstrates game play reasonably.
        Random r = new Random();
        while ( !game.dropPiece(r.nextInt(7)) )
            ;

        // Note: getBoard() will return a copy of a board, not the reference to the board
        //       This is important so that Java doesn't update the object (game.board, etc)
        //       To update the board, call dropPiece.

        return;
    }


    private int buildTree( ConnectFourNode parent, ConnectFourGame.SYMBOLS currentPlayer, boolean maximizer, int myDepth ) {
        return 0;
    }

    public static int scoreBoard( char[][] board, ConnectFourGame.SYMBOLS turn, boolean isMaximizer, int depth ) {
        int score = 0;

        // Note: this is only being provided to shorten your development time, this is not the
        //       scoring algorithm, you need to build the algorithm around this code.

        // look at the board 4 squares at a time: vertically, horizontally, and diagonally
        for ( int i=0; i< board.length; i++) {
            for ( int j=0; j<board[i].length; j++) {
                int rowSymb=0, colSymb=0, pdiagSymb=0, ndiagSymb=0, rowBlank=0, colBlank=0, pdiagBlank=0, ndiagBlank=0;

                if ( board[i][j] == turn.getLabel() ) { rowSymb++; colSymb++; pdiagSymb++; ndiagSymb++; }
                else if ( board[i][j] == ' ' ) { rowBlank++; colBlank++; pdiagBlank++; ndiagBlank++; }

                if ( j < board[i].length - 3 ) {
                    if (board[i][j + 1] == turn.getLabel()) colSymb++;
                    else if (board[i][j + 1] == ' ') colBlank++;

                    if (board[i][j + 2] == turn.getLabel()) colSymb++;
                    else if (board[i][j + 2] == ' ') colBlank++;

                    if (board[i][j + 3] == turn.getLabel()) colSymb++;
                    else if (board[i][j + 3] == ' ') colBlank++;
                }

                // horizontal check
                if ( i < board.length-3 ) {
                    if (board[i+1][j] == turn.getLabel()) rowSymb++;
                    else if (board[i+1][j] == ' ') rowBlank++;

                    if (board[i+2][j] == turn.getLabel()) rowSymb++;
                    else if (board[i+2][j] == ' ') rowBlank++;

                    if (board[i+3][j] == turn.getLabel()) rowSymb++;
                    else if (board[i+3][j] == ' ') rowBlank++;

                }

                // diagonal checks
                if ( i < board.length - 3 && j < board[i].length - 3 ) {
                    if (board[i + 1][j + 1] == turn.getLabel()) pdiagSymb++;
                    else if (board[i + 1][j + 1] == ' ') pdiagBlank++;
                    if (board[i + 2][j + 2] == turn.getLabel()) pdiagSymb++;
                    else if (board[i + 2][j + 3] == ' ') pdiagBlank++;
                    if (board[i + 3][j + 3] == turn.getLabel()) pdiagSymb++;
                    else if (board[i + 2][j + 3] == ' ') pdiagBlank++;
                }

                if ( i >= 3 && j < board[i].length - 3 ) {
                    if (board[i - 1][j + 1] == turn.getLabel()) ndiagSymb++;
                    else if (board[i - 1][j + 1] == ' ') ndiagBlank++;
                    if (board[i - 2][j + 2] == turn.getLabel()) ndiagSymb++;
                    else if (board[i - 2][j + 2] == ' ') ndiagBlank++;
                    if (board[i - 3][j + 3] == turn.getLabel()) ndiagSymb++;
                    else if (board[i - 3][j + 3] == ' ') ndiagBlank++;
                }

            } // j
        } // i

        return score;
    }

}
