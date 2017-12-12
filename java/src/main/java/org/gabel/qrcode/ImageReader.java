package org.gabel.qrcode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

public final class ImageReader {

    private static final String BASE64TOKEN = "base64,";

    private ImageReader() {
    }

    public static BufferedImage readImage(URI uri) throws IOException {
        if ("data".equals(uri.getScheme())) {
            return readDataURIImage(uri);
        }
        BufferedImage result;
        try {
            result = ImageIO.read(uri.toURL());
        } catch (IllegalArgumentException iae) {
            throw new IOException("Resource not found: " + uri, iae);
        }
        if (result == null) {
            throw new IOException("Could not load " + uri);
        }
        return result;
    }

    public static BufferedImage readDataURIImage(URI uri) throws IOException {
        String uriString = uri.getSchemeSpecificPart();
        if (!uriString.startsWith("image/")) {
            throw new IOException("Unsupported data URI MIME type");
        }
        int base64Start = uriString.indexOf(BASE64TOKEN);
        if (base64Start < 0) {
            throw new IOException("Unsupported data URI encoding");
        }
        String base64DataEncoded = uriString.substring(base64Start + BASE64TOKEN.length());
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] imageBytes = decoder.decode(base64DataEncoded);
        return ImageIO.read(new ByteArrayInputStream(imageBytes));
    }
}
