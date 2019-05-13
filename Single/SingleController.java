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
                    if(isWin())model.setTotalTurns();
                    else if(isDraw())model.setTotalTurns();
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

    public void cpuTurnController() {
        int[] turn;
        switch(model.getTotalTurns()){

            case 0: turn = model.cpuFirstTurn();
                    model.setCpuLastTurn(turn[0],turn[1]);
                    cpuMove(turn[0],turn[1]);
                    break;

            case 1: if (view.getCell(1,1).getSymbol() == ' '){
                    model.setCpuLastTurn(1,1);
                    cpuMove(1,1);
            }
                    else {
                    turn = model.cpuGetCorner();
                    model.setCpuLastTurn(turn[0],turn[1]);
                    cpuMove(turn[0],turn[1]);
                    }
                    break;
            case 2:  turn = model.cpuFindMove(view.getCells());

        }
    }

    public void cpuMove(int i, int j){
        Platform.runLater(()-> { view.getCell(i,j).fire(); });
    }
}