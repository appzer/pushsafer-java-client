package staygrounded.pushsafer.client.configuration;

import java.net.URI;
import java.time.Duration;

/**
 * Created by chrisholly on 04/04/2018.
 */
public class InsecurePushsaferClientConfiguration implements PushsaferClientConfiguration {

    @Override
    public URI pushsaferBaseUrl() {
        return URI.create("http://www.pushsafer.com");
    }

    @Override
    public Duration connectionTimeoutDuration() {
        return Duration.ofSeconds(10);
    }

    @Override
    public Duration responseTimeoutDuration() {
        return Duration.ofSeconds(30);
    }
}
