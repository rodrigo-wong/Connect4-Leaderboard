public abstract class ConnectFourPlayer {
    private String playerName;
    private int depth; // how far to search down the tree
    private ConnectFourNode cfRoot; // root of the Connect Four state space tree
    protected final boolean automaticPlayer; // is the player automated?

    public ConnectFourPlayer(String playerName, char difficulty, boolean automaticPlayer) {
        this.playerName = playerName;
        this.automaticPlayer = automaticPlayer;
        setDifficulty( difficulty );
    }

    // Getter for player name

    public String getPlayerName() {
        return playerName;
    }

    // Setter for player name
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public boolean setDifficulty( char difficulty ) {
        switch ( Character.toUpperCase( difficulty ) ) {
            case 'E' : depth = 2; break;
            case 'M' : depth = 4; break;
            case 'H' : depth = 6; break;
            default: return false;
        }
        return true;
    }


    // Abstract method for making a move in the Connect Four game
    public abstract void makeMove(ConnectFourGame game);

    // Additional methods or properties specific to Connect Four players can be added here
}
