import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelCaseTab extends JLabel{


    private String valeur = "-";

    private Boolean deja_pris = false;

    private Boolean deja_valider = false;

    private Boolean trouver = false;

    private final Border blackline = BorderFactory.createLineBorder(Color.BLACK);

    private final Font font = new Font(Font.SERIF,Font.BOLD,30);



    public PanelCaseTab() {
        initcomposant();
    }

    public String getValeur() {
        return valeur;
    }

    public Boolean getDeja_pris() {
        return deja_pris;
    }

    public void setDeja_pris(Boolean deja_pris) {
        this.deja_pris = deja_pris;
    }

    public Boolean getDeja_valider() {
        return deja_valider;
    }

    public void setDeja_valider(Boolean deja_valider) {
        this.deja_valider = deja_valider;
    }

    public Boolean getTrouver() {
        return trouver;
    }

    public void setTrouver(Boolean trouver) {
        this.trouver = trouver;
    }

    public void setValeur(String valeur){
        if (!getDeja_pris()){
            valeur = valeur.toUpperCase();
            this.valeur = valeur;
            setText(valeur);

            setDeja_pris(true);
        }

    }


    public void suppValeur(){
        this.valeur = "-";
        setText(valeur);
        setDeja_pris(false);
        setDeja_valider(false);
        setTrouver(false);
        colorBasic();
    }


    private void initcomposant(){
        setText(valeur);
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        colorBasic();
        setFont(font);
        setBorder(blackline);





    }

    public void findLettre(){
        if (!getDeja_valider() && !getTrouver()){
            setBackground(Color.YELLOW);
            setDeja_valider(true);
        }

    }

    public void findLettreEtPlace(){
        if (!getDeja_valider()) {
            setBackground(Color.RED);
            setDeja_valider(true);
            setTrouver(true);
        }
    }

    public void colorBasic(){
        setBackground(Color.WHITE);
    }

}
