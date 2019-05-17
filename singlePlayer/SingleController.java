import javafx.application.Platform;


public class SingleController extends Controller<SingleModel, SingleView> {
    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);


        view.getBackButton().setOnAction(event -> {
            TicTacToeGame.getMainProgram().startMainMenu();
            serviceLocator.getLogger().info("SinglePlayer Game has stopped");
        });
        view.getRestartButton().setOnAction(event -> {
            model.randomizePlayer();
            view.resetBoard();
            addEvents();
            if(model.isCpuTurn() && TicTacToeGame.getCpuPlayer())cpuTurnController();
            view.addToConsole("Game has been restarted");
            view.updateTurnLabel();
            view.removeLine();

        });
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

    //Controller for CPU tries to have some randomness in its logic, tries to counter the player
    //as best as possible, its not minmax but its working.
    //Difficulty is implemented by giving the cpuFindRandom method more possible cells to choose form
    public void cpuTurnController() {
        char player = model.player1.getSymbol();
        char hal = model.player2.getSymbol();
        int[][] cornersAndMiddle = {{0,0},{0,2},{2,2},{2,0},{1,1}};
        int[][] cross = {{0,1},{1,0},{2,1},{1,2}};
        int[][] easy = {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};
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
        else if (view.getCell(1, 1).getSymbol() == ' ' && TicTacToeGame.getCpuDifficulty().equals("hard")) {
            foundCpuMove[0]= 1;
            foundCpuMove[1] = 1;
        }
        else if (model.cpuFindMoveRandom(cornersAndMiddle,view.getCells())!= null && TicTacToeGame.getCpuDifficulty() != "easy"){
            int[][] foundCorners = model.cpuFindMoveRandom(cornersAndMiddle,view.getCells());
            foundCpuMove[0] = foundCorners[0][0];
            foundCpuMove[1] = foundCorners[0][1];
        }
        else if (model.cpuFindMoveRandom(cross,view.getCells())!= null && TicTacToeGame.getCpuDifficulty() != "easy") {
            int[][] foundCross = model.cpuFindMoveRandom(cross, view.getCells());
            foundCpuMove[0] = foundCross[0][0];
            foundCpuMove[1] = foundCross[0][1];
        }
        else if (model.cpuFindMoveRandom(cross,view.getCells())!= null && TicTacToeGame.getCpuDifficulty().equals("easy")){
            int[][] foundEasy = model.cpuFindMoveRandom(easy,view.getCells());
            foundCpuMove[0] = foundEasy[0][0];
            foundCpuMove[1] = foundEasy[0][1];
        }

        cpuMove(foundCpuMove[0],foundCpuMove[1]);

    }
    //Without Platform.runLater the "crosses" sometimes don't get drawn fully, im not sure why.
    public void cpuMove(int i, int j){
        Platform.runLater(()-> view.getCell(i,j).fire());
    }
}