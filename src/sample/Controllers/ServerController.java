//package sample.Controllers;
//
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextField;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
//import sample.Chat.Client;
//import sample.Chat.Server;
//
//import java.io.IOException;
//import java.net.URL;
//import java.rmi.MarshalException;
//import java.util.ResourceBundle;
//
//public class ServerController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private TextFlow consoleFlow;
//
//    @FXML
//    private TextField consoleField;
//
//    @FXML
//    private Button startServer;
//
//    @FXML
//    private TextField ipField;
//
//    @FXML
//    private TextField portField;
//    public ScrollPane scrollPane;
//
//    private Server server = new Server();
//    private static boolean working = false;
//    public static boolean added = false;
//    public static String message;
//
//    @FXML
//    void initialize() {
////        tableWriter();
//
//        consoleField.setOnKeyPressed(enter -> {
//            KeyCode keyCode = enter.getCode();
//            if (keyCode.equals(KeyCode.ENTER)) {
//                String line = consoleField.getText();
//                write(line);
//            }
//        });
//        startServer.setOnAction(event -> {
//            try {
//                if (!working) {
//                    String ip = ipField.getText().trim();
//                    int port = Integer.parseInt(portField.getText().trim());
////                    server.setIp(ip);
////                    server.setPort(port);
//                    server.start();
//
//                    portField.clear();
//                    ipField.clear();
//
//                    startServer.setText("Остановить");
//                    working = true;
//                } else {
//                    working = false;
//                    startServer.setText("Запустить");
//                    server.end();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    void write(String message) {
//        ObservableList list = consoleFlow.getChildren();
//        Text text = new Text(message + "\n");
//        text.setFont(Font.font("Raleway Regular", 15));
//        text.setFill(Color.BLACK);
//        Platform.runLater(() -> list.add(text));
//        consoleField.clear();
//        scrollPane.setVvalue(1);
//    }
//    private void tableWriter() {
//        new Thread(() -> {
//            while (true) {
//                if (added) {
//                    added = false;
//                    ObservableList list = consoleFlow.getChildren();
//                    Text text = new Text(message + "\n");
//                    text.setFont(Font.font("Raleway Regular", 15));
//                    text.setFill(Color.BLACK);
//                    Platform.runLater(() -> list.add(text));
//                    consoleField.clear();
//                    scrollPane.setVvalue(1);
//                }
//            }
//        }).start();
//    }
//}
