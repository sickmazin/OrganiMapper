package ModuleHomeScreen;

import ModuloCreationGui.CreationGuiApplication;
import com.sun.tools.javac.Main;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class newOrganigrammaButtonController {
    public AnchorPane pane;

    /**
     * Permette di aprire una nuova finestra in cui l'utente potrà personalizzare ed inserire le informazioni del nuovo organigramma
     * */
    public void clickForNewProject(MouseEvent mouseEvent) {
        try {
            CreationGuiApplication creationGuiApplication=new CreationGuiApplication();
            creationGuiApplication.start(new Stage());
            Stage thisStage=(Stage) pane.getScene().getWindow();
            thisStage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
