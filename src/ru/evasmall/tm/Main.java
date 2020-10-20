package ru.evasmall.tm;

import ru.evasmall.tm.entity.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getLogger(Main.class.getName());

    public static final String FILE_NAME = "data.csv";

    public static void main(String[] args) {
        logger.info("PROGRAM BEGIN.");
        List<Object> subjects  = new ArrayList<>();
        subjects.add(new Person("Василий", "Чапаев", LocalDate.of(1887, 1, 28), "chapaev_vi@gmail.com"));
        subjects.add(new Person("Пётр", "Исаев", LocalDate.of(1890, 9, 5), "isaev_ps@gmail.com"));
        subjects.add(new Person("Дмитрий", "Фурманов", LocalDate.of(1891, 10, 26), "furmanov_da@gmail.com"));
        subjects.add(new Person("Анна", null, null, null));
        try {
            Serializer.exportCsv(subjects, FILE_NAME);
            logger.info("DATA EXPORTED TO FILE " + FILE_NAME);
        } catch (Exception e) {
            logger.severe(e.toString());
        }
        logger.info("PROGRAM END.");
    }

}
