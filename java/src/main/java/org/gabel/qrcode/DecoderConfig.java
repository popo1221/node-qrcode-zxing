package org.gabel.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;

import java.util.*;
import java.util.stream.Collectors;

public final class DecoderConfig {

    /**
     * Use the TRY_HARDER hint, default is normal mode
     */
    boolean tryHarder = false;

    /**
     * Input image is a pure monochrome barcode image, not a photo
     */
    boolean pureBarcode = false;

    /**
     * Only decode the UPC and EAN families of barcodes
     */
    boolean productsOnly = false;

    /**
     * Only examine cropped region of input image(s)
     */
    List<Integer> crop = null;

    /**
     * Formats to decode, where format is any value in BarcodeFormat
     */
    List<BarcodeFormat> possibleFormats;

    public boolean isTryHarder() {
        return tryHarder;
    }

    public DecoderConfig setTryHarder(boolean tryHarder) {
        this.tryHarder = tryHarder;
        return this;
    }

    public boolean isPureBarcode() {
        return pureBarcode;
    }

    public DecoderConfig setPureBarcode(boolean pureBarcode) {
        this.pureBarcode = pureBarcode;
        return this;
    }

    public boolean isProductsOnly() {
        return productsOnly;
    }

    public DecoderConfig setProductsOnly(boolean productsOnly) {
        this.productsOnly = productsOnly;
        return this;
    }

    public List<Integer> getCrop() {
        return crop;
    }

    public DecoderConfig setCrop(List<Integer> crop) {
        this.crop = crop;
        return this;
    }

    public List<BarcodeFormat> getPossibleFormats() {
        return possibleFormats;
    }

    public DecoderConfig setPossibleFormats(List<String> possibleFormats) {
        if (null == possibleFormats) {
            this.possibleFormats = null;
        } else {
            this.possibleFormats = possibleFormats.stream()
                    .map((format) -> BarcodeFormat.valueOf(format))
                    .collect(Collectors.toList());
        }

        return this;
    }

    public Map<DecodeHintType,?> buildHints() {
        List<BarcodeFormat> finalPossibleFormats = possibleFormats;
        if (finalPossibleFormats == null || finalPossibleFormats.isEmpty()) {
            finalPossibleFormats = new ArrayList<>();
            finalPossibleFormats.addAll(Arrays.asList(
                    BarcodeFormat.UPC_A,
                    BarcodeFormat.UPC_E,
                    BarcodeFormat.EAN_13,
                    BarcodeFormat.EAN_8,
                    BarcodeFormat.RSS_14,
                    BarcodeFormat.RSS_EXPANDED
            ));
            if (!productsOnly) {
                finalPossibleFormats.addAll(Arrays.asList(
                        BarcodeFormat.CODE_39,
                        BarcodeFormat.CODE_93,
                        BarcodeFormat.CODE_128,
                        BarcodeFormat.ITF,
                        BarcodeFormat.QR_CODE,
                        BarcodeFormat.DATA_MATRIX,
                        BarcodeFormat.AZTEC,
                        BarcodeFormat.PDF_417,
                        BarcodeFormat.CODABAR,
                        BarcodeFormat.MAXICODE
                ));
            }
        }

        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, finalPossibleFormats);
        if (tryHarder) {
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        }
        if (pureBarcode) {
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        }
        return Collections.unmodifiableMap(hints);
    }
}
