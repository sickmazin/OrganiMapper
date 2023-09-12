package Model;

public class OrganigrammaSimple extends AbstractOrganigramma{
    public OrganigrammaSimple(String nomeAzienda, String anno,String modello,String pathImage) {
        this.nomeAzienda = nomeAzienda;
        this.anno = anno;
        this.modello=modello;
        imgPath=pathImage;
    }
}
