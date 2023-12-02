package observer;

/**
 * @author A. Toudeft
 * @since 2002
 * @version 1.0
 */
public interface Observateur {
	//Permet à l'observateur de se mettre � jour lorsqu'il est notifi� par
	//l'objet Observable qu'il observe :
	public void seMettreAJour(Observable observable);
}

