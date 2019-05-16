import javafx.application.Platform;


public class MultiController extends Controller<MultiModel, MultiView> {
    ServiceLocator serviceLocator;

    public MultiController(MultiModel model, MultiView view) {
        super(model, view);


        view.getBackButton().setOnAction(event -> {
            TicTacToeGame.getMainProgram().startMainMenu();
            serviceLocator.getLogger().info("MultiPlayer Game has stopped");
        });
        view.getRestartButton().setOnAction(event -> {
            model.randomizePlayer();
            view.createBoard();
            addEvents();
            view.addToConsole("Game has been restarted");
            view.updateTurnLabel();
            view.removeLine();
        });
        addEvents();

        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi controller initialized");
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

                });
            }
        }
    }

    //controller part of win
    public boolean isWin(){
        boolean result = model.isWinLogic(view.getCells());
        if (result) {
            view.animateWin();
            view.addToConsole(model.getCurrentPlayer().getName() + " is the winner\nplease press restart to continue");
            for (int i = 0;i<3;i++){
                for(int j=0;j<3;j++){
                    view.getCells()[i][j].setDisable(true);
                }
            }
        }
        return result;
    }

    //controller part of draw
    public boolean isDraw() {

        boolean result = model.isDrawLogic(view.getCells());

        if (result){
            view.addToConsole("Game has ended in a draw\nplease press restart to continue");
            for (int i = 0;i<3;i++){
                for(int j=0;j<3;j++){
                    view.getCells()[i][j].setDisable(true);
                }
            }
        }
        return result;
    }


    //Without Platform.runLater the "crosses" sometimes don't get drawn fully, im not sure why.
    public void playerMove(int i, int j){
        Platform.runLater(()-> view.getCell(i,j).fire());
    }
}