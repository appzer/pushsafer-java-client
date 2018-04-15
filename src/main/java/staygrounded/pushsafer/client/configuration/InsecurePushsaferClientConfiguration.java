package staygrounded.pushsafer.client.configuration;

import java.net.URI;

/**
 * Created by chrisholly on 04/04/2018.
 */
public class InsecurePushsaferClientConfiguration implements PushsaferClientConfiguration {

    @Override
    public URI pushsaferBaseUrl() {
        return URI.create("http://www.pushsafer.com");
    }
}
