package prankmailrobot.model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe définissant un group de personnes pour les emails
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class Group
{
   /**
    * Récepteurs de l'email.
    */
   private final ArrayList<Person> receivers = new ArrayList<>();

   /**
    * Exéditeur de l'email.
    */
   private Person sender;

   /**
    * Constructeur.
    * La première personne est l'expéditrice et le reste les réceptrices.
    * Une personne nulle ou en double est ignorée.
    * @param persons Liste des personnes du groupe de l'email.
    */
   public Group(List<Person> persons)
   {
      boolean first = true;

      for(Person person: persons)
      {
         if(person == null) continue;
         //La première personne est l'expéditeur.
         if(first)
         {
            first = false;
            sender = person;
         }
         // Le reste sont les réceptrices.
         else
         {
            if(!receivers.contains(person) && person != sender)
            {
               receivers.add(person);
            }
         }
      }
   }

   /**
    * Getter de l'expéditeur.
    * @return Retourne l'expéditeur de l'email.
    */
   public Person getSender()
   {
      return sender;
   }

   /**
    * Getter des récepteurs de l'email.
    * @return Retourne les récepteur de l'email.
    */
   public ArrayList<Person> getReceivers()
   {
      return receivers;
   }

   /**
    * Crée les groupes en fonctions des personnes à piéger et du nombre de groupes
    * @param persons Liste des victimes.
    * @param nbGroup Nombre de groupes
    * @return Retourne la liste des groupes formés.
    * @throws RuntimeException si il n'est pas possible de former des groupes d'au moins 3 personnes.s
    */
   public static ArrayList<Group> makeGroups(List<Person> persons, int nbGroup)
   {
      // Vérification de la taille minimale des groupes.
      if(persons.size() / 3 < nbGroup)
      {
         throw new RuntimeException("Pas assez de victimes pour le nombre de groupes (min 3p / groupe)");
      }

      // Nombre de membres d'un groupe.
      double sizeGroup = (double)persons.size() / (double)nbGroup;

      // Groupes résultants
      ArrayList<Group> groups = new ArrayList<>();

      // Par groupe
      for(int i = 0; i < nbGroup ; ++i)
      {
         // Si c'est le dernier groupe, arrondi le nombre de personnes à insérer au supérieur.
         int nbPersonToInsert = (int) ((i != nbGroup - 1) ? Math.floor(sizeGroup) : Math.ceil(sizeGroup));
         ArrayList<Person> victims = new ArrayList<>(persons.subList(0, nbPersonToInsert));

         // Retrait des personnes qui viennent d'être groupées.
         persons.subList(0, nbPersonToInsert).clear();

         // Création du groupe
         groups.add(new Group(victims));
      }

      return groups;
   }
}
