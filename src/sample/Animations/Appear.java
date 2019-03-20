package sample.Animations;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Appear {
 private FadeTransition ft;

    public Appear(Node node) {
        ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0);
        ft.setToValue(1);
//        ft.setCycleCount(4);
//        ft.setAutoReverse(true);
    }
    public Appear(Node node, double duration, double setFrom, double setTo) {
        ft = new FadeTransition(Duration.millis(duration), node);
        ft.setFromValue(setFrom);
        ft.setToValue(setTo);
    }

    public void play() {
        ft.playFromStart();
    }
}
