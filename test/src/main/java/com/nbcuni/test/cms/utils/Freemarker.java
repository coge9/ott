package com.nbcuni.test.cms.utils;

import com.nbcuni.test.webdriver.Utilities;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

public class Freemarker {

    public static String getStringFromTemplate(String templateName, Map<String, Object> parameters) {
        String result = null;
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        try {
            StringWriter writer = new StringWriter();
            Template template = cfg.getTemplate(Config.getInstance().getPathToFreemarkerTemplates() + templateName);
            template.process(parameters, writer);
            writer.flush();
            result = writer.toString();
            writer.close();
        } catch (IOException e) {
            Utilities.logSevereMessage("There is error during template processing" + Utilities.convertStackTraceToString(e));
            e.printStackTrace();
        } catch (TemplateException e) {
            Utilities.logSevereMessage("There is error with freemarkerTemplate" + Utilities.convertStackTraceToString(e));
        }
        return result;
    }


}
