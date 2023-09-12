package ModuleOrganigrammaViewer;

import ModuleDipendentiRuoli.Ruolo;
import PossibleModelOrg.MediatorUnita;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BoxRuoli extends HBox {
    private TextArea textArea;
    private final String RUOLO_REGEX="\\s*[A-Z][a-z]+\\s*";
    private List<Ruolo> ruoli=new ArrayList<>();
    private MediatorUnita mediator;

    public BoxRuoli(double x, double y) {
        super();
        setLayoutX(x-10);
        setLayoutY(y);
        setAlignment(Pos.CENTER);
        setPrefHeight(156);
        setPrefWidth(450);


        getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        getStyleClass().add("info-box");
        // Create the VBox
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        // Create the Label
        Label label = new Label("Inserisci i ruoli possibili all'interno dell'unita");
        label.setFont(Font.font("Cambria", 14));
        label.setPrefHeight(87.0); label.setPrefWidth(94.0);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);
        label.setTextFill(Color.WHITE);
        vBox.getChildren().add(label);

        // Create the Button
        Button button = new Button("Inserisci");
        button.setFont(Font.font("Cambria", 11));
        button.setPrefHeight(30.0);
        button.setPrefWidth(90.0);
        button.getStyleClass().add("inserisciRuoli-button");
        button.setFont(new Font("Cambria", 16));
        button.setTextFill(Color.WHITE);
        button.setOnAction(this::setRuoli);
        vBox.getChildren().add(button);

        // Create the TextArea
        textArea = new TextArea();
        textArea.setPromptText("Ruolo, ruolo, ...");
        textArea.setWrapText(true);
        textArea.setPrefHeight(150.0);
        textArea.setPrefWidth(332.0);

        setMargin(textArea, new Insets(7, 14, 7, 5));
        // Add the VBox and TextArea to the HBox
        getChildren().addAll(vBox, textArea);
        setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)) setVisible(false);
        });
    }

    private void setRuoli(ActionEvent actionEvent) {
        mediator.setRuoliDellUnita(getRuoliInseriti());
        setVisible(false);
    }


    /**
     * @return una lista di ruoli ottenuti leggendo dalla text area ciò che l'utente a inserito.
     * Il metodo accetta ruoli inseriti come segue:
     * <li>Direttore, segretaria</li>
     * <li>Direttore , segretaria</li>
     * <li>Direttore, \n
     *     segretaria</li>
        <li> Ingegniere del software, dipendente</li>
     */
    private List<Ruolo> getRuoliInseriti() {
        String text=textArea.getText(),linea,ruolo="",fine;
        Scanner sc= new Scanner(text);
        while (sc.hasNext()){
            linea=sc.nextLine();
            StringTokenizer st = new StringTokenizer(linea, " ");
            if(linea.matches("["+RUOLO_REGEX+"\\,]*")){
                while (st.hasMoreTokens()) {
                    ruolo+=" "+st.nextToken();
                    if(ruolo.toLowerCase().contains(",")){
                        fine="";
                        for (int i = 0; i < ruolo.toLowerCase().length(); i++) {
                            if(ruolo.charAt(i)==',') break;
                            fine+=ruolo.charAt(i);
                        }
                        ruoli.add(new Ruolo(fine.trim()));
                        ruolo="";
                    }
                    if(!st.hasMoreTokens()) ruoli.add(new Ruolo(ruolo.trim()));
                }
                ruolo="";
            }

        }
        return ruoli;
    }

    public void setMediator(MediatorUnita mediator) {
        this.mediator=mediator;
    }
}
