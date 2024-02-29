import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class PanelBtnSupp extends JPanel implements ActionListener {

    private final PanelBtnSuppListener app;

    public PanelBtnSupp(PanelBtnSuppListener app) {
        this.app = app;
        inicomposant();
    }

    private final JButton btnsup = new JButton("supprimer");

    private void inicomposant(){
        setLayout(new GridLayout());

        btnsup.addActionListener(this);
        btnsup.setActionCommand("sup");
        add(btnsup);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (Objects.equals(e.getActionCommand(), "sup")){
            app.actionBtnSupp();
        }

    }
}
