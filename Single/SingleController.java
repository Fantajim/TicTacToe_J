import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SingleController extends Controller<SingleModel, SingleView> {

    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);
        String result ="";

        addEvents();




        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single controller initialized");
    }

    public void buttonClick(){

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

    public void isDraw() {

        boolean result = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (view.getCell(i,j).getIdent() == ' ') {
                    result = false;
                    break;
                }
                else result = true;
            }
        }

        if (result==true){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game result");
            alert.setHeaderText("DRAW");
            alert.setContentText("Game has ended in a draw, please select how to continue");

            ButtonType goMainMenu = new ButtonType("MainMenu");
            ButtonType restart = new ButtonType("Restart");
            alert.getButtonTypes().removeAll(ButtonType.OK,ButtonType.CANCEL);
            alert.getButtonTypes().addAll(goMainMenu,restart);
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get()== goMainMenu){

                TicTacToeGame.getMainProgram().startMainMenu();

                    }
            else if (action.get() == restart){
                view.addToConsole("Game has been restarted");
                view.createBoard();
                addEvents();
            }




        }
    }

    public void addEvents(){

        for (int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                view.getCell(i,j).setOnMouseClicked(event -> {
                    int index[] = getButtonPressed((Cell)event.getSource());
                    view.getCell(index[0],index[1]).setIdent(model.getCurrentPlayer());
                    model.toggleCurrentPlayer();
                    isDraw();
                });

            }
        }

    }

    public boolean isWin(){
        boolean result = false;
        Cell[][] temp = view.getCells();
return result;
    }


}