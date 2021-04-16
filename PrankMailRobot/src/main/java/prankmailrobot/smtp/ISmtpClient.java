/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.smtp.ISmtpClient.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.smtp;

import prankmailrobot.model.mail.Message;

/**
 * Interface a implémenter pour être un smtp client du prankMailRobot.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public interface ISmtpClient
{
   /**
    * Envoie les messages en paramètre grâce au protocole SMTP.
    * @param messages
    */
   public void sendMessages(Message ... messages);
}
