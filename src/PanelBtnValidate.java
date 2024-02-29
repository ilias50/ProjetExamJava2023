import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class PanelBtnValidate extends JPanel implements ActionListener {


    PanelBtnValidateListener app;

    JButton btn = new JButton("valider le mot ");

    public PanelBtnValidate(PanelBtnValidateListener app) {
        this.app = app;
        initcomposant();
    }

    private void initcomposant(){
        setLayout(new GridLayout());

        btn.addActionListener(this);
        btn.setActionCommand("valide");
        add(btn);

    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "valide")){
            app.actionBtnValidate();
        }
    }
}
