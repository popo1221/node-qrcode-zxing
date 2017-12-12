package org.gabel.qrcode;


import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public final class Decoder {

    public final static String decode(String input, DecoderConfig conf) throws URISyntaxException, IOException {
        if (null == conf) {
            conf = new DecoderConfig();
        }

        Map<DecodeHintType,?> hints = conf.buildHints();

        BufferedImage image = ImageReader.readImage(new URI(input));

        LuminanceSource source;
        List<Integer> crop = conf.getCrop();
        if (crop == null) {
            source = new BufferedImageLuminanceSource(image);
        } else {
            source = new BufferedImageLuminanceSource(
                    image, crop.get(0), crop.get(1), crop.get(2), crop.get(3));
        }

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        MultiFormatReader multiFormatReader = new MultiFormatReader();
        Result results = null;
        try {
            results = multiFormatReader.decode(bitmap, hints);
        } catch (NotFoundException ignored) {
            System.out.println(": No barcode found");
        }

        return results.getText();
    }
}
