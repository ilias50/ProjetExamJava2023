import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class PanelClavier extends JPanel implements ActionListener, KeyEventDispatcher {

    private final PanelClavierListener app;

    List<String> alphabets;

    public PanelClavier(PanelClavierListener app, List<String> alphabets) {
        this.app = app;
        this.alphabets = alphabets;
        initcomposant();
    }


    private void initcomposant(){
        setLayout(new GridLayout(3, 0));


        for (String i : alphabets) {
            JButton btn = new JButton(i);
            btn.addActionListener(this);

            add(btn);
        }

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.sendlettre(e.getActionCommand());

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {

        if (e.getID() == KeyEvent.KEY_RELEASED){
            if (alphabets.contains(String.valueOf(e.getKeyChar()).toUpperCase())){
                app.sendlettre(String.valueOf(e.getKeyChar()));
            }

            if (e.getKeyCode() == 8){
                app.sendKeySupp();
            }


            if (e.getKeyCode() == 10){
                app.sendEnter();
            }

        }



        return false;
    }
}