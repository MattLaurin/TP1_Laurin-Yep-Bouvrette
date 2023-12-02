package com.chat.commun.thread;
/**
 * Cette classe permet de créer des threads capables de lire continuellement sur un un objet de type Lecteur.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ThreadEcouteurDeTexte extends Thread {
	Lecteur lecteur;

	/**
	 * Construit un thread sur un lecteur
	 * @param lecteur Le lecteur sur lequel le thread va lire
	 */
	public ThreadEcouteurDeTexte(Lecteur lecteur)
	 {
		 this.lecteur = lecteur;
	 }

	/**
	 * Méthode principale du thread. Cette méthode appelle continuellement la méthode lire() du
	 * lecteur (client ou serveur)
	 */
	public void run()
	 {
		while (!interrupted())
		{
			lecteur.lire();
			try
			{
			  Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				break;
			}			
		}
	 }
}
