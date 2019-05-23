import javafx.application.Platform;

public class MultiController extends Controller<MultiModel, MultiView> {
    ServiceLocator serviceLocator;

    public MultiController(MultiModel model, MultiView view) {
        super(model, view);
        addEvents();
        view.blockBoard();
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi controller initialized");


        view.getChat().setOnAction(event -> chatSend());
        view.getSendMessage().setOnAction(event -> chatSend());

        view.getBackButton().setOnAction(event -> {
            if (model.getSocket() != null) {
                model.getOut().println(model.getEndToken());
                model.getOut().flush();
                try {
                    model.getSocket().close();
                    model.stopMulti();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            TicTacToeGame.getMainProgram().startMainMenu();
            serviceLocator.getLogger().info("MultiPlayer Game has stopped");
        });
        view.getRestartButton().setOnAction(event -> {
                model.getOut().println(model.getDoRestartToken());
                model.getOut().flush();
                resetGame();
        });
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
                    model.getOut().println(model.getGameToken() + index[0] + index[1]);
                    model.getOut().flush();
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
            model.getOut().println(s);
            model.getOut().flush();
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
