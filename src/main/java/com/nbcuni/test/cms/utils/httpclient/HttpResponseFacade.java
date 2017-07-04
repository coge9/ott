package com.nbcuni.test.cms.utils.httpclient;

import com.google.gson.JsonElement;
import com.nbcuni.test.cms.utils.jsonparsing.JsonParserHelper;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class decided to be a facade for HttResponse. It take HttpResponse as constructor argument.
 * It designed to provide more convinient methods for working with HTTP Responses (e.g. take status, take response as String , etc.).
 * <p>
 * In future our custom Http Client can be updated to return this facade object instead of regular HTTP Response.
 * </p>
 *
 * Created by Dzianis_Kulesh on 8/9/2016.
 */
public class HttpResponseFacade {

    HttpResponse wrapedResponse;

    public HttpResponseFacade(HttpResponse response) {
        wrapedResponse = response;
    }

    public String getHeaderValue(String headerName) {
        Header header = wrapedResponse.getFirstHeader(headerName);
        return header.getValue();
    }

    public int getResponseStatusCode() {
        return wrapedResponse.getStatusLine().getStatusCode();
    }

    public JsonElement getJsonFromResponse() {
        try {
            InputStream is = wrapedResponse.getEntity().getContent();
            return JsonParserHelper.getInstance().getJsonFromInputStream(is);
        } catch (IOException e) {
            Utilities.logSevereMessage("There were an error during taking JSON from response " + Utilities.convertStackTraceToString(e));
            return null;
        }
    }
}
