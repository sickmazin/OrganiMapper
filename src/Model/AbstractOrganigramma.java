package Model;

import ModuleDipendentiRuoli.Dipendente;
import PossibleModelOrg.AbstractUnita;
import PossibleModelOrg.ModelDisplayStrategy;

import java.io.Serializable;
import java.util.List;

public class AbstractOrganigramma implements Serializable {
    //INFORMAZIONI DELL'ORGANIGRAMMA
    protected String nomeAzienda,anno,imgPath,modello,filePath;
    protected List<Dipendente> dipendenti;

    //INFORMAZIONI PER GRAPHIC ORGANIGRAMM
    protected List<AbstractUnita> allUnita;
    private ModelDisplayStrategy modelDisplayStrategy;


    //GETTERS AND SETTERS

    public ModelDisplayStrategy getModelDisplayStrategy() {
        return modelDisplayStrategy;
    }

    public void setModelDisplayStrategy(ModelDisplayStrategy modelDisplayStrategy) {
        this.modelDisplayStrategy = modelDisplayStrategy;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }
    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }
    public String getAnno() {
        return anno;
    }
    public void setAnno(String anno) {
        this.anno = anno;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public List<Dipendente> getDipendenti() {
        return dipendenti;
    }
    public void setDipendenti(List<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }
    public List<AbstractUnita> getAllUnita() {
        return allUnita;
    }
    public void setAllUnita(List<AbstractUnita> unita) {
        this.allUnita = unita;
    }

    public String getModello() { return modello;
    }


}
