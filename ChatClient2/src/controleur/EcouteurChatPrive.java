package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;

import java.awt.event.ActionEvent;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurChatPrive extends EcouteurChatPublic {
    private String alias;
    public EcouteurChatPrive(String alias, ClientChat clientChat, PanneauChat panneauChat) {
        super(clientChat, panneauChat);
        this.alias = alias;
    }
    //à compléter (redéfinir la méthode actionPerformed())
    public void actionPerformed(ActionEvent evt){
        if (evt.getActionCommand().equals("ACCEPTER")){
            clientChat.envoyer("CHESS ");
        }
        else if (evt.getActionCommand().equals("REFUSER")){
            clientChat.envoyer("DECLINE ");
        }
    }

}