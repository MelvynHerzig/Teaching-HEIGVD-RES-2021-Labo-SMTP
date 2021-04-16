/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.config.IConfigurationManager.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.config;

import prankmailrobot.model.mail.Person;

import java.util.ArrayList;

/**
 * Interface à implémenter pour être un chargeur de la configuration de PrankMailRobot.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public interface IConfigurationManager
{
   /**
    * Getter de l'adresse du serveur smtp.
    * @return Retourne l'adresse du serveur smtp.
    */
   public String getSmtpServerAddress();

   /**
    * Getter du port du serveur smtp.
    * @return Retourne le port du serveur smtp.
    */
   public int getSmtpServerPort();

   /**
    * Getter du nombre de groupe de victimes.
    * @return Retourne le nombre de groupes.
    */
   public int getNumberOfGroups();

   /**
    * Getter des personnes en CC.
    * @return Retourne les personnes en copie.
    */
   public ArrayList<Person> getWitnessesToCC();

   /**
    * Getter des victimes.
    * @return Retourne les victimes.
    */
   public ArrayList<Person> getVictims();

   /**
    * Getter des contenus.
    * @return Retourne les contenus.
    */
   public ArrayList<String> getContents();
}
