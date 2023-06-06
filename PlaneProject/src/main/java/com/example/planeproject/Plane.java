package com.example.planeproject;



public class Plane {
    String name;
    double weight;
    double dragcoef;
    double liftcoef;
    double area;

    public Plane(){

    }

    public Plane(String name, double weight, double dragcoef, double liftcoef, double area) {
        this.name = name;
        this.weight = weight;
        this.dragcoef = dragcoef;
        this.liftcoef = liftcoef;
        this.area = area;
    }

    public Plane(Plane other) {
        this.name = other.getName();
        this.weight = other.getWeight();
        this.dragcoef = other.getDragcoef();
        this.liftcoef = other.getLiftcoef();
        this.area = other.getArea();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDragcoef() {
        return dragcoef;
    }

    public void setDragcoef(double dragcoef) {
        this.dragcoef = dragcoef;
    }

    public double getLiftcoef() {
        return liftcoef;
    }

    public void setLiftcoef(double liftcoef) {
        this.liftcoef = liftcoef;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", dragcoef=" + dragcoef +
                ", liftcoef=" + liftcoef +
                '}';
    }
}
