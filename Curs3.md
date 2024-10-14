## Activitati - Important Info: 

1. Componente de baza ale aplicatiilor mobile

2. Sunt asociate ecranelor aplicatiei

3. Au un ciclu de viata

&emsp;&emsp;&emsp; -> Starile unei activitati: onCreate, onPause, onResume, onDestroy

5. Clasa de baza este Activity

6. Interfata grafica este create prin fisiere XML in cadrul activitatii

&emsp;&emsp;&emsp; -> SAU: Activitatea afiseaza un fragment si fragmentul are in spate un XML

7. Stiva de activitati (Task-uri)

- Clasa R este generata de compilator la compile time 

- action.MAIN -> Clasa asociata activitatii / la tipul activitatii (MAIN / LAUNCHER)

## Activitati - other info

Activitatea este asociata unui ecran

&emsp; -> Ex: onCreate pentru a porni un ecran

<i>Cand deschizi un ecran ai o activitate</i>

<i>Fragmentele</i> sunt legate de o activitate. Containerul pentru un fragment este activitatea. 

Folosesc clasa Activity

**Android Debug Bridge (ADB)** -> Cand am pornit Android Studio ruleaza aplicatia (implicit si pe telefonul mobil). Serverul de dezvoltare comunica cu telefonul mobil prin ADB. 

## Aplicatia

- Lansata in executie de utilizator la invocarea unei componente

- Ciclu de viata propriu

- Clasa derivata din Application

- Referinta la obiect prin <i>getApplication</i>

## Context

- Pentru a comunica cu mediul unei aplicatii

- Clasa Context - ofera access la resurse, sistem de fisiere, servicii si mesaje la nivelul platformei

- Context este atop Application, Service, Activity. Clasele acestea implementeaza Context (sau o mostenesc)

## Servicii

Serviciile ruleaza fara o activitate sau interfata

&emsp; -> Ex: un serviciu preia coordonatele GPS, ascultarea muzicii in background

<i>Cand rulezi in background ai un serviciu</i>

Avem clasa Services

## Permisiuni

- Normale (internet, bluetooth, NFC)

- Cu risc (Calendar, Camera, Contacts, SMS, etc):

&emsp;&emsp;&emsp; -> Acordate de user

&emsp;&emsp;&emsp; -> Controleaza accesul la executie

## Scripturi (Sistemul de compilare)

Folosim sistemul bazat pe **gradle**

Versiuni: libs.versions.toml

Proprietatile: gradle.properties

Build - Sistemul de compilare: build.gradle.kts

## Q&A

<b><i>Q: </i></b>Componentele aplicatiei Android

&emsp; <b><i>-> A: </i></b>Aplicatia are una sau mai multe componente de mai multe tipuri: Activitatea, Ecrane, Servicii, Fragmente

<b><i>Q: </i></b>Ce este un intent?

&emsp; <b><i>-> A: </i></b>Este o metoda a unei clase care comunica cu OS printr-un mesaj
