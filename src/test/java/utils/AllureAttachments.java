package utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class AllureAttachments {

    private AllureAttachments() {}

    public static void attachPng(String name, byte[] pngBytes) {
        if (pngBytes == null || pngBytes.length == 0) return;
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(pngBytes), "png");
    }

    public static void attachText(String name, String content) {
        if (content == null) return;
        Allure.addAttachment(name, "text/plain", content);
    }
}
