import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Console extends TextArea {
    private LocalDateTime ts = LocalDateTime.now();
    private String fts;
    private static final String DATE_FORMATTER= "dd-MM-yyyy HH:mm:ss";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    public Console(String s){

        this.setPrefWidth(300);
        this.setWrapText(true);
        fts = ts.format(formatter);
        this.setText(s+" game has started "+"\n"+ fts+"\n\n");
        this.setEditable(false);

    }

    //method for easy console access and autoscrolling
    public void addToConsole(String s){
        this.setText(this.getText()+s+"\n");
        this.setScrollTop(Double.MAX_VALUE);
    }

}
