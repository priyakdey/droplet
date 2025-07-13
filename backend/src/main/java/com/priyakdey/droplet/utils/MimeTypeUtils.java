package com.priyakdey.droplet.utils;

import org.springframework.http.MediaType;

/**
 * @author Priyak Dey
 */
public final class MimeTypeUtils {

    private MimeTypeUtils() {
    }

    public static String getExtensionFromContentType(String contentType) {
        return switch (contentType) {
            case MediaType.IMAGE_JPEG_VALUE -> ".jpg";
            case MediaType.IMAGE_PNG_VALUE -> ".png";
            case MediaType.IMAGE_GIF_VALUE -> ".gif";
            default -> throw new UnsupportedOperationException(
                    String.format("Unsupported content-type: %s", contentType));
        };
    }

}
