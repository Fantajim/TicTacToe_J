public class SinglePlayer {

    private String name;
    private char symbol;
    private boolean turn;

    public SinglePlayer(char c, String name){
        this.symbol = c;
        this.name = name;
        turn = false;

    }

    public char getSymbol(){
        return symbol;
    }

    public String getName(){
        return name;
    }

    public void setSymbol(char c){
        this.symbol = c;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTurn(boolean b){ this.turn = b; }

    public boolean getTurn() { return turn; }
}
