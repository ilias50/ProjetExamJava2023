
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Application extends JFrame implements PanelBtnSuppListener, PanelBtnSonListener, PanelBtnValidateListener, PanelClavierListener, PanelGameOverListener, PanelWinListener {

    List<String> alphabets = new ArrayList<>(Arrays.asList("A", "Z", "E", "R", "T", "Y",
            "U", "I", "O", "P", "Q", "S",
            "D", "F", "G", "H", "J", "K",
            "L", "M", "W", "X", "C", "V","B", "N"));


    private boolean perdue = false;

    private final int nbLettreDuMot = 5;

    private final int max_essaie = 5;
    private int numCaseAct = 0;
    private int numEssaiActu = 0;

    private final JLabel msgMotInvalide = new JLabel("le mot entrer n'existe pas ");


    private final JPanel pancont = (JPanel) getContentPane();
    private final JPanel panelCenter = new JPanel(new GridLayout(2, 0));

    private final JPanel panelhaut = new JPanel(new FlowLayout());

    private final JPanel panelbas = new JPanel(new GridLayout(2, 0));

    private final PanelFullTab tabFull = new PanelFullTab(nbLettreDuMot, max_essaie);

    private final PanelClavier panclavier = new PanelClavier(this, alphabets);

    private final Sound son = new Sound("assets/son/musique", true, true);

    private final PanelBtnSon panBtnSon = new PanelBtnSon( son.isPlay_musique(), son.isPlay_sound_effects(), this);



    private final PanelBtnValidate panBtnValidate = new PanelBtnValidate(this);

    private final PanelBtnSupp panBtnSupp = new PanelBtnSupp(this);

    private final  PanelGameOver panGameOver = new PanelGameOver(this);

    private final PanelWin panwin = new PanelWin(this);




    private String mot_a_trouver = "salut";

    private final String [] tab_mot_a_trouver = new String[nbLettreDuMot];
    private final Map<Integer, Boolean> dic_bol_mot_a_trouver = new HashMap<>();

    private final Map<String, String> dic_link_sons = new HashMap<>();

    private final List<String> list_mots = new LinkedList<>();




    public Application()  {
        initcomposant();
        this.setVisible(true);

    }

    private void initcomposant() {
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/image/img icone/motus_icone2.jpg");
        setIconImage(icon);
        setTitle("MOTUS GAME");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 300);


        setExtendedState(MAXIMIZED_BOTH);

        pancont.setLayout(new BorderLayout());


        panelhaut.add(panBtnSon);
        pancont.add(panelhaut, BorderLayout.PAGE_START);

        panelCenter.add(tabFull);
        panelCenter.add(panclavier);
        pancont.add(panelCenter, BorderLayout.CENTER);


        panelbas.add(panBtnValidate);
        panelbas.add(panBtnSupp);

        pancont.add(panelbas, BorderLayout.LINE_END);
        addSoundIntoDic();
        recupAllMots();
        chooseMot();






    }


    private void addSoundIntoDic(){
        dic_link_sons.put("win", "assets/son/sound_effects/win.wav");
        dic_link_sons.put("game over", "assets/son/sound_effects/Game Over.wav");
        dic_link_sons.put("error", "assets/son/sound_effects/error.wav");
        dic_link_sons.put("tap", "assets/son/sound_effects/tap.wav");
        dic_link_sons.put("delete", "assets/son/sound_effects/delete.wav");
    }


    private void recupAllMots(){

        File file = new File("assets/liste_mot");


        for (File i: Objects.requireNonNull(file.listFiles())){
            Scanner scan = null;
            try {
                scan = new Scanner(i);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (scan.hasNextLine())
            {
                String mot = scan.nextLine();
                if (mot.length() == nbLettreDuMot){
                    if (verifCarMot(mot)){
                        list_mots.add(mot);

                    }

                }

            }

        }

    }


    private boolean verifCarMot(String mot){

        char [] letre = mot.toCharArray();

        for (int i =0; i<letre.length; i++){
            if (!alphabets.contains(String.valueOf(letre[i]))){
                return false;
            }
        }

        return true;
    }


    private boolean motIsExist(){

        String motTest = "";

        for (int i =0; i<nbLettreDuMot; i++){
            motTest = motTest + "" + tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].getValeur();
        }


        File file = new File("assets/liste_mot");


        for (File i: Objects.requireNonNull(file.listFiles())){
            Scanner scan = null;
            try {
                scan = new Scanner(i);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (scan.hasNextLine())
            {
                String mot = scan.nextLine();
                if (mot.length() == nbLettreDuMot){
                    if (mot.equalsIgnoreCase(motTest)){
                        return true;
                    }
                }

            }

        }


        displayMsgMotInvalide();
        son.playSound(dic_link_sons.get("error"), false);
        return false;
    }

    private void chooseMot(){
        if (list_mots.isEmpty()){
            recupAllMots();
        }

        int nb = (int) (Math.random() * ( list_mots.size()));
        mot_a_trouver = list_mots.get(nb);
        System.out.println(list_mots.get(nb));
        list_mots.remove(nb);
        motIntoDic();
        tapMot(tab_mot_a_trouver[0]);
    }



    private void motIntoDic(){

        char [] letre = mot_a_trouver.toCharArray();

        for (int i =0; i<letre.length; i++){
            tab_mot_a_trouver[i] = String.valueOf(letre[i]);
        }

        for (int i =0; i<tab_mot_a_trouver.length; i++){
            dic_bol_mot_a_trouver.put(i, false);
        }


    }

    private void verif_mot(){

        if (verifNbLettreMotTester()){
            if (motIsExist()){
                verifLettreCorrecte();
                verifLettreMalPlacer();
                if (!verif_win()){
                    change_ligne();
                }
            }
        }
    }


    private void suppDerValeur(){
        if (tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].getDeja_pris() && !tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].getTrouver()){
            tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].suppValeur();
            son.playSound(dic_link_sons.get("delete"), false);
        }else {
            if (numCaseAct == 2){

                if (!tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct-1].getTrouver()) {
                    numCaseAct -= 1;
                    tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].suppValeur();
                    son.playSound(dic_link_sons.get("delete"), false);
                }

            }else {
                if (numCaseAct > 1) {
                    numCaseAct -= 1;
                    if (!tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].getTrouver()){

                        tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].suppValeur();
                        son.playSound(dic_link_sons.get("delete"), false);
                    }else {
                        suppDerValeur();
                    }

                }
            }

        }

        remoovemsg();

    }



    private boolean verif_win(){
        boolean verif = true;

        for (int i =0; i<tabFull.getTabLigne()[numEssaiActu].getTabCase().length; i++){
            if (!tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].getTrouver()){
                verif = false;
            }

        }

        if (verif){
            win();
        }

        return verif;
    }

    private void win(){
        son.playSound(dic_link_sons.get("win"), true);
        numEssaiActu = 0;
        numCaseAct = 0;
        displaywin();

    }

    private void remettreTabAZero(){
        for (int ligne = 0; ligne<max_essaie; ligne++){
            for (int caree = 0; caree<tabFull.getTabLigne()[ligne].getTabCase().length; caree++){

                tabFull.getTabLigne()[ligne].getTabCase()[caree].suppValeur();


            }
        }
        numEssaiActu = 0;
        numCaseAct = 0;
        chooseMot();

    }

    private void reloadpaneljeux(){
        pancont.remove(panGameOver);
        pancont.remove(panwin);
        pancont.add(panelCenter, BorderLayout.CENTER);
        pancont.add(panelbas, BorderLayout.LINE_END);
        perdue = false;
        revalidate();
        repaint();
    }

    private void verifLettreCorrecte(){
        for (int i = 0; i<tab_mot_a_trouver.length; i++) {


            String lettre_test = tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].getValeur();


            if (tab_mot_a_trouver[i].equalsIgnoreCase(lettre_test)) {

                tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].findLettreEtPlace();
                dic_bol_mot_a_trouver.put(i, true);


            }


        }

    }



    private void verifLettreMalPlacer(){

        for (int i = 0; i<tab_mot_a_trouver.length; i++){
            for (int j =0; j<nbLettreDuMot; j++){

                String lettre_test = tabFull.getTabLigne()[numEssaiActu].getTabCase()[j].getValeur();


                if (tab_mot_a_trouver[i].equalsIgnoreCase(lettre_test) && !dic_bol_mot_a_trouver.get(i)){
                    if (i != j){

                        tabFull.getTabLigne()[numEssaiActu].getTabCase()[j].findLettre();
                        dic_bol_mot_a_trouver.put(i, true);
                    }

                }


            }
        }
    }


    private boolean verifNbLettreMotTester(){
        boolean verif = true;

        for (int i = 0; i < tabFull.getTabLigne()[numEssaiActu].getTabCase().length; i++){

            if (!tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].getDeja_pris()){
                verif = false;
            }
        }

        return verif;

    }


    private void tapMot(String cara){
            tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].setValeur(cara);
            son.playSound(dic_link_sons.get("tap"), false);
            change_lettre();


    }

    private void change_lettre(){
        if ( tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].getDeja_pris()){
            if (numCaseAct+1 < nbLettreDuMot){
                numCaseAct += 1;
                if ( tabFull.getTabLigne()[numEssaiActu].getTabCase()[numCaseAct].getDeja_pris())  {
                    change_lettre();
                }

            }
        }

    }

    private void change_ligne(){
        if (numEssaiActu+1 < max_essaie){
            numEssaiActu += 1;
            numCaseAct = 0;
            for (int i = 0; i< nbLettreDuMot; i++){
                if (tabFull.getTabLigne()[numEssaiActu-1].getTabCase()[i].getTrouver()){
                    tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].setValeur(tab_mot_a_trouver[i]);
                    tabFull.getTabLigne()[numEssaiActu].getTabCase()[i].findLettreEtPlace();
                }
            }
            tapMot(tab_mot_a_trouver[0]);

        }else {
            gameOver();
        }
    }


    private void gameOver(){
        son.playSound(dic_link_sons.get("game over"), true);
        pancont.remove(panelCenter);
        pancont.remove(panelbas);
        pancont.add(panGameOver, BorderLayout.CENTER);
        revalidate();
        repaint();
        perdue = true;


    }

    private void displaywin(){
        pancont.remove(panelCenter);
        pancont.remove(panelbas);
        pancont.add(panwin, BorderLayout.CENTER);
        perdue = true;
        revalidate();
        repaint();



    }


    private void displayMsgMotInvalide(){
        msgMotInvalide.setBackground(Color.YELLOW);
        panelhaut.add(msgMotInvalide, FlowLayout.CENTER);
        repaint();
        revalidate();

    }

    private void remoovemsg(){
        panelhaut.remove(msgMotInvalide);
        repaint();
        revalidate();
    }


    @Override
    public void btnSon() {
        son.setPlay_musique();


    }

    @Override
    public void btnSound() {
        son.setPlay_sound_effects();

    }

    @Override
    public void next() {
        son.nextSon();

    }

    @Override
    public void actionBtnSupp() {
        if (!perdue) {
            suppDerValeur();
        }

    }

    @Override
    public void actionBtnValidate() {
        if (!perdue) {
            verif_mot();
        }


    }

    @Override
    public void sendlettre(String lettre) {
        if (!perdue) {
            tapMot(lettre);
        }

    }

    @Override
    public void sendKeySupp() {
        if (!perdue) {
            suppDerValeur();

        }

    }

    @Override
    public void sendEnter() {
        if (!perdue){
            verif_mot();

        }


    }


    @Override
    public void restart() {
        remettreTabAZero();
        reloadpaneljeux();

    }

    @Override
    public void suivant() {
        remettreTabAZero();
        reloadpaneljeux();

    }

    @Override
    public void quitter() {
        dispose();
    }
}
