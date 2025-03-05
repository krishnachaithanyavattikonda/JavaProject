package com.framework.utils;

import io.qameta.allure.Attachment;

public class ReportUtil {

    @Attachment(value = "Test Log", type = "text/plain")
    public static String attachLog(String message) {
        return message;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshot(byte[] screenshot) {
        return screenshot;
    }
}
