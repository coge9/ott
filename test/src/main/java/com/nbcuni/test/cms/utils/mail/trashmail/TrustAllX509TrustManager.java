package com.nbcuni.test.cms.utils.mail.trashmail;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class TrustAllX509TrustManager implements X509TrustManager {
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                   String authType) {
    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                   String authType) {
    }

}