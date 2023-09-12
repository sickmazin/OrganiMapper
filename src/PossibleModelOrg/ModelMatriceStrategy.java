package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ModelMatriceStrategy  extends ModelDisplayStrategy implements Observer{
    private Unita direzAmmProget;
    final String[] Titoli_DIREZIONI={"Direzione Ricerca e Sviluppo","Direzione Produzione","Direzione Marketing","Direzione Vendite"};
    final int POS_CENTRALE_ORG_X=750, POS_CENTRALE_ORG_Y=145;
    private List<AbstractUnita> unitaList=new ArrayList<>();
    private List<DirezioneProgetto> direzioniProgettiList=new ArrayList<>();
    @Override
    public AnchorPane getModelDisplayStrategy() {
        setPane();
        Unita direzioneGenUnita = new Unita(650,85);    direzioneGenUnita.setLabelText("Direzione Generale");                     direzioneGenUnita.setMediator(this);      allBoxInOrganigramma.add(direzioneGenUnita);
        Unita segreteriaBox= new Unita(450,185);        segreteriaBox.setLabelText("Segreteria");                               segreteriaBox.setMediator(this);        allBoxInOrganigramma.add(segreteriaBox);
        Unita qualitaBox= new Unita(850,185);           qualitaBox.setLabelText("Reparto qualità");                             qualitaBox.setMediator(this);           allBoxInOrganigramma.add(qualitaBox);
        direzAmmProget= new Unita(150,304);             direzAmmProget.setLabelText("Direzione Amministrativa Progetti");       direzAmmProget.setMediator(this);       allBoxInOrganigramma.add(direzAmmProget);
        pane.getChildren().addAll(
                direzioneGenUnita,
                segreteriaBox,
                qualitaBox,
                direzAmmProget,
                LineaBuilder.getLineaOrizzontale(segreteriaBox.getLayoutX()+LARGHEZZA_CELL,qualitaBox.getLayoutX(),segreteriaBox.getLayoutY()+(ALTEZZA_CELL/2)),
                LineaBuilder.getLineaVerticale(direzAmmProget.getLayoutX()+(LARGHEZZA_CELL/2),direzAmmProget.getLayoutY(),direzAmmProget.getLayoutY()-(ALTEZZA_CELL/2))

        );
        for (int i = 0; i < Titoli_DIREZIONI.length; i++) {
            Unita boxUnita= new Unita((i*250)+400,304); boxUnita.setLabelText(Titoli_DIREZIONI[i]);
            boxUnita.setMediator(this); allBoxInOrganigramma.add(boxUnita);

            double coordinateX=boxUnita.getLayoutX(), coordinateY=boxUnita.getLayoutY();

            pane.getChildren().addAll(
                    boxUnita,
                    LineaBuilder.getLineaVerticale(coordinateX+(LARGHEZZA_CELL/2),coordinateY,coordinateY-(ALTEZZA_CELL/2)),
                    LineaBuilder.getLineaVerticale(coordinateX+(LARGHEZZA_CELL/2),coordinateY+ALTEZZA_CELL,434)
            );

            unitaList.add(boxUnita);
        }
        pane.getChildren().addAll(
                LineaBuilder.getLineaVerticale(POS_CENTRALE_ORG_X,POS_CENTRALE_ORG_Y,unitaList.get(1).getLayoutY()),
                LineaBuilder.getLineaOrizzontale(direzAmmProget.getLayoutX()+(LARGHEZZA_CELL/2),unitaList.get(unitaList.size()-1).getLayoutX()+(LARGHEZZA_CELL/2),direzAmmProget.getLayoutY()-(ALTEZZA_CELL/2))
        );

        update();

        return pane;
    }

    @Override
    public void aggiungiSottoUnita(AbstractUnita unita, String text) {
        int i= direzioniProgettiList.size();
        double coordinateX=unita.getLayoutX(), coordinateY=unita.getLayoutY()+100+(100*i),coordinateLineaY;
        SottoUnita sottoUnita= new SottoUnita(25+coordinateX,coordinateY);      sottoUnita.setLabelText("Direzione Progetto n°"+(i+1));         sottoUnita.setMediator(this);
        if(i==0) coordinateLineaY=unita.getLayoutY();
        else coordinateLineaY=direzioniProgettiList.get(i-1).getSottoUnita().getLayoutY();
        List<LineSerial> lineList=new ArrayList<>();
        //LINEA TRA DIREZIONE AMM E LA DIREZ DEL PRIMO PROGETTO
        lineList.add(LineaBuilder.getLineaVerticale(coordinateX+(LARGHEZZA_CELL/2),coordinateLineaY+ALTEZZA_CELL,sottoUnita.getLayoutY()));
        //LINEA ORIZZ PER COLLEGARE QUELLE VERTICALI DELLE UNITA
        lineList.add(LineaBuilder.getLineaOrizzontale(coordinateX+LARGHEZZA_CELL-25,unitaList.get(unitaList.size()-1).getLayoutX()+(LARGHEZZA_CELL/2),coordinateY+(ALTEZZA_CELL/2)));
        pane.getChildren().add(sottoUnita);
        for (LineSerial l: lineList) {
            pane.getChildren().add(l);
        }
        for (int j = 0; j <unitaList.size(); j++) {
            AbstractUnita unitaCentrale =unitaList.get(j);
            double x= unitaCentrale.getLayoutX()+(LARGHEZZA_CELL/2),lineaY;
            if(i==0) lineaY=unita.getLayoutY();
            else lineaY=direzioniProgettiList.get(i-1).getSottoUnita().getLayoutY()-(ALTEZZA_CELL/2);
            LineSerial l= LineaBuilder.getLineaVerticale(x,coordinateY+(ALTEZZA_CELL/2),lineaY+ALTEZZA_CELL);
            pane.getChildren().add(l);
            lineList.add(l);
        }
        DirezioneProgetto direzioneProgetto=new DirezioneProgetto(sottoUnita,lineList);
        direzioniProgettiList.add(direzioneProgetto);
        setLastComponentAdded(direzioneProgetto);
    }

    @Override
    protected void removedComponent(ComponentOrg lastComponentAdded) {
        direzioniProgettiList.remove(direzioniProgettiList.size()-1);
    }

    @Override
    public void update() {
        addNewDirezioneProgetto();
    }

    private void addNewDirezioneProgetto() {
        aggiungiSottoUnita(direzAmmProget,"Direzione Progetto "+(direzioniProgettiList.size()+1));
    }

}
