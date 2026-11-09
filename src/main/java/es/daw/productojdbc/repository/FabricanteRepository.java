package es.daw.productojdbc.repository;

import es.daw.productojdbc.dto.FabricanteResumen;
import es.daw.productojdbc.model.Fabricante;

import java.util.List;

public interface FabricanteRepository extends GenericRepository<Fabricante, Integer> {

    //RETO 3
    public abstract boolean existsByNombreFabricante(String nombre);
    boolean existsByNombreIgnoreCaseAndCodigoNot(String nombre, Integer id);

    //RETO 1
    //devolver todos los fabricantes y por fab cuantos productos tiene
    //opciones:
    //recorrer lista select * from fabricantes y lanzar select count * nº prod
    //en una sola query
    List<FabricanteResumen> findAllWithNumProductos();

}
