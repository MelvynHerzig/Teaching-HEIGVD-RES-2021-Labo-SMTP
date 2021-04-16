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
   private Person from;

   /**
    * Liste des récepteurs.
    */
   private ArrayList<Person> to;

   /**
    * Liste des personnes jointes.
    */
   private ArrayList<Person> cc;

   /**
    * Sujet du mail
    */
   private String subject;

   /**
    * Contenu du mail
    */
   private String content;

   /**
    * Constructeur.
    * @param from Expéditeur
    * @param to Récepteurs
    * @param cc En copies.
    * @param subject Sujet
    * @param content Contenu du mail.
    */
   public Message(Person from, ArrayList<Person> to, ArrayList<Person> cc, String subject, String content)
   {
      this.from = from;
      this.to = to;
      this.cc = cc;
      this.subject = subject;
      this.content = content;
   }

   /**
    * Getter expéditeur
    * @return Retourne l'expéditeur.
    */
   public Person getFrom()
   {
      return from;
   }

   /**
    * Getter des récepteurs
    * @return Retourne les récepteurs.
    */
   public ArrayList<Person> getTo()
   {
      return to;
   }

   /**
    * Getter des personnes en copies.
    * @return Retourne les personnes en copies.
    */
   public ArrayList<Person> getCc()
   {
      return cc;
   }

   /**
    * Getter du sujet du mail.
    * @return Retourne le sujet du mail.
    */
   public String getSubject()
   {
      return subject;
   }

   /**
    * Getter du contenu du mail.
    * @return Retourne le contenu du mail.
    */
   public String getContent()
   {
      return content;
   }
}
