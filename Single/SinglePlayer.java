public class SinglePlayer {

    private String name;
    private char symbol;

    public SinglePlayer(char c, String name){
        this.symbol = c;
        this.name = name;

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


}
