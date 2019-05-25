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
    private ArrayList<Cell> winnerCombo = new ArrayList<>();
    private boolean isQuad = false;

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
    public boolean isWinLogic (Cell[][]cells) {
        boolean result = false;
        int counter = 1;


        //check horizontal

        for (int i = 0; i < TicTacToeGame.dimension ; i++) {
            for (int j = 0; j < TicTacToeGame.dimension - 1; j++) {

                if(cells[i][j].getSymbol() == cells[i][j+1].getSymbol() && cells[i][j].getSymbol() != ' ' ){
                    counter++;
                    if (counter == TicTacToeGame.dimension){
                        result = true;
                        for (int k = 0;k<TicTacToeGame.dimension;k++){
                            winnerCombo.add(cells[i][k]);

                        }
                    }
                }

            }
            counter = 1;
        }

        for (int i = 0; i < TicTacToeGame.dimension ; i++) {
            for (int j = 0; j < TicTacToeGame.dimension - 1; j++) {

                if(cells[j][i].getSymbol() == cells[j+1][i].getSymbol() && cells[j][i].getSymbol() != ' ' ){
                    counter++;
                    if (counter == TicTacToeGame.dimension){
                        result = true;
                        for (int k = 0;k<TicTacToeGame.dimension;k++){
                            winnerCombo.add(cells[k][i]);

                        }
                    }
                }

            }
            counter = 1;
        }



        //check diagonally tl-br
        for (int i = 0; i < TicTacToeGame.dimension - 1; i++) {

                if(cells[i][i].getSymbol() == cells[i+1][i+1].getSymbol() && cells[i][i].getSymbol() != ' ' ){
                    counter++;
                    if (counter == TicTacToeGame.dimension){
                        result = true;
                        for (int k = 0;k<TicTacToeGame.dimension;k++){
                            winnerCombo.add(cells[k][k]);
                        }
                    }
                }
                else counter = 1;

        }

        //check diagonally bl-tr
        for (int j = 0, i = TicTacToeGame.dimension-1; j < TicTacToeGame.dimension - 1 && i > 0 ; j++,i--) {

            if(cells[i][j].getSymbol() == cells[i-1][j+1].getSymbol() && cells[i][j].getSymbol() != ' ' ){
                counter++;
                if (counter == TicTacToeGame.dimension){
                    result = true;
                    for (int k = 0, l = TicTacToeGame.dimension-1;k<TicTacToeGame.dimension && l > -1;k++,l--){
                        winnerCombo.add(cells[l][k]);
                    }
                }
            }
            else counter = 1;

        }

        //check quad

        for (int i = 0; i < TicTacToeGame.dimension-1;i++){
            for (int j= 0; j< TicTacToeGame.dimension -1;j++) {
                if (    cells[i][j].getSymbol() == cells[i][j+1].getSymbol() &&
                        cells[i][j].getSymbol() == cells[i+1][j].getSymbol() &&
                        cells[i][j].getSymbol() == cells[i+1][j+1].getSymbol() &&
                        cells[i][j].getSymbol() != ' '){
                    result = true;
                    winnerCombo.add(cells[i][j]);
                    winnerCombo.add(cells[i][j+1]);
                    winnerCombo.add(cells[i+1][j+1]);
                    winnerCombo.add(cells[i+1][j]);
                    isQuad = true;
                }
                else counter = 1;
            }


        }



        return result;

    }

        //Logic part of draw to check if there's an empty cell left
    public boolean isDrawLogic (Cell[][]cells){
            boolean result = true;
            for (int i = 0; i < TicTacToeGame.dimension; i++) {
                for (int j = 0; j < TicTacToeGame.dimension; j++) {
                    if (cells[i][j].getSymbol() == ' ') {
                        result = false;
                        break;
                    }
                }
            }
            return result;
    }

    public void createBoard() {
        cells = new Cell[TicTacToeGame.dimension][TicTacToeGame.dimension];
        grid = new GridPane();
        for (int i = 0;i<TicTacToeGame.dimension;i++){
            for( int j = 0;j< TicTacToeGame.dimension;j++){
                cells[i][j] = new Cell();
                grid.add(cells[i][j],j,i);
            }
        }
    }

    public void blockBoard(){

        for (int i = 0;i<TicTacToeGame.dimension;i++){
            for( int j = 0;j< TicTacToeGame.dimension;j++){
                if (cells[i][j].getSymbol()==' ') cells[i][j].setDisable(true);
            }
        }
    }

    public void unblockBoard(){
        for (int i = 0;i<TicTacToeGame.dimension;i++){
            for( int j = 0;j< TicTacToeGame.dimension;j++){
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

    public ArrayList<Cell> getWinnerCombo () {
        return winnerCombo;
    }

    public boolean isQuad(){return isQuad;}

    public void setIsQuad(boolean b){isQuad = b;}

}