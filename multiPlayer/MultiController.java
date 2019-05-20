import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiController extends Controller<MultiModel, MultiView> {
    ServiceLocator serviceLocator;
    private int serverPort;
    private int clientServerPort;
    private String serverIP;
    private ServerSocket listener = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private boolean isTurn;
    private final String symbolToken = "sdkjfsdnfjksnfeskjfneskdsfsdfsfdj";
    private final String gameToken = "jksfdnjkfnsdfshnfsdhjbfsdbfjs";
    private final String doRestartToken = "fmfwekmfownefjfbwfnewjefnwifnewifw";
    private final String endToken = "sdsfenrjfkvlkwmekwmewjfnjekfnw";



    public MultiController(MultiModel model, MultiView view) {
        super(model, view);
        addEvents();
        view.blockBoard();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi controller initialized");
        serverIP = TicTacToeGame.serverIP;
        serverPort = TicTacToeGame.serverPort;
        clientServerPort = TicTacToeGame.clientServerPort;

        view.getChat().setOnAction(event -> chatSend());
        view.getSendMessage().setOnAction(event -> chatSend());

        view.getBackButton().setOnAction(event -> {
            if (socket != null) {
                out.println(endToken);
                out.flush();
                try {
                    socket.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            TicTacToeGame.getMainProgram().startMainMenu();
            serviceLocator.getLogger().info("MultiPlayer Game has stopped");
        });
        view.getRestartButton().setOnAction(event -> {
                out.println(doRestartToken);
                out.flush();
                resetGame();
        });
        startMulti();
    }

    public void startMulti(){
        if (TicTacToeGame.isServer) {
            model.setCurrentPlayerID(model.player1);
            model.console.addToConsole("SERVER: Waiting for Client on port: "+TicTacToeGame.serverPort);
            try {
                listener = new ServerSocket(55555, 10, null);
                Runnable r = ()-> {

                        try {
                            socket = listener.accept();
                            serviceLocator.getLogger().info("Connection established");
                            model.console.addToConsole("SERVER: Client found at IP-address: "+socket.getInetAddress().getAddress());
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            out = new PrintWriter(socket.getOutputStream());
                            out.println(symbolToken+model.player1.getSymbol()+model.player2.getSymbol());
                            out.flush();
                            view.unblockBoard();

                            while(true){
                                String input = in.readLine();
                                if (input.startsWith(gameToken)){
                                    String move;
                                    move=input.replace(gameToken,"");
                                    Integer.parseInt(move);

                                    char rowChar = move.charAt(0);
                                    int row = Character.getNumericValue(rowChar);

                                    char colChar = move.charAt(1);
                                    int col = Character.getNumericValue(colChar);
                                    oponnentMove(model.getCurrentPlayer().getSymbol(),row,col);
                                    view.unblockBoard();
                                }

                                else if (input.equals(doRestartToken)){
                                    Platform.runLater(()-> resetGame());
                                }

                                else if(input.equals(endToken)){
                                    model.console.addToConsole("Client has left the session");

                                    try {
                                        socket.close();
                                        model.console.addToConsole("Client has closed the connection");
                                        break;

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                else if(!input.startsWith(gameToken)){
                                    model.console.addToConsole("Client: "+input);
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            if(TicTacToeGame.isServer ) {
                                try{
                                    socket.close();
                                    listener.close();
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
            model.setCurrentPlayerID(model.player2);
            try {
                socket = new Socket(serverIP,clientServerPort);
                serviceLocator.getLogger().info("Connected to Server");
                model.console.addToConsole("CLIENT: Connected to Server");
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
                                oponnentMove(model.getCurrentPlayer().getSymbol(),row,col);
                                view.unblockBoard();
                            }

                            else if(input.startsWith(symbolToken)){
                                String symbol;
                                symbol = input.replace(symbolToken,"");
                                model.player1.setSymbol(symbol.charAt(0));
                                model.player2.setSymbol(symbol.charAt(1));
                                view.updatePlayerSymbols();
                            }

                            else if (input.equals(doRestartToken)){
                                Platform.runLater(()-> resetGame());
                            }

                            else if(input.equals(endToken)){
                                model.console.addToConsole("Server has left the session");

                                try {
                                    socket.close();
                                    model.console.addToConsole("Server has closed the connection");
                                    model.console.addToConsole("Initiate a new connection through MainMenu");
                                    serviceLocator.getLogger().info("Lost connection to Server");
                                    break;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            else if(!input.startsWith(gameToken)){
                                model.console.addToConsole("Server: "+input);
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
                                model.console.addToConsole("Lost connection to Server!");
                                model.console.addToConsole("Initiate a new connection through MainMenu");
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
                model.console.addToConsole("Could not establish connection to Server at given IP + Port, please go back to MainMenu and check settings");
            }

        }

    }

    public int[] getButtonPressed(Cell c){
        int[] index = new int[2];
        for (int i = 0;i<3;i++){
            for (int j = 0;j<3;j++){
                if (c == view.getCell(i,j)){
                    index[0] = i;
                    index[1] = j;
                }
            }
        }
        return index;
    }

    //add events to buttons
    private void addEvents(){

        for (int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                view.getCell(i,j).setOnAction(event -> {
                    int[] index = getButtonPressed((Cell) event.getSource());
                    view.drawSymbol(model.getCurrentPlayer().getSymbol(), view.getCell(index[0], index[1]));
                    out.println(gameToken + index[0] + index[1]);
                    out.flush();
                    view.blockBoard();
                    isWin();
                    isDraw();
                    model.toggleCurrentPlayer();
                    view.updateTurnLabel();
                });
            }
        }
    }

    public void oponnentMove(char c, int i, int j){
        Platform.runLater(()-> {
            view.drawSymbol(c,view.getCell(i,j));
            view.getCell(i,j).setDisable(true);
            isWin();
            isDraw();
            model.toggleCurrentPlayer();
            view.updateTurnLabel();

        });
    }

    //controller part of win
    public void isWin(){
        boolean result = model.isWinLogic(view.getCells());
        if (result) {
            view.animateWin();
            model.console.addToConsole("GAME: "+model.getCurrentPlayer().getName() + " is the winner\nplease press restart to continue");
            for (int i = 0;i<3;i++){
                for(int j=0;j<3;j++){
                    view.getCells()[i][j].setDisable(true);
                }
            }
        }
    }

    //controller part of draw
    public void isDraw() {

        boolean result = model.isDrawLogic(view.getCells());

        if (result){
            model.console.addToConsole("GAME: Game has ended in a draw\nplease press restart to continue");
            for (int i = 0;i<3;i++){
                for(int j=0;j<3;j++){
                    view.getCells()[i][j].setDisable(true);
                }
            }
        }
    }

    public void playerMove(int i, int j){
        Platform.runLater(()-> view.getCell(i,j).fire());
    }

    public void chatSend() {
        String s = view.getChat().getText();
        if(s != null && !s.isEmpty()){
            model.console.addToConsole(model.getCurrentPlayerID().getName()+": "+s);
            out.println(s);
            out.flush();
            view.getChat().clear();
        }
    }

    public void resetGame(){
        view.resetBoard();
        addEvents();
        model.console.addToConsole("GAME: Game has been restarted");
        view.removeLine();
        view.updateTurnLabel();
        if(model.getCurrentPlayer()== model.getCurrentPlayerID()){
            view.unblockBoard();
        }
        else view.blockBoard();
    }
}

//Todo Let AI take over and implement autoplay