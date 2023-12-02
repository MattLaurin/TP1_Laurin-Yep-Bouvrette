package com.chat.commun.evenement;

/**
 * Cette interface représente un gestionnaire d'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
@FunctionalInterface
public interface GestionnaireEvenement {
	/**
	* Méthode de gestion de l'événement.
	*
	* @param evenement L'événement à gérer.
	*/
	void traiter(Evenement evenement);
}
