package prankmailrobot;

import prankmailrobot.config.ConfigurationManager;
import prankmailrobot.config.IConfigurationManager;
import prankmailrobot.model.prank.PrankGenerator;
import prankmailrobot.smtp.ISmtpClient;
import prankmailrobot.smtp.SmtpClient;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Classe de l'application à prank.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 23/04/2021
 */
public class PrankMailRobot
{
   /**
    * Encodage à utiliser pour les différents flux.
    */
   public static Charset encoding = StandardCharsets.UTF_8;

   public static void main(String[] args)
   {
      try
      {
         // Parser des propriétés.
         IConfigurationManager configurationManager = new ConfigurationManager();

         // Client SMTP
         String address = configurationManager.getSmtpServerAddress(); // adresse
         int port = configurationManager.getSmtpServerPort();          // port
         ISmtpClient smtpClient = new SmtpClient(address, port);

         // Générateur de pranks
         PrankGenerator pg = new PrankGenerator(configurationManager, smtpClient);

         // Génération des pranks
         pg.makePrank();
      }
      catch(Exception e)
      {
         System.out.println(e.getMessage());
      }
   }
}
