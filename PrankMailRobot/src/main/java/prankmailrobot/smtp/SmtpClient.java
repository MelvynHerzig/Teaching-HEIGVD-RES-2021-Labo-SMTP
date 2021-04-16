/*
 -----------------------------------------------------------------------------------
 Cours       : RES
 Fichier     : prankmailrobot.smtp.SmtpClient.java
 Auteur(s)   : Forestier Quentin & Herzig Melvyn
 Date        : 16.04.2021
 -----------------------------------------------------------------------------------
 */

package prankmailrobot.smtp;

import prankmailrobot.model.mail.Message;

import java.io.PrintWriter;
import java.io. BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
   private String address;

   /**
    * Port du serveur smtp.
    */
   private int port;

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

   public SmtpClient(String address, int port){
      this.address = address;
      this.port = port;

      LOG.log(Level.INFO, "SMTPClient created...");
   }

   @Override
   public void sendMessages(Message... messages) throws IOException
   {
      for(Message message : messages)
      {
         if(message == null)
         {
            continue;
         }

         // Connexion au serveur.
         connect();

         skipMessageServer("220");

         // EHLO
         sendToServer("EHLO localhost");
         skipMessageServer("250");

         // FROM
         sendToServer("MAIL FROM: " + message.getFrom());

         // RCPT TO
         for (String addresse : message.getTo()) // To
         {
            sendToServer("RCPT TO: " +  addresse);
            skipMessageServer("250");
         }

         for (String addresse : message.getCc()) // Cc
         {
            sendToServer("RCPT TO: " +  addresse);
            skipMessageServer("250");
         }

         // DATA
         sendToServer("DATA");
         skipMessageServer("354");

         // from
         sendToServer("From: " + message.getFrom());

         // to
         String tos = "To: " + message.getTo().get(0);
         for(int i = 1; i < message.getTo().size(); ++i)
         {
            tos += ", " + message.getTo().get(i);
         }
         sendToServer(tos);

         // cc
         String ccs = "Cc: " + message.getCc().get(0);
         for(int i = 1; i < message.getCc().size(); ++i)
         {
            ccs += ", " + message.getCc().get(i);
         }
         sendToServer(ccs);

         // subject
         sendToServer("Subject: " + message.getSubject());

         // body
         sendToServer("");
         sendToServer(message.getContent());
         sendToServer("");
         sendToServer(".");
         sendToServer("");

         skipMessageServer("250");

         LOG.log(Level.INFO, "Message sent");

         disconnect();
      }

      LOG.log(Level.INFO, "All messages send");
   }

   /**
    * Connecte le client au serveur mail.
    */
   private void connect(){
      try
      {
         // Préparation des cannaux.
         socket = new Socket(address, port);
         out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
         in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

         LOG.log(Level.INFO, "Smtp client connected to server...");
      }
      catch(IOException e)
      {
         LOG.log(Level.SEVERE, "Unable to connect to server: {0}", e.getMessage());
         cleanup();
         return;
      }
   }

   /**
    * Déconnecte la connexion entre le client et le serveur mail.
    */
   private void disconnect()
   {
      sendToServer("QUIT");
      skipMessageServer("221");
      cleanup();
   }

   /**
    * Ferme les différents socket et stream.
    */
   private void cleanup()
   {
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

      if (out != null)
      {
         out.close();
      }

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
    * Envoie une ligne au server
    * @param line Ligne à envoyer
    */
   private void sendToServer(String line){
      out.println(line);
      out.flush();
   }

   /**
    * Lis un message du serveur sans le considérer.
    * Lis jusqu'a atteindre le pattern "expectedCode "
    * Si un autre code et reçu, fin du programme.
    * @param expectedCode Code que l'on souhaite recevoir.
    */
   private void skipMessageServer(String expectedCode)
   {
      try
      {
         String line = in.readLine();
         LOG.log(Level.INFO, "Message read: " + line);

         if(line != null && !line.startsWith(expectedCode))
         {
            LOG.log(Level.SEVERE, "An error occured, shutting down...");
            disconnect();
            System.exit(-1);
         }

         while(line != null && !line.startsWith(expectedCode + " ")); // Lis toutes les lignes jusqu'a obtenir le bon code
         {
            // Si la ligne commence par un code autre que celui attendu -> erreur
            if(!line.startsWith(expectedCode))
            {
               LOG.log(Level.SEVERE, "An error occured, shutting down...");
               disconnect();
               System.exit(-1);
            }

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
