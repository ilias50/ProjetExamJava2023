import javax.swing.*;
import java.awt.*;

public class Btn2Images extends JButton {

    private String pathImgFalse;
    private String pathImgTrue;
    private boolean bolImg;

    public Btn2Images(String pathImgFalse, String pathImgTrue, boolean bolDefault) {
        setPathImgFalse(pathImgFalse);
        setPathImgTrue(pathImgTrue);
        setBolImg(bolDefault);
        changeImg();
    }

    public String getPathImgFalse() {
        return pathImgFalse;
    }

    public void setPathImgFalse(String pathImgFalse) {
        this.pathImgFalse = pathImgFalse;
    }

    public String getPathImgTrue() {
        return pathImgTrue;
    }

    public void setPathImgTrue(String pathImgTrue) {
        this.pathImgTrue = pathImgTrue;
    }

    public boolean isBolImg() {
        return bolImg;
    }

    public void setBolImg(boolean bolImg) {
        this.bolImg = bolImg;
        changeImg();
    }

    public void setBolImg() {
        if (isBolImg()){
            setBolImg(false);
        }else {
            setBolImg(true);
        }
        changeImg();
    }



    private String chooseImg(){
        if (isBolImg()){
            return getPathImgTrue();
        }else{
            return getPathImgFalse();
        }
    }

    private void changeImg(){
        ImageIcon icon = new ImageIcon(new ImageIcon(chooseImg()).getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
        setIcon(icon);
    }

}
