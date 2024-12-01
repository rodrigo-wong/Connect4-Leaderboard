import java.lang.reflect.Array;
import java.util.ArrayList;

// Source code by Stephen Adams
// Version 202235000
public class ConnectFourNode {
    static long nodesCreated = 0;
    private char[][] board;
    ArrayList<ConnectFourNode> children = new ArrayList<>();
    int minimaxScore;

    public ConnectFourNode(char[][] board) {
        nodesCreated++;
        this.board = new char[board.length][];
        for ( int i=0; i < board.length; i++ )
            this.board[i] = board[i].clone();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(minimaxScore+" ");
        for ( int i=0; i< board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                s.append(board[i][j]);
            }
            s.append(("|"));
        }

        return s.toString();
    }

    public char[][] getBoard() {
        return getBoard( this.board );
    }

    private static char[][] getBoard( char[][] board ) {
        char[][] b = new char[board.length][];

        for ( int i=0; i<board.length; i++ ) {
            b[i] = board[i].clone();
        }

        return b;
    }

    public static char[][] copyBoard( char[][] source ) {
        return getBoard( source );
    }
    public static boolean dropPiece( char[][] board, int col, ConnectFourGame.SYMBOLS currentTurn ) {
        // the game context always refers to the current object where the
        // AI context may refer to an arbitrary board

        int rows = board.length -1;
        int cols = board[0].length-1;

        if ( col > cols )
            return false;

        int row = rows;
        int pos = -1;


        // the top of the row might be empty but we have to slide the piece down the column.
        // equivalently we look for the first open position from the bottom up.
        // the trick is the board is upside down vis a vis array indicies.
        while ( row >= 0 ) {
            if ( board[row][col] == ' ' )
                pos = row;

            row--;
        }

        if ( pos == -1 ) {
            return false;
        } else {
            return placePiece(board, pos, col, currentTurn);
        }
    }
    public boolean dropPiece( int col, ConnectFourGame.SYMBOLS currentTurn ) {
        return dropPiece( this.board, col, currentTurn );
    }

    private static boolean placePiece( char[][] board, int row, int col, ConnectFourGame.SYMBOLS player ) {
        if ( board[row][col] == ' ' ) {
            board[row][col] = player.getLabel();
            return true;
        }

        return false;
    }

    public boolean isGameWon( ConnectFourGame.SYMBOLS turn ) {
       return isGameWon( this.board, turn );
    }

    public static boolean isGameWon( char[][] board, ConnectFourGame.SYMBOLS turn ) {
        for ( int i=0; i< board.length; i++)
            for ( int j=0; j<board[i].length; j++) {
                // vertical check
                if ( j < board[i].length - 3 &&
                        board[i][j]   == turn.getLabel() &&
                        board[i][j+1] == turn.getLabel() &&
                        board[i][j+2] == turn.getLabel() &&
                        board[i][j+3] == turn.getLabel()
                )   return true;

                // horizontal check
                if ( i < board.length-3 &&
                        board[i][j] == turn.getLabel() &&
                        board[i+1][j] == turn.getLabel() &&
                        board[i+2][j] == turn.getLabel() &&
                        board[i+3][j] == turn.getLabel()
                ) return true;

                // diagonal checks
                if ( i < board.length - 3 && j < board[i].length - 3 &&
                        board[i][j] == turn.getLabel() &&
                        board[i+1][j+1] == turn.getLabel() &&
                        board[i+2][j+2] == turn.getLabel() &&
                        board[i+3][j+3] == turn.getLabel()
                ) return true;

                if ( i >= 3 && j < board[i].length - 3 &&
                        board[i][j] == turn.getLabel() &&
                        board[i-1][j+1] == turn.getLabel() &&
                        board[i-2][j+2] == turn.getLabel() &&
                        board[i-3][j+3] == turn.getLabel()
                ) return true;

            }

        return false;
    }

    public boolean isGameWon() { return isGameWon(ConnectFourGame.SYMBOLS.RED) || isGameWon(ConnectFourGame.SYMBOLS.YELLOW); }
    public static boolean isGameDraw(char[][] board) {
        for ( int i=0; i< board.length; i++)
            for ( int j=0; j<board[i].length; j++)
                if ( board[i][j] == ' ' )
                    return false;

        return true;
    }
    public boolean isGameDraw() {
        return isGameDraw( this.board );
    }

    public static String showBoard(char[][] board) {
        StringBuilder s = new StringBuilder();
        int width=0;

        for ( int i=board.length-1; i >= 0; i--) {
            s.append("| "+(i+1)+" | ");
            for (int j = 0; j < board[i].length; j++) {
                s.append( board[i][j] + " | ");
            }
            s.append((i+1)+" |"+'\n');
            if ( i == board.length-1 )
                width = s.length();
        }

        s.append("+===+");
        int i;
        for ( i = 0; i < width/4-3; i+=1 ) {
            s.append(" " + (i+1) + " |");
        }
        s.append( " " + (i+1) + " +===+");

        return s.toString();
    }

}
