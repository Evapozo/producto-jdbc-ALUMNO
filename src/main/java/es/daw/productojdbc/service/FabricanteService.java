package es.daw.productojdbc.service;

import es.daw.productojdbc.dto.FabricanteResumen;
import es.daw.productojdbc.exception.FabricanteConProductosException;
import es.daw.productojdbc.exception.FabricanteDuplicadoException;
import es.daw.productojdbc.exception.RecursoNoEncontradoException;
import es.daw.productojdbc.model.Fabricante;
import es.daw.productojdbc.repository.FabricanteRepository;
import es.daw.productojdbc.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //lo mismo que lo comentado
public class FabricanteService {

    private final FabricanteRepository fabricanteRepository;
    private final ProductoRepository productoRepository;

//    public FabricanteService(FabricanteRepository fabricanteRepository,
//                             ProductoRepository productoRepository) {
//        this.fabricanteRepository = fabricanteRepository;
//        this.productoRepository = productoRepository;
//    }

    public List<Fabricante> listar() {
        return fabricanteRepository.findAll();
    }

    //RETO 1.
    public List<FabricanteResumen> findAllWithNumProductos() {

        return fabricanteRepository.findAllWithNumProductos();
    }
    public Fabricante buscarPorCodigo(Integer codigo) {
        return fabricanteRepository.findById(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el fabricante con código " + codigo));
    }

    public Fabricante crear(Fabricante fabricante) {
        //eliminar espacios a izq y derecha
        fabricante.setNombre(fabricante.getNombre().strip());

        //RETO 3
        //NUEVA REGLA DE NEGOCIO: Nombre único
        //antes de crear el fabricante tengo que verificar que no existe ninguno con el mismo nombre

        if (fabricanteRepository.existsByNombreIgnoreCase(fabricante.getNombre())){
            //lanzar excepción propia (crearla)
            throw new FabricanteDuplicadoException(fabricante.getNombre());
        }

        fabricanteRepository.save(fabricante);
        return fabricante;
    }

    public Fabricante actualizar(Fabricante fabricante) {
        //regla de negocio para buscar el codigo de fabricante, si no existe -> exception
        buscarPorCodigo(fabricante.getCodigo());
        // regla para eliminar espacios en el nombre del fabricante
        fabricante.setNombre(fabricante.getNombre().strip());

        //RETO 3
        //Nueva regla de negocio: nombre unico
        //antes de crear el fabricante tengo que verificar que no existe ninguno con el mismo nombre

        if (fabricanteRepository.existsByNombreIgnoreCaseAndCodigoNot(fabricante.getNombre(), fabricante.getCodigo())){
            //lanzar excepción propia (crearla)
            throw new FabricanteDuplicadoException(fabricante.getNombre());
        }
        fabricanteRepository.update(fabricante);
        return fabricante;
    }

    public void borrar(Integer codigo) {
        buscarPorCodigo(codigo);

        // Regla de negocio: comprobada aquí, antes de llegar a la BD
        if (productoRepository.existsByFabricanteCodigo(codigo)) {
            throw new FabricanteConProductosException(codigo);
        }
        fabricanteRepository.delete(codigo);
    }

    //RETO 3
    //ampliar funcionalidad, primero capa crear


}
