package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurChatPublic implements ActionListener {
    protected ClientChat clientChat;
    protected PanneauChat panneauChat;

    public EcouteurChatPublic(ClientChat clientChat, PanneauChat panneauChat) {
        this.clientChat = clientChat;
        this.panneauChat = panneauChat;
    }
    @Override
    public void actionPerformed(ActionEvent evt) {  //completer et tester ********
        Object action = evt.getSource();
        if (action instanceof JTextField){
            String texte = ((JTextField) action).getText();
            if (!texte.isEmpty()){
                this.panneauChat.ajouter("MOI>> " + texte);
                ((JTextField) action).setText("");
                clientChat.envoyer("MSG " + texte);
            }
        }
    }
}