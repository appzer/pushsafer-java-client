package staygrounded.pushsafer.client.fakes;

import uk.staygrounded.httpstubby.server.HttpStubbyServer;
import uk.staygrounded.httpstubby.server.request.HttpRequest;
import uk.staygrounded.httpstubby.server.response.HttpStatus;

import java.time.Duration;

import static uk.staygrounded.httpstubby.server.HttpServerFactory.httpConfiguration;
import static uk.staygrounded.httpstubby.server.HttpStubbyServer.stubbyServerWith;
import static uk.staygrounded.httpstubby.server.response.HttpResponseBuilder.responseOf;
import static uk.staygrounded.httpstubby.server.response.HttpStatus.Code.OK;

/**
 * Created by chrisholly on 16/04/2018.
 */
public class FakePushsaferServer {

    private final HttpStubbyServer fakePushsaferServer;

    public FakePushsaferServer(int pushsaferMessageApiPortNumber) {
        this.fakePushsaferServer = stubbyServerWith(httpConfiguration(pushsaferMessageApiPortNumber));
    }

    public void start() {
        this.fakePushsaferServer.start();
    }

    public void stop() {
        this.fakePushsaferServer.stop();
    }

    public void willResponseWithStatusCodeAndBody(HttpStatus.Code code, String responseBody) {
        fakePushsaferServer.willReturn(responseOf(code).withBody(responseBody));
    }

    public void willResponseWithStatusCodeAndBody(int statusCode, String responseBody) {
        fakePushsaferServer.willReturn(responseOf(statusCode).withBody(responseBody));
    }

    public void willTimeout() {
        fakePushsaferServer.willReturn(responseOf(OK).withLatency(Duration.ofSeconds(20)));
    }

    public HttpRequest theLastRequest() {
        return fakePushsaferServer.httpRequestResponseHistory().lastRequest();
    }

}
