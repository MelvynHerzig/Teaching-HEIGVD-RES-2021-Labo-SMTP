/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.model.mail.Group.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe définissant un group de personnes pour les emails
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class Group
{
   /**
    * Récepteurs de l'email.
    */
   private final ArrayList<Person> receivers = new ArrayList<>();

   /**
    * Exéditeur de l'email.
    */
   private Person sender;

   /**
    * Constructeur.
    * La première personne est l'expéditrice et le reste les réceptrices.
    * Une personne nulle ou en double est ignorée.
    * @param persons Liste des personnes du groupe de l'email.
    */
   public Group(Person...persons)
   {
      boolean first = true;

      for(Person person: persons)
      {
         if(person == null) continue;
         //La première personne est l'expéditeur.
         if(first)
         {
            first = false;
            sender = person;
         }
         // Le reste sont les réceptrices.
         else
         {
            if(!receivers.contains(person) && person != sender)
            {
               receivers.add(person);
            }
         }
      }
   }

   /**
    * Getter de l'expéditeur.
    * @return Retourne l'expéditeur de l'email.
    */
   public Person getSender()
   {
      return sender;
   }

   /**
    * Getter des récepteurs de l'email.
    * @return Retourne les récepteur de l'email.
    */
   public List<Person> getReceivers()
   {
      return receivers;
   }
}
