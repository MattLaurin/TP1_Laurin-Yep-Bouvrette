package controleur;

import com.chat.client.ClientChat;
import com.chat.commun.net.Connexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class EcouteurJeuEchecs implements ActionListener {

    private ClientChat clientChat;
    private String clic1 = "";
    public EcouteurJeuEchecs(ClientChat clientChat) {
        this.clientChat = clientChat;
    }

    @Override
    public void actionPerformed(ActionEvent e) {    //completer reste a tester
        Object action = e.getSource();
        if (action instanceof JButton){
            JButton boutonCliquer = (JButton) action;
            String text = e.getActionCommand();

            if(text != null && text.length() == 2) {
                char x = text.charAt(0);
                char y = text.charAt(1);

                if (clic1.isEmpty()){
                    clic1 = "" + x + y;
                }else {
                    String clic2 = "" + x + y;
                    String deplacement = clic1 + clic2;
                    clientChat.envoyer("MOVE " + deplacement);
                    clic1 = "";
                }
            }

        }
    }
}