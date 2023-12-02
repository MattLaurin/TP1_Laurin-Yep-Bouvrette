package vue;

import com.chat.echecs.EtatPartieEchecs;
import controleur.EcouteurJeuEchecs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-10-01
 */
public class PanneauEchiquier extends JPanel {
    private JButton[][] boutons = new JButton[8][8];
    private EtatPartieEchecs partie;
    private ActionListener ecouteurJeuEchecs;
    public PanneauEchiquier(EtatPartieEchecs partie) {
        this.partie = partie;
        JPanel p = new JPanel(new GridLayout(8,8));
        this.setLayout(new BorderLayout());
        char[][] echiquier = partie.getEtatEchiquier();
        ecouteurJeuEchecs = new EcouteurJeuEchecs(null);
        for (int i=0;i<boutons.length;i++)
            for (int j=0;j<boutons[i].length;j++) {
                boutons[i][j] = new JButton();
                boutons[i][j].setActionCommand((char)('a'+j)+""+(8-i));
                if ((i+j)%2==0)
                    boutons[i][j].setBackground(Color.WHITE);
                else
                    boutons[i][j].setBackground(Color.GRAY);
                p.add(boutons[i][j]);
                if (echiquier[i][j]!=' ')
                    boutons[i][j].setIcon(ServiceImages.getIconePourPiece(echiquier[i][j]));
            }
        this.add(p,BorderLayout.CENTER);
       //partie.ajouterObservateur(this);
    }

    public void setEcouteurJeuEchecs(ActionListener ecouteurJeuEchecs) {
        this.ecouteurJeuEchecs = ecouteurJeuEchecs;
        for (int i=0;i<boutons.length;i++)
            for (int j=0;j<boutons[i].length;j++)
                boutons[i][j].addActionListener(ecouteurJeuEchecs);
    }
}