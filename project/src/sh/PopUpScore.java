package sh;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class PopUpScore extends Group{
    
    private Text text;
    public double lifespan;
   
    public PopUpScore(int scoreToShow, double x, double y){
       
        text = new Text("" + scoreToShow);
        text.setFont(new Font(50));
        text.setTranslateX(x);
        text.setTranslateY(y);
        text.setFill(Color.DARKORANGE);
        text.setStroke(Color.CRIMSON);
        lifespan = 0;
        this.setMouseTransparent(true);
        this.getChildren().add(text);
    }
    
    public void update(int numTargetsOnScreen){
        
        double scale = 0;
        if(numTargetsOnScreen > 10) scale = 0.05;
        else scale = 0.025;
        
        if(this.getScaleX() > 0.11) this.setScaleX(this.getScaleX()-scale);
        if(this.getScaleY() > 0.11) this.setScaleY(this.getScaleY()-scale);
    }
    
}
