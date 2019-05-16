public class Player {

    private String name;
    private char symbol;

    public Player(char c, String name){
        this.symbol = c;
        this.name = name;
    }

    public char getSymbol(){ return symbol; }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

}
