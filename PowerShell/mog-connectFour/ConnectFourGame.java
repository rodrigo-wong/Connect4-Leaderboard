// Source code by Stephen Adams
// Version 202235000

import java.util.Scanner;

public class ConnectFourGame {
    public enum SYMBOLS {
        RED('R'), YELLOW('Y');

        private char label;

        private SYMBOLS( char label ) {
            this.label = label;
        }

        SYMBOLS() {
            label = ' ';
        }

        public char getLabel() {return label;}
    };
    private final char[][] board;
    private final int ROWS;
    private final int COLS;
    SYMBOLS currentTurn = SYMBOLS.RED;

    private boolean moved = false;
    ConnectFourPlayer player1, player2;
    Scanner in;

    public ConnectFourGame( char difficulty, ConnectFourPlayer player1, ConnectFourPlayer player2 ) {
        ROWS = 6;
        COLS = 7;
        board = new char[ROWS][COLS];
        for ( int i=0; i<ROWS; i++)
            for ( int j=0; j<COLS; j++)
                board[i][j] = ' ';

        this.player1 = player1;
        this.player2 = player2;

        if ( !( player1.automaticPlayer && player2.automaticPlayer ) )
            in = new Scanner( System.in );
    }

    public void play() {
        int move;
        boolean done;

        System.out.println(showBoard());
        while (true) {
            done = false;
            ConnectFourPlayer currentPlayer;
            if (currentTurn == ConnectFourGame.SYMBOLS.RED) {
                currentPlayer = player1;
            } else {
                currentPlayer = player2;
            }

            System.out.println("Current player is " + currentPlayer.getPlayerName() + " (" + currentTurn + ")");
            currentPlayer.makeMove( this );
            System.out.println();
            System.out.println(showBoard());

            System.out.println();
            if (isGameOver()) {
                SYMBOLS chip = winner();
                if (chip != null) {
                    switch ( chip ) {
                        case RED -> System.out.println(player1.getPlayerName() + " (" + chip + ") wins!");
                        case YELLOW -> System.out.println(player2.getPlayerName() + " (" + chip + ") wins!");
                    }
                } else
                    System.out.println("It's a draw!");
                break;
            }
            nextTurn();
        }
    }

    public void nextTurn() {
        currentTurn = currentTurn == SYMBOLS.RED ? SYMBOLS.YELLOW : SYMBOLS.RED;
        moved = false;
    }

    public boolean dropPiece( int col ) {
        // A wrapper to control turn order.
        if ( moved )
            return false;

        moved = ConnectFourNode.dropPiece( board, col, currentTurn );
        return moved;
    }

    public SYMBOLS winner() {
        if ( isGameWon(SYMBOLS.RED))
            return SYMBOLS.RED;

        if ( isGameWon(SYMBOLS.YELLOW))
            return SYMBOLS.YELLOW;

        return null;
    }

    public String showBoard() {
        return ConnectFourNode.showBoard( board );
    }

    public boolean isGameWon( ) {
        return ConnectFourNode.isGameWon( board, SYMBOLS.RED ) || ConnectFourNode.isGameWon( board, SYMBOLS.YELLOW );
    }
    public boolean isGameWon( SYMBOLS turn ) {
        return ConnectFourNode.isGameWon( board, turn );
    }

    public boolean isGameDraw() {
        return ConnectFourNode.isGameDraw( board );
    }

    public boolean isGameOver() {
        return isGameWon() || isGameDraw();
    }

    public char[][] getBoard() {
        return ConnectFourNode.copyBoard( board );
    }
}
