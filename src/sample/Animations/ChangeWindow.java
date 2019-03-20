package sample.Animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ChangeWindow {

    private TranslateTransition tt;

    public ChangeWindow(Node node) {
        tt = new TranslateTransition(Duration.millis(300),node);
    }
    public void down(){
//        tt.setFromY(-1280);
        tt.setByY(720);
        tt.playFromStart();
    }
    public void up() {
//        tt.setFromY(0);
        tt.setByY(-720);
        tt.playFromStart();
    }
}