package sh;

import java.util.LinkedList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Aim extends Group implements EventHandler<InputEvent>{

    private Circle c1, c2, c3;
    private Line l1, l2;
    private double speed;
    private static enum DIRECTION {UP, DOWN, LEFT, RIGHT, STILL};
    private DIRECTION direction;
    private Group group;
    private Main app;
    private int score = 0;
    private LinkedList<Bullet> ammo;
    private int remainingAmmo;
    private VisualScore visualScore = null;
    private VisualAmmo visualAmmo = null;
    
    public Aim(Group group, Main app){
        this.group = group;
        this.app = app;
        c1 = new Circle(10);
        c2 = new Circle(20);
        c3 = new Circle(30);
        l1 = new Line(0,-40,0,40);
        l2 = new Line(-40,0,40,0);
        c1.setFill(null);
        c2.setFill(null);
        c3.setFill(null);
        l1.setStroke(Color.DARKRED);
        l2.setStroke(Color.DARKRED);
        c1.setStroke(Color.DARKRED);
        c2.setStroke(Color.DARKRED);
        c3.setStroke(Color.DARKRED);
        c1.setStrokeWidth(3);
        c2.setStrokeWidth(3);
        c3.setStrokeWidth(3);
        this.getChildren().addAll(l1,l2,c1,c2,c3);
        
        ammo = new LinkedList<>();
        speed = 20;
        direction = DIRECTION.STILL;
        remainingAmmo = 30;
        score = 0;
        this.setMouseTransparent(true);
    }
    
    
    void shoot(){
        if(!app.end()){
            if(remainingAmmo == 0) return;
            Bullet m = new Bullet();
            ammo.add(m);
            m.setTranslateX(this.getTranslateX());
            m.setTranslateY(this.getTranslateY());
            group.getChildren().add(m);
            remainingAmmo--;
        }
    }
    
    
    @Override
    public void handle(InputEvent event) {
        KeyEvent ke = (KeyEvent)event;
        
        if (ke.getCode() == KeyCode.LEFT && ke.getEventType() == KeyEvent.KEY_PRESSED)
            direction = DIRECTION.LEFT;
            
        if (ke.getCode() == KeyCode.RIGHT && ke.getEventType() == KeyEvent.KEY_PRESSED) 
            direction = DIRECTION.RIGHT;
            
        if (ke.getCode() == KeyCode.UP && ke.getEventType() == KeyEvent.KEY_PRESSED) 
            direction = DIRECTION.UP;
            
        if (ke.getCode() == KeyCode.DOWN && ke.getEventType() == KeyEvent.KEY_PRESSED) 
            direction = DIRECTION.DOWN;
        
        if (ke.getCode() == KeyCode.LEFT && ke.getEventType() == KeyEvent.KEY_RELEASED)
            if(direction == DIRECTION.LEFT) direction = DIRECTION.STILL;
        
        if (ke.getCode() == KeyCode.RIGHT && ke.getEventType() == KeyEvent.KEY_RELEASED) 
            if(direction == DIRECTION.RIGHT) direction = DIRECTION.STILL;
            
        if (ke.getCode() == KeyCode.UP && ke.getEventType() == KeyEvent.KEY_RELEASED) 
            if(direction == DIRECTION.UP) direction = DIRECTION.STILL;
            
        if (ke.getCode() == KeyCode.DOWN && ke.getEventType() == KeyEvent.KEY_RELEASED) 
            if(direction == DIRECTION.DOWN) direction = DIRECTION.STILL;
        
        if(ke.getCode() == KeyCode.SPACE && ke.getEventType() == KeyEvent.KEY_PRESSED){
           shoot();
           app.spaceDetected(this.getTranslateX(), this.getTranslateY());
        }
            
    }
    
    
    
    public void update(double width, double heigth){
        
         for(Bullet m: ammo){
            m.setScaleX(m.getScaleX()-0.1);
            m.setScaleY(m.getScaleY()-0.1);
            if (m.getScaleX() <= 0){
                ammo.remove(m);
                group.getChildren().remove(m);
            }
        }
        
        visualScore.update(score) ;
        visualAmmo.update(remainingAmmo) ;
        
        if (remainingAmmo == 0 && ammo.isEmpty()) app.endGame();
         
        if(direction == DIRECTION.STILL) return;
       
        switch (direction){
            case LEFT:
                if(this.getTranslateX()-40-speed >= 0)
                this.setTranslateX(this.getTranslateX()-speed);
                break;
            case RIGHT:
                if(this.getTranslateX()+40+speed <= width)
                this.setTranslateX(this.getTranslateX()+speed);
                break;
            case UP:
                if(this.getTranslateY()-40-speed >= 0)
                this.setTranslateY(this.getTranslateY()-speed);
                break;
            case DOWN:
                if(this.getTranslateY()+40+speed <= heigth)
                this.setTranslateY(this.getTranslateY()+speed);
                break;                    
        }
        
    }

    public void addPoints(int p){
        score += p;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setVisualScore(VisualScore p){
        visualScore = p;
    }
    
    public void setAmmo(VisualAmmo m){
        visualAmmo = m;
    }
    
    public int getAmmo(){
        return remainingAmmo;
    }
    
    public int scalePoints(double skX, int p){
        int ret = p;
        if(skX < 6) ret *= 3;
        else if(skX >= 6 && skX<10) ret *=2;
        return ret;
    }
    
    
    public void reset(){
        speed = 20;
        direction = DIRECTION.STILL;
        remainingAmmo = 30;
        score = 0;
    }
}
