public class MainPuentes {
    public static void main(String[] args) {
        Puente puente = new Puente();

        // Crear e iniciar los coches
        Thread cocheNorte = new Thread(new Coche("Norte", puente));
        Thread cocheSur = new Thread(new Coche("Sur", puente));
        Thread cocheSur2 = new Thread(new Coche("Sur", puente));
        Thread cocheSur3 = new Thread(new Coche("Sur", puente));

        cocheNorte.start();
        cocheSur.start();
        cocheSur2.start();
        cocheSur3.start();

        //El coche norte siempre pasa primero
        // y sabe que no hay nadie en el puente, mientras que los del sur,
        // esperan a que salgan los del norte y continuan hacia el norte
    }
} // Finald el main puente
