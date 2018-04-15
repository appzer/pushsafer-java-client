package staygrounded.pushsafer.client;

import org.junit.Ignore;
import org.junit.Test;
import staygrounded.pushsafer.client.configuration.InsecurePushsaferClientConfiguration;
import staygrounded.pushsafer.client.configuration.SecurePushsaferClientConfiguration;
import staygrounded.pushsafer.client.domain.SendPushNotificationResponse;

import java.net.URL;
import java.time.Duration;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static staygrounded.pushsafer.client.PushsaferClientFactory.pushsaferClientWithConfiguration;
import static staygrounded.pushsafer.client.domain.PushNotification.Icon.ICON_1;
import static staygrounded.pushsafer.client.domain.PushNotification.Sound.Silent;
import static staygrounded.pushsafer.client.domain.PushNotification.Vibration.SETTING_1;
import static staygrounded.pushsafer.client.domain.PushNotificationBuilder.newPushNotification;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.Result.SUCCESS;

/**
 * Created by chrisholly on 04/04/2018.
 *
 * Test is ignored and private key changed as source is checked into a public repository
 */
@Ignore
public class PushsaferClientIntegrationTest {

    private static final String TEST_PRIVATE_KEY = "CHANGE_ME";
    private SendPushNotificationResponse sendPushNotificationResponse;

    @Test
    public void returnsSuccessResponseForRequestsWithSecureConfiguration() throws Exception {

        final PushsaferClient underTest =
                pushsaferClientWithConfiguration(TEST_PRIVATE_KEY, new SecurePushsaferClientConfiguration());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message-over-secure-connection")
                .withDevice("a")
                .withTitle("some-title")
                .withSound(Silent)
                .withVibration(SETTING_1)
                .withIcon(ICON_1)
                .withIconColor("#FF00FF")
                .withLink(new URL("http://some-link.com"))
                .withLinkText("some-link-text")
                .withTimeToLive(Duration.ofMinutes(5))
                .build()));

        assertThat(sendPushNotificationResponse.getResult(), is(SUCCESS));
        assertThat(sendPushNotificationResponse.getErrorReason(), nullValue());
    }

    @Test
    public void returnsSuccessResponseForRequestsWithInsecureConfiguration() throws Exception {

        final PushsaferClient underTest =
                pushsaferClientWithConfiguration(TEST_PRIVATE_KEY, new InsecurePushsaferClientConfiguration());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message-over-insecure-connection")
                .withDevice("a")
                .withTitle("some-title")
                .withSound(Silent)
                .withVibration(SETTING_1)
                .withIcon(ICON_1)
                .withIconColor("#FF00FF")
                .withLink(new URL("http://some-link.com"))
                .withLinkText("some-link-text")
                .withTimeToLive(Duration.ofMinutes(5))
                .build()));

        assertThat(sendPushNotificationResponse.getResult(), is(SUCCESS));
        assertThat(sendPushNotificationResponse.getErrorReason(), nullValue());
    }

    private void when(SendPushNotificationResponse sendPushNotificationResponse) {
        this.sendPushNotificationResponse = sendPushNotificationResponse;
    }

}
