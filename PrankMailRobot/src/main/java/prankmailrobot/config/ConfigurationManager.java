/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.config.ConfigurationManager.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.config;

import prankmailrobot.model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Classe qui charge la configuration de PrankMailRobot.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
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
   private ArrayList<Person> witnessesToCC;

   /**
    * Victimes du prank
    */
   private ArrayList<Person> victims;

   /**
    * Personnes à joindre au mail
    */
   private ArrayList<String> contents;

   /**
    * Constructeur, lis les fichiers de configuration.
    * @throws IOException
    */
   public ConfigurationManager() throws IOException
   {
      loadProperties("./config/config.properties");
      victims = loadAddressesFromFile("./config/victims.utf8");
      contents = loadMessagesFromFile("./config/messages.utf8");
   }

   /**
    * Lis la configuration de config.properties
    * @param fileName Chemin vers config.properties
    * @throws IOException En cas d'erreur de lecture.
    */
   private void loadProperties(String fileName)
   {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))))
      {
         Properties properties = new Properties();
         properties.load(reader);
         this.smtpServerAddress = properties.getProperty("smtpServerAddress");
         this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
         this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
         this.witnessesToCC = new ArrayList<>();

         String witnesses = properties.getProperty("witnessToCC");
         String[] witnessesAddress = witnesses.split(",");
         for(String address : witnessesAddress)
         {
            this.witnessesToCC.add(new Person(address));
         }
      }
      catch (IOException e)
      {
         System.exit(-1);
      }

   }

   /**
    * Lis le fichier victims afin d'obtenir les adresses.
    * @param fileName Fichier contenant les adresses des victimes
    * @return Retourne la liste des victimes de la prank.
    */
   private ArrayList<Person> loadAddressesFromFile(String fileName)
   {
      ArrayList<Person> result = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))))
      {
         String address = reader.readLine();
         while(address != null)
         {
            result.add(new Person(address));
            address = reader.readLine();
         }
      }
      catch(IOException e)
      {
         System.exit(-1);
      }

      return result;
   }

   /**
    * Lis le fichier des messages afin d'obtenir les prank à envoyer.
    * @param fileName Fichier contenant les contenu "prank".
    * @return Retourne la liste des prank.
    */
   private ArrayList<String> loadMessagesFromFile(String fileName)
   {
      ArrayList<String> result = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName))))
      {
         String line = reader.readLine();
         while(line != null)
         {
            StringBuilder sb = new StringBuilder();
            while((line != null) && (!line.equals("==")))
            {
               sb.append(line).append("\r\n");
               line = reader.readLine();
            }
            result.add(sb.toString());
           line = reader.readLine();
         }
      }
      catch(IOException e)
      {
         System.exit(-1);
      }

      return result;
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
