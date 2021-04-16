/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.model.mail.Person.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.model.mail;

/**
 * Classe définissant une personne qui envoie/recoit des emails.
 * @author Forestier Quentin et Herzig Melvyn
 * @version 16/04/2021
 */
public class Person
{
   private String addressMail;

   /**
    * Constructeur.
    * @param addressMail Adresse mail de la personne.
    */
   public Person(String addressMail)
   {
      this.addressMail = addressMail;
   }

   /**
    * Getter adresse mail.
    * @return Retourne l'adresse email de la personne.
    */
   public String getAddressMail()
   {
      return addressMail;
   }
}
