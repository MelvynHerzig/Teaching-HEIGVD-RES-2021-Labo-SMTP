package prankmailrobot.smtp;

import prankmailrobot.PrankMailRobot;
import prankmailrobot.model.mail.Message;

import java.io.PrintWriter;
import java.io. BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui implémente l'interface ISmtpClient pour envoyer des pranks.
 * @author Forestier Quentin et Herzig Melvyn
 * @date 16/04/2021
 */
public class SmtpClient implements ISmtpClient
{
   /**
    * Logger l'exécution du client
    */
   private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

   /**
    * Adresse du serveur smtp.
    */
   private final String address;

   /**
    * Port du serveur smtp.
    */
   private final int port;

   /**
    * Socket en connexion avec le serveur smtp.
    */
   private Socket socket = null;

   /**
    * Stream d'écriture pour envoyer des messages au serveur mail.
    */
   private PrintWriter out = null;

   /**
    * Stream de lecture pour lire les messages en provenance du serveur mail.
    */
   private BufferedReader in = null;

   /**
    * Constructeur
    * @param address Adresse du serveur.
    * @param port Numéro de port du serveur.
    */
   public SmtpClient(String address, int port)
   {
      this.address = address;
      this.port = port;

      LOG.log(Level.INFO, "SMTPClient created...");
   }

   @Override
   public void sendMessages(List<Message> messages)
   {
      // Initialisation de la connexion.
      connect();
      skipMessageServer("220");

      // Pour chaque message
      for(Message message : messages)
      {
         if(message == null)
         {
            continue;
         }

         // EHLO
         out.println("EHLO localhost");
         skipMessageServer("250");


         // AUTH mailtrap.io
         /*out.println("AUTH LOGIN");
         skipMessageServer("334");

         out.println("M2NmNzUyMTA1OWRmZGE=");
         skipMessageServer("334");

         out.println("NjM1YzJhM2Y4Mjg3YzE=");
         skipMessageServer("235");*/


         // FROM
         out.println("MAIL FROM: <" + message.getFrom() + ">");
         skipMessageServer("250");

         // RCPT TO
         for (String addresse : message.getTo()) // To
         {
            out.println("RCPT TO: <" +  addresse + ">");
            skipMessageServer("250");
         }

         for (String addresse : message.getCc()) // Cc
         {
            out.println("RCPT TO: <" +  addresse + ">");
            skipMessageServer("250");
         }

         // DATA
         out.println("DATA");
         skipMessageServer("354");

         StringBuilder content = new StringBuilder();

         // from
         content.append("Content-Type: text/plain; charset=utf-8\r\n");
         content.append("From: ").append(message.getFrom()).append("\r\n");

         // to
         content.append("To: ").append(message.getTo().get(0));
         for(int i = 1; i < message.getTo().size(); ++i)
         {
            content.append(", ").append(message.getTo().get(i));
         }
         content.append("\r\n");

         // cc
         content.append("Cc: ").append(message.getCc().get(0));
         for(int i = 1; i < message.getCc().size(); ++i)
         {
            content.append(", ").append(message.getCc().get(i));
         }
         content.append("\r\n");

         // sujet
         content.append("Subject: =?UTF-8?B?").append(Base64.getEncoder().encodeToString(message.getSubject().getBytes(PrankMailRobot.encoding))).append("?=\r\n");

         // corps du message
         content.append("\r\n");
         content.append(message.getContent());
         content.append("\r\n");
         content.append(".\r\n");

         out.println(content.toString());

         skipMessageServer("250");

         // Réinitialisation de la transaction pour un autre email
         out.println("RSET");
         skipMessageServer("250");

         LOG.log(Level.INFO, "Message sent");
      }

      LOG.log(Level.INFO, "All messages sent");
      out.println("QUIT");
      disconnect();
   }

   /**
    * Connect le client au serveur spécifié.
    */
   private void connect()
   {
      try
      {
         // Préparation des flux.
         socket = new Socket(address, port);
         out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), PrankMailRobot.encoding), true);
         in = new BufferedReader(new InputStreamReader(socket.getInputStream(), PrankMailRobot.encoding));

         LOG.log(Level.INFO, "Smtp client connected to server...");
      }
      catch(IOException e)
      {
         LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
         disconnect();
      }
   }

   /**
    * Ferme la connexion entre le client et le serveur mail.
    */
   private void disconnect()
   {
      // Flux d'entrée
      try
      {
         if (in != null)
         {
            in.close();
         }
      }
      catch (IOException e)
      {
         LOG.log(Level.SEVERE, e.getMessage(), e);
      }

      // Flux de sortie
      if (out != null)
      {
         out.close();
      }

      // Socket
      try
      {
         if (socket != null)
         {
            socket.close();
         }
      }
      catch (IOException e)
      {
         LOG.log(Level.SEVERE, e.getMessage(), e);
      }
   }

   /**
    * Lis un message du serveur sans le considérer.
    * Lis jusqu'a atteindre le pattern "expectedCode "
    * Si un autre code et reçu, lève une exception.
    * @param expectedCode Code que l'on souhaite recevoir.
    * @throws RuntimeException Si le code de retour ne correspond pas à expectedCode.
    */
   private void skipMessageServer(String expectedCode)
   {
      try
      {
         String line = in.readLine();
         LOG.log(Level.INFO, "Message read: " + line);

         // Si le message reçu ne commence pas par le code attendu.
         if(line != null && !line.startsWith(expectedCode))
         {
            LOG.log(Level.SEVERE, "An error occured, shutting down...");
            disconnect();
            throw new RuntimeException("Error while talking with the server");
         }

         while(line != null && !line.startsWith(expectedCode + " ")) // Lis toutes les lignes jusqu'a obtenir le code final.
         {
            LOG.log(Level.INFO, "Message skipped");

            line = in.readLine();
            LOG.log(Level.INFO, "Message read: " + line);
         }
      }
      catch (IOException e)
      {
         LOG.log(Level.SEVERE, "Error while reading server" + e);
      }
   }
}
