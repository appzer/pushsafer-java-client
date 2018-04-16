package staygrounded.pushsafer.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import staygrounded.pushsafer.client.configuration.PushsaferClientConfiguration;
import staygrounded.pushsafer.client.domain.SendPushNotificationResponse;
import staygrounded.pushsafer.client.fakes.FakePushsaferServer;
import uk.staygrounded.httpstubby.server.request.HttpRequest;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static java.lang.String.format;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static staygrounded.pushsafer.client.PushsaferClientFactory.pushsaferClientWithConfiguration;
import static staygrounded.pushsafer.client.domain.PushNotification.Icon.ICON_1;
import static staygrounded.pushsafer.client.domain.PushNotification.Sound.Silent;
import static staygrounded.pushsafer.client.domain.PushNotification.Vibration.SETTING_1;
import static staygrounded.pushsafer.client.domain.PushNotificationBuilder.newPushNotification;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.ErrorReason.*;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.Result.FAILURE;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.Result.SUCCESS;
import static staygrounded.pushsafer.client.domain.TestMessageApiResponseBuilder.*;
import static uk.staygrounded.httpstubby.matchers.request.RequestHeaderEqualsMatcher.requestHeaderContains;
import static uk.staygrounded.httpstubby.matchers.request.RequestMethodMatcher.forAPostRequest;
import static uk.staygrounded.httpstubby.matchers.request.RequestUriMatcher.uriEqualTo;
import static uk.staygrounded.httpstubby.matchers.request.RequestUrlEncodedFormPayloadMatcher.urlFormPayload;
import static uk.staygrounded.httpstubby.matchers.request.builder.UrlEncodedFormPayloadMatcherBuilder.aUrlFormMatcher;
import static uk.staygrounded.httpstubby.server.HttpPortNumberGenerator.nextAvailablePortNumber;
import static uk.staygrounded.httpstubby.server.response.HttpStatus.Code.INTERNAL_SERVER_ERROR;
import static uk.staygrounded.httpstubby.server.response.HttpStatus.Code.OK;

public class PushsaferClientTest {

    private final int pushsaferMessageApiPortNumber = nextAvailablePortNumber();
    private final FakePushsaferServer fakePushsaferServer = new FakePushsaferServer(pushsaferMessageApiPortNumber);
    private final PushsaferClient underTest = pushsaferClientWithConfiguration("some-private-key", testPushsaferClientConfiguration());

    private SendPushNotificationResponse sendPushNotificationResponse;

    @Before
    public void setUp() throws Exception {
        fakePushsaferServer.start();
    }

    @After
    public void teardown() {
        fakePushsaferServer.stop();
    }


    @Test
    public void returnsSuccessResponseForRequestsWithAllFieldsPopulated() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(OK, successMessageApiJsonResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .withTitle("some-title")
                .withSound(Silent)
                .withVibration(SETTING_1)
                .withIcon(ICON_1)
                .withIconColor("#FF00FF")
                .withLink(new URL("http://some-link.com"))
                .withLinkText("some-link-text")
                .withTimeToLive(Duration.ofMinutes(5))
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest(),
                requestHeaderContains("Content-type", "application/x-www-form-urlencoded"),
                urlFormPayload(aUrlFormMatcher()
                        .withKeyAndValue("k", "some-private-key")
                        .withKeyAndValue("m", "some-message")
                        .withKeyAndValue("d", "some-device-name")
                        .withKeyAndValue("t", "some-title")
                        .withKeyAndValue("s", "0")
                        .withKeyAndValue("v", "1")
                        .withKeyAndValue("i", "1")
                        .withKeyAndValue("c", "#FF00FF")
                        .withKeyAndValue("u", "http://some-link.com")
                        .withKeyAndValue("ut", "some-link-text")
                        .withKeyAndValue("l", "5")
                )));

        assertThat(sendPushNotificationResponse.getResult(), is(SUCCESS));
        assertThat(sendPushNotificationResponse.getErrorReason(), nullValue());
    }

    @Test
    public void returnsSuccessResponseForRequestsWithOnlyRequiredFieldsPopulated() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(OK, successMessageApiJsonResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest(),
                requestHeaderContains("Content-type", "application/x-www-form-urlencoded"),
                urlFormPayload(aUrlFormMatcher()
                        .withKeyAndValue("k", "some-private-key")
                        .withKeyAndValue("m", "some-message")
                        .withKeyAndValue("d", "some-device-name")
                )));

        assertThat(sendPushNotificationResponse.getResult(), is(SUCCESS));
        assertThat(sendPushNotificationResponse.getErrorReason(), nullValue());
    }

    @Test
    public void returnsFailureWithErrorCodeRequestTimedOut() throws Exception {

        givenThePushsaferServer().willTimeout();

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(REQUEST_TIMED_OUT));
    }

    @Test
    public void returnsFailureWithErrorCodeInvalidKeyForStatusCode250() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(250, invalidKeyErrorMessageApiJsonResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(INVALID_KEY));
    }

    @Test
    public void returnsFailureWithErrorCodeInvalidKeyForStatusCode255() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(255, invalidKeyErrorMessageApiJsonResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(INVALID_KEY));
    }

    @Test
    public void returnsFailureWithErrorCodeInvalidDeviceForStatusCode270() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(270, invalidDeviceErrorMessageApiJsonResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(INVALID_DEVICE));
    }

    @Test
    public void returnsFailureWithErrorCodeInvalidDeviceGroupForStatusCode275() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(275, invalidDeviceGroupErrorMessageApiResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(INVALID_DEVICE_GROUP));
    }

    @Test
    public void returnsFailureWithErrorCodeExceededApiCallsQuotaForStatusCode280() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(280, exceededApiCallsQuotaErrorMessageApiResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(EXCEEDED_API_CALLS_QUOTA));
    }

    @Test
    public void returnsFailureWithErrorCodeUnknownForStatusCodeAllOtherStatusCodes() throws Exception {

        givenThePushsaferServer().willResponseWithStatusCodeAndBody(INTERNAL_SERVER_ERROR, unknownErrorMessageApiResponse());

        when(underTest.sendPushNotification(newPushNotification()
                .withMessage("some-message")
                .withDevice("some-device-name")
                .build()));

        assertThat(theLastRequestTo(fakePushsaferServer), allOf(
                uriEqualTo("/api"),
                forAPostRequest()));

        assertThat(sendPushNotificationResponse.getResult(), is(FAILURE));
        assertThat(sendPushNotificationResponse.getErrorReason(), is(UNKNOWN));
    }

    private FakePushsaferServer givenThePushsaferServer() {
        return fakePushsaferServer;
    }

    private void when(SendPushNotificationResponse sendPushNotificationResponse) {
        this.sendPushNotificationResponse = sendPushNotificationResponse;
    }

    private HttpRequest theLastRequestTo(FakePushsaferServer fakePushsaferServer) {
        return fakePushsaferServer.theLastRequest();
    }

    private PushsaferClientConfiguration testPushsaferClientConfiguration() {
        return new PushsaferClientConfiguration() {
            @Override
            public URI pushsaferBaseUrl() {
                return URI.create(format("http://localhost:%d", pushsaferMessageApiPortNumber));
            }

            @Override
            public Duration connectionTimeoutDuration() {
                return Duration.ofSeconds(1);
            }

            @Override
            public Duration responseTimeoutDuration() {
                return Duration.ofSeconds(1);
            }
        };
    }

}
