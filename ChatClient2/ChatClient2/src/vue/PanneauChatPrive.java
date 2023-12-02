package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauChatPrive extends PanneauChat {
    private JButton bAccepterOuInviter, bRefuser;
    private FenetreEchecs fenetreEchecs;
    public PanneauChatPrive() {
        bAccepterOuInviter = new JButton("Inviter échec");
        bRefuser = new JButton("Refuser");

        bAccepterOuInviter.setActionCommand("ACCEPTER");
        bRefuser.setActionCommand("REFUSER");

        //à compléter

    }
    @Override
    public void setEcouteur(ActionListener ecouteur) {
        super.setEcouteur(ecouteur);
        bAccepterOuInviter.addActionListener(ecouteur);
        bRefuser.addActionListener(ecouteur);
    }
    public void invitationEchecRecue() {
        //à compléter
    }
    public void invitationEchecAnnulee() {
        //à compléter
    }

    public void setFenetreEchecs(FenetreEchecs fenetreEchecs) {
        if (this.fenetreEchecs!=null){
            this.fenetreEchecs.setVisible(false);
            this.fenetreEchecs.dispose();
        }
        this.fenetreEchecs = fenetreEchecs;
    }
    public FenetreEchecs getFenetreEchecs() {
        return this.fenetreEchecs;
    }
}