package staygrounded.pushsafer.client.domain;

import java.net.URL;
import java.time.Duration;

/**
 * Created by chrisholly on 13/04/2018.
 */
public class PushNotificationBuilder {

    private String message;
    private String device;
    private String title;
    private PushNotification.Sound sound;
    private PushNotification.Vibration vibration;
    private PushNotification.Icon icon;
    private String iconColorHex;
    private URL url;
    private String urlText;
    private Duration timeToLive;

    public static PushNotificationBuilder newPushNotification() {
        return new PushNotificationBuilder();
    }

    public PushNotificationBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public PushNotificationBuilder withDevice(String device) {
        this.device = device;
        return this;
    }

    public PushNotificationBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PushNotificationBuilder withSound(PushNotification.Sound sound) {
        this.sound = sound;
        return this;
    }

    public PushNotificationBuilder withVibration(PushNotification.Vibration vibration) {
        this.vibration = vibration;
        return this;
    }

    public PushNotificationBuilder withIcon(PushNotification.Icon icon) {
        this.icon = icon;
        return this;
    }

    public PushNotificationBuilder withIconColor(String iconColorHex) {
        this.iconColorHex = iconColorHex;
        return this;
    }

    public PushNotificationBuilder withLink(URL url) {
        this.url = url;
        return this;
    }

    public PushNotificationBuilder withLinkText(String urlText) {
        this.urlText = urlText;
        return this;
    }

    public PushNotificationBuilder withTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
        return this;
    }

    public PushNotification build() {
        return new PushNotification(message, device, title, sound, vibration, icon, iconColorHex, url, urlText, timeToLive);
    }

}