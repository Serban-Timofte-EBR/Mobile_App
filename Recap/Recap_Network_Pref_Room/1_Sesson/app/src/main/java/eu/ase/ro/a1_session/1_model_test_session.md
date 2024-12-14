
# 1_model_test_session

## Proiect: Gestionarea Sesiunilor de Conferințe

### Context
Creează o aplicație Android pentru gestionarea sesiunilor dintr-o conferință. Aplicația va permite descărcarea unui JSON complex, parsarea acestuia, afișarea informațiilor relevante în UI și salvarea datelor în baza de date locală (Room).

---

## Cerințe

### **1. Definirea Clasei `Session`**
Clasa `Session` reprezintă o sesiune de conferință și conține următoarele atribute:
- `id` (long): Identificator unic pentru sesiune.
- `title` (String): Titlul sesiunii.
- `date` (Date): Data și ora sesiunii.
- `speaker` (String): Numele speaker-ului care susține sesiunea.
- `duration` (int): Durata sesiunii (în minute).
- `room` (String): Sala unde se desfășoară sesiunea.

Toate atributele trebuie să aibă getter-e, setter-e și o metodă `toString()` care să returneze un rezumat al informațiilor.

---

### **2. Citirea și Parsarea JSON**
Endpoint-ul JSON:
```
{
  "conference": {
    "sessions": [
      {
        "id": 101,
        "title": "Android Best Practices",
        "date": "2024-01-15 09:30",
        "speaker": "John Doe",
        "duration": 60,
        "room": "Room A1"
      },
      {
        "id": 102,
        "title": "Kotlin Coroutines Deep Dive",
        "date": "2024-01-15 11:00",
        "speaker": "Jane Smith",
        "duration": 45,
        "room": "Room B2"
      }
    ],
    "welcome_message": "Welcome to the 2024 Android Conference!"
  },
  "last_updated": "2024-01-10",
  "updated_by": "admin@conference.com"
}
```

#### Cerințe pentru parsare:
1. Extrage doar array-ul `sessions` din obiectul `conference`.
2. Creează o metodă `fromJSON(String json)` pentru a parsa informațiile în obiecte `Session`.
3. Afișează lista de sesiuni parșate în consolă.

---

### **3. UI pentru Gestionarea Sesiunilor**
Creează o activitate principală care să includă următoarele componente:
1. Un buton **"Descarcă Sesiuni"** pentru a descărca datele de la endpoint.
2. Un spinner pentru a filtra sesiunile descărcate după atributul speaker.
   - Spinner-ul este populat cu datele din lista de sesiuni.
   - Fiecare sesiune este afișată folosind metoda `toString()` din clasa `Session`.
3. Selectarea unei sesiuni din spinner va afișa detaliile în fragmentul de mai jos.

---

### **4. Salvarea și Gestionarea Datelor**
1. **Room Database**:
   - Definește o bază de date locală pentru a salva sesiunile descărcate.
   - Creează un DAO pentru operațiile de inserare și citire a sesiunilor.
2. **SharedPreferences**:
   - Salvează data ultimei descărcări a datelor.
   - Afișează data ultimei descărcări pe ecranul principal.

---

### **5. Transferul Datelor între Activități**
Creează o activitate secundară pentru a adăuga manual o sesiune nouă:
- Activitatea trebuie să conțină input-uri pentru toate atributele clasei `Session`.
- Datele introduse trebuie validate și trimise înapoi către activitatea principală.

---

### Testare și Validare
1. Testează aplicația pe un emulator Android.
2. Asigură-te că datele sunt descărcate corect și sunt afișate în UI.
3. Verifică funcționalitatea bazei de date și a SharedPreferences.
