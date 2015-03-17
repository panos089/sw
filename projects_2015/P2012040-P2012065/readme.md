## 1ο Παραδοτέο

>Εφαρμογή android που θα σκανάρει ένα qr code σε μια καφετέρια, θα συνδέεται αυτόματα στο wifi της και θα ανοίγει μια σελίδα με >τον κατάλογο.

Παππάς Χριστόδουλος	Π2012040-Ανδρέας Αποστόλου Π2012065

## 2ο Παραδοτέο

> Ένα sample της εφαρμογής για το android υπάρχει στο 
> [Android App!](https://github.com/Kitsopappas/Qr-Fidelity)
> Παρακάτω παραθέτουμε τμήμα κώδικα

```sh

private void connectTo(String SSID, String password) { WifiConfiguration wifiConfig = new WifiConfiguration(); wifiConfig.SSID = String.format("\"%s\"", SSID); wifiConfig.preSharedKey = String.format("\"%s\"", password);

    WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    // remember id
    int netId = wifiManager.addNetwork(wifiConfig);
    wifiManager.disconnect();
    wifiManager.enableNetwork(netId, true);
    wifiManager.reconnect();

    // WifiManager wifiManager = (WifiManager)
    // getSystemService(Context.WIFI_SERVICE);

}
```

###Image

![alt tag](https://raw.githubusercontent.com/Kitsopappas/Qr-Fidelity/master/images/img1.jpg)

## 3ο Παραδοτέο


