package staygrounded.pushsafer.client;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import staygrounded.pushsafer.client.configuration.PushsaferClientConfiguration;
import staygrounded.pushsafer.client.domain.PushNotification;
import staygrounded.pushsafer.client.domain.SendPushNotificationResponse;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.entity.ContentType.APPLICATION_FORM_URLENCODED;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.ErrorReason.*;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.failureResponse;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.successfulResponse;

/**
 * Created by chrisholly on 04/04/2018.
 */
class DefaultPushsaferClient implements PushsaferClient {

    private final String privateKey;
    private final PushsaferClientConfiguration configuration;
    private final HttpClient httpClient = HttpClientBuilder.create().build();

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPushsaferClient.class);

    DefaultPushsaferClient(String privateKey, PushsaferClientConfiguration configuration) {
        this.privateKey = requireNonNull(privateKey, "privateKey must not be null");
        this.configuration = configuration;
    }

    @Override
    public SendPushNotificationResponse sendPushNotification(PushNotification pushNotification) throws Exception {

        final List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(withUrlParameter("k", privateKey));
        urlParameters.add(withUrlParameter("m", requireNonNull(pushNotification.getMessage())));
        urlParameters.add(withUrlParameter("d", requireNonNull(pushNotification.getDevice())));
        pushNotification.getTitle().ifPresent(title -> urlParameters.add(withUrlParameter("t", title)));
        pushNotification.getSound().ifPresent(sound -> urlParameters.add(withUrlParameter("s", sound.getSoundId())));
        pushNotification.getVibration().ifPresent(vibration -> urlParameters.add(withUrlParameter("v", vibration.getVibrationId())));
        pushNotification.getIcon().ifPresent(icon -> urlParameters.add(withUrlParameter("i", icon.getIconId())));
        pushNotification.getIconColorHex().ifPresent(iconColor -> urlParameters.add(withUrlParameter("c", iconColor)));
        pushNotification.getUrl().ifPresent(url -> urlParameters.add(withUrlParameter("u", url.toExternalForm())));
        pushNotification.getUrlText().ifPresent(urlText -> urlParameters.add(withUrlParameter("ut", urlText)));
        pushNotification.getTimeToLive().ifPresent(timeToLive -> urlParameters.add(withUrlParameter("l", timeToLive.toMinutes())));

        final HttpPost post = new HttpPost(configuration.pushsaferBaseUrl() + "/api");
        post.addHeader(CONTENT_TYPE, APPLICATION_FORM_URLENCODED.getMimeType());
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try {
            LOGGER.info("Sending Push Notification: {}", pushNotification);
            final HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == SC_OK) {
                return successfulResponse();
            } else if (asList(250, 255).contains(response.getStatusLine().getStatusCode())) {
                return failureResponse(INVALID_KEY);
            } else if (270 == response.getStatusLine().getStatusCode()) {
                return failureResponse(INVALID_DEVICE);
            } else if (275 == response.getStatusLine().getStatusCode()) {
                return failureResponse(INVALID_DEVICE_GROUP);
            } else if (280 == response.getStatusLine().getStatusCode()) {
                return failureResponse(EXCEEDED_API_CALLS_QUOTA);
            }
            throw new RuntimeException("Unknown error code: " + response.getStatusLine().getStatusCode());
        } catch (Exception ex) {
            return failureResponse(UNKNOWN);
        }
    }

    private NameValuePair withUrlParameter(String name, String value) {
        return new BasicNameValuePair(name, value);
    }

    private NameValuePair withUrlParameter(String name, long value) {
        return new BasicNameValuePair(name, String.valueOf(value));
    }
}
