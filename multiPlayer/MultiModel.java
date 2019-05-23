import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MultiModel extends Model {

    // I know this is not good
    private MultiView view;
    private MultiController controller;
    //
    ServiceLocator serviceLocator;
    Console console;
    public Player player1;
    public Player player2;
    private Player currentPlayer;
    private Player currentPlayerID;
    private char symbol1;
    private char symbol2;
    private Cell[] winnerCombo = new Cell[3];
    private final String symbolToken = "sdkjfsdnsdlsflfkrkrkrfjksnfeskjfneskdpolkhsfsdfsfdj";
    private final String gameToken = "jksfdnjkfnsdfshreofreifejferjfemfenfsdhjbfsdbfjs";
    private final String doRestartToken = "fmfwekmfownefjfbwfdsfmkldsfmlsnewjefnwifnewifw";
    private final String endToken = "sdsfenrjfkvlkwmekwmewjdfuitutgnfnjklmdfmkslfmelkekfnw";
    private int serverPort;
    private int clientServerPort;
    private String serverIP;
    private ServerSocket listener = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private boolean isTurn;
    private volatile boolean stop = false;

    public MultiModel() {
        randomizePlayerSymbol();
        player1 = new Player(symbol1, "Server");
        player2 = new Player(symbol2, "Client");
        console = new Console("MultiPlayer");
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi model initialized");
        currentPlayer = player1;
        serverIP = TicTacToeGame.serverIP;
        serverPort = TicTacToeGame.serverPort;
        clientServerPort = TicTacToeGame.clientServerPort;

        startMulti();
        shutdownHook();

    }

    public void startMulti(){
        if (TicTacToeGame.isServer) {
            setCurrentPlayerID(player1);
            console.addToConsole("SERVER: Waiting for Client on port: "+TicTacToeGame.serverPort);
            try {
                listener = new ServerSocket(serverPort, 10, null);
                Runnable r = ()-> {

                    try {
                        socket = listener.accept();
                        serviceLocator.getLogger().info("Connection established");
                        console.addToConsole("SERVER: Client found at IP-address: "+socket.getInetAddress().toString());
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream());
                        out.println(symbolToken+player1.getSymbol()+player2.getSymbol());
                        out.flush();
                        view.unblockBoard();

                        while(!stop){
                            String input = in.readLine();
                            if (input.startsWith(gameToken)){
                                String move;
                                move=input.replace(gameToken,"");
                                Integer.parseInt(move);

                                char rowChar = move.charAt(0);
                                int row = Character.getNumericValue(rowChar);

                                char colChar = move.charAt(1);
                                int col = Character.getNumericValue(colChar);
                                controller.oponnentMove(getCurrentPlayer().getSymbol(),row,col);
                                view.unblockBoard();
                            }

                            else if (input.equals(doRestartToken)){
                                Platform.runLater(()-> controller.resetGame());
                            }

                            else if(input.equals(endToken)){
                                console.addToConsole("Client has left the session");

                                try {
                                    socket.close();
                                    console.addToConsole("Client has closed the connection");
                                    break;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            else if(!input.startsWith(gameToken)){
                                console.addToConsole("Client: "+input);
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        if(TicTacToeGame.isServer) {
                            try{
                                if(socket != null && !socket.isClosed())socket.close();
                                if(listener!= null && !listener.isClosed()) listener.close();
                                in = null;
                                out = null;
                                socket = null;
                                listener = null;
                                ServiceLocator.getServiceLocator().getLogger().info("Closed connection to Client");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            startMulti();
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
            setCurrentPlayerID(player2);
            try {
                socket = new Socket(serverIP,clientServerPort);
                serviceLocator.getLogger().info("Connected to Server");
                console.addToConsole("CLIENT: Connected to Server");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());


                Runnable r = () -> {
                    try {
                        while (true) {
                            String input = in.readLine();
                            if (input.startsWith(gameToken)){
                                String move;
                                move=input.replace(gameToken,"");
                                Integer.parseInt(move);

                                char rowChar = move.charAt(0);
                                int row = Character.getNumericValue(rowChar);

                                char colChar = move.charAt(1);
                                int col = Character.getNumericValue(colChar);
                                controller.oponnentMove(getCurrentPlayer().getSymbol(),row,col);
                                view.unblockBoard();
                            }

                            else if(input.startsWith(symbolToken)){
                                String symbol;
                                symbol = input.replace(symbolToken,"");
                                player1.setSymbol(symbol.charAt(0));
                                player2.setSymbol(symbol.charAt(1));
                                view.updatePlayerSymbols();
                            }

                            else if (input.equals(doRestartToken)){
                                Platform.runLater(()-> controller.resetGame());
                            }

                            else if(input.equals(endToken)){
                                console.addToConsole("Server has left the session");

                                try {
                                    socket.close();
                                    console.addToConsole("Server has closed the connection");
                                    console.addToConsole("Initiate a new connection through MainMenu");
                                    serviceLocator.getLogger().info("Lost connection to Server");
                                    break;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            else if(!input.startsWith(gameToken)){
                                console.addToConsole("Server: "+input);
                            }

                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                    finally {
                        if(!TicTacToeGame.isServer) {
                            try{
                                socket.close();
                                in = null;
                                out = null;
                                socket = null;
                                console.addToConsole("Lost connection to Server!");
                                console.addToConsole("Initiate a new connection through MainMenu");
                                ServiceLocator.getServiceLocator().getLogger().info("Lost connection to Server");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                Thread t = new Thread(r,"Socket");
                t.start();

            } catch (Exception e) {
                console.addToConsole("Could not establish connection to Server at given IP + Port, please go back to MainMenu and check settings");
            }

        }

    }

    public void stopMulti(){
        stop = true;
        if (listener != null) {
            try {
                listener.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        listener = null;
    }

    public void shutdownHook(){
        stop = true;
        Runtime.getRuntime().addShutdownHook(
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                    try {
                        if(listener!=null)listener.close();
                        in = null;
                        out = null;
                        socket = null;
                        listener = null;
                        System.exit(1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   }
               }));
        }



        //Method for toggling currentPlayer
        public void toggleCurrentPlayer () {
            if (getCurrentPlayer() == player1) {
                setCurrentPlayer(player2);
            } else {
                setCurrentPlayer(player1);
            }
        }

        //Method for randomizing PlayerSymbol
        public void randomizePlayerSymbol () {
            double temp = Math.random();
            if (temp <= 0.5) {
                symbol1 = 'X';
                symbol2 = 'O';
            } else {
                symbol1 = 'O';
                symbol2 = 'X';
            }
        }

        //Logic part of win
        public boolean isWinLogic (Cell[][]cells){
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
        public boolean isDrawLogic (Cell[][]cells){
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

        public Player getCurrentPlayer () {
            return currentPlayer;
        }

        public Player getCurrentPlayerID () {
            return currentPlayerID;
        }

        public void setCurrentPlayerID(Player p) {
            this.currentPlayerID = p;
        }

        public void setCurrentPlayer (Player player){
            currentPlayer = player;
        }

        public Cell[] getWinnerCombo () {
            return winnerCombo;
        }

        public Socket getSocket() {
            return socket;
        }

        public PrintWriter getOut() {
            return out;
        }

        public String getGameToken() {
            return gameToken;
        }

        public String getDoRestartToken() {
            return doRestartToken;
        }

        public String getEndToken() {
            return endToken;
        }

        public BufferedReader getIn() {
         return in;
        }
}