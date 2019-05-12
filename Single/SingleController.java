import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SingleController extends Controller<SingleModel, SingleView> {

    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);
        addEvents();

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
        return index;
    }

    //controller part of draw
    public void isDraw() {

        boolean result = model.isDrawLogic(view.getCells());

        if (result==true){
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
                view.addToConsole("Game resulted in a Draw\nGame has been restarted" );
                model.randomizePlayer();
                view.createBoard();
                addEvents();
            }
        }
    }

    //add events to buttons
    public void addEvents(){

        for (int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                view.getCell(i,j).setOnMouseClicked(event -> {
                    int index[] = getButtonPressed((Cell)event.getSource());
                    view.getCell(index[0],index[1]).setIdent(model.getCurrentPlayer());
                    isWin();
                    isDraw();
                    model.toggleCurrentPlayer();

                });
            }
        }
    }


    //controller par of win
    public void isWin(){
        boolean result = model.isWinLogic(view.getCells());

        if (result == true){
            Alert alertWin = new Alert(Alert.AlertType.CONFIRMATION);
            alertWin.setTitle("Game result");
            alertWin.setHeaderText("Winner");
            alertWin.setContentText("Game has ended in a winner, please select how to continue");

            ButtonType goMainMenu = new ButtonType("MainMenu");
            ButtonType restart = new ButtonType("Restart");
            alertWin.getButtonTypes().removeAll(ButtonType.OK,ButtonType.CANCEL);
            alertWin.getButtonTypes().addAll(goMainMenu,restart);
            Optional<ButtonType> action = alertWin.showAndWait();

            if (action.get()== goMainMenu){
                TicTacToeGame.getMainProgram().startMainMenu();
            }

            else if (action.get() == restart){
                view.addToConsole("Winner: "+ model.getCurrentPlayer()+"\nGame has been restarted");
                model.randomizePlayer();
                view.createBoard();
                addEvents();
            }
        }
    }
}