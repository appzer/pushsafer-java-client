package staygrounded.pushsafer.client.domain;

import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.Result.FAILURE;
import static staygrounded.pushsafer.client.domain.SendPushNotificationResponse.Result.SUCCESS;

/**
 * Created by chrisholly on 13/04/2018.
 */
public class SendPushNotificationResponse {

    private final Result result;
    private final ErrorReason errorReason;

    private SendPushNotificationResponse(Result result, ErrorReason errorReason) {
        this.result = result;
        this.errorReason = errorReason;
    }

    public static SendPushNotificationResponse successfulResponse() {
        return new SendPushNotificationResponse(SUCCESS, null);
    }

    public static SendPushNotificationResponse failureResponse(ErrorReason errorReason) {
        return new SendPushNotificationResponse(FAILURE, errorReason);
    }

    public Result getResult() {
        return result;
    }

    public ErrorReason getErrorReason() {
        return errorReason;
    }

    public enum Result {
        SUCCESS,
        FAILURE
    }

    public enum ErrorReason {
        INVALID_KEY,
        INVALID_DEVICE,
        INVALID_DEVICE_GROUP,
        EXCEEDED_API_CALLS_QUOTA,
        UNKNOWN
    }

    @Override
    public String toString() {
        return "SendPushNotificationResponse{" +
                "result=" + result +
                ", errorReason=" + errorReason +
                '}';
    }
}
