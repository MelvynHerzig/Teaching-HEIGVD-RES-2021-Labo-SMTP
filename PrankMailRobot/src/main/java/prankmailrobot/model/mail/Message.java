package prankmailrobot.model.mail;

import java.util.ArrayList;

/**
 * Classe définissant le contenu d'un email SMTP.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class Message
{
   /**
    * Expéditeur
    */
   private final String from;

   /**
    * Liste des récepteurs.
    */
   private final ArrayList<String> to;

   /**
    * Liste des personnes jointes.
    */
   private final ArrayList<String> cc;

   /**
    * Sujet du mail
    */
   private final String subject;

   /**
    * Contenu du mail
    */
   private final String content;

   /**
    * Constructeur.
    * @param from Expéditeur
    * @param to Récepteurs
    * @param cc En copies.
    * @param subject Sujet
    * @param content Contenu du mail.
    */
   public Message(String from, ArrayList<String> to, ArrayList<String> cc, String subject, String content)
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
   public String getFrom()
   {
      return from;
   }

   /**
    * Getter des récepteurs
    * @return Retourne les récepteurs.
    */
   public ArrayList<String> getTo()
   {
      return to;
   }

   /**
    * Getter des personnes en copies.
    * @return Retourne les personnes en copies.
    */
   public ArrayList<String> getCc()
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
