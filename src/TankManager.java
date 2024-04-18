import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TankManager {
    
    // Clase interna para representar los elementos de cada ubicación
    static class TankElement {
        private String ubicacionX;
        private String ubicacionY;
        private boolean ocupado;

        public TankElement(String ubicacionX, String ubicacionY, boolean ocupado) {
            this.ubicacionX = ubicacionX;
            this.ubicacionY = ubicacionY;
            this.ocupado = ocupado;
        }

        // Getters y setters para ubicacion y ocupado
        public String getUbicacionX() {
            return ubicacionX;
        }
        
        // Getters y setters para ubicacion y ocupado
        public String getUbicacionY() {
            return ubicacionY;
        }

        public void setUbicacionX(String ubicacionX) {
            this.ubicacionX = ubicacionX;
        }
        
        public void setUbicacionY(String ubicacionX) {
            this.ubicacionY = ubicacionY;
        }

        public boolean isOcupado() {
            return ocupado;
        }

        public void setOcupado(boolean ocupado) {
            this.ocupado = ocupado;
        }
    }

    private List<List<TankElement>> tankList;

    public TankManager() {
        // Inicializa la lista con una lista vacía
        this.tankList = new CopyOnWriteArrayList<>();
        
        // Ubicaciones predeterminadas con valores de ocupado y no ocupado
        List<TankElement> ubicacion1 = new CopyOnWriteArrayList<>();
        ubicacion1.add(new TankElement("-50", "-10", false));
        ubicacion1.add(new TankElement("-50", "500", false));
        ubicacion1.add(new TankElement("600", "-10", false));
        ubicacion1.add(new TankElement("600", "-500", false));
        tankList.add(ubicacion1);
        
    }

    // Método para consultar la lista de ubicaciones
    public List<List<TankElement>> getTankList() {
        return tankList;
    }

  
}