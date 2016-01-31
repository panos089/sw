<?php

DEFINE ('DBUSER', '*******');
DEFINE ('DBPW', '*******');
DEFINE ('DBHOST', '*******');
DEFINE ('DBNAME', '*******');



$dbc = mysqli_connect(DBHOST,DBUSER,DBPW);
if (!$dbc) {
    die("Database connection failed: " . mysqli_error($dbc));
    exit();
}

$dbs = mysqli_select_db($dbc, DBNAME);

if (!$dbs) {
    die("Database selection failed: " . mysqli_error($dbc));
    exit();
}

mysqli_set_charset($dbc, "utf8");

$key = mysqli_real_escape_string($dbc, $_GET['key']);
$action = mysqli_real_escape_string($dbc, $_GET['action']);



  if ($action == "addCustomer") {
    $customerName = mysqli_real_escape_string($dbc, $_GET['customer_name']);
    $address = mysqli_real_escape_string($dbc,$_GET['address']);
    $phone = mysqli_real_escape_string($dbc,$_GET['phone']);

    $query = "INSERT INTO customers (customer_name, address, phone) VALUES ('$customerName', '$address', '$phone')";

    $result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));
  }


  if ($action == "updateCustomer") {
    $id = mysqli_real_escape_string($dbc, $_GET['id']);
    $customerName = mysqli_real_escape_string($dbc, $_GET['customer_name']);
    $address = mysqli_real_escape_string($dbc,$_GET['address']);
    $phone = mysqli_real_escape_string($dbc,$_GET['phone']);

    $query = "UPDATE customers SET customer_name='$customerName', address='$address', phone='$phone' WHERE id='$id'";

    $result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));
  }


  if ($action == "deleteCustomer") {
    $id = mysqli_real_escape_string($dbc, $_GET['id']);
    $customerName = mysqli_real_escape_string($dbc, $_GET['customer_name']);
    $address = mysqli_real_escape_string($dbc,$_GET['address']);
    $phone = mysqli_real_escape_string($dbc,$_GET['phone']);

    $query = "DELETE FROM customers WHERE id='$id'";

    $result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));
  }


  if ($action == "getCustomers") {

    $result = mysqli_query($dbc, "SHOW COLUMNS FROM customers");
    $numberOfRows = mysqli_num_rows($result);

    if ($numberOfRows > 0) {

      $values = mysqli_query($dbc, "SELECT * FROM customers");

      while ($rowr = mysqli_fetch_row($values)) {

        for ($j=0;$j<$numberOfRows;$j++) {
          $csv_output .= $rowr[$j];

          if ($j != $numberOfRows-1) {
            $csv_output .= ", ";
          }
        }

        $csv_output .= "\n";

      }
    }

    print $csv_output;
    exit;
  }


  if ($action == "getCustomer") {

    $id = mysqli_real_escape_string($dbc, $_GET['id']);

    $result = mysqli_query($dbc, "SHOW COLUMNS FROM customers");
    $numberOfRows = mysqli_num_rows($result);

    if ($numberOfRows > 0) {

      $values = mysqli_query($dbc, "SELECT * FROM customers WHERE id='$id'");

      while ($rowr = mysqli_fetch_row($values)) {

        for ($j=0;$j<$numberOfRows;$j++) {
          $csv_output .= $rowr[$j];

          if ($j != $numberOfRows-1) {
            $csv_output .= ", ";
          }
        }

        $csv_output .= "\n";
      }

    }

    print $csv_output;
    exit;

  }


  if ($action == "addFare") {
    $customerName = mysqli_real_escape_string($dbc, $_GET['customer_name']);
    $start = mysqli_real_escape_string($dbc,$_GET['start']);
    $finish = mysqli_real_escape_string($dbc,$_GET['finish']);
    $price = mysqli_real_escape_string($dbc,$_GET['price']);
    $date = mysqli_real_escape_string($dbc,$_GET['date']);


    $query = "INSERT INTO fares (customer_name, start, finish, price, date) VALUES ('$customerName', '$start', '$finish', '$price', STR_TO_DATE('$date', '%Y-%m-%d'))";

    $result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));
  }


  if ($action == "getFares") {

    $result = mysqli_query($dbc, "SHOW COLUMNS FROM fares");
    $numberOfRows = mysqli_num_rows($result);

    if ($numberOfRows > 0) {

      $values = mysqli_query($dbc, "SELECT * FROM fares");

      while ($rowr = mysqli_fetch_row($values)) {

        for ($j=0;$j<$numberOfRows;$j++) {
          $csv_output .= $rowr[$j];

          if ($j != $numberOfRows-1) {
            $csv_output .= ", ";
          }
        }

        $csv_output .= "\n";

      }
    }

    print $csv_output;
    exit;
  }


  if ($action == "addSpend") {
    $type = mysqli_real_escape_string($dbc, $_GET['type']);
    $price = mysqli_real_escape_string($dbc,$_GET['price']);
    $date = mysqli_real_escape_string($dbc,$_GET['date']);

    $query = "INSERT INTO spends (type, price, date) VALUES ('$type', '$price', STR_TO_DATE('$date', '%Y-%m-%d'))";

    $result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));
  }


  if ($action == "getSpends") {

    $result = mysqli_query($dbc, "SHOW COLUMNS FROM spends");
    $numberOfRows = mysqli_num_rows($result);

    if ($numberOfRows > 0) {

      $values = mysqli_query($dbc, "SELECT * FROM spends");

      while ($rowr = mysqli_fetch_row($values)) {

        for ($j=0;$j<$numberOfRows;$j++) {
          $csv_output .= $rowr[$j];

          if ($j != $numberOfRows-1) {
            $csv_output .= ", ";
          }
        }

        $csv_output .= "\n";

      }
    }

    print $csv_output;
    exit;
  }




  if ($action == "addPagio") {

    $insurrance = mysqli_real_escape_string($dbc,$_GET['insurrance']);
    $teve = mysqli_real_escape_string($dbc,$_GET['teve']);
    $rent = mysqli_real_escape_string($dbc,$_GET['rent']);
    $teli = mysqli_real_escape_string($dbc,$_GET['teli']);

    $sum = (int)$teve/60 + (int)$insurrance/180 + (int)$rent/30 + (int)$teli/365;

    $sum = (string)round($sum, 2);

    $sql = "SELECT * FROM pagia";

    $result = mysqli_query($dbc,$sql);

    $rowcount = mysqli_num_rows($result);

    if ($rowcount == 0) {
      $query = "INSERT INTO pagia (id, insurrance, teve, rent, teli, sum) VALUES ('1', '$insurrance', '$teve', '$rent', '$teli', '$sum')";
    } else {
      $query = "UPDATE pagia SET insurrance='$insurrance', teve='$teve', rent='$rent', teli='$teli', sum='$sum' WHERE id='1'";
    }

    $result = mysqli_query($dbc, $query) or trigger_error("Query MySQL Error: " . mysqli_error($dbc));

  }


  if ($action == "getPagia") {

    $result = mysqli_query($dbc, "SHOW COLUMNS FROM pagia");
    $numberOfRows = mysqli_num_rows($result);

    if ($numberOfRows > 0) {

      $values = mysqli_query($dbc, "SELECT * FROM pagia");

      while ($rowr = mysqli_fetch_row($values)) {

        for ($j=0;$j<$numberOfRows;$j++) {
          $csv_output .= $rowr[$j];

          if ($j != $numberOfRows-1) {
            $csv_output .= ", ";
          }
        }

        $csv_output .= "\n";

      }
    }

    print $csv_output;
    exit;
  }




  if ($action == "getStats") {
    $last = mysqli_real_escape_string($dbc,$_GET['last']);

    $date1 =  mysqli_real_escape_string($dbc,$_GET['date1']);
    $date2 =  mysqli_real_escape_string($dbc,$_GET['date2']);

    if ($last == "day") {
      $result = mysqli_query($dbc, "SELECT SUM(price) AS esoda FROM fares WHERE DATEDIFF(NOW(), date) <= 1");
      $result2 = mysqli_query($dbc, "SELECT SUM(price) AS eksoda FROM spends WHERE DATEDIFF(NOW(), date) <= 1");
      $days = 1;
    }

    if ($last == "week") {
      $result = mysqli_query($dbc, "SELECT SUM(price) AS esoda FROM fares WHERE DATEDIFF(NOW(), date) <= 7");
      $result2 = mysqli_query($dbc, "SELECT SUM(price) AS eksoda FROM spends WHERE DATEDIFF(NOW(), date) <= 7");
      $days = 7;
    }

    if ($last == "month") {
      $result = mysqli_query($dbc, "SELECT SUM(price) AS esoda FROM fares WHERE (date BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND NOW())");
      $result2 = mysqli_query($dbc, "SELECT SUM(price) AS eksoda FROM spends WHERE (date BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND NOW())");
      $now = time();
      $your_date = strtotime(date('d-m-Y', strtotime(date('Y-m-1'))));
      $datediff = $now - $your_date;
      $days = floor($datediff/(60*60*24)) + 1;
    }

    if ($last == "custom") {
      $result = mysqli_query($dbc, "SELECT SUM(price) AS esoda FROM fares WHERE (date BETWEEN STR_TO_DATE('$date1', '%Y-%m-%d') AND STR_TO_DATE('$date2', '%Y-%m-%d'))");
      $result2 = mysqli_query($dbc, "SELECT SUM(price) AS eksoda FROM spends WHERE (date BETWEEN STR_TO_DATE('$date1', '%Y-%m-%d') AND STR_TO_DATE('$date2', '%Y-%m-%d'))");
      $now = strtotime(date('Y-m-d', strtotime($date2)));
      $your_date = strtotime(date('Y-m-d', strtotime($date1)));
      $datediff = $now - $your_date;
      $days = floor($datediff/(60*60*24)) + 1;
    }

    $result3 = mysqli_query($dbc, "SELECT sum FROM pagia");


    $row = mysqli_fetch_array($result, MYSQLI_ASSOC); //esoda
    $row2 = mysqli_fetch_array($result2, MYSQLI_ASSOC); // eksoda
    $row3 = mysqli_fetch_array($result3, MYSQLI_ASSOC); //pagia ana hmera

    $pagia = $row3['sum'] * $days;
    $esoda = ($row['esoda'] != 0) ? $row['esoda'] : 0;
    $eksoda = ($row2['eksoda'] != 0) ? $row2['eksoda'] : 0;

    $summary = $esoda - $eksoda - $pagia;

    $csv_output = $esoda . ", " . $eksoda . ", " . $pagia . ", " . $summary . "\n";

    // export -> CSV[pagia, esoda, eksoda, synolo]
    print $csv_output;
    exit;
  }

//}

mysqli_close($dbc);

?>
