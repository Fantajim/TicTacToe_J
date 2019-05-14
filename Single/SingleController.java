import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SingleController extends Controller<SingleModel, SingleView> {
    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);

        addEvents();
        if (model.isCpuTurn() == true){
            cpuTurnController();
        }

        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single controller initialized");
    }

    public int[] getButtonPressed(Cell c){
        int index[] = new int [2];
        for (int i = 0;i<3;i++){
            for (int j = 0;j<3;j++){
                if (c == view.getCell(i,j)){
                    index[0] = i;
                    index[1] = j;
                }
            }
        }
        model.setLastTurn(index);
        return index;
    }

    //add events to buttons
    private void addEvents(){

        for (int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                view.getCell(i,j).setOnAction(event -> {
                    boolean toggle;
                    int[] index = getButtonPressed((Cell) event.getSource());
                    view.drawSymbol(model.getCurrentPlayer().getSymbol(),view.getCell(index[0],index[1]));
                    if(isWin())model.resetCpuTurn();
                    else if(isDraw())model.resetCpuTurn();
                    else model.toggleCurrentPlayer();
                    view.updateTurnLabel();
                    if(model.isCpuTurn())cpuTurnController();

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
                if (model.isCpuTurn()) cpuTurnController();
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
                if (model.isCpuTurn()) cpuTurnController();
                view.addToConsole("Game resulted in a Draw\nGame has been restarted" );
                view.updateTurnLabel();

            }
        }
        return result;
    }

    //Controller for CPU tries to have some randomness in its logic, tries to counter the player
    //as best as possible, to be honest it's a mess, i'm very very sure there are much much more elegant ways to do this..
    public void cpuTurnController() {
        int[] temp;
        int[] lastTurn = model.getLastTurn();
        char player = model.player1.getSymbol();
        char hal = model.player2.getSymbol();

        switch(model.getCpuTurn()){

            case 0: temp = model.cpuFirstTurn();
                    cpuMove(temp[0],temp[1]);
                    break;

            case 1: if (view.getCell(1,1).getSymbol() == ' ')cpuMove(1,1);
                    else {
                    temp = model.cpuGetCorner();
                    cpuMove(temp[0],temp[1]);
                    }
                    break;

                    //TODO player has moved to 1,1
                    //CPU made move 0
            case 2:
                if(lastTurn[0] == 1 && lastTurn[1] == 1) {
                    if (model.getCpuLastTurn()[0] == 0 && model.getCpuLastTurn()[1] == 0) cpuMove(2, 2);
                    else if (model.getCpuLastTurn()[0] == 0 && model.getCpuLastTurn()[1] == 2) cpuMove(2, 0);
                    else if (model.getCpuLastTurn()[0] == 2 && model.getCpuLastTurn()[1] == 2) cpuMove(0, 0);
                    else if (model.getCpuLastTurn()[0] == 2 && model.getCpuLastTurn()[1] == 0) cpuMove(0, 2);
                    if (model.getCpuLastTurn()[0] == 1 && model.getCpuLastTurn()[1] == 1) {
                        if (model.isCross()) {
                            if (lastTurn[0] == 1 && lastTurn[1] == 0) {
                                int[][] possibleTurns = {{0, 2}, {2, 2}};
                                int[][] foundMoves = model.cpuFindMoveRandom(possibleTurns, view.getCells());
                                cpuMove(foundMoves[0][0], foundMoves[0][1]);
                            } else if (lastTurn[0] == 0 && lastTurn[1] == 1) {
                                int[][] possibleTurns = {{0, 2}, {2, 2}};
                                int[][] foundMoves = model.cpuFindMoveRandom(possibleTurns, view.getCells());
                                cpuMove(foundMoves[0][0], foundMoves[0][1]);
                            } else if (lastTurn[0] == 1 && lastTurn[1] == 2) {
                                int[][] possibleTurns = {{0, 0}, {0, 2}};
                                int[][] foundMoves = model.cpuFindMoveRandom(possibleTurns, view.getCells());
                                cpuMove(foundMoves[0][0], foundMoves[0][1]);
                            } else if (lastTurn[0] == 2 && lastTurn[1] == 1) {
                                int[][] possibleTurns = {{0, 0}, {0, 2}};
                                int[][] foundMoves = model.cpuFindMoveRandom(possibleTurns, view.getCells());
                                cpuMove(foundMoves[0][0], foundMoves[0][1]);
                            }
                        } else if (lastTurn[0] == 0 && lastTurn[1] == 0) {
                            cpuMove(2, 2);
                        } else if (lastTurn[0] == 0 && lastTurn[1] == 2) {
                            cpuMove(2, 0);
                        } else if (lastTurn[0] == 2 && lastTurn[1] == 2) {
                            cpuMove(0, 0);
                        } else if (lastTurn[0] == 2 && lastTurn[1] == 0) {
                            cpuMove(0, 2);
                        }
                    }
                    break;
                }
//Todo case 2 Player has not clicked middle cell
                    //CPU made move 1
                    case 3: if(lastTurn[0] == 0 && lastTurn[1] == 2 ){ //corners
                        int [][] possibleTurns = {{0,1},{0,2}};
                        int [][] foundMoves = model.cpuFindMoveRandom(possibleTurns,view.getCells());
                        cpuMove(foundMoves[0][0],foundMoves[0][1]); }
                    else if(lastTurn[0] == 0 && lastTurn[1] == 0)cpuMove(0,0);
                    else if(lastTurn[0] == 2 && lastTurn[1] == 2)cpuMove(2,2);

                    else if(lastTurn[0] == 1 && lastTurn[1] == 0){ //cross
                        int [][] possibleTurns = {{0,2},{2,2}};
                        int [][] foundMoves = model.cpuFindMoveRandom(possibleTurns,view.getCells());
                        cpuMove(foundMoves[0][0],foundMoves[0][1]);
                    }
                    else if (lastTurn[0] == 0 && lastTurn[1] == 1 ) {
                        int [][] possibleTurns = {{2,0},{2,2}};
                        int [][] foundMoves = model.cpuFindMoveRandom(possibleTurns,view.getCells());
                        cpuMove(foundMoves[0][0],foundMoves[0][1]);
                    }
                    else if (lastTurn[0] == 1 && lastTurn[1] == 2){
                        int [][] possibleTurns = {{0,0},{0,2}};
                        int [][] foundMoves = model.cpuFindMoveRandom(possibleTurns,view.getCells());
                        cpuMove(foundMoves[0][0],foundMoves[0][1]);
                    }
                    else if (lastTurn[0] == 2 && lastTurn[1] == 1) {
                        int [][] possibleTurns = {{0,0},{0,2}};
                        int [][] foundMoves = model.cpuFindMoveRandom(possibleTurns,view.getCells());
                        cpuMove(foundMoves[0][0],foundMoves[0][1]);
                    }
                        break;

                    //CPU made move 2
                    case 4: int[][] foundMoves = model.checkTwo(view.getCells(),player);
                        if (foundMoves == null){
                            if(lastTurn[0] == 0 && lastTurn[1] == 1)cpuMove(2,2);
                            else if (lastTurn[0] == 1 && lastTurn[1] == 2)cpuMove(2,0);
                            else if (lastTurn[0] == 2 && lastTurn[1] == 1)cpuMove(0,0);
                            else if (lastTurn[0] == 1 && lastTurn[1] == 0)cpuMove(0,2);
                        }
                        break;

                }
            }

    public void cpuMove(int i, int j){
        model.setCpuLastTurn(i,j);
        Platform.runLater(()-> view.getCell(i,j).fire());
    }
}