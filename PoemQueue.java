import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

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
