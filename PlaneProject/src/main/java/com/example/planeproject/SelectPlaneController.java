package com.example.planeproject;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.List;

public class SelectPlaneController {
    static final Planet DEFAULT_PLANET = new Planet().setDefaultPlanet();

    @FXML private TableColumn<Plane, Double> areaColumn;
    @FXML private TableColumn<Plane, Double> dragColumn;
    @FXML private TableColumn<Plane, Double> liftColumn;
    @FXML private TableColumn<Plane, String> nameColumn;
    @FXML private TableView<Plane> tableView;
    @FXML private TableColumn<Plane, Double> weightColumn;
    @FXML private TextField areaField;
    @FXML private TextField dragField;
    @FXML private TextField liftField;
    @FXML private TextField nameField;
    @FXML private TextField weightField;
    @FXML private ComboBox<String> comboPlanet;

    ObservableList<Plane> planes;
    private Stage stage;
    private Scene scene;
    Plane selectedPlane;
    ObservableList<Planet> planets;


    /**
     * For each column setting the name of the Plane's attributes
     */
    public void initialize(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        dragColumn.setCellValueFactory(new PropertyValueFactory<>("dragcoef"));
        liftColumn.setCellValueFactory(new PropertyValueFactory<>("liftcoef"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        planes = FXCollections.observableArrayList();
        tableView.setItems(getPlanesData());
        fillComboBox();
    }

    /**
     * Fill ComboBox field with planet names
     */
    void fillComboBox(){
        planets = FXCollections.observableArrayList();
        planets.add(new Planet("Earth", 9.81,1.225,new Image("./com/example/planeproject/images/backgroundplane.jpg")));
        planets.add(new Planet("Moon", 1.62,0.000000000001,new Image("./com/example/planeproject/images/moon.jpg")));
        ObservableList<String> planetNames=FXCollections.observableArrayList();
        for(Planet planet : planets){
            planetNames.add(planet.getName());
        }
        comboPlanet.setItems(planetNames);
        comboPlanet.getSelectionModel().selectFirst();
    }

    /**
     * Set and show Simulation Scene
     */
    @FXML
    void onNextClick(ActionEvent event) throws IOException {
        selectedPlane= tableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("plane-simulation.fxml"));
        Parent root= loader.load();

        //Passing selected plane to PlaneSimulationController
        PlaneSimulationController controller = loader.getController();
        controller.setPlaneSelected1(selectedPlane);
        Planet selectedPlanet=findSelectedPlanet(comboPlanet.getSelectionModel().getSelectedItem());
        controller.setPlanet(selectedPlanet);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * search selected plane
     * @param name Name of the selected planet
     * @return if correspondence of the name are found returns the planet found, else return default Planet
     */
    Planet findSelectedPlanet(String name){
        for(Planet planet : planets){
            if(planet.getName().equals(name)){
                return planet;
            }
        }
        return DEFAULT_PLANET;
    }

    /**
     * take data from 'planes.json'
     */
    public ObservableList<Plane> getPlanesData() {
        ObjectMapper objectMapper = new ObjectMapper();
        planes = FXCollections.observableArrayList();
        try{
            File file = new File("./src/main/java/com/example/planeproject/planes.json");
            List<Plane> tmp = objectMapper.readValue(file, new TypeReference<>() {});
            for(Plane plane: tmp){
                planes.add(plane);
            }
        } catch (IOException e){
            new Alert(Alert.AlertType.ERROR, "JSON read failed").showAndWait();
            e.printStackTrace();
        }
        return planes;
    }

    /**
     * add Plane to tableView and write back in planes.json
     */
    @FXML
    void addPlane(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Empty Field");

        if(nameField.getText().isEmpty()){
            alert.setContentText("Error:cannot create Plane with an empty Name field");
            alert.show();
            return;
        }
        if(dragField.getText().isEmpty()){
            alert.setContentText("Error:cannot create Plane with an empty Drag field");
            alert.show();
            return;
        }
        if(weightField.getText().isEmpty()){
            alert.setContentText("Error:cannot create Plane with an empty Weight field");
            alert.show();
            return;
        }
        if(liftField.getText().isEmpty()){
            alert.setContentText("Error:cannot create Plane with an empty Lift field");
            alert.show();
            return;
        }
        if(areaField.getText().isEmpty()){
            alert.setContentText("Error:cannot create Plane with an empty Area field");
            alert.show();
            return;
        }
        planes.add(new Plane(nameField.getText(),Double.parseDouble(weightField.getText()),Double.parseDouble(dragField.getText()),Double.parseDouble(liftField.getText()),Double.parseDouble(areaField.getText())));
        File file = new File("./src/main/java/com/example/planeproject/planes.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file,planes);
        tableView.setItems(planes);
    }

    /**
     * delete Plane and update planes.json
     */
    @FXML
    void deletePlane(ActionEvent event) throws IOException {
        Plane plane = tableView.getSelectionModel().getSelectedItem();
        planes.remove(plane);
        File file = new File("./src/main/java/com/example/planeproject/planes.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file,planes);
        tableView.setItems(planes);
    }

    /**
     * Show game-plane scene
     */
    @FXML
    void onPlayClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("game-plane.fxml"));
        Parent root = loader.load();
        PlaneGameController gameController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        gameController.movePlaneUp();
                        break;
                    case DOWN:
                        gameController.movePlaneDown();
                        break;
                }
            }
        });
        stage.setScene(scene);
        stage.show();

    }
}