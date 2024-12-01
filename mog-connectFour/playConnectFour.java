// Source code by Stephen Adams
import java.lang.reflect.Constructor;
import java.util.Scanner;

public class playConnectFour {
    public static final int VERSION=202335001;

    // Initialize variables to store command line argument values
    static char difficulty = '\0';
    static String player1ClassName="HumanPlayer";
    static String player1name = "Hooman";
    static boolean player1auto = false;
    static String player2ClassName="RandomAI";
    static String player2name = "RandomAI";
    static boolean player2auto = true;

    public static void process_command_line(String[] args) {
        // Process command line arguments
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-?":
                case "-h":
                case "-help":
                case "--help":
                case "?":
                    System.out.println("Available arguments:");
                    System.out.println("-d <E/M/H>    : Set difficulty level (Easy/Medium/Hard)");
                    System.out.println("-p1 <class>   : Set player 1 class");
                    System.out.println("-p1name <name>: Set player 1 name");
                    System.out.println("-p1auto       : Set player 1 as automatic");
                    System.out.println("-p2 <class>   : Set player 2 class");
                    System.out.println("-p2name <name>: Set player 2 name");
                    System.out.println("-p2auto       : Set player 2 as automatic");
                    break;
                case "-d":
                    difficulty = Character.toUpperCase(args[i + 1].charAt(0));

                    // Validate difficulty level
                    if (!(difficulty == 'E' || difficulty == 'M' || difficulty == 'H')) {
                        difficulty = '\0';
                        System.out.println("Invalid difficulty level. Please choose 'E', 'M', or 'H'. Ignoring input.");
                    }

                    break;
                case "-p1":
                    player1ClassName = args[i+1];
                    break;
                case "-p1name":
                    player1name = args[i+1];
                    break;
                case "-p1auto":
                    player1auto = true;
                    break;
                case "-p2":
                    player2ClassName = args[i+1];
                    break;
                case "-p2name":
                    player2name = args[i+1];
                    break;
                case "-p2auto":
                    player2auto = true;
                    break;
                default:
                    System.out.println("Invalid argument: " + args[i]);
                    return;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println( "Welcome to Connect Four version " + VERSION );
        process_command_line( args );

        if ( difficulty == '\0' ) {
            System.out.println("What difficulty level would you like to try?");
            String msg = "Please pick one of E, M, or H for [E]asy, [M]edium, or [H]ard: ";
            do {
                System.out.print(msg);
                difficulty = Character.toUpperCase(in.next().charAt(0));
            } while (difficulty != 'E' && difficulty != 'M' && difficulty != 'H');
        }

        ConnectFourPlayer player1=null;
        ConnectFourPlayer player2=null;

        try {
            // Dynamically load the player classes
            Class<ConnectFourPlayer> player1Class = (Class<ConnectFourPlayer>) Class.forName(player1ClassName);
            Class<ConnectFourPlayer> player2Class = (Class<ConnectFourPlayer>) Class.forName(player2ClassName);

            // Create instances of the player classes
            Constructor<ConnectFourPlayer> player1Constructor = player1Class.getDeclaredConstructor(String.class, char.class, boolean.class);
            player1 = player1Constructor.newInstance( player1name, difficulty, player1auto );

            Constructor<ConnectFourPlayer> player2Constructor = player2Class.getDeclaredConstructor(String.class, char.class, boolean.class);
            player2 = player2Constructor.newInstance( player2name, difficulty, player2auto );

        } catch ( ReflectiveOperationException e) {
            e.printStackTrace();
        }

        ConnectFourGame game = new ConnectFourGame(difficulty, player1, player2);
        game.play();
    }
}