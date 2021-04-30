package prankmailrobot.model.prank;

import prankmailrobot.config.ConfigurationManager;
import prankmailrobot.config.IConfigurationManager;
import prankmailrobot.model.mail.Group;
import prankmailrobot.model.mail.Message;
import prankmailrobot.model.mail.Person;
import prankmailrobot.smtp.ISmtpClient;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe permettant de générer des pranks.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class PrankGenerator
{
   /**
    * Classe ayant parsé les configurations.
    */
   private final IConfigurationManager configurationManager;

   /**
    * Classe permettant l'envoi des messages
    */
   private final ISmtpClient smtpClient;

   /**
    * Constructeur.
    * @param configurationManager Objet responsable du traitement des fichiers de configuration.
    * @param smtpClient Object responsable de l'envoi des emails.
    */
   public PrankGenerator(IConfigurationManager configurationManager, ISmtpClient smtpClient)
   {
      this.configurationManager = configurationManager;
      this.smtpClient = smtpClient;
   }

   /**
    * Lance la génération des pranks.
    * @throws RuntimeException Si aucun message n'est configurés.
    */
   public void makePrank()
   {
      // Liste des messages à envoyer
      ArrayList<Message> messages = new ArrayList<>();

      // Liste des contenus du fichier de configuration
      ArrayList<String> contents = configurationManager.getContents();

      if(contents.isEmpty())
      {
         throw new RuntimeException("Pas de message trouvé.");
      }

      // Liste des groupes
      ArrayList<Group> groups = Group.makeGroups(configurationManager.getVictims(),
                                                 configurationManager.getNumberOfGroups());

      // Liste des Cc.
      ArrayList<Person> witness = configurationManager.getWitnessesToCC();

      // Création des messages par groupe.
      for(Group group : groups)
      {
         // Choix d'un contenu aléatoire
         String content = contents.get((int)(Math.random() * contents.size()));

         // Création de la prank
         Prank prank = new Prank(group.getSender(),         // expéditeur
                                 group.getReceivers(),      // destinataires
                                 witness,                   // Cc
                                 extractTitle(content),     // Titre
                                 extractContent(content));  // Corps

         // Génération du message
         messages.add(prank.generateMailMessage());
      }

      // Envoi des messages
      smtpClient.sendMessages(messages);
   }


   /**
    * Extrait le titre d'un contenu lu depuis messages.utf8
    * @param message Contenu extrait de messages.utf8
    * @return Retourne le titre lu.
    */
   static private String extractTitle(String message)
   {
      Scanner scanner = new Scanner(message);
      String firstLine = scanner.nextLine();
      int index = firstLine.indexOf(": ");
      return firstLine.substring(index + 2);
   }

   /**
    * Extrait le cors d'un contenu lu depuis messages.utf8
    * @param message Contenu extrait de messages.uts8
    * @return Retourne le corps lu.
    */
   static private String extractContent(String message)
   {
      return message.substring(message.indexOf("\n") + 1);
   }
}
