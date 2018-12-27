package sh;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class FinalScreen extends Group{
    
    private Rectangle background;
    private Text score, time;
    private Group newGame, exit;
    private Main app;
    
    public FinalScreen(int finalScore, double timePassed){
        
        String scoreText = "";
        if(finalScore>999) scoreText = "" + finalScore;
        else if (finalScore > 99) scoreText = "0" + finalScore;
        else if (finalScore > 9) scoreText = "00" + finalScore;
        else scoreText = "000" + finalScore;
        
        background = new Rectangle(600, 600);
        Color color = Color.rgb(0, 0, 255, 0.5);
        background.setArcHeight(300);
        background.setArcWidth(300);
        background.setFill(color);
        background.setStroke(Color.DARKBLUE);
        background.setStrokeType(StrokeType.OUTSIDE);
        
        score = new Text("RESULT: " + scoreText);
        score.setFont(new Font(50));
        score.setTranslateX(120);
        score.setTranslateY(100);
        score.setFill(Color.YELLOW);
        score.setStroke(Color.DARKRED);
        score.setStrokeWidth(2);
        
        
        String t = String.format("%.3f", timePassed);
        
        time = new Text("TIME: " + t + "s");
        time.setFont(new Font(50));
        time.setTranslateX(120);
        time.setTranslateY(150);
        time.setFill(Color.YELLOW);
        time.setStroke(Color.DARKRED);
        time.setStrokeWidth(2);
        
        newGame = new Group();
        
        Rectangle newGameBackground = new Rectangle(300,100);
        Color color1 = Color.rgb(255, 0, 0, 0.4);
        newGameBackground.setTranslateX(0);
        newGameBackground.setTranslateY(0);
        newGameBackground.setArcHeight(30);
        newGameBackground.setArcWidth(30);
        newGameBackground.setFill(color1);
        newGameBackground.setStroke(Color.DARKBLUE);
        newGameBackground.setStrokeType(StrokeType.OUTSIDE);
        
        Text newGameText;
        
        newGameText = new Text("NEW GAME");
        newGameText.setFont(new Font(50));
        newGameText.setTranslateX(20);
        newGameText.setTranslateY(70);
        newGameText.setFill(Color.YELLOW);
        newGameText.setStroke(Color.DARKRED);
        newGameText.setStrokeWidth(2);
        
        newGame.setTranslateX(150);
        newGame.setTranslateY(250);
        newGame.getChildren().add(newGameBackground);
        newGame.getChildren().add(newGameText);
        
        EventHandler<MouseEvent> handl = (MouseEvent event) -> {
                app.systemReset();
        };
        
        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, handl);
        ////////////////
        
        exit = new Group();
        
        Rectangle exitBackground = new Rectangle(300,100);
        Color color2 = Color.rgb(255, 0, 0, 0.4);
        exitBackground.setTranslateX(0);
        exitBackground.setTranslateY(0);
        exitBackground.setArcHeight(30);
        exitBackground.setArcWidth(30);
        exitBackground.setFill(color2);
        exitBackground.setStroke(Color.DARKBLUE);
        exitBackground.setStrokeType(StrokeType.OUTSIDE);
        
        Text exitText;
        
        exitText = new Text("      EXIT");
        exitText.setFont(new Font(50));
        exitText.setTranslateX(20);
        exitText.setTranslateY(70);
        exitText.setFill(Color.YELLOW);
        exitText.setStroke(Color.DARKRED);
        exitText.setStrokeWidth(2);
        
        exit.setTranslateX(150);
        exit.setTranslateY(400);
        exit.getChildren().add(exitBackground);
        exit.getChildren().add(exitText);
        
        EventHandler<MouseEvent> handl1 = (MouseEvent event) -> {
                app.close();
        };
        
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, handl1);
        
        this.getChildren().add(background);
        this.getChildren().add(score);
        this.getChildren().add(time);
        this.getChildren().add(newGame);
        this.getChildren().add(exit);
    }
    
    public void setApp(Main main){
        app = main;
    };
}
