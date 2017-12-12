# node-qrcode-zxing  
Decode qrcode with zxing library

## Requirements:
- JDK 1.8 or above

## Installation
```bash
npm install node-qrcode-zxing -S 
# Or with yarn 
yarn add node-qrcode-zxing -S 
```

## Usage 
```js
const Decoder = require('./');

Decoder.decode(imageData);

// Or decode sync
Decoder.decodeSync(imageData); 
```

## Mac OSX Issue
If you run into strange runtime issues, it could be because the Oracle JDK does not advertise itself as available for JNI. See [Issue 90](https://github.com/joeferner/node-java/issues/90#issuecomment-45613235) for more details and manual workarounds. If this does occur for you, please update the issue.

