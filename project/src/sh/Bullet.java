package sh;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


class Bullet extends Group{
    
    Circle in, out;
    
    public Bullet(){
       
        in = new Circle(10);
        out = new Circle(20);
        in.setFill(Color.DARKORANGE);
        out.setFill(Color.CRIMSON);
        
        this.getChildren().addAll(out,in);
    }
    
}
