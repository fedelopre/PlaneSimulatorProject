package com.example.planeproject;

import javafx.scene.image.Image;

public class Planet {
    String name;
    double gForce;
    double airDensity;
    Image backgroundImage;


    public Planet(){

    }

    public Planet(Planet planet){
        this.name=planet.name;
        this.gForce=planet.gForce;
        this.airDensity=planet.airDensity;
        this.backgroundImage=planet.backgroundImage;
    }

    public Planet(String name, double gForce, double airDensity,Image backgroundImage) {
        this.name = name;
        this.gForce = gForce;
        this.airDensity = airDensity;
        this.backgroundImage = backgroundImage;
    }

    public Planet setDefaultPlanet(){
        return new Planet("Earth", 9.81,1.225,new Image("./com/example/planeproject/images/backgroundplane.jpg"));
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getgForce() {
        return gForce;
    }

    public void setgForce(double gForce) {
        this.gForce = gForce;
    }

    public double getAirDensity() {
        return airDensity;
    }

    public void setAirDensity(double airDensity) {
        this.airDensity = airDensity;
    }
}
