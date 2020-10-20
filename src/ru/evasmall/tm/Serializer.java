package ru.evasmall.tm;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serializer {

    private static final Logger logger = Logger.getLogger(Serializer.class.getName());

    //Разделитель
    private static final char SEPARATOR = ';';

    /**
     * Экспорт списка объектов в CSV файл
     * @param subjects список объектов
     */
    public static boolean exportCsv(List<Object> subjects, String file) {
        //Проверка на пустой список объектов.
        if (subjects == null || subjects.isEmpty()) {
            logger.log(Level.SEVERE, "SUBJECT IS EMPTY.");
            return false;
        }
        //Определение класса по первому элементу массива.
        Class<?> clazz = subjects.get(0).getClass();
        try (BufferedWriter bufferedWriter = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)))) {
            bufferedWriter.write(getFieldHeaders(clazz));
            String str = "";

            StringBuilder fieldLine = new StringBuilder();
            for (Object subject : subjects) {
                Class<?> clazzSubject = subject.getClass();
                //Проверка списка объектов на принадлежность к одному типу.
                if (clazzSubject != clazz) {
                    throw new IllegalArgumentException("THE LIST CONTAINS OBJECTS OF DIFFERENT TYPES. FAIL.");
                }
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    fieldLine.append(field.get(subject)).append(SEPARATOR);
                  }
                fieldLine.setLength(Math.max(fieldLine.length() - 1, 0));
                fieldLine.append("\n");
                str = fieldLine.toString();
            }
            bufferedWriter.write(str);
            bufferedWriter.flush();
            return true;
        } catch (IOException | IllegalAccessException e) {
            logger.log(Level.SEVERE, e.toString());
            return false;
        }
    }

    /**
     * Построение строки заголовков
     * @param clazz Класс объекта
     * @return строка с заголовками полей
     */
    private static String getFieldHeaders(Class<?> clazz) {
        StringBuilder fieldHeader = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            fieldHeader.append(field.getName()).append(SEPARATOR);
        }
        String str = fieldHeader.toString();
        str = str.substring(0, str.length() - 1);
        str = str + "\n";
        return str;
    }

}
