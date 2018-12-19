package ars.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class UtilityServices {
    public static JFXDialog dialog;
    public static JFXButton button;;
    public static void displayDialog(Text heading, Text body, StackPane myController) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setStyle("-fx-background-color: #607D8B; -fx-fill: white;");
        heading.setFill(Color.WHITE);
        body.setFill(Paint.valueOf("#BDBDBD"));
        content.setHeading(heading);
        content.setBody(body);
        dialog = new JFXDialog(myController, content, JFXDialog.DialogTransition.CENTER);
        button = new JFXButton("OK");
        button.setStyle("-fx-background-color:  #006064 ; -jfx-button-type: FLAT ; -fx-text-fill: white ");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }
}
