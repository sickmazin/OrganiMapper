package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.*;

public class ModelFunzionaleStrategy extends ModelDisplayStrategy implements Observer{
    private final String[] Titoli_DIREZIONI={"Direzione Amministrativa","Direzione MKTG & COMM.","Direzione Commerciale","Direzione Personale","Direzione Produzione","Direzione R & S"};
    private Map<Unita, LinkedList<SottoUnitaFunzionali>> direzioni=new HashMap<>();
    private final double[] COORDINATE_DIV_GEN={615,45};


    @Override
    public AnchorPane getModelDisplayStrategy() {
        setPane();
        Unita direzioneGenBox= new Unita(COORDINATE_DIV_GEN[0],COORDINATE_DIV_GEN[1]); direzioneGenBox.setLabelText("Direzione Generale"); direzioneGenBox.setMediator(this); allBoxInOrganigramma.add(direzioneGenBox);
        pane.getChildren().add(direzioneGenBox);
        double coordinateX,coordinateY=174;
        for (int i = 0; i < Titoli_DIREZIONI.length; i++) {
            coordinateX=(i*250)+110;
            Unita unita= new Unita(coordinateX,coordinateY); unita.setLabelText(Titoli_DIREZIONI[i]); direzioni.put(unita,new LinkedList<>());
            unita.setMediator(this);  allBoxInOrganigramma.add(unita);
            pane.getChildren().addAll(
                    unita,
                    LineaBuilder.getLineaTraBox(COORDINATE_DIV_GEN[0],COORDINATE_DIV_GEN[1],coordinateX,coordinateY)
            );
        }
        aggiungiSottoUnita(getUnita(Titoli_DIREZIONI[1]),"Marketing e Ricerca");
        aggiungiSottoUnita(getUnita(Titoli_DIREZIONI[1]),"Comunicazione corporate");
        aggiungiSottoUnita(getUnita(Titoli_DIREZIONI[3]),"Amministrazione e organizzazione");
        aggiungiSottoUnita(getUnita(Titoli_DIREZIONI[3]),"Formazione e selezione");

        return pane;
    }

    private Unita getUnita(String s) {
        for (Unita unita:direzioni.keySet()) {
            if(unita.getNomeUnita().equals(s)) return unita;
        }return null;
    }

    @Override
    public void aggiungiSottoUnita(AbstractUnita unita, String text) {
        LinkedList<SottoUnitaFunzionali> sottoUnitaList=direzioni.get(unita);
        double coordinateX=unita.getLayoutX()-(LARGHEZZA_CELL/2),coordinateY=unita.getLayoutY()+(ALTEZZA_CELL+40),coordinateXDiv=unita.getLayoutX(),coordinateYDiv=unita.getLayoutY();

        if(sottoUnitaList.size()>0)coordinateY=sottoUnitaList.getLast().getSottoUnita().getLayoutY()+(ALTEZZA_CELL+20);

        SottoUnita sottoUnita=new SottoUnita(coordinateX,coordinateY); sottoUnita.setLabelText(text); sottoUnita.setMediator(this); allBoxInOrganigramma.add(sottoUnita);
        pane.getChildren().addAll(
                sottoUnita
        );
        List<LineSerial> lineList=new ArrayList<>();
        //LINEA VERT PER COLLEGARE LE SOTTOUNITA ALLA DIVISIONE PRODOTTO
        double endY;
        if(sottoUnitaList.size()>0) endY=sottoUnitaList.getLast().getSottoUnita().getLayoutY()+(ALTEZZA_CELL/2);
        else endY=coordinateYDiv+ALTEZZA_CELL;
        lineList.add(LineaBuilder.getLineaVerticale(coordinateXDiv+(LARGHEZZA_CELL/2),coordinateY+(ALTEZZA_CELL/2),endY));
        //LINEA ORIZZ DALLA SOTT ALLA VERTICALE DELLA DIV
        lineList.add(LineaBuilder.getLineaOrizzontale(coordinateX+150,coordinateXDiv+(LARGHEZZA_CELL/2),coordinateY+(ALTEZZA_CELL/2)));

        pane.getChildren().addAll(lineList);

        LinkedList<SottoUnitaFunzionali> sottoUnitaFunzionalis=direzioni.get(unita);
        SottoUnitaFunzionali sottoUnitaFunzionale=new SottoUnitaFunzionali(sottoUnita,lineList);
        sottoUnitaFunzionale.setUnitaAssociata((Unita) unita);
        sottoUnitaList.add(sottoUnitaFunzionale);
        setLastComponentAdded(sottoUnitaFunzionale);
    }

    @Override
    protected void removedComponent(ComponentOrg lastComponentAdded) {
        LinkedList<SottoUnitaFunzionali> sottoUnitaList=direzioni.get(((SottoUnitaFunzionali)lastComponentAdded).getUnitaAssociata());
        sottoUnitaList.removeLast();
    }

    @Override
    public void update() {
        //unitaSelected.getLayoutY()<175 significa che non sono sotto unita mentre unitaSelected.getLayoutY()!=COORDINATE_DIV_GEN[1] utilizzato per non aggiungere sotto unita alla direz generale
        if(unitaSelected!=null && unitaSelected.getLayoutY()<175 && unitaSelected.getLayoutY()!=COORDINATE_DIV_GEN[1])aggiungiSottoUnita(unitaSelected,"Sotto Unità");
    }

}
