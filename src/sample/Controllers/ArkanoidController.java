package sample.Controllers;

import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ArkanoidController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle userPlatform;

    public AnchorPane mainPane;
    public TextField kostil;

    @FXML
    void initialize() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), userPlatform);


        kostil.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.A)) {
                double x = userPlatform.getLayoutX();
                System.out.println("left");
                if (x - 20 > 0) {
//                    userPlatform.setLayoutX(x - 10);
                    tt.setByX(-60);
                    tt.playFromStart();
                    tFill();
                }
            } else if (keyCode.equals(KeyCode.D)) {
                double x = userPlatform.getLayoutX();
                System.out.println("rigth");
                if (x + 20 < 930) {
//                    userPlatform.setLayoutX(x + 10);
                    tt.setByX(60);
                    tt.play();
                }
            }
        });

    }

    public void tFill() {
        Group g = new Group();

        System.out.println("gotcha!!!");
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setColor(Color.color(0.4, 0.4, 0.4));

        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(50.0f);
        ellipse.setCenterY(50.0f);
        ellipse.setRadiusX(50.0f);
        ellipse.setRadiusY(25.0f);
        ellipse.setEffect(ds);

        Rectangle rect = new Rectangle (100, 40, 100, 100);
        rect.setArcHeight(50);
        rect.setArcWidth(50);
        rect.setFill(Color.VIOLET);

        ScaleTransition st = new ScaleTransition(Duration.millis(2000), rect);
        st.setByX(1.5f);
        st.setByY(1.5f);
        st.setCycleCount(4);
        st.setAutoReverse(true);

        st.play();
        //Переход цвета в другой
        //FillTransition ft = new FillTransition(Duration.millis(1000), ellipse, Color.RED, Color.BLUE);

        ScaleTransition ft = new ScaleTransition(Duration.millis(1000),ellipse);
        ft.setAutoReverse(true);
        ft.play();

        g.getChildren().add(rect);
        mainPane.getChildren().add(g);
    }
}
