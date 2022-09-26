/**
 * Clase Película.
 */
package tareaalgord;

/**
 * 28 de febrero, 2020.
 * @author Murillo
 */
public class Pelicula implements Comparable <Pelicula> {
    private int id;
    private int aho;
    private String nombre;
    
    //Constructor
    public Pelicula(int id, int aho, String nombre){
        this.id = id;
        this.aho = aho;
        this.nombre = nombre;
    }
    
    //Getters
    public int getId(){
        return id;
    }
    public int getAho(){
        return aho;
    }
    public String getNombre(){
        return nombre;
    }
    
    //Setters
    public void setId(int id){
        this.id = id;
    }
    public void setAho(int aho){
        this.aho = aho;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pelicula other = (Pelicula) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int compareTo(Pelicula otro){
        int res;
        
        if(this.id == otro.id)
            res = 0;
        else
            if(this.id > otro.id)
                res = 1;
            else 
                res = -1;
        return res;
    }
    
    @Override
    public String toString(){
        StringBuilder cad = new StringBuilder();
        
        cad.append(id);
//        cad.append(id+", "+aho+", "+nombre);
        return cad.toString();
    }

    
}
