import javax.swing.*;
import java.awt.*;

public class PanelLignesTab  extends JPanel {

    private final int nbCase;
    private final PanelCaseTab [] tabCase;



    public PanelCaseTab[] getTabCase() {
        return tabCase;
    }

    public PanelLignesTab(int nbLettre) {
        this.nbCase = nbLettre;
        tabCase = new PanelCaseTab[nbLettre];
        initComposant();
    }

    private void initComposant(){
        for (int i = 0; i<nbCase; i++){
            tabCase[i] = new PanelCaseTab();
        }

        setLayout(new GridLayout(0, nbCase));




        for (int i = 0; i<tabCase.length; i++) {
            add(tabCase[i]);


        }

    }
}
