package vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        setLayout(new GridLayout(2,2));
        setPreferredSize(new Dimension(90,40));

        JTextField ip = new JTextField(adr);
        JTextField portServ = new JTextField(port);



        JPanel p1 = new JPanel(new GridLayout(1,2));
        JPanel p2 = new JPanel(new GridLayout(1,2));

        p1.add(new JLabel("Addresse IP : "));
        p1.add(ip);


        p2.add(new JLabel("Port : "));
        p2.add(portServ);


        this.add(p1);
        this.add(p2);

    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
