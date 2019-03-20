package sample.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import sample.Animations.*;
import sample.Chat.Client;
import sample.StartWindow;
import sample.settings.GameMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class CategoriesController {

    /*
        █───█─████─███─█──█─────█───█─███─█──█─████──████─█───█
        ██─██─█──█──█──██─█─────█───█──█──██─█─█──██─█──█─█───█
        █─█─█─████──█──█─██─███─█─█─█──█──█─██─█──██─█──█─█─█─█
        █───█─█──█──█──█──█─────█████──█──█──█─█──██─█──█─█████
        █───█─█──█─███─█──█──────█─█──███─█──█─████──████──█─█
         */
    public AnchorPane mainWindow;

    public Pane categoriesPane;
    public Button chooseSportButton;
    public Button chooseHotButton;
    public Button chooseCybersportButton;

    public Button closeButton;

    public Pane profileMenu;
    public Button changeAvatarButton;
    public ImageView avatarView;
    public Text userLevelField;
    public FlowPane flowAvatars;
    public Pane avatarsPane;
    public Button closeAvatarsPane;
    public Text nickname;
    public Pane changeNicknamePane;
    public TextField nicknameField;
    public Button changeNickname;
    public Button cancelChange;

    public Button slideToCurrentFromMain;
    private static ImageView imageForSlideToCurrentFromMain = new ImageView();

    public Button slideToResultFromMain;
    public ImageView imageForSlideToResultFromMain;

    private static Pane searchPane = new Pane();
    private static Pane searchBox = new Pane();
    private static Label searchLabel = new Label("Поиск соперника");
    private static Button cancelSearch = new Button("Отмена");
    private static ImageView duck = new ImageView(new Image("file:///C:/Users/shokk/Desktop/distour/src/sample/assets/XOsX.gif"));
    private static Slide search = new Slide(searchPane, 953, -953, 150);

    private static Pane findBox = new Pane();
    private static Label findLabel = new Label("Игра найдена");
    private static Button acceptFind = new Button("ОК");
    private static Slide find = new Slide(findBox, 256, -256, 150);

    public Pane holdForHideProfilePane;

    public AnchorPane sportPane;
    public Button startSearchSport;

    public AnchorPane hotPane;
    public Button startSearchHot;

    public Pane cybersportPane;
    public Button startSearchCybersport;

    public AnchorPane closeField;
    public Button closing;
    public Button cancelClosing;
    public Button logOut;

    /*
    ████ ███ ███ █ █ █   ███     █   █ ███ █  █ ████  ████ █   █
    █  █ █   █   █ █ █    █      █   █  █  ██ █ █  ██ █  █ █   █
    ████ ███ ███ █ █ █    █  ███ █ █ █  █  █ ██ █  ██ █  █ █ █ █
    █ █  █     █ █ █ █    █      █████  █  █  █ █  ██ █  █ █████
    █ █  ███ ███ ███ ███  █       █ █  ███ █  █ ████  ████  █ █
     */
    public AnchorPane resultWindow;

    public Button slideToMainFromResult;
    public ImageView imageForSlideToMainFromResult;
    public AnchorPane lastGamesPane;

    private static Pane resultPane = new Pane();
    private static ScrollPane scrollPaneForResult = new ScrollPane();


    /*
    ████─█─█─████─████─███─█──█─███─────█───█─███─█──█─████──████─█───█
    █──█─█─█─█──█─█──█─█───██─█──█──────█───█──█──██─█─█──██─█──█─█───█
    █────█─█─████─████─███─█─██──█──███─█─█─█──█──█─██─█──██─█──█─█─█─█
    █──█─█─█─█─█──█─█──█───█──█──█──────█████──█──█──█─█──██─█──█─█████
    ████─███─█─█──█─█──███─█──█──█───────█─█──███─█──█─████──████──█─█
     */
    public AnchorPane currentWindow;

    public Button slideToMainFromCurrent;
    public ImageView imageForSlideToMainFromCurrent;
    private static Pane currentPane1 = new Pane();
    private static Pane currentPane2 = new Pane();
    private static Pane currentPane3 = new Pane();

    private static final FileChooser fileChooser = new FileChooser();


    private static boolean onDisplay = false;
    private static boolean open = false;
    public static boolean ready = false;
    private static Slide slide;
    public static int nowGameId;
    private static boolean downloaded;


    public static void showReadyGame() {
        imageForSlideToCurrentFromMain.setImage(new Image("file:///C:/Users/shokk/Desktop/distour/src/sample/assets/arrowUpdate.png"));
        search.playLeft();
        find.playRight();
    }

    @FXML
    void initialize() {
        Platform.runLater(() -> {


            //SETTING FOR SEARCH PANE
            {
                setButton(cancelSearch);
                setLabel(searchLabel);

                searchPane.setLayoutX(-953);
                searchPane.setLayoutY(126);
                searchPane.setPrefWidth(953);
                searchPane.setPrefHeight(523);

                searchBox.setLayoutX(0);
                searchBox.setLayoutY(122);
                searchBox.setPrefWidth(256);
                searchBox.setPrefHeight(212);

                duck.setLayoutX(76);
                duck.setLayoutY(6);
                duck.setFitHeight(129);
                duck.setFitWidth(105);

                cancelSearch.setOnAction(cancel -> {
                    search.playLeft();
                    Client.send("Прекращение поиска игры", 2);
                });

                searchBox.getStyleClass().add("searchBox");
                searchBox.getChildren().addAll(cancelSearch, searchLabel, duck);
                searchPane.getChildren().add(searchBox);
                mainWindow.getChildren().add(searchPane);
            }
            //SETTING FOR FIND PANE
            {
                setLabel(findLabel);
                setButton(acceptFind);

                acceptFind.setOnAction(cancel -> find.playLeft());

                findBox.setLayoutX(-256);
                findBox.setLayoutY(248);
                findBox.setPrefWidth(256);
                findBox.setPrefHeight(212);

                findBox.getStyleClass().add("searchBox");
                findBox.getChildren().addAll(findLabel, acceptFind);
                mainWindow.getChildren().add(findBox);

            }
            //SETTING FOR IMAGE VIEW;
            {
                imageForSlideToCurrentFromMain.setImage(new Image("file:///C:/Users/shokk/Desktop/distour/src/sample/assets/slideUp-Down.png"));
                imageForSlideToCurrentFromMain.setRotate(180);
                imageForSlideToCurrentFromMain.setFitWidth(35);
                imageForSlideToCurrentFromMain.setFitHeight(35);
                imageForSlideToCurrentFromMain.setOpacity(0.45);
                slideToCurrentFromMain.graphicProperty().setValue(imageForSlideToCurrentFromMain);
            }

            //SETTING FOR CURRENT PANES
            {
                currentPane1.setPrefWidth(346);
                currentPane1.setPrefHeight(516);

                currentPane2.setPrefWidth(346);
                currentPane2.setPrefHeight(516);

                currentPane3.setPrefWidth(346);
                currentPane3.setPrefHeight(516);

                currentPane1.setLayoutY(125);
                currentPane2.setLayoutY(125);
                currentPane3.setLayoutY(125);

                currentPane1.setLayoutX(82);
                currentPane2.setLayoutX(473);
                currentPane3.setLayoutX(851);

                currentWindow.getChildren().addAll(currentPane1, currentPane2, currentPane3);
            }

            //SETTINGS FOR RESULT PANE
            {
                resultPane.setLayoutX(469);
                resultPane.setLayoutY(130);
                resultPane.setPrefHeight(799);
                resultPane.setPrefWidth(547);
                resultWindow.getChildren().add(resultPane);
            }
            //SETTINGS FOR SCROLL PANE ON RESULT WINDOW
            {
                scrollPaneForResult.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPaneForResult.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                scrollPaneForResult.setMaxHeight(461);
                scrollPaneForResult.setMaxWidth(412);
                lastGamesPane.getChildren().add(scrollPaneForResult);
            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(400), profileMenu);
        Appear appear = new Appear(mainWindow);
        appear.play();

//CHANGING WINDOWS
        ChangeWindow change1 = new ChangeWindow(mainWindow);
        ChangeWindow change2 = new ChangeWindow(resultWindow);
        ChangeWindow change3 = new ChangeWindow(currentWindow);

        new ClickMe(imageForSlideToMainFromResult).play();
        new ClickMe(imageForSlideToResultFromMain).play();
        new ClickMe(imageForSlideToCurrentFromMain).play();
        new ClickMe(imageForSlideToMainFromCurrent).play();

        slideToMainFromResult.setOnAction(toMain -> {
            change1.down();
            change2.down();
        });
        slideToResultFromMain.setOnAction(toResult -> {
            change1.up();
            change2.up();
            Client.send("", 12);
        });
        slideToCurrentFromMain.setOnAction(toResult -> {
            imageForSlideToCurrentFromMain.setImage(new Image("file:///C:/Users/shokk/Desktop/distour/src/sample/assets/slideUp-Down.png"));
            change1.down();
            change3.down();
            Client.send("", 12);
        });
        slideToMainFromCurrent.setOnAction(toResult -> {
            change1.up();
            change3.up();
        });

//PROFILE PANEL
        DisablingOptions avatars = new DisablingOptions(avatarsPane);
        DisablingOptions changeNick = new DisablingOptions(changeNicknamePane);

        nickname.setOnMouseClicked(event -> {
            changeNick.unHide();
        });

        cancelChange.setOnAction(event -> changeNick.hide());

        changeNickname.setOnAction(event -> {
            Client.send(nicknameField.getText(),18);
            changeNick.hide();
        });

        profileMenu.setOnMouseMoved(mouseEvent -> {
            tt.setFromX(0);
            tt.setByX(-190);
            if (!open) {
                tt.playFromStart();
                open = true;
            }
        });

        holdForHideProfilePane.setOnMouseMoved(mouseEvent -> {
            if (open) {
                avatars.hide();
                tt.setFromX(-190);
                tt.setByX(190);
                tt.playFromStart();
                open = false;
            }
        });

        closeAvatarsPane.setOnAction(closeAvatars -> {
            avatars.hide();
        });

        changeAvatarButton.setOnAction(changeAvatar -> {
            if (!downloaded) {
                try {
                    for (Path entry : Files.walk(Paths.get("c:/users/shokk/desktop/distour/src/sample/assets/avatars")).
                            filter(Files::isRegularFile).
                            collect(Collectors.toList())) {
                        ImageView image = new ImageView(new Image("file:///" + entry.toString()));
                        image.setFitHeight(50);
                        image.setFitWidth(50);
                        image.setOnMouseClicked(event -> {
                            Client.send(entry.getFileName().toString(), 16);
                            avatars.hide();
                        });
                        image.setOpacity(0.8);
                        flowAvatars.getChildren().add(image);
                        downloaded = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flowAvatars.getChildren().addAll();
                avatars.unHide();
            } else avatars.unHide();
        });

//CATEGORIES BUTTON

        //Hit to close left panel with selected category
        mainWindow.setOnMouseReleased(event -> {
            if (onDisplay) {
                slide.playLeft();
                onDisplay = false;
            }
        });

        startSearchHot.setOnAction(event -> {
            Client.send("", 8);
            slide.playLeft();
            onDisplay = false;
            search.playRight();
        });
        startSearchSport.setOnAction(event -> {
            Client.send("", 9);
            slide.playLeft();
            onDisplay = false;
            search.playRight();
        });
        startSearchCybersport.setOnAction(event -> {
            Client.send("", 10);
            slide.playLeft();
            onDisplay = false;
            search.playRight();
        });

        chooseHotButton.setOnAction(hot -> {
            if (onDisplay)
                slide.playLeft();
            slide = new Slide(hotPane);
            slide.playRight();
            onDisplay = true;
        });

        chooseCybersportButton.setOnAction(cybersport -> {
            if (onDisplay)
                slide.playLeft();
            slide = new Slide(cybersportPane);
            slide.playRight();
            onDisplay = true;
        });

        chooseSportButton.setOnAction(sport -> {
            if (onDisplay)
                slide.playLeft();
            slide = new Slide(sportPane);
            slide.playRight();
            onDisplay = true;
        });


//CLOSE APP
        closeButton.setOnAction(event -> {
            if (onDisplay)
                slide.playLeft();
            Lift lift = new Lift(closeField);
            lift.playDown();
            onDisplay = false;

            closing.setOnAction(closing -> {
                Client.send("", 3);
                Platform.exit();
                System.exit(0);
            });
            cancelClosing.setOnAction(stopClosing -> lift.playUp());

            logOut.setOnAction(logOut -> {
                Client.send("", 3);
                StartWindow.create("fxmlFiles/StartWindow.fxml");
            });
        });
    }

    private void setButton(Button acceptFind) {
        acceptFind.setLayoutX(40);
        acceptFind.setLayoutY(135);
        acceptFind.setPrefWidth(178);
        acceptFind.setPrefHeight(38);
        acceptFind.getStyleClass().add("lsButton");
        acceptFind.setTextFill(Color.WHITE);
        acceptFind.setEffect(new Bloom(0.3));
    }

    private void setLabel(Label findLabel) {
        findLabel.setLayoutX(40);
        findLabel.setLayoutY(14);
        findLabel.setPrefWidth(178);
        findLabel.setPrefHeight(30);
        findLabel.setFont(Font.font(20));
        findLabel.setTextFill(Color.WHITE);
        findLabel.setEffect(new Bloom(0.3));
    }

    public static void setLastGames(List<GameMessage> list) {
        FlowPane flowPaneForResults = new FlowPane();
        flowPaneForResults.setHgap(412);
        flowPaneForResults.setVgap(10);
        flowPaneForResults.setPadding(new Insets(10));
        Platform.runLater(() -> {
            for (GameMessage entry : list) {
                if (entry.getWinner().equalsIgnoreCase(Client.nickname)) {
                    Button button = new Button("WIN " + entry.getCategory());
                    setResultButton(flowPaneForResults, entry, button);
                } else if (!entry.getWinner().equalsIgnoreCase("nothing")) {
                    Button button = new Button("LOST " + entry.getCategory());
                    setResultButton(flowPaneForResults, entry, button);
                }
            }
            scrollPaneForResult.setContent(flowPaneForResults);
        });
    }

    private static void setResultButton(FlowPane flowPaneForResults, GameMessage entry, Button button) {
        button.setPrefSize(412, 60);
        button.getStyleClass().add("resultButton");
        button.setAlignment(Pos.CENTER_LEFT);
        button.setFont(Font.font("Raleway regular", 20));
        button.setOnAction(event -> {
            resultPane.getChildren().clear();
            createResultInfo(entry);
        });
        flowPaneForResults.getChildren().add(button);
    }

    private static void createResultInfo(GameMessage game) {

        Text category = setTextStyle(new Text(game.getCategory()), 374, 83, 25);
        Text exercise = setTextStyle(new Text(game.getExercise()), 246, 277, 36);
        Text nick1 = setTextStyle(new Text(game.getPlayer1()), 36, 92, 45);
        Text nick2 = setTextStyle(new Text(game.getPlayer2()), 494, 92, 45);

        resultPane.getChildren().addAll(category, exercise, nick1, nick2);
    }

    private static Label setStyle(Label label, double layoutY) {
        label.setLayoutX((double) 0);
        label.setLayoutY(layoutY);
        label.setFont(Font.font("Raleway Regular", 25));
        return label;
    }

    private static Text setTextStyle(Text text, double layoutX, double layoutY, int fontSize) {
        text.setLayoutX(layoutX);
        text.setLayoutY(layoutY);
        text.setFont(Font.font("Raleway Regular", fontSize));
        return text;
    }


    //JUST FOR TEST
//    private static class Delta {
//        double x, y;
//    }

    //Drag any node
//    public void makeDraggable(final Node byNode) {
//        final Delta dragDelta = new Delta();
//
//        byNode.setOnMousePressed(mouseEvent -> {
//            dragDelta.x = byNode.getLayoutX() - mouseEvent.getScreenX();
//            dragDelta.y = byNode.getLayoutY() - mouseEvent.getScreenY();
//        });
//        byNode.setOnMouseDragged(mouseEvent -> {
//            byNode.setLayoutX(mouseEvent.getScreenX() + dragDelta.x);
//            byNode.setLayoutY(mouseEvent.getScreenY() + dragDelta.y);
//        });
//    }

//    public void scroll(Node node) {
//        Platform.runLater(() -> {
//            node.getScene().setOnScrollStarted(event -> get(event));
//        });
//    }
//
//    private void get(ScrollEvent event) {
//        if(event.getDeltaY() > )
//
//    }

    public static void createCurrentGame(List<GameMessage> list) {
        int busyPaneCounts = 0;
        for (GameMessage entry : list) {
            if (entry.getWinner().equalsIgnoreCase("nothing")) {
                if (busyPaneCounts == 0) {
                    currentPane1.getChildren().clear();
                    currentPane1.getChildren().add(setCurrentInfo(entry));
                    busyPaneCounts++;
                } else if (busyPaneCounts == 1) {
                    currentPane2.getChildren().clear();
                    currentPane2.getChildren().add(setCurrentInfo(entry));
                    busyPaneCounts++;
                } else {
                    currentPane3.getChildren().clear();
                    currentPane3.getChildren().add(setCurrentInfo(entry));
                }
            }
        }
    }

    private static Pane setCurrentInfo(GameMessage game) {
        Label categoryText = setStyle(new Label(game.getCategory()), 0);
        Label exerciseText = setStyle(new Label(game.getExercise()), 100);
        Button uploadVideo = new Button("Upload video");

        categoryText.setPrefHeight(100);
        categoryText.setPrefWidth(345);
        categoryText.setTextFill(Color.WHITE);
        categoryText.setAlignment(Pos.CENTER);

        exerciseText.setPrefWidth(345);
        exerciseText.setPrefHeight(100);
        exerciseText.setTextFill(Color.WHITE);
        exerciseText.setContentDisplay(ContentDisplay.CENTER);
        exerciseText.setWrapText(true);
        exerciseText.setPadding(new Insets(10));

        uploadVideo.setFont(Font.font("Raleway Regular", 25));
        uploadVideo.setTextFill(Color.WHITE);
        uploadVideo.setLayoutY(420);
        uploadVideo.getStyleClass().add("lsButton");
        uploadVideo.setContentDisplay(ContentDisplay.CENTER);

        Pane pane = new Pane();
        pane.getChildren().addAll(categoryText, exerciseText, uploadVideo);

        uploadVideo.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(StartWindow.getPrimaryStage());
            if (file != null && file.getName().contains(".mp4")) {
                uploadVideo.setDisable(true);
                Client.sendMedia(file, game.getGameId());
            } else System.out.println("Это не mp4");
        });
        return pane;
    }

//Open file on computer
//    private void openFile(File file) {
//        try {
//            this.desktop.open(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
