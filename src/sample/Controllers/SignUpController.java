package sample.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.Animations.Appear;
import sample.Animations.Lift;
import sample.Animations.Shake;
import sample.Chat.Client;
import sample.settings.User;

public class SignUpController {

    public Button closeButton;
    public AnchorPane closeField;
    public Button closing;
    public Button cancelClosing;
    public static int counter = 128;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField emailField;
    @FXML
    private AnchorPane allWindow;

    private static boolean emailAccess = false;
    private static User user = new User();

    public static User getUser() {
        return user;
    }

    @FXML
    void initialize() {

        closeButton.setOnAction(event -> {
            Lift lift = new Lift(closeField);
            lift.playDown();
            closing.setOnAction(closing -> {
                Client.send("", 3);
                Platform.exit();
                System.exit(0);
            });
            cancelClosing.setOnAction(stopClosing -> {
                lift.playUp();
            });
        });

        Appear appear = new Appear(allWindow);
        appear.play();

        signUpButton.setOnAction(event -> signUpNewUser());
    }

    private void signUpNewUser() {
        String username = loginField.getText().trim().toLowerCase();
        String password = passwordField.getText().trim();
        String email = emailField.getText().trim().toLowerCase();

        try {
            if (email.contains("@")) {
                String[] emailParts = email.split("@");
                if (emailParts[1].contains(".")) {
                    emailAccess = true;
                } else {
                    Shake userEmailAnimation = new Shake(emailField);
                    userEmailAnimation.play();
                    System.out.println("Неправильный формат почты");
                }
            } else {
                Shake userEmailAnimation = new Shake(emailField);
                userEmailAnimation.play();
                System.out.println("Неправильный формат почты");
            }


            if (!username.equals("") && !password.equals("") && emailAccess) {
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                StartWindowController.afterSignUpLogin = user.getUsername();
                StartWindowController.afterSignUpPassword = user.getPassword();

                Client.helloServer(user, 5);

            } else {
                counter = 128;
                wrongAuthorize();

            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    public void wrongAuthorize() {
        System.out.println("opa");
        loginField.getStyleClass().add("wrong");
        passwordField.getStyleClass().add("wrong");
        emailField.getStyleClass().add("wrong");

        Shake userLoginAnimation = new Shake(loginField);
        Shake userPasswordAnimation = new Shake(passwordField);
        Shake userEmailAnimation = new Shake(emailField);
        userLoginAnimation.play();
        userPasswordAnimation.play();
        userEmailAnimation.play();
    }
}

