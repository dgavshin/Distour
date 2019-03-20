package sample.Animations;


import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Lift {

    private TranslateTransition tt;

    public Lift (Node node) {
        tt = new TranslateTransition(Duration.millis(150), node);
        tt.setFromY(0);
        tt.setByY(720);
    }
    public void playUp() {
        tt.setFromY(720);
        tt.setByY(-720);
        tt.play();
    }
    public void playDown() {
        tt.playFromStart();
    }
}
