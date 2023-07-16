import java.util.concurrent.Semaphore;

class Parte1 {
    private Semaphore recurso;
    private int unidadesNecesarias;
    private int unidadesLibres;

    public Parte1(Semaphore recurso, int unidadesNecesarias) {
        this.recurso = recurso;
        this.unidadesNecesarias = unidadesNecesarias;
        this.unidadesLibres = 0;
    }

    public void reserva(int unidades) throws InterruptedException {
        recurso.acquire(unidades);
        unidadesLibres += unidades;
        System.out.println("Parte1 adquirió " + unidades + " unidades del recurso. Unidades disponibles: " + unidadesLibres);
    }

    public void libera(int unidades) {
        recurso.release(unidades);
        unidadesLibres -= unidades;
        System.out.println("Parte1 liberó " + unidades + " unidades del recurso. Unidades disponibles: " + unidadesLibres);
    }

    public void ejecutar() {
        try {
            // Realizar reservas y liberaciones según las necesidades de Parte1
            reserva(4);
            libera(2);
            reserva(1);
            libera(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}