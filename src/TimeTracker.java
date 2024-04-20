import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TimeTracker {
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private static boolean running = true;
    private static int timeCounter = 0;
    private static final String TIME_LOG_FILE = "time_log.txt";
    private static final String TIME_USED_FOR_CODING = "Time_Used_For_Coding.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to start tracking time, or type 'exit' to quit.");

        Thread timeThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000); // Sleep 1 sekunde
                    trackTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();

        while (!scanner.nextLine().equals("e")) {
            System.out.println("Press Enter to continue tracking, or type 'exit' to quit.");
        }

        running = false; // Stop
        try {
            timeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // letzte Zeit wird gespeichert
        try (FileWriter Time_Used_For_Coding = new FileWriter(TIME_USED_FOR_CODING, true)) {
            Time_Used_For_Coding.write(String.format("%02d:%02d:%02d\n", hours, minutes, seconds));
            Time_Used_For_Coding.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Time tracking stopped.");
    }

    private static void trackTime() {
        seconds++;
        if (seconds >= 60) {
            seconds = 0;
            minutes++;
            if (minutes >= 60) {
                minutes = 0;
                hours++;
                if (hours >= 24) {
                    hours = 0;
                }
            }
        }
        timeCounter++;

        // Print und sichere Zeit zu file
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        System.out.println("Time: " + time);

        try (FileWriter fileWriter = new FileWriter(TIME_LOG_FILE)) {
            fileWriter.write(time);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}