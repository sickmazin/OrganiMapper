package ModuloCreationGui;

import ModuleDipendentiRuoli.Dipendente;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DipendentiTxtArea {
    private static final String NOME="\\s*[a-z]+\\s*";
    private static final String COGNOME="\\s*[a-z]+\\s*\\,";
    public static List<Dipendente> ottieniDipendenti(String text,int id) {
        List<String> nomieCognomi=new ArrayList<>();
        if(text.length()>0 && text.charAt(0)=='[' ){
            text=text.substring(1,text.length());
        }
        Scanner sc= new Scanner(text);
        String dipendente="",s;
        while (sc.hasNext()){
            String linea=sc.nextLine(), newString="";
            int j=0;
            for (int i = 0; i < linea.length(); i++) {
                if(linea.charAt(i)==',') {
                    newString += linea.substring(j, i + 1) + " ";
                    j=i+1;
                }
            }
            newString += linea.substring(j);
            StringTokenizer st= new StringTokenizer(newString);
            while (st.hasMoreTokens()){
                s=st.nextToken();
                if(s.toLowerCase().matches(NOME)) {
                    dipendente+=s+" ";
                }
                if (s.toLowerCase().matches(COGNOME)){
                    dipendente += s;
                    nomieCognomi.add(dipendente.replace(",", "").trim());
                    dipendente="";
                }else if (s.matches(",")) {
                    nomieCognomi.add(dipendente.trim());
                    dipendente="";
                }else if (!st.hasMoreTokens()) {
                    nomieCognomi.add(dipendente);
                    dipendente="";
                }
            }
        }
        List<Dipendente> ret= new ArrayList<>();
        for (String name: nomieCognomi){
            Dipendente d = new Dipendente(id, name);
            id++;
            ret.add(d);
        }
        return ret;
    }
}
