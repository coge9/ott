package com.nbcuni.test.cms.pageobjectutils.entities.mvpd;

import com.nbcuni.test.webdriver.Utilities;


public class MvpdErrorMessage {
    private String MessageId;
    private String MessageTitle;
    private String MessageBody;
    private boolean UseAdobeDesc;

    public MvpdErrorMessage() {
        super();
    }

    public MvpdErrorMessage(final String messageId, final String messageTitle, final String messageBody,
                            final boolean useAdobeDesc) {
        super();
        MessageId = messageId;
        MessageTitle = messageTitle;
        MessageBody = messageBody;
        UseAdobeDesc = useAdobeDesc;
    }

    public boolean verifyErrorMessages(MvpdErrorMessage mvpdErrorMessage) {

        boolean status = true;

        if (!MessageTitle.equals(mvpdErrorMessage.MessageTitle)) {
            Utilities.logInfoMessage("Messages titles is not equals. [" + MessageTitle + "] and [" + mvpdErrorMessage.MessageTitle + "].");
            status = false;
        }
        if (!MessageBody.equals(mvpdErrorMessage.MessageBody)) {
            Utilities.logInfoMessage("Messages bodyes is not equals. [" + MessageBody + "] and [" + mvpdErrorMessage.MessageBody + "].");
            status = false;
        }

        if (!UseAdobeDesc == mvpdErrorMessage.UseAdobeDesc) {
            Utilities.logInfoMessage("Messages useAdobeDescs checkboxes is not equals. [" + UseAdobeDesc + "] and [" + mvpdErrorMessage.UseAdobeDesc + "].");
            status = false;
        }

        return status;
    }

    @Override
    public String toString() {
        return "ErrorMessage [MessageId=" + MessageId + ", MessageTitle=" + MessageTitle + ", MessageBody="
                + MessageBody + ", UseAdobeDesc=" + UseAdobeDesc + "]";
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(final String messageId) {
        MessageId = messageId;
    }

    public String getMessageTitle() {
        return MessageTitle;
    }

    public void setMessageTitle(final String messageTitle) {
        MessageTitle = messageTitle;
    }

    public String getMessageBody() {
        return MessageBody;
    }

    public void setMessageBody(final String messageBody) {
        MessageBody = messageBody;
    }

    public boolean isUseAdobeDesc() {
        return UseAdobeDesc;
    }

    public void setUseAdobeDesc(final boolean useAdobeDesc) {
        UseAdobeDesc = useAdobeDesc;
    }
}