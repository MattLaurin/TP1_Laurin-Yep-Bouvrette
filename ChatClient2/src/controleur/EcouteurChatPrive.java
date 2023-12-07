package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;

import javax.swing.*;
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
        Object event = evt.getSource();
        if (evt.getActionCommand().equals("ACCEPTER")){
            this.clientChat.envoyer("CHESS " + alias);
        }
        else if (evt.getActionCommand().equals("REFUSER")){
            this.clientChat.envoyer("DECLINE ");
        }
        else if (event instanceof JTextField){
            String msg = ((JTextField) event).getText();
            if(!msg.isEmpty()){
                if (msg.equals("QUIT")) {
                    this.clientChat.envoyer("QUIT " + this.alias);
                }
                else {
                    if (msg.equals("ABANDON")){
                        this.clientChat.envoyer("ABANDON " +this.alias);
                    }
                    else{
                        this.panneauChat.ajouter("MOI>> " + msg);
                        ((JTextField) event).setText("");
                        this.clientChat.envoyer("PRV " + this.alias + " " + msg);
                    }
                }
            }


        }

    }

}