package prankmailrobot.model.prank;

import prankmailrobot.model.mail.Message;
import prankmailrobot.model.mail.Person;

import java.util.ArrayList;

/**
 * Classe définissant un mail "prank".
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class Prank
{

   /**
    * Expéditeur
    */
   private Person victimSender;

   /**
    * Récepteurs
    */
   private ArrayList<Person> victimRecievers;

   /**
    * Personnes en copie.
    */
   private ArrayList<Person> witnesses;

   /**
    * Prank title
    */
   private String title;

   /**
    * Contenu de la prank.
    */
   private String message;

   public Prank(Person sender, ArrayList<Person> recievers, ArrayList<Person> witnesses, String title, String message) {
      this.victimSender = sender;
      this.victimRecievers = recievers;
      this.witnesses = witnesses;
      this.title = title;
      this.message = message;
   }

   /**
    * Génère le message mail à partir des différents attributs
    * @return Retourne le message mail.
    * @throws NullPointerException si une Person est nulle.
    */
   public Message generateMailMessage()
   {
      // Récupération des adresses email de destination.
      ArrayList<String> victimRecieversMail = new ArrayList<>();

      for(Person person : victimRecievers)
      {
         victimRecieversMail.add(person.getAddressMail());
      }

      // Récupération des adresses email jointes.
      ArrayList<String> witnessesMail = new ArrayList<>();

      for(Person person : witnesses)
      {
         witnessesMail.add(person.getAddressMail());
      }

      return new Message(victimSender.getAddressMail(), victimRecieversMail, witnessesMail, title, message);
   }
}
