# Other relevant info

## Async Task

- Clasa Android care permite rularea unor operatii asincrone in afara thread-ului principal (UiThread)

- Permite si actualizarea interfetei utilizatorului (UI) cu rezultatele obtinute

**Metodele AsynTask**

1. *onPreExecute()*

- Este apelata inainte de inceperea executarii in thread-ul secundar

- **Ruleaza pe UI Thread - firul principal**

- Este folosita pentru initializarea unor elemente din UI

```java
@Override
protected void onPreExecute() {
    super.onPreExecute();
    progressBar.setVisibility(View.VISIBLE); // Afișează un ProgressBar
}
```

2. *doInBackground(Params... params)*

- Ruleaza in thread-ul secundar

- Sunt plasate operatiile consumatoare de timp (ex: descarcare de documente, interogari ale bazei de date)

- NU are acces la thread-ul principal

```java
@Override
protected String doInBackground(String... params) {
    String result = downloadDataFromURL(params[0]); // Descărcare date
    return result;
}
```

3. *publishProgress()*

- Este utilizata in interiorul metodei doInBackground() pentru a trimite informatii catre Thread-ul principal

- Cheama automat metoda onProgressUpdate()

```java
publishProgress(50); // Transmite progresul de 50%
```

4. *onProgressUpdate(Progress... values)*

- Este apelata pe firul principal cand publishProgress() este invocata

- Este folosita pentru actualizarea elementelor UI, cum ar fi ProgressBar sau TextView

```java
@Override
protected void onProgressUpdate(Integer... values) {
    progressBar.setProgress(values[0]); // Actualizează progresul
}
```

5. *onPostExecute(Result result)*

- Este apeladata dupa ce metoda doInBackground() si-a terminat executia

- Rulata pe thread-ul principal

- Este folosita pentru a afisa rezultatele operatiei asincrone 

6. *onCancelled()*

- Este apelata atunci cand sarcina asincrona este anulata prin metoda cancel()

- Ruleaza pe UI Thread

**Fluxul execuției în AsyncTask**

1.	onPreExecute() – Pregătirea interfeței utilizatorului.

2.	doInBackground() – Execuția operației în fundal.

- Opțional: publishProgress() pentru actualizări intermediare.

3.	onProgressUpdate() – Actualizarea progresului pe UI.

4.	onPostExecute() – Finalizarea și afișarea rezultatelor pe UI.

## Ciclurile de viata

1. **onCreate()**

- Este configurata interfata utilizatorului (*setContentView()*)

- Activitatea inca nu este vizibila si utilizatorul nu poate interactiona cu ea in acest moment

2. **onStart()**

- Apelata cand activitatea revine in prim-plan

- Activitatea devine vizibila, dar nu interactive

3. **onResume()**

- Activitatea devine interactive pentru utilizator

- Activitatea este foreground complet si poate primi evenimente precum touch sau click

## Canvas

- Clasa Android pentru desenarea graficelor 2D

- Oferă metode precum:

&emsp;&emsp; - drawLine() - pentru a desena linii.

&emsp;&emsp; -  drawRect() - pentru a desena dreptunghiuri.

&emsp;&emsp; - drawCircle() - pentru a desena cercuri.

&emsp;&emsp; - drawText() - pentru a desena text.

&emsp;&emsp; - drawBitmap() - pentru a desena imagini.

- Functioneaza impreuna cu metoda **onDraw()** a unui View.

- De fiecare data cand ecranul trebuie redesenat, Android apeleaza **onDraw()**

## Servicii locale

- Sunt initializate cu 2 comenzi (una sau cealalta)

1. **startService()** - daca serviciul ruleaza independent de componenta care l-a initializat

2. **bindService()** -  daca serviciul ruleaza dependent de componente care l-a initializat

## Actions (Mesaje implicite)

1. ACTION_CALL

- Apeleaza direct un numar, fara a mai deschide o interfata

2. ACTION_DIAL

- Deschide interfata de telefon cu un numar predefinit

## BroadcastReceiver

- O componenta Android care permite plicațiilor să asculte și să răspundă la mesaje sau evenimente transmise la nivel de sistem sau aplicație. Aceste mesaje (numite broadcasts) pot fi generate de sistemul Android sau de aplicații.

## Fisiere specifice Gradle

1. libs.versions.toml

- Este un catalog de versiuni pentru proiectele Android

- Centralizeaza versiunile si plug-in-urile

- Contine versions, libraries si plug-ins

2. build.gradle (root)

- Specifica versiunea Gradle Plugin utilizata 

- Defineste configurarile generale ale proiectului

3. build.gradle (module)

- Defineste configurarile unui modul (spre exemplu app)

- Specifica versiunea de SDK

- Listeaza dependentele din respectivul modul

**OBS:** Libraria este un sed de cod reutilizabil care aduce o functionalitate noua. Plug-in-ul este extindere a sistemului de build Gradle care permite adaugarea unor configuratii speciale

## Testele JUnit

- JUnit este un limbaj pentru crearea de teste unitare in Java

