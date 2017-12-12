const java = require('./java');
const path = require('path');
const Decoder = java.import('org.gabel.qrcode.Decoder');
const DecoderConfig = java.import('org.gabel.qrcode.DecoderConfig');

function buildConfig(
    tryHarder = false,
    pureBarcode = false,
    productsOnly = false,
    crop = null,
    possibleFormats = null
) {
    const conf = new DecoderConfig();
    conf.setTryHarderSync(tryHarder)
        .setPureBarcodeSync(pureBarcode)
        .setProductsOnlySync(productsOnly)
        .setCropSync(crop)
        .setPossibleFormatsSync(possibleFormats);
    return conf;
}

module.exports = {
    /**
     * Decode asynchronously.
     * @param {string} input Use the TRY_HARDER hint, default is normal mode
     * @param {boolean} tryHarder Input image is a pure monochrome barcode image, not a photo
     * @param {boolean} pureBarcode Input image is a pure monochrome barcode image, not a photo
     * @param {boolean} productsOnly Only decode the UPC and EAN families of barcodes
     * @param {Array<int>} crop Only examine cropped region of input image(s)
     * @param {Array<string>} possibleFormats Formats to decode, where format is any value in BarcodeFormat
     */
    decode(
        input, 
        tryHarder = false,
        pureBarcode = false,
        productsOnly = false,
        crop = null,
        possibleFormats = null
    ) {
        const conf = buildConfig(
            tryHarder,
            pureBarcode,
            productsOnly,
            crop,
            possibleFormats,
        );
    
        return new Promise((resolve, reject) => {
            return Decoder.decode(input, conf, (err, data) => {
                if (err) throw err;
                resolve(data);
            });
        });
    },

    /**
     * Decode synchronously.
     * @param {string} input Use the TRY_HARDER hint, default is normal mode
     * @param {boolean} tryHarder Input image is a pure monochrome barcode image, not a photo
     * @param {boolean} pureBarcode Input image is a pure monochrome barcode image, not a photo
     * @param {boolean} productsOnly Only decode the UPC and EAN families of barcodes
     * @param {Array<int>} crop Only examine cropped region of input image(s)
     * @param {Array<string>} possibleFormats Formats to decode, where format is any value in BarcodeFormat
     */
    decodeSync(
        input, 
        tryHarder = false,
        pureBarcode = false,
        productsOnly = false,
        crop = null,
        possibleFormats = null
    ) {
        const conf = buildConfig(
            tryHarder,
            pureBarcode,
            productsOnly,
            crop,
            possibleFormats,
        );
    
        return Decoder.decodeSync(input, conf);
    },
};