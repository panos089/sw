<h1>FluTrack NoSQL Infrastructure</h1>
Student: Giannakouris - Salalidis Victor <br>
Task: Costruction of a NoSQL Infrastructure (Back-end) for FluTrack project

<h2>Description</h2>
Upgrade the infrastructure of the existing FluTrack project with a scalable backend system using a NoSQL - Big Data architecture. <br><br>

<section>
  <h3>Tools</h3>
  <ul>
    <li>Okeanos: IaaS providing virtual machines</li>
    <li>MongoDB: NoSQL Document Store</li>
  </ul>
  <h3>Resources</h3>
  <ul>
    <li>Number of VMs: 2</li>
    <li>CPUs / VM: 2</li>
    <li>RAM / VM: 6 GB</li>
  </ul>
</section>

<div id="progress">
  <section>
    <h2>Progress</h2>
      <ul>
        <li>
          <p>Implementation of Twitter Streaming script in Python</p>
        </li>
        <li>
          <p>Installation of NoSQL infrastructure with MongoDB</p>
        </li>
      </ul>
  </section>
</div>

<div id="final_report">
  <h1>Τελική Αναφορά<h1>
  <h2>Εισαγωγή<h2>
  <p>Η παραπάνω εργασία με θέμα την υλοποίηση της NoSQL υποδομής του Flutrack Project αποτελεί εργασία εξαμήνου για το μάθημα   Τεχνολογία Λογισμικού (ΗΥ320). Στόχος της εργασίας ήταν η ανάπτυξη εκ νέου του Back-end συστήματος του project προκειμένου   να μπορεί να ανταπεξέλθει μελλοντικά στις ανάγκες των μαζικών δεδομένων αλλά και αυτές των χρηστών. Το τελικό αποτέλεσμα     της εργασίας είναι ένα ολοκληρωμένο σύστημα που συλλέγει δεδομένα από το Twitter, τα διαμορφώνει σύμφωνα με τις ανάγκες του   συστήματος και στην συνέχεια τα διαθέτει στο χρήστη μέσω ενός REST Service με τον ίδιο ακριβώς τρόπο και χειρισμό όπως το    ‘παλιό’ σύστημα του Flutrack.</p>
</div>

<div id='tech'>
  <h2>Τεχνικά Χαρακτηριστικά</h2>
  <p>
   Για την υλοποίηση του παραπάνω συστήματος χρησιμοποιήθηκαν τα εξής εργαλεία-τεχνολογίες:
    <ul>
      <li>Γλώσσα Προγραμματισμού: Python</li>
      <li>Βάση Δεδομένων: MongoDB</li>
      <li>REST API: Django</li>
      <li>Twitter Streaming: Tweepy</li>
    </ul>
  </p>
</div>

<div id='usage'>
  <h2>Usage - Benchmark</h2>
  <ul>
    <li>Χρόνος: 0:53:33</li>
    <li>Όγκος (MB): 21985264.0</li>
    <li>Πλήθος (#tweets): 8232</li>
    <li>
      <p>Time - Storage διάγραμμα</p>
      <img src='http://users.ionio.gr/~p12gian1/time-storage.png'><br>
    </li>
    <li>
      <p>Time - #Tweets διάγραμμα</p>
      <img src='http://users.ionio.gr/~p12gian1/time-tweets.png'><br>
    </li>
  </ul>
</div>

<div id='description-dataflow'>
  <h2>Περιγραφή ροής δεδομένων και ερωτήματος</h2>
  <ul>
    <li>
      <h3>Ροή (Data flow)</h3>
      <ul>
        <li>Συλλογή δεδομένων σε πραγματικό χρόνο με το πακέτο Tweepy</li>
        <li>Μετασχηματισμός της κάθε αναφοράς/tweet στην επιθυμητή δομή (field structure)</li>
        <li>Εισαγωγή στο MongoDB</li>
      </ul>
    </li>
    <li>
      <h3>Ερώτημα (Query)</h3>
      <ul>
        <li>Αποστολή ερωτήματος από το χρήστη μέσω του REST Service με τις παραμέτρους s, limit και time.</li>
        <li>Text search στη βάση Mongo</li>
        <li>Επιστροφή αποτελέσματος στο χρήστη σε μορφή json</li>
      </ul>
    </li>
  </ul>

</div>
