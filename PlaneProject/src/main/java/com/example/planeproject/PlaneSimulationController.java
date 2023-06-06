package com.example.planeproject;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;




public class PlaneSimulationController {
    @FXML private Pane root;
    @FXML private TextField speedField;
    @FXML private Label planeName;
    @FXML private ImageView background1;
    @FXML private ImageView background;
    @FXML private Label speedLabel;
    AnimationTimer timer;
    ImageView plane;
    Double speed;
    double gravity;
    Plane planeSelected1;
    Planet planet;

    /**
     * used to receive passed plane
     */
    public void setPlaneSelected1(Plane planeSelected) {
        this.planeSelected1=planeSelected;
    }

    /**
     * used to receive passed planet
     */
    public void setPlanet(Planet planet){
        this.planet=planet;
    }

    /**
     * initialize speed text field
     * setting listener
     */
    public void initialize() {
        speedField.textProperty().addListener((observable, oldValue, newValue) -> speed = Double.parseDouble(newValue));
        speedField.textProperty().addListener((observable, oldValue, newValue) -> speedLabel.setText(newValue));
        speedField.setText("0");
        speedLabel.setText(speed.toString());
    }

    /**
     * Start simulation
     */
    @FXML
    void startPlane(ActionEvent event) {
        interfaceSettings();
        creatingSprite();
        initializeTimer();
        settingBackground();
    }

    /**
     * Shift background based on plane speed
     */
    private void movingBackground(){
        if(background1.getLayoutX() < 0 && background.getLayoutX() < background1.getLayoutX()){
            background.setLayoutX(background1.getLayoutX() + 800);
        }
        if(background.getLayoutX()<0 && background1.getLayoutX()<background.getLayoutX()){
            background1.setLayoutX(background.getLayoutX() + 800);
        }
        background.setLayoutX(background.getLayoutX() - speed/50);
        background1.setLayoutX(background1.getLayoutX() - speed/50);
    }

    /**
     * Creating main plane
     * setting sizes
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
     * loop that must repeat
     */
    private void mainLoop(){
        checkSpeed();
        movingBackground();
        movePlane();
        checkBorder();
    }

    /**
     * Set gravity and lift force based on speed
     * Making plane lift or fall
     */
    private void movePlane(){
        double speedSq = speed/60/60 * 1000;
        speedSq *= speedSq;
        double yLift = planeSelected1.getLiftcoef() * planet.getAirDensity() * planeSelected1.getArea() * speedSq/2;
        double upperForce = gravity - yLift;
        if(upperForce < 0){
            plane.setY(plane.getY() - 1);
            plane.setY(plane.getY() - speed/300);
        } else if(upperForce > 0) {
            plane.setY(plane.getY() + 2);
            plane.setY(plane.getY() - speed/300);
        }
    }

    /**
     * Checking speed limit
     */
    private void checkSpeed(){
        if (speed > 2000) {
            speedField.setText("2000");
        }
    }

    /**
     * Setting limits to movement
     */
    private void checkBorder() {
        if (plane.getY() <= 25) {
            plane.setY(plane.getY() + 1);
            plane.setY(plane.getY() + speed/300);
        } else if (plane.getY() >= 385) {
            plane.setY(plane.getY() - 2);
            plane.setY(plane.getY() + speed/300);
        }
    }

    /**
     * set gravity based on selected planet
     * fill label name
     */
    private void interfaceSettings(){
        gravity = planet.getgForce() * planeSelected1.getWeight();
        planeName.setText(planeSelected1.getName());
    }

    /**
     * set image based on selected planet
     */
    private void settingBackground(){
        background.setImage(planet.getBackgroundImage());
        background1.setImage(planet.getBackgroundImage());

    }
}
