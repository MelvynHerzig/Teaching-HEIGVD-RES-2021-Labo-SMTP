package prankmailrobot.model.mail;

/**
 * Classe définissant une personne qui envoie/recoit des emails.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class Person
{
   private final String addressMail;

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
