package staygrounded.pushsafer.client;

import staygrounded.pushsafer.client.configuration.SecurePushsaferClientConfiguration;
import staygrounded.pushsafer.client.configuration.PushsaferClientConfiguration;

/**
 * Created by chrisholly on 04/04/2018.
 */
public class PushsaferClientFactory {

    public static PushsaferClient pushsaferClient(String privateKey) {
        return new DefaultPushsaferClient(privateKey, new SecurePushsaferClientConfiguration());
    }

    public static PushsaferClient pushsaferClientWithConfiguration(String privateKey,
                                                                   PushsaferClientConfiguration configuration) {
        return new DefaultPushsaferClient(privateKey, configuration);
    }
}
