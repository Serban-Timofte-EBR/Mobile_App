package eu.ase.ro.labex;

public enum Course {
    DAM, CTS, POO, TW, SDD;

    // Metodă pentru a obține o valoare validă sau null (sau o valoare implicită)
    public static Course fromString(String value) {
        for (Course course : Course.values()) {
            if (course.name().equalsIgnoreCase(value)) {
                return course;
            }
        }
        return null; // Sau o valoare implicită, de exemplu: `return Course.DAM;`
    }
}