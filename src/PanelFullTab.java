import javax.swing.*;
import java.awt.*;

public class PanelFullTab extends JPanel {

    private final int nbCase;
    private final int nbLigne;



    private final PanelLignesTab [] tabLigne;

    public PanelLignesTab[] getTabLigne() {
        return tabLigne;
    }

    public PanelFullTab(int nbLettre, int nbTentative) {
        this.nbCase = nbLettre;
        this.nbLigne = nbTentative;
        tabLigne = new PanelLignesTab [nbTentative];
        iniComposant();
    }

    private void iniComposant(){
        setLayout(new GridLayout(nbLigne, 0));


        for (int i = 0; i<nbLigne; i++){
            tabLigne[i] = new PanelLignesTab(nbCase);
        }

        for (int i =0; i<tabLigne.length; i++){
            add(tabLigne[i]);
        }
    }


}
