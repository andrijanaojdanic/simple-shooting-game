package sh;

import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;


public class Target extends Group{
  
    private Target thisTarget;
    private Main app;
    private static Random rnd = new Random();
    
    private int numCircles;
    private double scaleX, scaleY;  
    private int factor;     // sluzi pri odredjivanju broja poena koji se dobiju skidanjem mete
    private int speed;
    private double rDif;
    
    private Circle[] circles;
    
    private int directionX;
    private int directionY;
   
    private double scale;   // bolji naziv bi bio smerSkaliranja, jer ima vrednosti samo +1/-1
    
    private double lastScalingMoment = 0;    // da se ne bi precesto menjala velicina mete
    
    private Aim aim;
    
   
    public Target(Main main){
        
        thisTarget = this;
        this.app = main;
        
        numCircles = 3+rnd.nextInt(5);
        scaleX = 10;
        scaleY = 10;
        factor = 1;
        
        if(numCircles == 5 || numCircles == 6) factor = 2;
        else if(numCircles == 7) factor = 3;
        
        speed = 3 + rnd.nextInt(4);
        rDif = 7.0/numCircles;
       
        circles = new Circle[numCircles];
        
        makeCircles();
        
        // to decide initial direction of a target
        // and if it is initally goint to get bigger or smaller
        int x = rnd.nextInt(2);
        int y = rnd.nextInt(2);
        int s = rnd.nextInt(2);
          
        if(x == 0) directionX = 1;
        else directionX = -1;
          
        if(y == 0) directionY = 1;
        else directionY = -1;
          
        if(s == 0) scale = 0.1;
        else scale = -0.1; 
          
    }
    
    private void makeCircles(){
        
        Color color = Color.RED;
        double rMax = 7;
        double r = rMax;
        
        for (int i = 0; i < numCircles; i++){
            Circle circle = new Circle(r);
            r -= rDif;
            
            if (i == numCircles-1){
                int b = rnd.nextInt(6);
               
                switch (b){
                    case 0:
                        color = Color.YELLOW;
                        break;
                    case 1:
                        color = Color.DARKORANGE;
                        break;
                    case 2:
                        color = Color.RED;
                        break;
                    case 3:
                        color = Color.rgb(255,0,255);
                        break;
                    case 4:
                        color = Color.BLUE;
                        break;    
                    case 5:
                        color = Color.LIME;
                        break;    
                }
                circle.setFill(color);
            }   
            else if(i%2 == 1)
                circle.setFill(Color.WHITE);
            else 
                circle.setFill(Color.BLACK);
            
            
            int primaryPoints = (i+1)*10*factor;

            EventHandler<MouseEvent> ruk = (MouseEvent event) -> {
                app.removeTarget(thisTarget);
                int finalPoints = aim.scalePoints(scaleX,primaryPoints);
                aim.addPoints(finalPoints);
                PopUpScore ip = new PopUpScore(finalPoints, this.getTranslateX(), this.getTranslateY());
                app.addScore(ip);
            };
            
            circle.addEventHandler(MouseEvent.MOUSE_PRESSED, ruk);
           
            circles[i] = circle;
            
        }
        
          this.getChildren().addAll(circles);
          assignPoints(numCircles, color);
    }
    
    // variable i stores index of the circle that has been hit
    public void addPointsForSpace(int i){
                int primaryPoints = (i+1)*10*factor;
                int finalPoints = aim.scalePoints(scaleX,primaryPoints);
                aim.addPoints(finalPoints);
                PopUpScore ip = new PopUpScore(finalPoints, this.getTranslateX(), this.getTranslateY());
                app.addScore(ip);
    }
    
    private void assignPoints(int numOfCircles, Color color){
        switch (numOfCircles){
            case 3:
                Text text1 = new Text("30");
                text1.setMouseTransparent(true);
                
                text1.setFont(new Font(10));
                text1.setStroke(Color.BLACK);
                text1.setFill(Color.BLACK);
                text1.setTextAlignment(TextAlignment.CENTER);
                text1.getTransforms().add(new Translate(-0.50,0.35));
                text1.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text1);
                
                Text text2 = new Text("20");
                text2.setMouseTransparent(true);
                
                text2.setFont(new Font(10));
                text2.setStroke(color);
                text2.setFill(color);
                text2.setTextAlignment(TextAlignment.CENTER);
                text2.getTransforms().add(new Translate(-0.50, 3.75));
                text2.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text2);
                
                Text text3 = new Text("10");
                text3.setMouseTransparent(true);
                
