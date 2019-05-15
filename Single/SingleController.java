import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class SingleController extends Controller<SingleModel, SingleView> {
    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);

        addEvents();
        if (model.isCpuTurn() == true && TicTacToeGame.getCpuPlayer())cpuTurnController();

        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single controller initialized");
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
                    view.drawSymbol(model.getCurrentPlayer().getSymbol(),view.getCell(index[0],index[1]));
                    if(isWin());
                    else if(isDraw());
                    else model.toggleCurrentPlayer();
                    view.updateTurnLabel();
                    if(model.isCpuTurn() && TicTacToeGame.getCpuPlayer())cpuTurnController();

                });
            }
        }
    }

    //controller part of win
    public boolean isWin(){
        boolean result = model.isWinLogic(view.getCells());

        if (result){
            Alert alertWin = new Alert(Alert.AlertType.CONFIRMATION);
            alertWin.setTitle("Game result");
            alertWin.setHeaderText("Winner");
            alertWin.setContentText(model.getCurrentPlayer().getName()+ " is the winner, please select how to continue");

            ButtonType goMainMenu = new ButtonType("MainMenu");
            ButtonType restart = new ButtonType("Restart");
            alertWin.getButtonTypes().removeAll(ButtonType.OK,ButtonType.CANCEL);
            alertWin.getButtonTypes().addAll(goMainMenu,restart);
            Optional<ButtonType> action = alertWin.showAndWait();

            if (action.get()== goMainMenu){
                TicTacToeGame.getMainProgram().startMainMenu();
                serviceLocator.getLogger().info("SinglePlayer Game has stopped");
            }

            else if (action.get() == restart){
                model.randomizePlayer();
                view.createBoard();
                addEvents();
                if(model.isCpuTurn() && TicTacToeGame.getCpuPlayer())cpuTurnController();
                view.addToConsole("Winner: "+ model.getCurrentPlayer().getName()+"\nGame has been restarted");
                view.updateTurnLabel();

            }
        }
        return result;
    }

    //controller part of draw
    public boolean isDraw() {

        boolean result = model.isDrawLogic(view.getCells());

        if (result){
            Alert alertDraw = new Alert(Alert.AlertType.CONFIRMATION);
            alertDraw.setTitle("Game result");
            alertDraw.setHeaderText("DRAW");
            alertDraw.setContentText("Game has ended in a draw, please select how to continue");
            ButtonType goMainMenu = new ButtonType("MainMenu");
            ButtonType restart = new ButtonType("Restart");
            alertDraw.getButtonTypes().removeAll(ButtonType.OK,ButtonType.CANCEL);
            alertDraw.getButtonTypes().addAll(goMainMenu,restart);
            Optional<ButtonType> action = alertDraw.showAndWait();

            if (action.get()== goMainMenu){
                TicTacToeGame.getMainProgram().startMainMenu();
            }

            else if (action.get() == restart){
                model.randomizePlayer();
                view.createBoard();
                addEvents();
                if(model.isCpuTurn() && TicTacToeGame.getCpuPlayer())cpuTurnController();
                view.addToConsole("Game resulted in a Draw\nGame has been restarted" );
                view.updateTurnLabel();

            }
        }
        return result;
    }

    //Controller for CPU tries to have some randomness in its logic, tries to counter the player
    //as best as possible, its not minmax but its working
    public void cpuTurnController() {
        char player = model.player1.getSymbol();
        char hal = model.player2.getSymbol();
        int[][] corners = {{0,0},{0,2},{2,2},{2,0}};
        int[][] cross = {{0,1},{1,0},{2,1},{1,2}};
        int[] foundCpuMove = new int[2];
        int[][] turnWin = model.checkTwo(view.getCells(), hal);
        int[][] turnDraw = model.checkTwo(view.getCells(), player);

        if (turnWin != null){
            foundCpuMove[0]= turnWin[0][0];
            foundCpuMove[1]= turnWin[0][1];
        }
        else if (turnDraw != null){
            foundCpuMove[0]= turnDraw[0][0];
            foundCpuMove[1]= turnDraw[0][1];
        }
        else if (view.getCell(1, 1).getSymbol() == ' ') {
            foundCpuMove[0]= 1;
            foundCpuMove[1] = 1;
        }
        else if (model.cpuFindMoveRandom(corners,view.getCells())!= null){
            int[][] foundCorners = model.cpuFindMoveRandom(corners,view.getCells());
            foundCpuMove[0] = foundCorners[0][0];
            foundCpuMove[1] = foundCorners[0][1];
            view.addToConsole(foundCorners[0][0]+" foundcorner Row "+ foundCorners[0][1]+ " foundcorner col");
        }
        else if (model.cpuFindMoveRandom(cross,view.getCells())!= null) {
            int[][] foundCross = model.cpuFindMoveRandom(cross, view.getCells());
            foundCpuMove[0] = foundCross[0][0];
            foundCpuMove[1] = foundCross[0][1];
            view.addToConsole(foundCross[0][0]+" foundcross Row "+ foundCross[0][1]+ " foundcross col");
        }

        cpuMove(foundCpuMove[0],foundCpuMove[1]);
        view.addToConsole(foundCpuMove[0]+" row "+foundCpuMove[1]+" col");

    }

    public void cpuMove(int i, int j){
        Platform.runLater(()-> view.getCell(i,j).fire());
    }
}