package staygrounded.pushsafer.client.domain;

import java.net.URL;
import java.time.Duration;
import java.util.Optional;

/**
 * Created by chrisholly on 04/04/2018.
 */
public class PushNotification {

    private final String message;
    private final String device;
    private final String title;
    private final Sound sound;
    private final Vibration vibration;
    private final Icon icon;
    private final String iconColorHex;
    private final URL url;
    private final String urlText;
    private final Duration timeToLive;

    PushNotification(String message,
                     String device,
                     String title,
                     Sound sound,
                     Vibration vibration,
                     Icon icon,
                     String iconColorHex,
                     URL url,
                     String urlText,
                     Duration timeToLive) {
        this.message = message;
        this.device = device;
        this.title = title;
        this.sound = sound;
        this.vibration = vibration;
        this.icon = icon;
        this.iconColorHex = iconColorHex;
        this.url = url;
        this.urlText = urlText;
        this.timeToLive = timeToLive;
    }

    public String getMessage() {
        return message;
    }

    public String getDevice() {
        return device;
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<Sound> getSound() {
        return Optional.ofNullable(sound);
    }

    public Optional<Vibration> getVibration() {
        return Optional.ofNullable(vibration);
    }

    public Optional<Icon> getIcon() {
        return Optional.ofNullable(icon);
    }

    public Optional<String> getIconColorHex() {
        return Optional.ofNullable(iconColorHex);
    }

    public Optional<URL> getUrl() {
        return Optional.ofNullable(url);
    }

    public Optional<String> getUrlText() {
        return Optional.ofNullable(urlText);
    }

    public Optional<Duration> getTimeToLive() {
        return Optional.ofNullable(timeToLive);
    }

    @Override
    public String toString() {
        return "PushNotification{" +
                "message='" + message + '\'' +
                ", device='" + device + '\'' +
                ", title='" + title + '\'' +
                ", sound=" + sound +
                ", vibration=" + vibration +
                ", icon=" + icon +
                ", iconColorHex='" + iconColorHex + '\'' +
                ", url=" + url +
                ", urlText='" + urlText + '\'' +
                ", timeToLive=" + timeToLive +
                '}';
    }

    public enum Sound {

        Silent(0),
        Ahem_IM(1),
        Applause_Mail(2),
        Arrow_Reminder(3),
        Baby_SMS(4),
        Bell_Alarm(5),
        Bicycle_Alarm2(6),
        Boing_Alarm3(7),
        Buzzer_Alarm4(8),
        Camera_Alarm5(9),
        Car_Horn_Alarm6(10),
        Cash_Register_Alarm7(11),
        Chime_Alarm8(12),
        Creaky_Door_Alarm9(13),
        Cuckoo_Clock_Alarm10(14),
        Disconnect_Call(15),
        Dog_Call2(16),
        Doorbell_Call3(17),
        Fanfare_Call4(18),
        Gun_Shot_Call_5(19),
        Honk_Call_6(20),
        Jaw_Harp_Call_7(21),
        Morse_Call_8(22),
        Electricity_Call_9(23),
        Radio_Tuner_Call_10(24),
        Sirens(25),
        Military_Trumpets(26),
        Ufo(27),
        Whah_Whah_Whah(28),
        Man_Saying_Goodbye(29),
        Man_Saying_Hello(30),
        Man_Saying_No(31),
        Man_Saying_Ok(32),
        Man_Saying_Ooohhhweee(33),
        Man_Saying_Warning(34),
        Man_Saying_Welcome(35),
        Man_Saying_Yeah(36),
        Man_Saying_Yes(37),
        Beep_short(38),
        Weeeee_short(39),
        Cut_in_and_out_short(40),
        Finger_flicking_glas_short(41),
        Wa_Wa_Waaaa_short(42),
        Laser_short(43),
        Wind_Chime_short(44),
        Echo_short(45),
        Zipper_short(46),
        HiHat_short(47),
        Beep_2_short(48),
        Beep_3_short(49),
        Beep_4_short(50);

        private final int soundId;

        Sound(int soundId) {
            this.soundId = soundId;
        }

        public int getSoundId() {
            return soundId;
        }
    }

    public enum Vibration {

        SETTING_1(1),
        SETTING_2(2),
        SETTING_3(3);

        private final int vibrationId;

        Vibration(int vibrationId) {
            this.vibrationId = vibrationId;
        }

        public int getVibrationId() {
            return vibrationId;
        }
    }

    public enum Icon {

        ICON_1(1),
        ICON_2(2),
        ICON_3(3),
        ICON_4(4),
        ICON_5(5),
        ICON_6(6),
        ICON_7(7),
        ICON_8(8),
        ICON_9(9),
        ICON_10(10),
        ICON_11(11),
        ICON_12(12),
        ICON_13(13),
        ICON_14(14),
        ICON_15(15),
        ICON_16(16),
        ICON_17(17),
        ICON_18(18),
        ICON_19(19),
        ICON_20(20),
        ICON_21(21),
        ICON_22(22),
        ICON_23(23),
        ICON_24(24),
        ICON_25(25),
        ICON_26(26),
        ICON_27(27),
        ICON_28(28),
        ICON_29(29),
        ICON_30(30),
        ICON_31(31),
        ICON_32(32),
        ICON_33(33),
        ICON_34(34),
        ICON_35(35),
        ICON_36(36),
        ICON_37(37),
        ICON_38(38),
        ICON_39(39),
        ICON_40(40),
        ICON_41(41),
        ICON_42(42),
        ICON_43(43),
        ICON_44(44),
        ICON_45(45),
        ICON_46(46),
        ICON_47(47),
        ICON_48(48),
        ICON_49(49),
        ICON_50(50),
        ICON_51(51),
        ICON_52(52),
        ICON_53(53),
        ICON_54(54),
        ICON_55(55),
        ICON_56(56),
        ICON_57(57),
        ICON_58(58),
        ICON_59(59),
        ICON_60(60),
        ICON_61(61),
        ICON_62(62),
        ICON_63(63),
        ICON_64(64),
        ICON_65(65),
        ICON_66(66),
        ICON_67(67),
        ICON_68(68),
        ICON_69(69),
        ICON_70(70),
        ICON_71(71),
        ICON_72(72),
        ICON_73(73),
        ICON_74(74),
        ICON_75(75),
        ICON_76(76),
        ICON_77(77),
        ICON_78(78),
        ICON_79(79),
        ICON_80(80),
        ICON_81(81),
        ICON_82(82),
        ICON_83(83),
        ICON_84(84),
        ICON_85(85),
        ICON_86(86),
        ICON_87(87),
        ICON_88(88),
        ICON_89(89),
        ICON_90(90),
        ICON_91(91),
        ICON_92(92),
        ICON_93(93),
        ICON_94(94),
        ICON_95(95),
        ICON_96(96),
        ICON_97(97),
        ICON_98(98),
        ICON_99(99),
        ICON_100(100),
        ICON_101(101),
        ICON_102(102),
        ICON_103(103),
        ICON_104(104),
        ICON_105(105),
        ICON_106(106),
        ICON_107(107),
        ICON_108(108),
        ICON_109(109),
        ICON_110(110),
        ICON_111(111),
        ICON_112(112),
        ICON_113(113),
        ICON_114(114),
        ICON_115(115),
        ICON_116(116),
        ICON_117(117),
        ICON_118(118),
        ICON_119(119),
        ICON_120(120),
        ICON_121(121),
        ICON_122(122),
        ICON_123(123),
        ICON_124(124),
        ICON_125(125),
        ICON_126(126),
        ICON_127(127),
        ICON_128(128),
        ICON_129(129),
        ICON_130(130),
        ICON_131(131),
        ICON_132(132),
        ICON_133(133),
        ICON_134(134),
        ICON_135(135),
        ICON_136(136),
        ICON_137(137),
        ICON_138(138),
        ICON_139(139),
        ICON_140(140),
        ICON_141(141),
        ICON_142(142),
        ICON_143(143),
        ICON_144(144),
        ICON_145(145),
        ICON_146(146),
        ICON_147(147),
        ICON_148(148),
        ICON_149(149),
        ICON_150(150),
        ICON_151(151),
        ICON_152(152),
        ICON_153(153),
        ICON_154(154),
        ICON_155(155),
        ICON_156(156),
        ICON_157(157),
        ICON_158(158),
        ICON_159(159),
        ICON_160(160),
        ICON_161(161),
        ICON_162(162),
        ICON_163(163),
        ICON_164(164),
        ICON_165(165),
        ICON_166(166),
        ICON_167(167),
        ICON_168(168),
        ICON_169(169),
        ICON_170(170),
        ICON_171(171),
        ICON_172(172),
        ICON_173(173),
        ICON_174(174),
        ICON_175(175),
        ICON_176(176);

        private final int iconId;

        Icon(int iconId) {
            this.iconId = iconId;
        }

        public int getIconId() {
            return iconId;
        }
    }
}
