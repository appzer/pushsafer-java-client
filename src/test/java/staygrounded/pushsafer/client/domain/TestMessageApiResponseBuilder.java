package staygrounded.pushsafer.client.domain;

/**
 * Created by chrisholly on 06/04/2018.
 */
public class TestMessageApiResponseBuilder {

    public static String successMessageApiJsonResponse() {
        return "{ \n" +
                " \"status\":1,\n" +
                " \"success\":\"message transmitted\",\n" +
                " \"available\":1234\n" +
                "}";
    }

    public static String invalidKeyErrorMessageApiJsonResponse() {
        return "{ \n" +
                " \"status\":0,\n" +
                " \"error\":\"invalid key\",\n" +
                "}";
    }

    public static String invalidDeviceErrorMessageApiJsonResponse() {
        return "{ \n" +
                " \"status\":0,\n" +
                " \"error\":\"invalid device\",\n" +
                "}";
    }

    public static String invalidDeviceGroupErrorMessageApiResponse() {
        return "{ \n" +
                " \"status\":0,\n" +
                " \"error\":\"invalid device group\",\n" +
                "}";
    }

    public static String exceededApiCallsQuotaErrorMessageApiResponse() {
        return "{ \n" +
                " \"status\":0,\n" +
                " \"error\":\"not enough API calls\",\n" +
                "}";
    }

    public static String unknownErrorMessageApiResponse() {
        return "{ \n" +
                " \"status\":0,\n" +
                " \"error\":\"unknown\",\n" +
                "}";
    }
}
