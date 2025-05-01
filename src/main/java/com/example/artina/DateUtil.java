package com.example.artina;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static boolean compareDates(String dateStr1, String dateStr2) {
        try {
            // Définir le format de la date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Convertir les chaînes de caractères en objets Date
            Date date1 = sdf.parse(dateStr1);
            Date date2 = sdf.parse(dateStr2);

            // Comparer les deux dates
            return date1.equals(date2);  // Renvoie true si les dates sont égales
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        String date1 = "2025-05-01 14:30:00";
        String date2 = "2025-05-01 14:30:00";

        boolean result = compareDates(date1, date2);
        System.out.println("Les dates sont égales ? " + result);
    }
}
