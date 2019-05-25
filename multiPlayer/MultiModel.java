import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MultiModel extends Model {

    ServiceLocator serviceLocator;
    Console console;
    public Player player1;
    public Player player2;
    private Player currentPlayer;
    private Player currentPlayerID;
    private char symbol1;
    private char symbol2;
    private Cell[] winnerCombo = new Cell[3];

    private Cell[][] cells;
    private GridPane grid;

    public MultiModel() {
        randomizePlayerSymbol();
        player1 = new Player(symbol1, "Server");
        player2 = new Player(symbol2, "Client");
        console = new Console("MultiPlayer");
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Multi model initialized");
        currentPlayer = player1;

        createBoard();

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

    public void createBoard() {
            cells = new Cell[3][3];
            grid = new GridPane();
            for (int i = 0;i<3;i++){
                for( int j = 0;j< 3;j++){
                    cells[i][j] = new Cell();
                    grid.add(cells[i][j],j,i);
                }
            }
    }

    public void blockBoard(){

        for (int i = 0;i<3;i++){
            for( int j = 0;j< 3;j++){
                if (cells[i][j].getSymbol()==' ') cells[i][j].setDisable(true);
            }
        }
    }

    public void unblockBoard(){
        for (int i = 0;i<3;i++){
            for( int j = 0;j< 3;j++){
                if (cells[i][j].getSymbol() ==' ') cells[i][j].setDisable(false);
            }
        }
    }

    public GridPane getGrid(){return grid;}

    public Cell[][] getCells(){return cells;}

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

}