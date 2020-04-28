import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static void join(String in1, String in2, String out) throws IOException {
        FileOutputStream fos = new FileOutputStream(out);
        int ch;
        FileInputStream fis = new FileInputStream(in1);
        while ((ch = fis.read()) != -1)
            fos.write(ch);
        fis.close();

        fis = new FileInputStream(in2);
        while ((ch = fis.read()) != -1)
            fos.write(ch);
        fis.close();

        fos.flush();
        fos.close();
    }

    private static boolean isInFile(String fileName, String search) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] searchBytes = search.getBytes();
        int ch;
        int i = 0;
        while ((ch = fis.read()) != -1) {
            if (ch == searchBytes[i])
                i++;
            else {
                i = 0;
                if (ch == searchBytes[i])
                    i++;
            }
            if (i == searchBytes.length) {
                fis.close();
                return true;
            }
        }
        fis.close();
        return false;
    }

    public static void main(String[] args) {


        try {

            // задание 2

            join("a.txt", "b.txt", "out.txt");
            System.out.println("Сохранили результат объединения файлов в out.txt");

            // задание 3

            System.out.print("Введите слово для поиска: ");
            Scanner sc = new Scanner(System.in);
            String search = sc.next();
            sc.close();
            System.out.println("Проверяем, что в файле out.txt есть искомое слово: " + isInFile("out.txt", search));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
