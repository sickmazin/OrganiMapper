package PossibleModelOrg;

import ItemSerializable.LineSerial;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.*;

public class ModelDivisionaleStrategy extends ModelDisplayStrategy implements Observer {
    private final int DIVIS_PROD_INIZIALI =3 ;
    private final String[] TITOLI_CORPORATE={"Personale e Organizzazione","Finanza","Amministrazione e controllo","ICT"};
    private final double[][] COORDINATE_CORPORATE={{400, 200},{800,200},{400,300},{800,300}};
    private final String[] TITOLI_SOTTOUNITA={"Amministrazione e controllo","Acquisti","Produzioni","Vendite e Marketing"};
    private List<DivisioneProdotto> divisioniProdotto=new ArrayList<>();
    private final double PUNTO_UNIONE_DIV_COORDY=400;
    private final double[] COORDINATE_FIRST_DIV_PRODOTTO={300,430};
    @Override
    public AnchorPane getModelDisplayStrategy() {

        setPane();

        //INSERIMENTO DIREZIONE GENERALE
        Unita direzGenerale= new Unita(600,100);
        direzGenerale.setLabelText("Direzione Generale");
        direzGenerale.setMediator(this);
        allBoxInOrganigramma.add(direzGenerale);
        pane.getChildren().add(direzGenerale);

        //INSERIMENTO DELLE VARIE CORPORATE
        for (int i = 0; i < TITOLI_CORPORATE.length; i++) {
            Unita unita= new Unita(COORDINATE_CORPORATE[i][0],COORDINATE_CORPORATE[i][1]); unita.setMediator(this); allBoxInOrganigramma.add(unita);
            unita.setLabelText(TITOLI_CORPORATE[i]);
            pane.getChildren().add(unita);
        }
        for (int i = 0; i < DIVIS_PROD_INIZIALI; i++) {
            update();
        }
        pane.getChildren().addAll(
                //LINEA CENTRALE DALLA DIVISIONE GENERALE VERSO IL PUNTO CENTRALE
                LineaBuilder.getLineaVerticale(direzGenerale.getLayoutX()+(LARGHEZZA_CELL/2),direzGenerale.getLayoutY()+ALTEZZA_CELL,PUNTO_UNIONE_DIV_COORDY),
                //2 LINEE ORIZZ PER COLLEGARE DIV GEN ALLE DIV CORPORATE
                LineaBuilder.getLineaOrizzontale(COORDINATE_CORPORATE[0][0]+LARGHEZZA_CELL,COORDINATE_CORPORATE[1][0],COORDINATE_CORPORATE[0][1]+(ALTEZZA_CELL/2)),
                LineaBuilder.getLineaOrizzontale(COORDINATE_CORPORATE[2][0]+LARGHEZZA_CELL,COORDINATE_CORPORATE[3][0],COORDINATE_CORPORATE[2][1]+(ALTEZZA_CELL/2))
        );
        return pane;
    }

    @Override
    public void aggiungiSottoUnita(AbstractUnita unita, String text) {
        double coordinateX=unita.getLayoutX()-(LARGHEZZA_CELL/2),coordinateY=unita.getLayoutY()+(ALTEZZA_CELL+40);
       DivisioneProdotto divisoneProd=getDivisioneProdottoDaUnita(unita);
       List<SottoUnita> sottoUnitaList=divisoneProd.getSottoUnitaList();
        if(sottoUnitaList.size()>0)coordinateY=sottoUnitaList.get(sottoUnitaList.size()-1).getLayoutY()+(ALTEZZA_CELL+20);
        SottoUnita sottoUnita=new SottoUnita(coordinateX,coordinateY); sottoUnita.setLabelText(text); sottoUnita.setMediator(this);
        divisoneProd.addSottoUnita(sottoUnita);
        pane.getChildren().add(sottoUnita);
    }
    private DivisioneProdotto getDivisioneProdottoDaUnita(AbstractUnita unita){
        for (DivisioneProdotto d:divisioniProdotto){
            if(d.getUnita().equals(unita)) return d;
        }
        return null;
    }
    private void aggiungiDivisioneProdotto() {
        int numDiv=divisioniProdotto.size();
        double coordinateXDiv,coordinateYDiv;
        if(numDiv==0) {
            coordinateXDiv = COORDINATE_FIRST_DIV_PRODOTTO[0]; coordinateYDiv = COORDINATE_FIRST_DIV_PRODOTTO[1];
        }
        else{
            AbstractUnita lastDiv=divisioniProdotto.get(divisioniProdotto.size()-1).getUnita();
            coordinateXDiv = lastDiv.getLayoutX() + 300; coordinateYDiv = lastDiv.getLayoutY();
        }

        Unita unitaCentrale = new Unita(coordinateXDiv,coordinateYDiv);
        unitaCentrale.setLabelText("Divisione Prodotto "+(numDiv+1));
        unitaCentrale.setMediator(this);
        allBoxInOrganigramma.add(unitaCentrale);
        DivisioneProdotto newDivisioneProdotto=new DivisioneProdotto(unitaCentrale,new ArrayList<>(),new ArrayList<>());
        divisioniProdotto.add(newDivisioneProdotto);

        for (int j = 0; j < TITOLI_SOTTOUNITA.length ; j++) {
            aggiungiSottoUnita(unitaCentrale,TITOLI_SOTTOUNITA[j]);
        }
        List<SottoUnita> sottoUnitaList=newDivisioneProdotto.getSottoUnitaList();
        List<LineSerial> lineList=new ArrayList<>();
        //LINEA VERT PER COLLEGARE LE SOTTOUNITA ALLA DIVISIONE PRODOTTO
        lineList.add(LineaBuilder.getLineaVerticale(coordinateXDiv+(LARGHEZZA_CELL/2),coordinateYDiv+ALTEZZA_CELL,sottoUnitaList.get(sottoUnitaList.size()-1).getLayoutY()+(ALTEZZA_CELL/2)));
        //LINEA VERTICALE PER COLLEGARE QUELLA SUPER ORIZZ
        lineList.add(LineaBuilder.getLineaVerticale(coordinateXDiv+(LARGHEZZA_CELL/2),coordinateYDiv,PUNTO_UNIONE_DIV_COORDY));
        for (int i = 0; i < sottoUnitaList.size(); i++) {
            double coordinateX=sottoUnitaList.get(i).getLayoutX(),coordinateY=sottoUnitaList.get(i).getLayoutY();
            lineList.add(LineaBuilder.getLineaOrizzontale(coordinateX+150,coordinateXDiv+(LARGHEZZA_CELL/2),coordinateY+(ALTEZZA_CELL/2)));
        }
        pane.getChildren().add(unitaCentrale);
        double coordinateXFirstDiv=COORDINATE_FIRST_DIV_PRODOTTO[0]+(LARGHEZZA_CELL/2),coordinateXLastDiv= unitaCentrale.getLayoutX()+(LARGHEZZA_CELL/2);
        //LINEA ORIZZONTALE SOPRA LE DIVISIONI
        lineList.add(LineaBuilder.getLineaOrizzontale(coordinateXFirstDiv,coordinateXLastDiv,PUNTO_UNIONE_DIV_COORDY));

        pane.getChildren().addAll(lineList);

        newDivisioneProdotto.setLineList(lineList);
        setLastComponentAdded(newDivisioneProdotto);
    }

    @Override
    protected void removedComponent(ComponentOrg lastComponentAdded) {
        divisioniProdotto.remove(((DivisioneProdotto)lastComponentAdded));
    }

    @Override
    public void update() {
        aggiungiDivisioneProdotto();
    }
}