                text3.setFont(new Font(10));
                text3.setFill(color);
                text3.setStroke(color);
                text3.setTextAlignment(TextAlignment.CENTER);
                text3.getTransforms().add(new Translate(-0.50, 6.20));
                text3.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text3);
                break;
            
            case 4:
                Text text4 = new Text("40");
                text4.setMouseTransparent(true);
                
                text4.setFont(new Font(10));
                text4.setStroke(Color.BLACK);
                text4.setFill(Color.BLACK);
                text4.setTextAlignment(TextAlignment.CENTER);
                text4.getTransforms().add(new Translate(-0.50,0.35));
                text4.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text4);
                
                Text text5 = new Text("30");
                text5.setMouseTransparent(true);
                
                text5.setFont(new Font(10));
                text5.setStroke(color);
                text5.setFill(color);
                text5.setTextAlignment(TextAlignment.CENTER);
                text5.getTransforms().add(new Translate(-0.50, 2.9));
                text5.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text5);
                
                Text text6 = new Text("20");
                text6.setMouseTransparent(true);
                
                text6.setFont(new Font(10));
                text6.setFill(color);
                text6.setStroke(color);
                text6.setTextAlignment(TextAlignment.CENTER);
                text6.getTransforms().add(new Translate(-0.50, 4.65));
                text6.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text6);
                
                Text text7 = new Text("10");
                text7.setMouseTransparent(true);
                
                text7.setFont(new Font(10));
                text7.setFill(color);
                text7.setStroke(color);
                text7.setTextAlignment(TextAlignment.CENTER);
                text7.getTransforms().add(new Translate(-0.50, 6.40));
                text7.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text7);
                break;
                
            case 5:
                Text text8 = new Text("100");
                text8.setMouseTransparent(true);
                
                text8.setFont(new Font(10));
                text8.setStroke(Color.BLACK);
                text8.setFill(Color.BLACK);
                text8.setTextAlignment(TextAlignment.CENTER);
                text8.getTransforms().add(new Translate(-0.9,0.35));
                text8.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text8);
                
                Text text9 = new Text("80");
                text9.setMouseTransparent(true);
                
                text9.setFont(new Font(10));
                text9.setStroke(color);
                text9.setFill(color);
                text9.setTextAlignment(TextAlignment.CENTER);
                text9.getTransforms().add(new Translate(-0.50, 2.4));
                text9.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text9);
                
                Text text10 = new Text("60");
                text10.setMouseTransparent(true);
                
                text10.setFont(new Font(10));
                text10.setFill(color);
                text10.setStroke(color);
                text10.setTextAlignment(TextAlignment.CENTER);
                text10.getTransforms().add(new Translate(-0.50, 3.8));
                text10.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text10);
                
                Text text11 = new Text("40");
                text11.setMouseTransparent(true);
                
                text11.setFont(new Font(10));
                text11.setFill(color);
                text11.setStroke(color);
                text11.setTextAlignment(TextAlignment.CENTER);
                text11.getTransforms().add(new Translate(-0.50, 5.2));
                text11.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text11);
                    
                Text text12 = new Text("20");
                text12.setMouseTransparent(true);
                
                text12.setFont(new Font(10));
                text12.setFill(color);
                text12.setStroke(color);
                text12.setTextAlignment(TextAlignment.CENTER);
                text12.getTransforms().add(new Translate(-0.50, 6.5));
                text12.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text12);
                break;
                
            case 6:
                Text text13 = new Text("120");
                text13.setMouseTransparent(true);
                
                text13.setFont(new Font(10));
                text13.setStroke(Color.BLACK);
                text13.setFill(Color.BLACK);
                text13.setTextAlignment(TextAlignment.CENTER);
                text13.getTransforms().add(new Translate(-0.8,0.35));
                text13.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text13);
                
                Text text14 = new Text("100");
                text14.setMouseTransparent(true);
                
                text14.setFont(new Font(10));
                text14.setStroke(color);
                text14.setFill(color);
                text14.setTextAlignment(TextAlignment.CENTER);
                text14.getTransforms().add(new Translate(-0.8, 2.0));
                text14.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text14);
                
                Text text15 = new Text("80");
                text15.setMouseTransparent(true);
                
                text15.setFont(new Font(10));
                text15.setFill(color);
                text15.setStroke(color);
                text15.setTextAlignment(TextAlignment.CENTER);
                text15.getTransforms().add(new Translate(-0.50, 3.2));
                text15.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text15);
                
                Text text16 = new Text("60");
                text16.setMouseTransparent(true);
                
                text16.setFont(new Font(10));
                text16.setFill(color);
                text16.setStroke(color);
                text16.setTextAlignment(TextAlignment.CENTER);
                text16.getTransforms().add(new Translate(-0.50, 4.4));
                text16.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text16);
                    
                Text text17 = new Text("40");
                text17.setMouseTransparent(true);
                
                text17.setFont(new Font(10));
                text17.setFill(color);
                text17.setStroke(color);
                text17.setTextAlignment(TextAlignment.CENTER);
                text17.getTransforms().add(new Translate(-0.50, 5.6));
                text17.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text17);
                
                Text text18 = new Text("20");
                text18.setMouseTransparent(true);
                
                text18.setFont(new Font(10));
                text18.setFill(color);
                text18.setStroke(color);
                text18.setTextAlignment(TextAlignment.CENTER);
                text18.getTransforms().add(new Translate(-0.50, 6.8));
                text18.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text18);
                break;
                
            case 7:
                Text text19 = new Text("210");
                text19.setMouseTransparent(true);
                
                text19.setFont(new Font(10));
                text19.setStroke(Color.BLACK);
                text19.setFill(Color.BLACK);
                text19.setTextAlignment(TextAlignment.CENTER);
                text19.getTransforms().add(new Translate(-0.8,0.35));
                text19.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text19);
                
                Text text20 = new Text("180");
                text20.setMouseTransparent(true);
                
                text20.setFont(new Font(10));
                text20.setStroke(color);
                text20.setFill(color);
                text20.setTextAlignment(TextAlignment.CENTER);
                text20.getTransforms().add(new Translate(-0.8, 1.8));
                text20.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text20);
                
                Text text21 = new Text("150");
                text21.setMouseTransparent(true);
                
                text21.setFont(new Font(10));
                text21.setFill(color);
                text21.setStroke(color);
                text21.setTextAlignment(TextAlignment.CENTER);
                text21.getTransforms().add(new Translate(-0.8, 2.8));
                text21.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text21);
                
                Text text22 = new Text("120");
                text22.setMouseTransparent(true);
                
                text22.setFont(new Font(10));
                text22.setFill(color);
                text22.setStroke(color);
                text22.setTextAlignment(TextAlignment.CENTER);
                text22.getTransforms().add(new Translate(-0.8, 3.8));
                text22.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text22);
                    
                Text text23 = new Text("90");
                text23.setMouseTransparent(true);
                
                text23.setFont(new Font(10));
                text23.setFill(color);
                text23.setStroke(color);
                text23.setTextAlignment(TextAlignment.CENTER);
                text23.getTransforms().add(new Translate(-0.50, 4.8));
                text23.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text23);
                
                Text text24 = new Text("60");
                text24.setMouseTransparent(true);
                
                text24.setFont(new Font(10));
                text24.setFill(color);
                text24.setStroke(color);
                text24.setTextAlignment(TextAlignment.CENTER);
                text24.getTransforms().add(new Translate(-0.50, 5.8));
                text24.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text24);
                
                Text text25 = new Text("30");
                text25.setMouseTransparent(true);
                
                text25.setFont(new Font(10));
                text25.setFill(color);
                text25.setStroke(color);
                text25.setTextAlignment(TextAlignment.CENTER);
                text25.getTransforms().add(new Translate(-0.50, 6.8));
                text25.getTransforms().add(new Scale(0.1,0.1));
                this.getChildren().add(text25);
                break;
        
        }
    }
    
    void pomeri(double width, double height, double time){
        
        double currentX = this.getTranslateX();
        double currentY = this.getTranslateY();
        scaleX = this.getScaleX();
        scaleY = this.getScaleY();
       
        if(lastScalingMoment == 0){
          
            if(scaleX <= 3) scale = 0.1;
        
            if(scaleX >= 13) scale = -0.1;
            
            this.setScaleX(scaleX+scale*1);
            this.setScaleY(scaleY+scale*1);
            
            scaleX = this.getScaleX();
            scaleY = this.getScaleY();
            
            lastScalingMoment = time;
        }
        
        if((time - lastScalingMoment) >= 0.1){
           
            if(scaleX <= 4) scale = 0.1;
        
            if(scaleX >= 12) scale = -0.1;
            
            this.setScaleX(scaleX+scale*1);
            this.setScaleY(scaleY+scale*1);
            
            scaleX = this.getScaleX();
            scaleY = this.getScaleY();
            
            lastScalingMoment = time;
        }
        
        if((currentX + 1) > (width-7*scaleX)) directionX = -1;
        
        if((currentX - 1) < (7*scaleX)) directionX = +1;
       
        if((currentY + 1) > (height-7*scaleY)) directionY = -1;
        
        if((currentY - 1) < (7*scaleY)) directionY = +1;
        
        
        this.setTranslateX(currentX + speed*directionX);
        this.setTranslateY(currentY + speed*directionY);
        
    }
    
   
    void setAim(Aim n){
        this.aim = n;
    }
        
    int getNumOfCircles(){
        return numCircles;
    }
    
    Circle[] getCircles(){
          return circles;
    };
    
}
