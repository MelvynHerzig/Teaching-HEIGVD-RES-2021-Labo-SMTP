package prankmailrobot.config;

import prankmailrobot.model.mail.Person;

import java.util.ArrayList;

/**
 * Interface à implémenter pour être un parser de la configuration de PrankMailRobot.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public interface IConfigurationManager
{
   /**
    * Getter de l'adresse du serveur smtp.
    * @return Retourne l'adresse du serveur smtp.
    */
   String getSmtpServerAddress();

   /**
    * Getter du port du serveur smtp.
    * @return Retourne le port du serveur smtp.
    */
   int getSmtpServerPort();

   /**
    * Getter du nombre de groupe de victimes.
    * @return Retourne le nombre de groupes.
    */
   int getNumberOfGroups();

   /**
    * Getter des personnes en CC.
    * @return Retourne les personnes en copie.
    */
   ArrayList<Person> getWitnessesToCC();

   /**
    * Getter des victimes.
    * @return Retourne les victimes.
    */
   ArrayList<Person> getVictims();

   /**
    * Getter des contenus.
    * @return Retourne les contenus.
    */
   ArrayList<String> getContents();
}
