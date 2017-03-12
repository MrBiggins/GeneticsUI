package viewmodel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Tool for solving: a+2b+3c+4d=30");

            setDockIcon();

            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Error message: "+ex.getMessage()).showAndWait();
        }
    }

    public void setDockIcon() {
        URL iconURL = Main.class.getResource("/viewmodel/icon.png");
        java.awt.Image image = new ImageIcon(iconURL).getImage();
        com.apple.eawt.Application.getApplication().setDockIconImage(image);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
