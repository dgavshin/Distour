//package sample;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import sample.Controllers.Controllers;
//
//import java.io.IOException;
//
//public class StartServer extends Application {
//
//    private static Stage primaryStage;
//    private static FXMLLoader loader;
//
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        StartServer.primaryStage = primaryStage;
//        createConst();
//        Parent root = FXMLLoader.load(getClass().getResource("fxmlFiles/ServerWindow.fxml"));
//        Scene scene = new Scene(root, 1280, 720);
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Distour");
//        primaryStage.show();
//    }
//
//    void createConst() {
//        FXMLLoader mainLoader = new FXMLLoader();
//        mainLoader.setLocation(getClass().getResource("fxmlFiles/StartWindow.fxml"));
//        Controllers.startWindowController = mainLoader.getController();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
