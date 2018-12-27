package sh;

import java.util.LinkedList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    
    
    private boolean spacePressed = false;
    private double aimX, aimY; 
    private Target targetToRemove = null;
    private double finalTimeSec;
    
    private FinalScreen fs;
    
    private double width, height;
    private Scene scene;
    private Group root;
    
    private PopUpScore scoreToRemove;;
    
    private double timeSec = 0;
    private boolean endGame = false;
    private long currentTime = 0;
    private Random rnd;
    
    private LinkedList<Target> targets;
    private LinkedList<PopUpScore> popUpScore;
   
    private Aim aim;
    private VisualScore visualScore;
    private VisualAmmo visualAmmo;
    private boolean showFinalScreen = false;
    private boolean firstTime = true;
    
    private Stage stage;
    
    @Override
    public void start(Stage window) {
        
        stage = window;
        
        root = new Group();
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        width = visualBounds.getWidth();
        height = visualBounds.getHeight();
        scene = new Scene(root, width, height);
        scene.setCursor(Cursor.NONE);
        
        rnd = new Random();
        
        popUpScore = new LinkedList<>();
        targets = new LinkedList<>();
       
        setBackgroundPic();
        
        aim = new Aim(root,this);
        visualScore = new VisualScore();
        visualAmmo = new VisualAmmo();
        addVisualScore();
        addVisualAmmo();
        
        newGame();
    
        window.setTitle("Targets");
        window.setScene(scene);
        window.setResizable(false);
        window.setFullScreen(true);
        window.show();
        
        new AnimationTimer(){
            
            @Override
            public void handle(long currentNanoTime) {
                currentTime = currentNanoTime;
                update();
                timeSec += 1.0/60;
                if(endGame && !showFinalScreen){
                    root.getChildren().remove(aim);
                    fs = new FinalScreen(aim.getScore(), finalTimeSec);
                    fs.setTranslateX(width/2 -300);
                    fs.setTranslateY(height/2 - 300);
                    root.getChildren().add(fs);
                    root.getChildren().add(aim);
                    aim.setTranslateX(width/2);
                    aim.setTranslateY(height/2);
                    connectFinalScreen(fs);
                    showFinalScreen = true;
                }
            }
        
        }.start();
    }
    
    public void close(){
       stage.close();
    };
    
    public boolean end(){
        return endGame;
    };
    
    private void connectFinalScreen(FinalScreen fs){
        fs.setApp(this);
    };
    
    private void newGame(){
        
        initializeTargets();
        
        initializeAim();
        
        setMouseHandlers();
        
        setKeyHandlers();
    };
    
    private void setBackgroundPic(){
        Image pic = new Image("/img/oblaci.jpg");
        ImagePattern texture = new ImagePattern(pic, 0, 0, 1, 1, true);
        
        Rectangle background = new Rectangle(0, 0, width, height);
        background.setFill(texture);
        
        root.getChildren().add(background);
    }
    
   
    // assigns initial coordinates to targets
    // connects each of the targets to the aim and aadds tagrets to the root of this scene
    private void initializeTargets(){
        for(int i = 0; i < 15; i++){
            Target target = new Target(this);
           
            double xTarget = scene.getWidth()/10 + rnd.nextDouble()*(scene.getWidth()-scene.getWidth()/5);
            double yTarget = scene.getHeight()/10 + rnd.nextDouble()*(scene.getHeight()-scene.getHeight()/5);
            target.setTranslateX(xTarget);
            target.setTranslateY(yTarget);
            target.setScaleX(10);
            target.setScaleY(10);
            
            targets.add(target);
            target.setAim(aim);
        }
        
         root.getChildren().addAll(targets);
    }
    
    
    // postavlja nisan u centar ekrana i dodaje ga korenu
    private void initializeAim(){
         aim.setTranslateX(width/2);
         aim.setTranslateY(height/2);
         root.getChildren().add(aim);
    }
    
    
    public void systemReset(){
        if(!targets.isEmpty()){
            for(Target m: targets)
                root.getChildren().remove(m);
            targets.clear();
        }
        
        initializeTargets();
        
        aim.setTranslateX(width/2);
        aim.setTranslateY(height/2);
        aim.reset();
        root.getChildren().remove(aim);
        root.getChildren().add(aim);
        timeSec = 0;
        endGame = false;
        root.getChildren().remove(fs);
        showFinalScreen = false;
    }
    
    // secures that the aim moves whenever mouse is moved
    // secures that mouse click triggers shooting
    // adds these handlers to the scene
    private void setMouseHandlers(){
        
        EventHandler<MouseEvent> handlerMove = (MouseEvent event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            
            aim.setTranslateX(x);
            aim.setTranslateY(y);
        };
        
        EventHandler<MouseEvent> handlerClick = (MouseEvent event) -> {
            aim.shoot();
         };
        
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMove);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerClick);
       
    }
   
    private void setKeyHandlers(){
             scene.setOnKeyPressed(aim);
             scene.setOnKeyReleased(aim);
    }
    
    private void addVisualScore(){
        aim.setVisualScore(visualScore);
        root.getChildren().add(visualScore);
        visualScore.setTranslateY(height - 105);
        visualScore.setTranslateX(5);
    }
    
    private void addVisualAmmo(){
        visualAmmo.setAmmo(aim.getAmmo());
        aim.setAmmo(visualAmmo);
        root.getChildren().add(visualAmmo);
        visualAmmo.setTranslateY(height - 105);
        visualAmmo.setTranslateX(width - 205);
    }
    
    public void update(){
        
        if(!endGame){
            
            for(Target t:targets){
                t.pomeri(width, height, timeSec);
                if(spacePressed){
                    if(t.getBoundsInParent().contains(aimX, aimY)){
                        double targetRadius = 7.0*t.getScaleX();
                        int numOfCircles = t.getNumOfCircles();
                        double xTarget = t.getTranslateX();
                        double yTarget = t.getTranslateY();
                        double xdif = aimX-xTarget;
                        double ydif = aimY-yTarget;
                        double shotRadius = java.lang.Math.sqrt(xdif*xdif + ydif*ydif);
                        double circleWidth = targetRadius / numOfCircles;
                        int index = numOfCircles - (int)java.lang.Math.floor(shotRadius/circleWidth);
                        t.addPointsForSpace(index);
                        targetToRemove = t;
                        root.getChildren().remove(t);
                        spacePressed = false;
                    }
                }
            }
            
            targets.remove(targetToRemove);
            
            
            for(PopUpScore p:popUpScore){
                p.update(targets.size());
            }
            
            aim.update(width, height);
            
            
            
            for(PopUpScore ip:popUpScore){
                ip.lifespan += 1.0/60;
                if(ip.lifespan >= 0.5){
                    root.getChildren().remove(ip);
                    scoreToRemove = ip;
                }
            }
            
            popUpScore.remove(scoreToRemove);
            
            if(targets.isEmpty()){ 
                finalTimeSec = timeSec;
                endGame = true;
            }
        }
        
        
        
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void endGame(){
        finalTimeSec = timeSec;
        this.endGame = true;
    }
    
    public void removeTarget(Target m){
        root.getChildren().remove(m);
        targets.remove(m);
    }

    public void addScore(PopUpScore ip){
        popUpScore.add(ip);
        root.getChildren().add(ip);
    }
    
    public void removeScore(PopUpScore ip){
        popUpScore.remove(ip);
        root.getChildren().remove(ip);
    }
    
    public void spaceDetected(double nisanX, double nisanY){
       spacePressed = true;
       this.aimX = nisanX;
       this.aimY = nisanY;
    };
}
