package staygrounded.pushsafer.client.configuration;

import java.net.URI;
import java.time.Duration;

/**
 * Created by chrisholly on 04/04/2018.
 */
public interface PushsaferClientConfiguration {

    URI pushsaferBaseUrl();

    Duration connectionTimeoutDuration();

    Duration responseTimeoutDuration();
}
