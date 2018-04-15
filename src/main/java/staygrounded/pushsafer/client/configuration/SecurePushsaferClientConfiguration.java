package staygrounded.pushsafer.client.configuration;

import java.net.URI;

/**
 * Created by chrisholly on 04/04/2018.
 */
public class SecurePushsaferClientConfiguration implements PushsaferClientConfiguration {

    @Override
    public URI pushsaferBaseUrl() {
            return URI.create("https://www.pushsafer.com");
    }
}
