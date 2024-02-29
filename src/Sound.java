import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Sound implements LineListener {


    private File file_musique;
    private Boolean play_musique;

    private boolean play_sound_effects;
    private Clip clip;

    private final float volume_base = 6.0f;
    private final float volume_inferieur = -10.0f;


    private FloatControl fc;

    private String path_dossier_musique;

    private final List<File> listSound = new LinkedList<>();

    public String getPath_dossier_musique() {
        return path_dossier_musique;
    }

    public void setPath_dossier_musique(String path_dossier_musique) {
        this.path_dossier_musique = path_dossier_musique;
    }



    public Boolean isPlay_musique() {
        return play_musique;
    }

    public void setPlay_musique(Boolean play_musique) {
        this.play_musique = play_musique;
        play_pause(clip);
    }

    public void setPlay_musique() {
        if (play_musique){
            play_musique = false;
        }else {
            play_musique = true;
        }
        play_pause(clip);
    }

    public boolean isPlay_sound_effects() {
        return play_sound_effects;
    }

    public void setPlay_sound_effects(boolean play_sound_effects) {
        this.play_sound_effects = play_sound_effects;
    }

    public void setPlay_sound_effects() {
        if (play_sound_effects){
            play_sound_effects = false;
        }else{
            play_sound_effects = true;
        }
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    private void setFile_musique(File file_musique) {
        this.file_musique = file_musique;
        clip = createClip(getFile_musique());
        addcontroler(getClip());
    }

    public File getFile_musique() {
        return file_musique;
    }



    public Sound(String path_dossier, boolean play_sound_effects, Boolean play_musique) {

        setPlay_musique(play_musique);
        setPlay_sound_effects(play_sound_effects);
        setPath_dossier_musique(path_dossier);
        if (isDossierNotNull(getPath_dossier_musique())){

            recupAllSound();
            choose_music();
            play_pause(getClip());

        }




    }

    private boolean isDossierNotNull(String path_dossier){
        File file = new File(path_dossier);
        if (file.exists()){
            if (file.isDirectory()){
                if (file.list().length>0){
                    return true;
                }
            }
        }

        return false;
    }



    private Clip createClip(File son){
        try {
            if (verif_extenstion(son)){
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(son);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);


                clip.addLineListener(this);
                return clip;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void addcontroler(Clip clip){
        fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN) ;
        fc.setValue(volume_base);

    }

    private void recupAllSound(){
        File file = new File(getPath_dossier_musique());

        for (File i: Objects.requireNonNull(file.listFiles())){
            if (verif_extenstion(i)){
                listSound.add(i);
            }

        }
    }






    private void play_pause(Clip clip)  {
        if (clip != null){
            if (play_musique){
                clip.start();
            }
            else{
                clip.stop();
            }
        }

    }

    public void nextSon(){
        if (clip != null){
            if (clip.isActive()){
                clip.stop();
            }
            choose_music();
            play_pause(clip);
        }
    }

    private void choose_music(){
        if (listSound.isEmpty()){
            recupAllSound();
        }
        int nb = (int) (Math.random() * ( listSound.size()));
        if (file_musique != listSound.get(nb)){
            setFile_musique(listSound.get(nb));
            listSound.remove(nb);
        }else {
            choose_music();
        }

    }

    private void verif_end(){
        if (clip.getFrameLength() <= clip.getFramePosition()){
            nextSon();
        }
    }


    public void playSound(String sound_path, boolean diminueSon){
        if (play_sound_effects){
           Clip son = createClip(new File(sound_path));
            if (son != null){
               if (diminueSon){
                   changVolume(volume_inferieur);
                   son.addLineListener(new LineListener() {
                       @Override
                       public void update(LineEvent event) {
                           if (son.getFrameLength() <= son.getFramePosition()){
                                changVolume(volume_base);
                           }
                       }
                   });
               }
                son.start();
            }
        }
    }

    public void changVolume( Float volume){
        if (volume > 6.0f){
            volume = 6.0f;
        }else if (volume < -80.0f){
            volume = -80.0f;
        }
        fc.setValue(volume);

    }




    private boolean verif_extenstion(File file){
        String path_file = file.getPath();
        String ext = "";

        int i = path_file.lastIndexOf('.');
        if (i > 0) {
            ext = path_file.substring(i+1);
        }

        return ext.equals("wav");
    }



    @Override
    public void update(LineEvent event) {
        verif_end();
    }
}
