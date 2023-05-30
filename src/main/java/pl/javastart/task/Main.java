package pl.javastart.task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        // uzupełnij rozwiązanie. Korzystaj z przekazanego w parametrze scannera
        String text = getDateAndTimeFromUser(scanner);
        List<String> patterns = List.of("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "dd.MM.yyyy HH:mm:ss");
        for (String pat : patterns) {
            try {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern(pat);
                TemporalAccessor temporalAccessor = pattern.parse(text);
                LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
                ZoneId warsawZoneId = ZoneId.of(TimeZones.TIME_FROM_COMPUTER.getZoneId());
                ZonedDateTime warsawZoneDateTime =  localDateTime.atZone(warsawZoneId);
                for (TimeZones value : TimeZones.values()) {
                    ZoneId idOfRequestedZone = ZoneId.of(value.getZoneId());
                    ZonedDateTime requestedDateTime = warsawZoneDateTime.withZoneSameInstant(idOfRequestedZone);
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(patterns.get(0));
                    System.out.println(value.getName() + dateTimeFormatter.format(requestedDateTime));
                }
            } catch (DateTimeException e) {
                //
            }
        }
    }

    private static String getDateAndTimeFromUser(Scanner scanner) {
        System.out.println("Wczytaj date");
        String text = scanner.nextLine();
        if (text.length() < 11) {
            text = text + " 00:00:00";
        }
        return text;
    }
}
