package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;
import vue.PanneauInvitations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EcouteurEvenementsAction implements ActionListener {
    protected ClientChat clientChat;
    protected PanneauInvitations panneauInvite;

    public EcouteurEvenementsAction(ClientChat clientChat, PanneauInvitations panneauInvite){
        this.clientChat = clientChat;
        this.panneauInvite = panneauInvite;
    }

    public void actionPerformed(ActionEvent evt){
        List<String> invitationSelect = panneauInvite.getElementsSelectionnes();
        if (evt.getActionCommand().equals("ACCEPTER")){
            for(String i : invitationSelect) {
                clientChat.envoyer("JOIN " + i);
                panneauInvite.retirerInvitationRecue(i);
            }
        }
        else if (evt.getActionCommand().equals("REFUSER")){
            for(String i : invitationSelect) {
                clientChat.envoyer("DECLINE " + i);
                panneauInvite.retirerInvitationRecue(i);
            }
        }
    }

}
