package sh;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class VisualAmmo extends Group {
    
    private Text text;
    private Circle in, out;
    private Rectangle background;
   
    public VisualAmmo(){
        background = new Rectangle(200,100);
        Color color = Color.rgb(0, 0, 255, 0.5);
        background.setArcHeight(50);
        background.setArcWidth(50);
        background.setFill(color);
        background.setStroke(Color.DARKBLUE);
        background.setStrokeType(StrokeType.OUTSIDE);
        this.getChildren().add(background);
        
        in = new Circle(50,50,10);
        out = new Circle(50,50,20);
        in.setFill(Color.DARKORANGE);
        in.setStroke(Color.DARKRED);
        out.setFill(Color.CRIMSON);
        out.setStroke(Color.DARKRED);
        this.getChildren().addAll(out,in);
        
        text = new Text();
        text.setFont(new Font(50));
        text.setFill(Color.YELLOW);
        text.setStroke(Color.DARKRED);
        text.setStrokeWidth(2);
        text.setTranslateX(100);
        text.setTranslateY(70);
        this.getChildren().add(text);
        
    }
    
    public void update(int p){
       if(p<10) text.setText("0"+p);
       else text.setText(""+p);
    }
    
    public void setAmmo(int m){
        if(m<10) text.setText("0"+m);
        else text.setText(""+m);
    }
    
}
