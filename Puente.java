import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Puente {
    private int cochesEnElPuente;
    private int cochesEsperandoNorte;
    private int cochesEsperandoSur;
    private boolean cocheDelSurHaPasado;
    private Lock lock;
    private Condition esperaNorte;
    private Condition esperaSur;

    public Puente() {
        cochesEnElPuente = 0;
        cochesEsperandoNorte = 0;
        cochesEsperandoSur = 0;
        cocheDelSurHaPasado = false;
        lock = new ReentrantLock();
        esperaNorte = lock.newCondition();
        esperaSur = lock.newCondition();
    }

    public void entrarDesdeElNorte() throws InterruptedException {
        lock.lock();
        try {
            cochesEsperandoNorte++;

            while (cochesEnElPuente > 0 || cochesEsperandoSur > 0 || cocheDelSurHaPasado) {
                esperaNorte.await();
            }

            cochesEsperandoNorte--;
            cochesEnElPuente++;
            System.out.println("Coche del Norte cruzando el puente.");
        } finally {
            lock.unlock();
        }
    }

    public void entrarDesdeElSur() throws InterruptedException {
        lock.lock();
        try {
            cochesEsperandoSur++;

            while (cochesEnElPuente > 0 || cochesEsperandoNorte > 0) {
                esperaSur.await();
            }

            cochesEsperandoSur--;
            cochesEnElPuente++;
            cocheDelSurHaPasado = true;
            System.out.println("Coche del Sur cruzando el puente.");
        } finally {
            lock.unlock();
        }
    }

    public void salirDelPuente() {
        lock.lock();
        try {
            cochesEnElPuente--;
            System.out.println("Coche saliendo del puente.");

            if (cochesEsperandoNorte > 0) {
                esperaNorte.signal();
            } else if (cochesEsperandoSur > 0) {
                esperaSur.signal();
            } else {
                cocheDelSurHaPasado = false;
                esperaNorte.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}

class Coche implements Runnable {
    private String direccion;
    private Puente puente;

    public Coche(String direccion, Puente puente) {
        this.direccion = direccion;
        this.puente = puente;
    }

    @Override
    public void run() {
        try {
            if (direccion.equals("Norte")) {
                puente.entrarDesdeElNorte();
                Thread.sleep(1000); // Simular tiempo de cruce del puente
                puente.salirDelPuente();
            } else if (direccion.equals("Sur")) {
                puente.entrarDesdeElSur();
                Thread.sleep(1000); // Simular tiempo de cruce del puente
                puente.salirDelPuente();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
