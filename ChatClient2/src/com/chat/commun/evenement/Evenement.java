package com.chat.commun.evenement;
/**
 * Cette classe représente un événement. Un événement est caractérisé par son type (String) et peut avoir des
 * arguments regroupés dans une chaine de caractères (String).
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Evenement {
    private Object source;
    private final String type, argument;

    /**
     * Construit un événement.
     *
     * @param source La source de l'événement
     * @param type Le type de l'événement
     * @param argument Chaine de caractères contenant les arguments de l'événement
     */
    public Evenement(Object source, String type, String argument) {
        this.source = source;
        this.type = type;
        this.argument = argument;
    }

    /**
     * Retourne la source de l'événement
     *
     * @return Object source de l'événement
     */
    public Object getSource() {
        return source;
    }
    /**
     * Retourne le type de l'événement
     *
     * @return String type de l'événement
     */
    public String getType() {
        return type;
    }
    /**
     * Retourne l'argument de l'événement
     *
     * @return String argument de l'événement
     */
    public String getArgument() {
        return argument;
    }
}
