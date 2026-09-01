package clue.ui;

import java.io.CharArrayWriter;


import clue.Clue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {
    private class Output extends CharArrayWriter {
        @Override
        public void flush() {
            writeResponse(toString());
            reset();
        }
    }

    private final Ui ui;

    @FXML
    private TextField entryField;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messages;

    public Gui() {
        // initialize Ui object with my writer
        // Do not provide an input stream, we will call receive on any input.
        ui = new Ui(Clue.getParser(), null, new Output());
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(messages.heightProperty());
        ui.preamble();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/main_window.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap);
        stage.setScene(scene);
        stage.show();
    }

    public void writeResponse(String out) {
        messages.getChildren().add(new Message(out, true));
    }

    public void onInput() {
        String txt = entryField.getText();
        messages.getChildren().add(new Message(txt, false));
        if (!ui.receive(txt)) {
            // exit
            Platform.exit();
        }

        entryField.clear();
    }
}
