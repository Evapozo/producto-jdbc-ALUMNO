package es.daw.productojdbc.mapper;

import es.daw.productojdbc.dto.ProductoDto;
import es.daw.productojdbc.model.Fabricante;
import es.daw.productojdbc.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public static ProductoDto toDto(Producto p) {
        ProductoDto dto = new ProductoDto();
        dto.setCodigo(p.getCodigo());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setCodigoFabricante(p.getFabricante().getCodigo());
        return dto;
    }

    /** Formulario (vista) → Producto (modelo). Del fabricante solo tenemos el código.
     * lo copie y pegue del dto pero añadi el parametro y cambie por get*/
    public Producto toModelEntity(ProductoDto dto) {
        Fabricante fabricante = new Fabricante(dto.getCodigoFabricante(), null);
        return new Producto(dto.getCodigo(), dto.getNombre(), dto.getPrecio(), dto.getStock(), fabricante);
    }
}
/*
toDto:
Convierte un producto de la aplicación en un objeto que puedes utilizar para rellenar un formulario.
Por ejemplo, cuando pulsas editar, recuperas un Producto de la base de datos y lo conviertes
en un ProductoDto para mostrar sus datos en el formulario.

bd -> producto -> dto -> form

toModelEntity:
Convierte los datos que llegan del formulario en un objeto Producto.
Por ejemplo, cuando pulsas guardar, conviertes el ProductoDto en un Producto para
enviarlo al servicio y guardar o actualizar los datos.

form -> dto -> producto -> bd
* */
