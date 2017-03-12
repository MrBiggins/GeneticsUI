package viewmodel;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by denistimchenko on 12.03.17.
 */
public class Extentions {


    public static void showMessage(Exception ex) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(new Group(new Text(25, 25, ex.getMessage())));
        dialog.setScene(scene);
        dialog.show();
    }
}
