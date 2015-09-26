## 1ο Παραδοτέο

> Εφαρμογή android που θα σκανάρει ένα qr code σε μια καφετέρια, θα συνδέεται αυτόματα στο 
> wifi της και θα ανοίγει μια σελίδα με τον κατάλογο.
>
> Παππάς Χριστόδουλος	Π2012040-Ανδρέας Αποστόλου Π2012065

## 2ο Παραδοτέο

> Ένα sample της εφαρμογής για το android υπάρχει στο 
> [Android App!](https://github.com/Kitsopappas/Qr-Fidelity)
> Παρακάτω παραθέτουμε τμήμα κώδικα
> Θα χρησιμοποιήσουμε το eclipse και android sdk για την εφαρμογή και html5 για το site.

```sh
private void connectTo(String SSID, String password) { 

WifiConfiguration wifiConfig = new WifiConfiguration(); 
wifiConfig.SSID = String.format("\"%s\"", SSID); 
wifiConfig.preSharedKey = String.format("\"%s\"", password);

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

#### Text for the Qr sample
```sh
<ssid>networkSSID</ssid>
<pass>password</pass>
```

## 3ο Παραδοτέο

### Τίτλος
> QR FI

### Εισαγωγή
> Η παραπάνω εφαρμογή δημιουργήθηκε με σκοπό να μπορούν καφετέριες να 
> δίνουν εύκολη πρόσβαση στο WIFI τους αλλά και στον τιμοκατάλογό τους. Απλά
> με το να σκανάρει ο χρήστης ένα Qr Code.

### Εργαλεία που επιλέχθηκαν
*  eclipse και android sdk για την δημιουργία της εφαρμογής.
*  digital ocean για την δημιουργία βάσης δεδομένων. (Προσφέρεται απο το github student pack)
*  atom, ένας text editor που βοηθάει στην δημιουργία ιστοσελίδων. 
*  Διότι έχει επιλογή να φαίνονται όλοι οι φακέλοι. (Προσφέρεται απο το github student pack)
*  okeanos για την δημιουργία VM για τη φιλοξενία της ιστοσελίδας.
*  noip.com για το δωρεάν domain name [qrfi.noip.me!](http://qrfi.noip.me/qrfi/).
*  git hub για την ανάπτυξη της εφαρμογής.

### Διάγραμμα Λειτουργίας Συστήματος
> Αρχικά ο κάθε καταστηματάρχης πρέπει να κάνει εγγραφή στο site μας.
> Με το που γίνει η εγγαφή θα μπορεί να δει το προφίλ του και να αποθηκεύσει το qr code.
> Έπειτα μπορεί να προσθέσει προϊόντα στην βάση δεδομένων για να μπορεί έπειτα ο χρήστης να τα βλέπει
> Τέλος, πλαισιώσει τα qr codes πάνω στα τραπέζια του καταστήματος.
> Απο εκεί και πέρα με την χρήση της εφαρμογής για κινητά QR FI θα μπορεί ο πελάτης αφού σκανάρει το qr 
> που βρίσκεται πάνω στο τραπέζι να δει έναν τιμοκατάλογο με τα ποϊόντα του καταστήματος.

####Image

![alt tag](https://raw.githubusercontent.com/Kitsopappas/Qr-Fidelity/master/images/Screenshot_2015-05-14-09-20-58.png)
![alt tag](https://raw.githubusercontent.com/Kitsopappas/Qr-Fidelity/master/images/Screenshot_2015-05-14-09-21-38.png)
![alt tag](https://raw.githubusercontent.com/Kitsopappas/Qr-Fidelity/master/images/Screenshot_2015-05-14-09-22-12.png)

#### Code Sample για τις κανονικές εκφράσεις

```sh
package com.qrlab.qrfidelity.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QrFidelityReaderF {
	public static int USER = 1;
	public static int SSID = 2;
	public static int PASS = 3;
	public String getStuff(String str,int group) {
		//String str = "<ssid>Kitsopappas</ssid> <pass>7Zdd31s0</pass>";
		final Pattern pattern = Pattern.compile("<u>(.+?)</u><s>(.+?)</s><p>(.+?)</p>");
		final Matcher matcher = pattern.matcher(str);
		matcher.find();
		return matcher.group(group);

	}
	
}
```
