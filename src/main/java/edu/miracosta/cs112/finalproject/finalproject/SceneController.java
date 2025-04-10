package edu.miracosta.cs112.finalproject.finalproject;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;

// delete this
public class SceneController {
    public Scene scene;
    private HashMap<String, Pane> screenMap = new HashMap<>();

    public SceneController(Scene scene) {
        this.scene = scene;
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        scene.setRoot( screenMap.get(name) );
    }
}
