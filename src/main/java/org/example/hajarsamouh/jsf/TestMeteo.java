package org.example.hajarsamouh.jsf;

import org.example.hajarsamouh.jsf.outil.meteo.MeteoTool;

public class TestMeteo {
    public static void main(String[] args) {
        MeteoTool meteo = new MeteoTool();

        // Paris : latitude 48.85, longitude 2.35
        // Période de 3 jours qui commence aujourd'hui (nbJoursDebut = 0)
        System.out.println("Pluie à Paris sur 3 jours dès aujourd'hui : "
                + meteo.isPluiePrevue(48.85, 2.35, 0, 3));

        // Période de 1 jour qui commence demain (nbJoursDebut = 1)
        System.out.println("Pluie à Paris demain : "
                + meteo.isPluiePrevue(48.85, 2.35, 1, 1));
    }
}