package sh;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VisualScore extends Group{
    
    private Text score;
    private Rectangle background;
    
    public VisualScore(){
        background = new Rectangle(200,100);
        Color boja = Color.rgb(0, 0, 255, 0.5);
        background.setArcHeight(50);
        background.setArcWidth(50);
        background.setFill(boja);
        background.setStroke(Color.DARKBLUE);
        background.setStrokeType(StrokeType.OUTSIDE);
        score = new Text("0000");
        score.setFont(new Font(50));
        this.getChildren().add(background);
        this.getChildren().add(score);
        score.setTranslateX(45);
        score.setTranslateY(70);
        score.setFill(Color.YELLOW);
        score.setStroke(Color.DARKRED);
        score.setStrokeWidth(2);
        
    }
    
    public void update(int p){
        if(0<= p && p<=9) score.setText("000"+p);
        else if(9< p && p<=99) score.setText("00"+p);
        else if(99< p && p<=999) score.setText("0"+p);
        else score.setText(""+p);
    }
}
