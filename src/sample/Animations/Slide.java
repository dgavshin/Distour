package sample.Animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Slide {

    private TranslateTransition tt;
    private double setFromX = 310;
    private double setToX = -310;

    public Slide(Node node, double setFromX, double setToX, double duration) {
        tt = new TranslateTransition(Duration.millis(duration), node);
        this.setFromX = setFromX;
        this.setToX = setToX;
    }

    public Slide(Node node) {
        tt = new TranslateTransition(Duration.millis(150), node);
    }

    public void playLeft() {
        tt.setFromX(setFromX);
        tt.setToX(setToX);
        tt.playFromStart();
    }

    public void playRight() {
        tt.setFromX(setToX);
        tt.setToX(setFromX);
        tt.playFromStart();
    }


}
