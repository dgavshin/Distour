package sample.Animations;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ClickMe {

    private TranslateTransition tt;

    public ClickMe(Node node) {
        tt = new TranslateTransition(Duration.millis(1000),node);
        tt.setFromY(0f);
        tt.setByY(10f);
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setAutoReverse(true);
    }

    public void play() {
        tt.playFromStart();
    }
    public void stop() {
        tt.stop();
    }
}
