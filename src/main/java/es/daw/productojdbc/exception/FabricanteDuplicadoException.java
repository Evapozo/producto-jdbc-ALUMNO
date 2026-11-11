package es.daw.productojdbc.exception;

//RETO 3
public class FabricanteDuplicadoException extends RuntimeException {
    public FabricanteDuplicadoException(String nombre) {

        super("Ya existe un fabricante llamado\""+nombre+"\". ");
    }
}
