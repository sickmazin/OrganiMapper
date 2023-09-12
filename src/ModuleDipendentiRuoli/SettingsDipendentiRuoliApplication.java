package ModuleDipendentiRuoli;

import ModuleOrganigrammaViewer.OrganigrammaViewerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsDipendentiRuoliApplication extends Application {

    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/SettingsDipendentieRuoli.fxml"));
        Parent p = fxmlLoader.load();
        Scene scene=new Scene(p);
        stage.setScene(scene);
        stage.setTitle("Dipendenti e ruoli");
        stage.show();

    }
    public ControllerSettingDipendentiRuoli getController(){
        return fxmlLoader.getController();
    }

}
