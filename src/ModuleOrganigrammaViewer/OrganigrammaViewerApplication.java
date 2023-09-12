package ModuleOrganigrammaViewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrganigrammaViewerApplication extends Application {
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/OrganigrammaViewer.fxml"));
        Parent p = fxmlLoader.load();
        Scene scene=new Scene(p);
        stage.setScene(scene);
        stage.setTitle("OrganiMapper");
        stage.setMaximized(true);
        stage.show();

    }
    public OrganigrammaViewerController getController(){
        return fxmlLoader.getController();
    }
}
