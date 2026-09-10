package clue.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

/**
 * A message block in the GUI.
 */
public class Message extends HBox {
    private static final FXMLLoader LOADER = new FXMLLoader(Message.class.getResource("/ui/message.fxml"));

    @FXML
    private Label text;

    /**
     * Construct a new Message block for display in the GUI.
     *
     * @param msg the text this message block should contain.
     * @param isUser whether to display this box as from the user or from the program.
     */
    public Message(String msg, boolean isUser) {
        LOADER.setRoot(this);
        LOADER.setController(this);
        try {
            LOADER.load();
        } catch (IOException e) {
            // FXML file not found or other loading error
            throw new RuntimeException(e);
        }

        text.setText(msg);
        setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        text.setTextAlignment(isUser ? TextAlignment.RIGHT : TextAlignment.LEFT);
    }
}
