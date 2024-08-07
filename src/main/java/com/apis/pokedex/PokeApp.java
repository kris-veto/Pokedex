package com.apis.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PokeApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("Pokémon Viewer");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png"))); // adding personalized icon to the stage
        showScene("primary.fxml");
    }
    public void showScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.initData(this, fxml);  // Pass main application reference and FXML file name

        stage.setScene(new Scene(root));
        stage.show();
    }
    public Stage getPrimaryStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}