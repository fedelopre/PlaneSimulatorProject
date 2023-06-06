package com.example.planeproject;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaneGameController {
    public static final int DEFAULT_TIME = 60;
    private static final double PLANE_SPEED = 3;
    ImageView plane;
    List<ImageView> bullets = new ArrayList<>();
    AnimationTimer timer;
    int movement;
    int obstacleTimer;
    Integer score;
    @FXML private Pane root;
    @FXML private ImageView background;
    @FXML private ImageView background1;
    @FXML private Label scoreLabel;


    public void initialize(){
        score = 0;
        obstacleTimer = 0;
        movement = 0;
        creatingSprite();
        initializeTimer();
    }

    /**
     * moving backgrounds and bullets based of plane speed
     */
    private void movingBackground(){
        if(background1.getLayoutX() < 0 && background.getLayoutX() < background1.getLayoutX()){
            background.setLayoutX(background1.getLayoutX() + 600);
        }
        if(background.getLayoutX() < 0 && background1.getLayoutX() < background.getLayoutX()){
            background1.setLayoutX(background.getLayoutX() + 600);
        }
        background.setLayoutX(background.getLayoutX() - PLANE_SPEED);
        background1.setLayoutX(background1.getLayoutX() - PLANE_SPEED);

        for(ImageView bullet : bullets){
            bullet.setX(bullet.getX() - PLANE_SPEED*2);
        }
    }

    /**
     * Creating main plane and setting plane's size
     */
    private void creatingSprite(){
        Image image = new Image("./com/example/planeproject/images/plane2d.jpg");
        plane = new ImageView(image);
        plane.setY(320);
        plane.setX(250);
        plane.setFitHeight(75);
        plane.setFitWidth(140);
        root.getChildren().add(plane);
    }

    /**
     * make plane move up
     */
    public void movePlaneUp(){
        movement = -2;
    }

    /**
     * make plane move down
     */
    public void movePlaneDown(){
        movement = 2;
    }

    /**
     * Repeat mainLoop until timer stops
     */
    private void initializeTimer() {
        if (timer != null)
            timer.stop();
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                mainLoop();
            }
        };
        timer.start();
    }

    /**
     * set time between bullet spawn
     * update score
     */
    private void mainLoop(){
        movingBackground();
        checkBorder();
        plane.setY(plane.getY() + movement);
        checkHit();
        if(obstacleTimer == DEFAULT_TIME){
            creatingObstacle();
            obstacleTimer = 0;
        }
        ++obstacleTimer;
        ++score;
        scoreLabel.setText(score.toString());
    }

    /**
     * Creating bullet generate in random height
     * setting bullet size
     */
    private void creatingObstacle(){
        Random random = new Random();
        Image image = new Image("./com/example/planeproject/images/bullet.jpg");
        int rndNumber = random.nextInt(400);
        ImageView bullet = new ImageView(image);
        bullet.setY(rndNumber);
        bullet.setX(700);
        bullet.setFitHeight(40);
        bullet.setFitWidth(40);
        bullets.add(bullet);
        root.getChildren().add(bullet);
    }

    /**
     * Check if bullet collide with plane
     * if collide game over
     */
    private void checkHit(){
        for(ImageView bullet : bullets){
            double frontPlane = plane.getX() + plane.getFitWidth()/2;
            double backPlane = plane.getX() - plane.getFitWidth()/2;
            if(bullet.getX() < frontPlane && bullet.getX() > backPlane){
                double lowerPlane = plane.getY() + plane.getFitHeight()/6;
                double upperPlane = plane.getY() - plane.getFitHeight()/6;
                if(bullet.getY() < lowerPlane && bullet.getY() > upperPlane){
                    gameOver();
                    timer.stop();
                    break;
                }
            }
        }
    }

    /**
     * Display game over window
     */
    private void gameOver(){
        Image image = new Image("./com/example/planeproject/images/explosion.jpg");
        ImageView gameOver = new ImageView(image);
        gameOver.setY(100);
        gameOver.setX(250);
        gameOver.setFitHeight(200);
        gameOver.setFitWidth(300);
        root.getChildren().add(gameOver);
    }

    /**
     * Setting limits to movement
     */
    private void checkBorder() {
        if (plane.getY() <= 25 || plane.getY() >= 385) {
            movement = -movement;
        }
    }

}


