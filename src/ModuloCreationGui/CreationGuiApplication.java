package ModuloCreationGui;

import ModuleOrganigrammaViewer.OrganigrammaViewerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreationGuiApplication extends Application {
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/creationGui.fxml"));
        Parent p = fxmlLoader.load();
        Scene scene=new Scene(p);
        stage.setScene(scene);
        stage.setTitle("Informazioni dell'organigramma");
        stage.show();

    }
    public CreationGuiController getController(){
        return fxmlLoader.getController();
    }

}
