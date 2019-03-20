package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Chat.Client;
import sample.Controllers.ControllersConst;

import java.io.IOException;

public class StartWindow1 extends Application {

    public static Stage primaryStage;
    private static double xOffset;
    private static double yOffset;
//    public static Scene currentScene;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Client.connect();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);

        StartWindow1.primaryStage = primaryStage;
        create("fxmlFiles/ArkanoidController.fxml");
    }

    @Override
    public void stop() {
        Client.send("", 3);
        System.exit(0);
    }

    public static void create(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StartWindow1.class.getResource(fxmlFile));
        Parent root = loader.load();
        switch (fxmlFile) {
            case "fxmlFiles/StartWindow.fxml":
                ControllersConst.startWindowController = loader.getController();
                break;
            case "fxmlFiles/Categories.fxml":
                ControllersConst.categoriesController = loader.getController();
                break;
            case "fxmlFiles/SignUpWindow.fxml":
                ControllersConst.signUpController = loader.getController();
                break;
        }
        Scene scene = new Scene(root, 1280, 720);
        setStageStyle(scene);
    }

    private static void setStageStyle(Scene scene) {
        scene.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Distour");
            primaryStage.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
