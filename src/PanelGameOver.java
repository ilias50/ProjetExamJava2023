import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelGameOver extends JPanel implements ActionListener {


    private final JButton btnRestart = new JButton("reessayer");
    private final JButton btnquitter = new JButton("quitter");

    private final JLabel lblperdue = new JLabel("GAME OVER ");

    private final Font font = new Font(Font.SERIF,Font.BOLD,150);

    private final PanelGameOverListener app;

    public PanelGameOver(PanelGameOverListener app) {
        this.app = app;
        initcomposant();
    }

    private void initcomposant(){
        this.setLayout(new GridLayout(3, 0));


        btnRestart.setActionCommand("restart");
        this.add(btnRestart );



        lblperdue.setHorizontalAlignment(SwingConstants.CENTER);
        lblperdue.setFont(font);
        this.add(lblperdue);
        btnRestart.addActionListener(this);

        btnquitter.addActionListener(this);
        btnquitter.setActionCommand("quitter");
        this.add(btnquitter);
    }








    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            case "quitter":
                app.quitter();
                break;

            case  "restart":
                app.restart();
                break;
        }

    }
}
