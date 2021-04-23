package prankmailrobot.config;

import prankmailrobot.PrankMailRobot;
import prankmailrobot.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Classe qui parse la configuration de PrankMailRobot.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 23/04/2021
 */
public class ConfigurationManager implements IConfigurationManager
{
   /**
    * Adresse du serveur smtp à emprunter.
    */
   private String smtpServerAddress;

   /**
    * Port du serveur smtp
    */
   private int smtpServerPort;

   /**
    * Nombre de groupes à former.
    */
   private int numberOfGroups;

   /**
    * Personnes à joindre au mail
    */
   private final ArrayList<Person> witnessesToCC = new ArrayList<>();

   /**
    * Victimes du prank
    */
   private final ArrayList<Person> victims = new ArrayList<>();

   /**
    * Contenu du fichier messages.utf8
    */
   private final ArrayList<String> contents = new ArrayList<>();

   /**
    * Constructeur, lis les fichiers de configuration.
    */
   public ConfigurationManager()
   {
      loadProperties();
      loadAddressesFromFile();
      loadMessagesFromFile();
   }

   /**
    * Lis la configuration de config.properties
    */
   private void loadProperties()
   {
      // Ouverture d'un reader sur le fichier.
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("PrankMailRobot/config/config.properties"), PrankMailRobot.encoding)))
      {
         Properties properties = new Properties();
         properties.load(reader);

         // Lecture des propriétés des propriétés.
         this.smtpServerAddress = properties.getProperty("smtpServerAddress");
         this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
         this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));

         String witnesses = properties.getProperty("witnessesToCC");
         String[] witnessesAddress = witnesses.split(", ");
         for(String address : witnessesAddress)
         {
            this.witnessesToCC.add(new Person(address));
         }
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
         e.printStackTrace();
         System.exit(-1);
      }

   }

   /**
    * Lis le fichier victims afin d'obtenir les adresses.
    */
   private void loadAddressesFromFile()
   {
      // Ouverture d'un reader sur le fichier victims
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("PrankMailRobot/config/victims.utf8"), PrankMailRobot.encoding)))
      {
         // Lecture d'une adresse.
         String address = reader.readLine();
         while(address != null) // Tant qu'il y a des adresses.
         {
            victims.add(new Person(address));
            address = reader.readLine();
         }
      }
      catch(IOException e)
      {
         System.out.println(e.getMessage());
         e.printStackTrace();
         System.exit(-1);
      }
   }

   /**
    * Lis le fichier des messages afin d'obtenir les prank à envoyer.
    */
   private void loadMessagesFromFile()
   {
      // Ouverture d'un reader sur le fichier messages
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("PrankMailRobot/config/messages.utf8"), PrankMailRobot.encoding)))
      {
         String line = reader.readLine();
         while(line != null) // Tant que le fichier n'est pas vide
         {
            StringBuilder sb = new StringBuilder(); // Lecture du message.
            while((line != null) && (!line.equals("=="))) // Tant que pas un nouveau contenu ou fin de fichier.
            {
               sb.append(line).append("\r\n");
               line = reader.readLine();
            }
            // Ajout du contenu dans la liste des contenus.
            contents.add(sb.toString());

            line = reader.readLine();
         }
      }
      catch(IOException e)
      {
         System.out.println(e.getMessage());
         e.printStackTrace();
         System.exit(-1);
      }

   }

   /**
    * Getter de l'adresse du serveur smtp.
    * @return Retourne l'adresse du serveur smtp.
    */
   @Override
   public String getSmtpServerAddress()
   {
      return smtpServerAddress;
   }

   /**
    * Getter du port du serveur smtp.
    * @return Retourne le port du serveur smtp.
    */
   @Override
   public int getSmtpServerPort()
   {
      return smtpServerPort;
   }

   /**
    * Getter du nombre de groupe de victimes.
    * @return Retourne le nombre de groupes.
    */
   @Override
   public int getNumberOfGroups()
   {
      return numberOfGroups;
   }

   /**
    * Getter des personnes en CC.
    * @return Retourne les personnes en copie.
    */
   @Override
   public ArrayList<Person> getWitnessesToCC()
   {
      return witnessesToCC;
   }

   /**
    * Getter des victimes.
    * @return Retourne les victimes.
    */
   @Override
   public ArrayList<Person> getVictims()
   {
      return victims;
   }

   /**
    * Getter des contenus.
    * @return Retourne les contenus.
    */
   @Override
   public ArrayList<String> getContents()
   {
      return contents;
   }

}
