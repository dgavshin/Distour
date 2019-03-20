package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.Animations.Appear;
import sample.Animations.Lift;
import sample.Animations.Shake;
import sample.Chat.Client;
import sample.StartWindow;
import sample.settings.User;

import java.io.IOException;

public class StartWindowController {

    public Pane appearingPaneWhenAppWannaClose;

    public PasswordField passwordField;


    public TextField loginField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button logInButton;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane closeField;

    @FXML
    private Button closing;

    @FXML
    private Button cancelClosing;

    @FXML
    private AnchorPane allWindow;

    public static User getUser() {
        return user;
    }

    public static User user = new User();

    public static Scene scene;

    static String afterSignUpLogin = "";
    static String afterSignUpPassword = "";


    @FXML
    void initialize() {
        new Appear(allWindow).play();
        appearingPaneWhenAppWannaClose.setDisable(true);

        if (!afterSignUpLogin.equals("") && !afterSignUpPassword.equals("")) {
            loginField.setText(afterSignUpLogin);
            passwordField.setText(afterSignUpPassword);
        }

        loginField.setOnKeyPressed(this::handle);
        passwordField.setOnKeyPressed(this::handle);


        closeButton.setOnAction(event -> {
            appearingPaneWhenAppWannaClose.setDisable(false);
            new Appear(appearingPaneWhenAppWannaClose, 400,0,0.36).play();
            Lift lift = new Lift(closeField);

            lift.playDown();
            closing.setOnAction(closing -> {
                Platform.exit();
                System.exit(0);
            });
            cancelClosing.setOnAction(stopClosing -> {
                new Appear(appearingPaneWhenAppWannaClose,400,0.36,0).play();
                appearingPaneWhenAppWannaClose.setDisable(true);
                lift.playUp();
            });
        });

        signUpButton.setOnAction(event -> {
            StartWindow.create("fxmlFiles/SignUpWindow.fxml");
        });

        logInButton.setOnAction(event -> checkFields());
    }

    private void loginUser(String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        Client.helloServer(user, 6);
        Client.nickname = user.getUsername();
    }

    public void wrongAuthorize() {
        loginField.getStyleClass().add("wrong");
        passwordField.getStyleClass().add("wrong");
        Shake userLoginAnimation = new Shake(loginField);
        Shake userPasswordAnimation = new Shake(passwordField);
        userLoginAnimation.play();
        userPasswordAnimation.play();
    }

    private void checkFields() {

        String username = loginField.getText().trim();
        String password = passwordField.getText();

        if (!username.equals("") && !password.equals("")) {
            loginUser(username, password);
        } else {
            wrongAuthorize();
        }
    }

    private void handle(KeyEvent enterLogin) {
        KeyCode keyCode = enterLogin.getCode();
        if (keyCode.equals(KeyCode.ENTER)) {
            checkFields();
        }
    }
}
