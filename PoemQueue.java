import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class PoemQueue {
    public static void main(String[] args) {
        Queue<String> queue=new LinkedList<>();
        try {
            File inputFile=new File("poem.txt");
            Scanner scanner=new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line=scanner.nextLine();
                queue.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Кол-во строк в очереди: "+queue.size());
        printQueueWithDelay(queue);
        try {
            createJarFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createJarFile() throws IOException {
        File mainfestFile = new File("MANIFEST.MF");
        mainfestFile.createNewFile();
        try (FileWriter writer=new FileWriter(mainfestFile)) {
            writer.write("Main-Class: JarCreator\n");
        }
        JarOutputStream jarOutputStream=new JarOutputStream(new FileOutputStream("output.jar"),
                createManifest());
//        addToJar(jarOutputStream, "JarCreator.class");
        addToJar(jarOutputStream, "poem.txt");
        jarOutputStream.close();

    }

    public static Manifest createManifest() throws IOException {
        FileInputStream manifestInput=new FileInputStream("MANIFEST.MF");
        Manifest manifest=new Manifest(manifestInput);
        manifest.clear();
        return manifest;
    }

    public static void addToJar(JarOutputStream jarOutputStream,
                                String filePath) throws IOException {
        File file=new File(filePath);
        JarEntry entry=new JarEntry(file.getName());
        jarOutputStream.putNextEntry(entry);

        FileInputStream inputStream=new FileInputStream(file);
        byte[] buffer=new byte[1024];
        int bytesRead;
        while ((bytesRead=inputStream.read(buffer))!=-1) {
            jarOutputStream.write(buffer,0,bytesRead);
        }
        inputStream.close();
        jarOutputStream.closeEntry();
    }
    private static void printQueueWithDelay(Queue<String> queue) {
        Random random=new Random();
        while (!queue.isEmpty()) {
            try {
                Thread.sleep(random.nextInt(3000)+1000);
                System.out.println(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
