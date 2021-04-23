package prankmailrobot.smtp;

import prankmailrobot.model.mail.Message;

import java.io.IOException;
import java.util.List;

/**
 * Interface a implémenter pour être un smtp client du prankMailRobot.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public interface ISmtpClient
{
   /**
    * Envoie les messages en paramètre grâce au protocole SMTP.
    * @param messages Messages à envoyer.
    */
   public void sendMessages(List<Message> messages);
}
