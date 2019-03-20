package sample.Animations;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class DisablingOptions {

    private Node node;

    public DisablingOptions(Node node) {
        this.node = node;
    }

    public void hide(){
        node.setDisable(true);
        node.setOpacity(0);
    }
    public void unHide(){
        node.setDisable(false);
        node.setOpacity(1);
    }
}
