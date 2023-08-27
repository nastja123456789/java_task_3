import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SplitPoem {
    public static void main(String[] args) {
        try {
            File inputFile=new File("poem.txt");
            Scanner scanner = new Scanner(inputFile);
            int partCount=1;
            String separator=System.getProperty("line.separator");
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                if (line.isEmpty()) {
                    partCount++;
                } else {
                    String outputFileName="part"+partCount+".txt";
                    FileWriter writer=new FileWriter(outputFileName, true);
                    writer.write(line+separator);
                    writer.close();
                }
            }
            scanner.close();
            String zipFileName="poem_parts.zip";
            File zipFile=new File(zipFileName);
            FileOutputStream fos=new FileOutputStream(zipFile);
            ZipOutputStream zipOutputStream=new ZipOutputStream(fos);
            for (int i=1;i<=partCount;i++) {
                String fileName="part"+i+".txt";
                File file=new File(fileName);
                FileInputStream fis=new FileInputStream(file);
                ZipEntry zipEntry=new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                byte[] bytes=new byte[1024];
                int length;
                while ((length=fis.read(bytes))>=0) {
                    zipOutputStream.write(bytes, 0, length);
                }
                fis.close();
                file.delete();
            }
            zipOutputStream.close();
            fos.close();
            System.out.println("Успешно создан zip-архив");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
