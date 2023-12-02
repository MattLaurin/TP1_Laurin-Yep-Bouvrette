package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauInvitations extends JPanel {
    private JList<String> jlInvitationsRecues;
    private DefaultListModel<String> invitationsRecues;
    private JButton bAccepte, bRefuse;
    private ActionListener ecouteur;

    public PanneauInvitations() {
        Font police = new Font("",Font.BOLD,15);

        invitationsRecues = new DefaultListModel<>();
        jlInvitationsRecues = new JList<>(invitationsRecues);

        bAccepte = new JButton("+");
        bAccepte.setActionCommand("ACCEPTER");
        bRefuse = new JButton("x");
        bRefuse.setActionCommand("REFUSER");
        bAccepte.setFont(police);
        bRefuse.setFont(police);
        bAccepte.setForeground(new Color(0.2f,0.8f,0.2f));
        bAccepte.setBackground(Color.WHITE);
        bRefuse.setForeground(Color.RED);
        bRefuse.setBackground(Color.WHITE);

        this.setLayout(new BorderLayout());
        JPanel pSud = new JPanel();
        JScrollPane jsp1 = new JScrollPane(jlInvitationsRecues);
        jsp1.setBorder(BorderFactory.createTitledBorder("Invitations reçues"));

        pSud.add(bAccepte);
        pSud.add(bRefuse);
        this.add(jsp1, BorderLayout.CENTER);
        this.add(pSud, BorderLayout.SOUTH);
    }
    public void setEcouteur(ActionListener ecouteur) {
        this.ecouteur = ecouteur;
        bAccepte.addActionListener(ecouteur);
        bRefuse.addActionListener(ecouteur);
    }
    public void ajouterInvitationRecue(String alias) {
        invitationsRecues.addElement(alias);
    }
    public void retirerInvitationRecue(String alias) {
        invitationsRecues.removeElement(alias);
    }
    public List<String> getElementsSelectionnes() {
        return jlInvitationsRecues.getSelectedValuesList();
    }
    public void vider() {
        this.invitationsRecues.clear();
    }
}