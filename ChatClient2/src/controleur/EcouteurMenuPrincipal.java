package controleur;

import com.chat.client.ClientChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurMenuPrincipal implements ActionListener {
    private ClientChat clientChat;
    private JFrame fenetre;

    public EcouteurMenuPrincipal(ClientChat clientChat, JFrame fenetre) {
        this.clientChat = clientChat;
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String action;
        String alias;
        int res;
        boolean recommencer;
        if (source instanceof JMenuItem) {
            action = ((JMenuItem)source).getActionCommand();

            switch (action) {
                case "CONNECTER":
                    if (!clientChat.isConnecte()) {
                        if (!clientChat.connecter())
                            JOptionPane.showMessageDialog(fenetre,"Le serveur ne répond pas");
                    }
                    break;
                case "DECONNECTER":
                    if (!clientChat.isConnecte())
                        break;
                    res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                            "Confirmation Déconnecter",
                            JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if (res == JOptionPane.OK_OPTION){
                        clientChat.deconnecter();
                    }
                    break;
                case "CONFIGURER":
                    //TODO : compléter (question 1.3)
                    break;
                case "QUITTER":
                    if (clientChat.isConnecte()) {
                        res = JOptionPane.showConfirmDialog(fenetre, "Vous allez vous déconnecter",
                                "Confirmation Quitter",
                                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                        if (res == JOptionPane.OK_OPTION){
                            clientChat.deconnecter();
                            System.exit(0);
                        }
                    }
                    else
                        System.exit(0);
                    break;
            }
        }
    }
}