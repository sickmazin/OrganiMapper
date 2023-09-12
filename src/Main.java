import ModuleOrganigrammaViewer.OrganigrammaViewerApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("fxml/HomeScreen.fxml"));
        Scene scene=new Scene(root);
        stage.setTitle("OrganiMapper");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}