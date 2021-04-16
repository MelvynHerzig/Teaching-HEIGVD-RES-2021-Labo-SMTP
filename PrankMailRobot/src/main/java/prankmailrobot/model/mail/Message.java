/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.model.mail.Message.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.model.mail;

import java.util.ArrayList;

/**
 * Classe définissant le contenu d'un email SMTP.
 * @author Forestier Quentin et Herzig Melvyn
 * @version 16/04/2021
 */
public class Message
{
   /**
    * Expéditeur
    */
   private String from;

   /**
    * Liste des récepteurs.
    */
   private final ArrayList<String> to = new ArrayList<>();

   /**
    * Liste des personnes jointes.
    */
   private final ArrayList<String> cc = new ArrayList<>();

}
