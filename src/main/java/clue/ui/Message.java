package clue.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class Message extends HBox {
    private static final FXMLLoader LOADER = new FXMLLoader(Message.class.getResource("/ui/message.fxml"));

    @FXML
    private Label text;

    public Message() {}

    Message(String msg, boolean flip) {
        LOADER.setRoot(this);
        LOADER.setController(this);
        try {
            LOADER.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        text.setText(msg);
        setAlignment(flip ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        text.setTextAlignment(flip ? TextAlignment.RIGHT : TextAlignment.LEFT);
    }
}
