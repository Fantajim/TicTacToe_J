import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MultiModel extends Model {

    ServiceLocator serviceLocator;
    Console console;
    public Player player1;
    public Player player2;
    private Player currentPlayer;
    private boolean isTurn;
    private char symbol1;
    private char symbol2;
    private Cell[] winnerCombo = new Cell[3];
    private int serverPort;
    private String serverIP;
    private ServerSocket listener = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private final String gameToken = "jksfdnjkfnsdfshnfsdhjbfsdbfjs";

    public MultiModel() {
        randomizePlayerSymbol();
        player1 = new Player(symbol1, "Player 1");
        player2 = new Player(symbol2, "Player 2");
        console = new Console("MultiPlayer");
        randomizePlayer();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi model initialized");
        serverIP = TicTacToeGame.serverIP;
        serverPort = TicTacToeGame.serverPort;

        if (TicTacToeGame.isServer) {
            console.addToConsole("Waiting for Client ...");
            try {
                listener = new ServerSocket(55555, 10, null);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socket = listener.accept();
                            serviceLocator.getLogger().info("Connection established");
                            console.addToConsole("Connection established");
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(socket.getOutputStream());

                            while(true){
                                String input = in.readLine();
                                if (input.startsWith(gameToken)&&(isTurn)){
                                    Scanner scanner = new Scanner(input);
                                    int row = scanner.nextInt();
                                    int col = scanner.nextInt();
                                }
                                else if(!input.startsWith(gameToken)){
                                    console.addToConsole(input);
                                    //Todo make Textfield beneath console
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                };
                Thread t = new Thread(r, "ServerSocket");
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else {
            try {
                socket = new Socket(serverIP,serverPort);
                serviceLocator.getLogger().info("Connected");
                console.addToConsole("Connected");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());


                Runnable r = () -> {
                    try {
                        while (true) {
                         String input = in.readLine();
                         if (input.startsWith(gameToken)&&(isTurn)){
                            Scanner scanner = new Scanner(input);
                            int row = scanner.nextInt();
                            int col = scanner.nextInt();
                         }
                         else if(!input.startsWith(gameToken)){
                            console.addToConsole(input);
                         }

                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                };
                Thread t = new Thread(r,"Socket");
                        t.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

   //Method for toggling currentPlayer
  public void toggleCurrentPlayer(){
      if (getCurrentPlayer()==player1){
          setCurrentPlayer(player2);
      }
      else { setCurrentPlayer(player1); }
  }

    //Method for randomizing current Player
    public void randomizePlayer() {
        double turn = Math.random();
        if (turn <= 0.5) setCurrentPlayer(player1);
        else setCurrentPlayer(player2);
    }


    //Method for randomizing PlayerSymbol
    public void randomizePlayerSymbol(){
        double temp = Math.random();
        if (temp <= 0.5) {
            symbol1 = 'X';
            symbol2 = 'O';
        }
        else {
            symbol1 = 'O';
            symbol2 = 'X';
        }
    }

    //Logic part of win
    public boolean isWinLogic(Cell[][] cells) {
        boolean result = false;

        //TODO make loops instead of being a caveman
        //check if three cells are equal
        if (cells[0][0].getSymbol() == cells[0][1].getSymbol() && cells[0][0].getSymbol() == cells[0][2].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; // first row horizontal
            winnerCombo[0] = cells[0][0];
            winnerCombo[1] = cells[0][1];
            winnerCombo[2] = cells[0][2];
        } else if (cells[1][0].getSymbol() == cells[1][1].getSymbol() && cells[1][0].getSymbol() == cells[1][2].getSymbol() && cells[1][0].getSymbol() != ' ') {
            result = true; // 2nd row horizontal
            winnerCombo[0] = cells[1][0];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[1][2];
        } else if (cells[2][0].getSymbol() == cells[2][1].getSymbol() && cells[2][0].getSymbol() == cells[2][2].getSymbol() && cells[2][0].getSymbol() != ' ') {
            result = true; // 3rd row horizontal
            winnerCombo[0] = cells[2][0];
            winnerCombo[1] = cells[2][1];
            winnerCombo[2] = cells[2][2];
        } else if (cells[0][0].getSymbol() == cells[1][0].getSymbol() && cells[0][0].getSymbol() == cells[2][0].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; //first row vertical
            winnerCombo[0] = cells[0][0];
            winnerCombo[1] = cells[1][0];
            winnerCombo[2] = cells[2][0];
        } else if (cells[0][1].getSymbol() == cells[1][1].getSymbol() && cells[0][1].getSymbol() == cells[2][1].getSymbol() && cells[0][1].getSymbol() != ' ') {
            result = true; // 2nd row vertical
            winnerCombo[0] = cells[0][1];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[2][1];
        } else if (cells[0][2].getSymbol() == cells[1][2].getSymbol() && cells[0][2].getSymbol() == cells[2][2].getSymbol() && cells[0][2].getSymbol() != ' ') {
            result = true; //3rd row vertical
            winnerCombo[0] = cells[0][2];
            winnerCombo[1] = cells[1][2];
            winnerCombo[2] = cells[2][2];
        } else if (cells[0][0].getSymbol() == cells[1][1].getSymbol() && cells[0][0].getSymbol() == cells[2][2].getSymbol() && cells[0][0].getSymbol() != ' ') {
            result = true; //diagonal
            winnerCombo[0] = cells[0][0];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[2][2];
        } else if (cells[0][2].getSymbol() == cells[1][1].getSymbol() && cells[0][2].getSymbol() == cells[2][0].getSymbol() && cells[0][2].getSymbol() != ' ') {
            result = true; //diagonal
            winnerCombo[0] = cells[0][2];
            winnerCombo[1] = cells[1][1];
            winnerCombo[2] = cells[2][0];
        }
        return result;
    }

    //Logic part of draw to check if there's an empty cell left
    public boolean isDrawLogic(Cell[][] cells) {
        boolean result = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j].getSymbol() == ' ') {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public Player getCurrentPlayer() { return currentPlayer; }

    public void setCurrentPlayer(Player player){ currentPlayer = player; }

    public Cell[] getWinnerCombo(){ return winnerCombo; }

}
