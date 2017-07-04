package com.nbcuni.test.cms.utils.mail;

import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.webdriver.Utilities;
import com.sun.mail.imap.IMAPFolder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Mailer {
    Properties props;
    String username;
    String password;
    List<String> recepients = new LinkedList<String>();
    List<String> recepientsCc = new LinkedList<String>();
    String subject;
    List<String> body = new LinkedList<String>();
    List<File> attachments = new LinkedList<File>();

    public Mailer() {
        setUsername(Config.getInstance().getGmailUser());
        setPassword(Config.getInstance().getGmailPassword());
    }

    public Mailer(String user, String password) {
        this();
        setUsername(user);
        setPassword(password);
    }

    public static String getMessageSubject(Message message) {
        try {
            return message.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
            Utilities.logInfoMessage("Can't get messages subject for some reasons");
            return null;
        }
    }

    public static String getMessageContent(Message message) {
        try {
            return getMessageContent(message.getContent(),
                    message.getContentType());
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            Utilities.logInfoMessage("Cant get messages body by some reasons");
            return null;
        }
    }

    private static String getMessageContent(Object content, String mimeType)
            throws IOException, MessagingException {
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof MimeMultipart) {
            Multipart m = (Multipart) content;
            int parts = m.getCount();
            for (int i = parts - 1; i >= 0; i--) {
                String result = null;
                BodyPart part = m.getBodyPart(i);
                Object partContent = part.getContent();
                String contentType = part.getContentType();
                Utilities.logInfoMessage("Examining Multipart " + i + " with type "
                        + contentType + " and class " + partContent.getClass());
                if (partContent instanceof String) {
                    result = (String) partContent;
                } else if (partContent instanceof InputStream
                        && (contentType.startsWith("text/html"))) {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy((InputStream) partContent, writer);
                    result = writer.toString();
                } else if (partContent instanceof MimeMultipart) {
                    result = getMessageContent(partContent, contentType);
                }
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getRecepients() {
        return recepients;
    }

    public void setRecepients(List<String> to) {
        this.recepients = to;
    }

    public void setRecepient(String recepient) {
        recepients.clear();
        recepients.add(recepient);
    }

    public void addRecepient(String recepientEmail) {
        recepients.add(recepientEmail);
    }

    public void clearRecepients() {
        recepients.clear();
    }

    public List<String> getRecepientsCc() {
        return recepientsCc;
    }

    public void setRecepientsCc(List<String> recepientsCc) {
        this.recepientsCc = recepientsCc;
    }

    public void setRecepientCc(String recepientCc) {
        recepientsCc.clear();
        recepientsCc.add(recepientCc);
    }

    public void addRecepientCc(String recepientEmail) {
        recepientsCc.add(recepientEmail);
    }

    public void clearRecepientsCc() {
        recepientsCc.clear();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBody(List<String> body) {
        this.body = body;
    }

    public void addTextToBody(String bodyMessage) {
        body.add(bodyMessage);
    }

    public void clearBody() {
        body.clear();
    }

    public void addAttachment(File attachement) {
        attachments.add(attachement);
    }

    public void addAttachment(String attachementPath) {
        File attachement = new File(attachementPath);
        attachments.add(attachement);
    }

    public void clearAttachments() {
        attachments.clear();
    }

    public List<String> getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body.clear();
        this.body.add(body);
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }

    public void setAttachment(File attachment) {
        attachments.clear();
        attachments.add(attachment);
    }

    public void sendEmail() {
        // Get system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", Config.getInstance()
                .getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.smtp.auth",
                Config.getInstance().getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.host",
                Config.getInstance().getProperty("mail.smtp.host"));
        properties.put("mail.smtp.port",
                Config.getInstance().getProperty("mail.smtp.port"));
        // Get the default Session object.
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(username));

            // Set To: header field of the header.
            InternetAddress[] addresses = new InternetAddress[recepients.size()];
            int i = 0;
            for (String recepient : recepients) {
                addresses[i] = new InternetAddress(recepient);
                i++;
            }
            message.addRecipients(Message.RecipientType.TO, addresses);

            // Set Cc: header field of the header.
            addresses = new InternetAddress[recepientsCc.size()];
            i = 0;
            for (String recepientCc : recepientsCc) {
                addresses[i] = new InternetAddress(recepientCc);
                i++;
            }
            message.addRecipients(Message.RecipientType.CC, addresses);

            // Set Subject: header field
            message.setSubject(getSubject());

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create body
            StringBuilder bodyBuilder = new StringBuilder();
            for (String bodyItem : body) {
                bodyBuilder.append(bodyItem);
            }
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(bodyBuilder.toString(), "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Add attachments
            for (File attachment : attachments) {
                BodyPart attach = new MimeBodyPart();
                if (attachment != null) {
                    DataSource source = new FileDataSource(
                            attachment.getAbsolutePath());
                    attach.setDataHandler(new DataHandler(source));
                    attach.setFileName(attachment.getName());
                    multipart.addBodyPart(attach);
                }
            }

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public void replyToMessage(Message message) {

        // Get system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", Config.getInstance()
                .getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.smtp.auth",
                Config.getInstance().getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.host",
                Config.getInstance().getProperty("mail.smtp.host"));
        properties.put("mail.smtp.port",
                Config.getInstance().getProperty("mail.smtp.port"));
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            MimeMessage mime = new MimeMessage(session);
            mime.setContent((Multipart) message.getContent());
            mime.setRecipients(Message.RecipientType.TO, message.getFrom());
            mime.setSubject("RE: " + message.getSubject());
            Transport.send(mime);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public MailInbox getInboxEmails() {
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", Config.getInstance()
                .getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.smtp.auth",
                Config.getInstance().getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.host",
                Config.getInstance().getProperty("mail.smtp.host"));
        properties.put("mail.smtp.port",
                Config.getInstance().getProperty("mail.smtp.port"));

        Session emailSession = Session.getDefaultInstance(properties, null);

        Message[] messages = null;
        for (int i = 0; i < 5; i++) {
            try {
                Store store = emailSession.getStore("pop3s");
                store.connect(Config.getInstance()
                        .getProperty("mail.smtp.host"), username, password);

                Folder emailFolder = store.getFolder("INBOX");
                emailFolder.open(Folder.READ_ONLY);
                messages = emailFolder.getMessages();
            } catch (Throwable e) {
                e.printStackTrace();
                Utilities.logInfoMessage(e.getMessage());
            }
            if (messages != null) {
                break;
            }
        }
        return new MailInbox(messages);
    }

    public MailInbox getSpamEmails() {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");
        Session emailSession = Session.getDefaultInstance(properties, null);

        Message[] messages = null;
        try {
            Store store = emailSession.getStore("imaps");
            store.connect(Config.getInstance().getProperty("mail.smtp.host"),
                    username, password);

            Folder emailFolder = (IMAPFolder) store.getFolder("[Gmail]/Spam");

            emailFolder.open(Folder.READ_ONLY);
            messages = emailFolder.getMessages();
        } catch (Throwable e) {
            e.printStackTrace();
            Utilities.logInfoMessage(e.getMessage());
        }
        return new MailInbox(messages);
    }

    public int getNumberOfMessages() {
        return this.getInboxEmails().getNumberOfMessages();
    }

    public Message getLastMessage() {
        return this.getInboxEmails().getLastMessage();
    }

    // index 1 = last message , index 2 = one before last
    public Message getLastMessageByIndex(int index) {
        return this.getInboxEmails().getLastMessageByIndex(index);
    }

    public Message getLastMessageBySender(String sender) {
        return this.getInboxEmails().getLastMessageBySender(sender);
    }

    public Message getLastMessageBySubject(String subject) {
        return this.getInboxEmails().getLastMessageBySubject(subject);
    }

    public MailInbox waitForNewEmail(int countOfMessages) {
        Utilities.logInfoMessage("Executing: waiting for a new email");
        int i = 0;
        while (i < 15) {
            MailInbox mailInbox = this.getInboxEmails();
            int forLogger = mailInbox.getNumberOfMessages();
            if (countOfMessages == forLogger) {
                i++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            } else {
                Utilities.logInfoMessage("New email is found");
                Utilities.logInfoMessage("Count messages before: [" + countOfMessages + "]"
                        + " and now [" + forLogger + "]");
                return mailInbox;
            }
        }
        return null;
    }

    public MailInbox waitForNewEmail(Message message) {
        Utilities.logInfoMessage("Executing: waiting for a new email");
        int i = 0;
        while (i < 15) {
            try {
                MailInbox mailInbox = this.getInboxEmails();
                Message lastMessageBySender = mailInbox
                        .getLastMessageBySender(message.getFrom()[0].toString());
                if (!message.getSentDate().before(
                        lastMessageBySender.getSentDate())) {
                    i++;
                    Thread.sleep(5000);
                } else {
                    Utilities.logInfoMessage("New email is found");
                    return mailInbox;
                }
            } catch (MessagingException | InterruptedException e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    public MailInbox waitForNewEmail(int countOfMessages, String sender) {
        Utilities.logInfoMessage("Executing: waiting for a new email");
        int i = 0;
        while (i < 20) {
            MailInbox mailInbox = this.getInboxEmails();
            if (countOfMessages == mailInbox.getNumberOfMessages(sender)) {
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            } else {
                Utilities.logInfoMessage("New email is found");
                return mailInbox;
            }
        }
        return null;
    }

    public MailInbox waitForNewEmail(String sender) {
        Utilities.logInfoMessage("Executing: waiting for a new email");
        int i = 0;
        while (i < 15) {
            MailInbox mailInbox = this.getInboxEmails();
            Message lastMessageBySender = mailInbox
                    .getLastMessageBySender(sender);
            if (lastMessageBySender == null) {
                i++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.getMessage();
                }
            } else {
                Utilities.logInfoMessage("New email is found");
                return mailInbox;
            }
        }
        return null;
    }

    public boolean isRecepientPresent(Message message, String recepient) {
        if (message != null) {
            try {
                for (Address adress : message.getAllRecipients()) {
                    if (adress.toString().equals(recepient)) {
                        return true;
                    }
                }
            } catch (MessagingException e) {
                Utilities.logSevereMessage("there is error during getting email recepients");
                return false;
            }
        }
        return false;

    }

    /**
     * getMessageAttachments - download attachments from email to
     *          temporary folder. New folder with timestamp as name is created
     *          in global temp directory.
     *
     * @param message - Message from the email inbox.
     * @param deleteOnExit - not mandatory parameter. By default all downloaded
     *         attachments will be removed after tests are finished. If set to
     *         FALSE downloaded attachemnts will stay on system hard drive after
     *         exit.
     * @return return list of files wich represents downloaded attachments.
     */
    public List<File> getMessageAttachments(Message message,
                                            Boolean... deleteOnExit) {
        try {
            return getMessageAttachments(message.getContent(),
                    message.getContentType(), deleteOnExit);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            Utilities.logInfoMessage("Cant get messages attachments for some reasons");
            return null;
        }
    }

    private List<File> getMessageAttachments(Object content, String mimeType,
                                             Boolean... deleteOnExit) throws IOException, MessagingException {
        String dirName = String.valueOf(System.currentTimeMillis());
        File directory = new File(Config.getInstance().getPathToTempFiles()
                + dirName);
        directory.mkdir();
        if (deleteOnExit.length == 0
                || (deleteOnExit.length > 0 && deleteOnExit[0])) {
            directory.deleteOnExit();
        }
        List<File> attachments = new ArrayList<File>();
        if (content instanceof MimeMultipart) {
            Multipart m = (Multipart) content;
            int parts = m.getCount();
            for (int i = parts - 1; i >= 0; i--) {
                BodyPart part = m.getBodyPart(i);
                if (!Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())
                        && !StringUtils.isNotBlank(part.getFileName())) {
                    continue; // dealing with attachments only
                }
                InputStream is = part.getInputStream();
                File f = new File(Config.getInstance().getPathToTempFiles()
                        + File.separator + dirName + File.separator
                        + part.getFileName());
                if (deleteOnExit.length == 0
                        || (deleteOnExit.length > 0 && deleteOnExit[0])) {
                    f.deleteOnExit();
                }
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buf = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buf)) != -1) {
                    fos.write(buf, 0, bytesRead);
                }
                fos.close();
                attachments.add(f);
            }
        }
        return attachments;
    }
}
