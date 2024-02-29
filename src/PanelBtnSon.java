import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelBtnSon extends JPanel implements ActionListener {

    private boolean play_musique;

    private boolean play_sound;

    private final String path_dossier = "assets/image/image_button";

    private final PanelBtnSonListener app;


    public PanelBtnSon(boolean play_musique, boolean play_sound, PanelBtnSonListener app) {
        this.play_musique = play_musique;
        this.play_sound = play_sound;
        this.app = app;
        initcomposant();
    }

    public boolean isPlay_musique() {
        return play_musique;
    }

    public void setPlay_musique(boolean play_musique) {
        this.play_musique = play_musique;
        btnSon.setBolImg();
    }

    public void setPlay_musique() {
        if (isPlay_musique()){
            setPlay_musique(false);

        }else {
            setPlay_musique(true);
        }

        btnSon.setBolImg(isPlay_musique());
    }


    public boolean isPlay_sound() {
        return play_sound;
    }

    public void setPlay_sound(boolean play_sound) {
        this.play_sound = play_sound;
        btnSound.setBolImg();
    }

    public void setPlay_sound() {
        if (isPlay_sound()){
            setPlay_sound(false);

        }else {
            setPlay_sound(true);
        }
        btnSound.setBolImg(isPlay_sound());
    }


    private final Btn2Images btnSon = new Btn2Images(path_dossier+"/son couper.png", path_dossier+"/son.jpeg", isPlay_musique());
    private final Btn2Images btnNext = new Btn2Images(path_dossier+"/icone_next.png", path_dossier+"/icone_next.png", true );

    private final Btn2Images btnSound = new Btn2Images(path_dossier+"/sound effect couper.png", path_dossier+"/sound effect.jpg", isPlay_sound() );



    private void initcomposant(){
        setLayout(new FlowLayout());

        btnSon.addActionListener(this);
        btnSon.setActionCommand("son");
        btnSon.setBolImg(isPlay_musique());
        add(btnSon);

        btnSound.addActionListener(this);
        btnSound.setActionCommand("sound");
        btnSound.setBolImg(isPlay_sound());
        add(btnSound);

        btnNext.addActionListener(this);
        btnNext.setActionCommand("next");

        add(btnNext);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "son":
                app.btnSon();
                setPlay_musique();
                break;
            case "next":
                app.next();
                break;
            case "sound":
                app.btnSound();
                setPlay_sound();
                break;
        }

    }
}
