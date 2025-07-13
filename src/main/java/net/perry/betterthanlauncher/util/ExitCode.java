package net.perry.betterthanlauncher.util;

public enum ExitCode {
    SUCCESS(0),
    GENERAL_ERROR(1),
    NO_INTERNET(10),
    TIMEOUT_LOGIN(12),
    AUTH_FAILED(13),
    FILE_WRITE_FAILED(15),
    RESTART(100);

    public final int code;

    ExitCode(int code) {
        this.code = code;
    }
}
