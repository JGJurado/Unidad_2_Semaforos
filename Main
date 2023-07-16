import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int n = 20; // Número de procesos
        int k = 100; // Número de unidades disponibles del recurso

        Semaphore recurso = new Semaphore(k);

        // Crear e iniciar los procesos
        for (int i = 0; i < n; i++) {
            Parte1 parte1 = new Parte1(recurso, i + 1);
            Thread thread = new Thread(() -> parte1.ejecutar());
            thread.start();
        }
    }

}
