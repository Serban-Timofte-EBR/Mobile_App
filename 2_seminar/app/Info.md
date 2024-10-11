## Elemente vizuale

&emsp; Elementele vizuale sunt tinute intr-un container / layout

&emsp;&emsp; -> Cel mai folosit de noi este <b><i>Constraint Layout</i></b>

&emsp;&emsp; -> Avantaje: asigura pozitionarea pe pagina in functie de anumite constrangeri.

&emsp;&emsp; -> Este foarte flexibil

&emsp;&emsp; -> Elementele vizuale sunt puse intr-un Constraint Layout unde vor fi manageruite

&emsp;&emsp; -> Constrangeri container: Vertical si Horizontal Bias <= coltul din stanga sus este punctule de pornire in Android

&emsp;&emsp; -> <b>4 puncte de referinta</b>: Top, Start, End, Bottom

&emsp;&emsp; -> Regula este sa avem cel putin 3 combinatii diferite si maxim 4

## Notes 

&emsp;&emsp; -> 0dp: Calculeaza spatiul ramas dupa constrangeri = match_constraints

&emsp;&emsp; -> Horizontal Bias: cand punem constrangeri de margine si stanga si dreapta

&emsp;&emsp; -> Legi o activitate de fisierul XML cu componentele vizuale

&emsp;&emsp; -> Ex: Bottom toTopOf: partea de jos al elementului meu vine deasupra partii de sus a elementului ales

&emsp;&emsp; -> Intent folosit pentru transferul de date. Cere contextul prin constructor