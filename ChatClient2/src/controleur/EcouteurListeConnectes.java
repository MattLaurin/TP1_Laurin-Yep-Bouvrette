package controleur;

import com.chat.client.ClientChat;
import com.chat.commun.net.Connexion;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurListeConnectes extends MouseAdapter {

    private ClientChat clientChat;
    public EcouteurListeConnectes(ClientChat clientChat) {
        this.clientChat = clientChat;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if(evt.getClickCount() == 2 ){
            String userSelect = userSelect(evt);
            if(userSelect != null){
                clientChat.envoyer("JOIN " + userSelect);
            }
        }
    }

    private String userSelect(MouseEvent evt){
        if(evt.getSource() instanceof JList){
            JList<String> listeUsers = (JList<String>) evt.getSource();
            int index = listeUsers.locationToIndex(evt.getPoint());
            if(index != -1) {       // Vérifier qu'il a pas cliquer dans le vide.
                return listeUsers.getModel().getElementAt(index);
            }
        }
        return null;
    }
}

