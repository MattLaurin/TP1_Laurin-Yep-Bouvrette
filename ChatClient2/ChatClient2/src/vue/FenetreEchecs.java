package vue;

import com.chat.echecs.EtatPartieEchecs;
import controleur.EcouteurJeuEchecs;

import javax.swing.*;
import java.awt.*;

public class FenetreEchecs extends JFrame {

    PanneauEchiquier panneauEchiquier;

    public FenetreEchecs(PanneauEchiquier panneauEchiquier, String titre) {
        this.panneauEchiquier = panneauEchiquier;
        this.setTitle(titre);
        initialiserComposants();
        configurerFenetrePrincipale();
    }

    private void configurerFenetrePrincipale() {
        //Configuration de la fenêtre
        this.setSize(800,800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initialiserComposants() {
        //Création et initialisation des composants
        this.add(panneauEchiquier);
    }
}