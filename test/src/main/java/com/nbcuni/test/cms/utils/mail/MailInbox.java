package com.nbcuni.test.cms.utils.mail;

import com.nbcuni.test.webdriver.Utilities;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;

public class MailInbox {

    private Message[] messages;

    public MailInbox(Message[] messages) {
        this.messages = messages;
    }

    public int getNumberOfMessages() {
        return getNumberOfMessages(null);
    }

    public Message[] getMessages() {
        return messages;
    }

    public Message getMessageByIndex(int index) {
        return messages[index];
    }

    public Message getLastMessageBySender(final String sender) {
        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];
            String senderFromMessage = null;
            try {
                senderFromMessage = message.getFrom()[0].toString();
            } catch (MessagingException e) {
                Utilities.logSevereMessage("Error getting subject from e-mail");
            }
            if (senderFromMessage.equals(sender)) {
                Utilities.logInfoMessage("Message with sender [" + sender
                        + "] is founded");
                return message;
            }
        }
        Utilities.logInfoMessage("Havent email with sender: [" + sender + "].");
        return null;
    }

    public Message getLastMessageBySubject(final String subject) {
        for (int i = messages.length - 1; i >= 0; i--) {
            Message message = messages[i];
            String subjectFromMessage = null;
            try {
                subjectFromMessage = message.getSubject();
            } catch (MessagingException e) {
                Utilities.logSevereMessage("Error getting subject from e-mail");
            }
            if (subjectFromMessage.equals(subject)) {
                Utilities.logInfoMessage("Message with sender [" + subject
                        + "] is founded");
                return message;
            }
        }
        Utilities.logInfoMessage("Havent email with sender: [" + subject + "].");
        return null;
    }

    public int getNumberOfMessages(final String sender) {
        if (sender == null) {
            return messages.length;
        } else {
            int number = 0;
            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                String senderFromMessage = null;
                try {
                    senderFromMessage = message.getFrom()[0].toString();
                } catch (MessagingException e) {
                    Utilities.logSevereMessage("Error getting subject from e-mail");
                }
                if (senderFromMessage.equals(sender)) {
                    number++;
                }
            }
            return number;
        }
    }

    public void printMessage() {
        for (Message message : messages) {
            try {
                Utilities.logInfoMessage(message.getSubject());
            } catch (MessagingException e) {

                e.printStackTrace();
            }
        }
    }

    public Message getLastMessageByIndex(int index) {
        return messages[messages.length - index];
    }

    public Message getLastMessage() {
        return messages[messages.length - 1];
    }

    public Message getMessageBySubject(String subject) {
        for (Message message : messages) {
            String subjFromMessage = null;
            try {
                subjFromMessage = message.getSubject();
            } catch (MessagingException e) {
                Utilities.logSevereMessage("Error getting subject from e-mail");
            }
            if (subjFromMessage.equals(subject)) {
                return message;
            }
        }
        throw new RuntimeException("Havent email with subject: [" + subject
                + "].");
    }

    public void downloadAttachment(Message message, File file) {
        Multipart multiPart;
        try {
            multiPart = (Multipart) message.getContent();
            for (int i = 0; i < multiPart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    part.saveFile(file);
                }
            }
        } catch (IOException | MessagingException e) {
            Utilities.logSevereMessage("Error during downloiading attachments, "
                    + e.getMessage());
        }
    }

}
