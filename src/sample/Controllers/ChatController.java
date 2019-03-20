package sample.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.Chat.Client;
import sample.settings.UserMessage;
import sample.settings.UserMessageS;
import java.util.List;


public class ChatController {

    @FXML
    private TableView table;
    public TableColumn userCol;
    public TableColumn mesCol;
    public TableColumn dateCol;

    //Field for searching in DataBase
    public TextField loginField;
    public TextField messageField;
    public TextField dataFromField;
    public TextField dataToField;
    public Button searchButton;
    public ScrollPane scrollPane;
    public Button closeButton;

    public TextFlow chatWindow;

    @FXML
    public TextField chatField;
    public static boolean added = false;
    public static boolean resultReady = false;
    public static boolean mine = false;
    public static List<UserMessage> list;
    private static String message;


    public static void setMessage(String message) {
        ChatController.message = message;
    }

    private String read() {
        return message;
    }


    public static void setList(List<UserMessage> list) {
        ChatController.list = list;
    }

    public static List<UserMessage> getList() {
        return list;
    }

    @FXML
    void initialize() {

        chatWindow.usesMirroring();

        tableWriter();
        dbWriter();

        searchButton.setOnAction(search -> SearchProcessing());

        chatField.setOnKeyPressed(enter -> {
            KeyCode keyCode = enter.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                String line = chatField.getText();
                Client.send(line, 1);
            }
        });

        closeButton.setOnAction(closing -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void SearchProcessing() {
        String user = loginField.getText();
        String message = messageField.getText();
        String dataFrom = dataFromField.getText();
        String dataTo = dataToField.getText();

        UserMessageS messageS = new UserMessageS(user,message,dataFrom,dataTo);
        Client.send(messageS, 7);
        ObservableList<UserMessage> messages = FXCollections.observableArrayList();
        table.setItems(messages);

    }

    private void tableWriter() {
        new Thread(() -> {
            while (true) {
                if (added) {
                    ObservableList list = chatWindow.getChildren();
                    Text text = new Text(message + "\n");
                    text.setFont(Font.font("Raleway Regular", 20));
                    text.setFill(Color.BLACK);
                    Platform.runLater(() -> list.add(text));
                    chatField.clear();
                    scrollPane.setVvalue(1);
                    added = false;
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

//        private Label createMessageLabel (String color,boolean mine){
//            Label label = new Label(message);
//            label.setFont(Font.font("Raleway Regular", 30));
//            label.setPrefSize(230, 100);
//            if (mine) {
//                label.setStyle("-fx-background-color: " + color + " ; -fx-background-radius: 30 30 30 6;");
//            } else label.setStyle("-fx-background-color: " + color + " ; -fx-background-radius: 30 30 30 6;");
//
//            return label;
//        }
        private void dbWriter() {
            new Thread(() -> {
                while (true) {
                    if (resultReady) {
                        ObservableList<UserMessage> result = FXCollections.observableArrayList();
                        result.addAll(getList());
                        Platform.runLater(() -> table.setItems(result));
                        resultReady = false;
                    } else {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

//    private ObservableList<Message> getMessage() {
//        Message user = new Message();
//        return FXCollections.observableArrayList(user);
//    }


    }
