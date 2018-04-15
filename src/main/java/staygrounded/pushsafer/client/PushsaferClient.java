package staygrounded.pushsafer.client;

import staygrounded.pushsafer.client.domain.SendPushNotificationResponse;
import staygrounded.pushsafer.client.domain.PushNotification;

/**
 * Created by chrisholly on 04/04/2018.
 */
public interface PushsaferClient {

    SendPushNotificationResponse sendPushNotification(PushNotification pushNotification) throws Exception;
}
