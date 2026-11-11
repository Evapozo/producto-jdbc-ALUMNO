package es.daw.productojdbc.mapper;

import es.daw.productojdbc.dto.FabricanteDto;
import es.daw.productojdbc.model.Fabricante;
import org.springframework.stereotype.Component;

@Component
public class FabricanteMapper {

    public static FabricanteDto toDto(Fabricante f) {
        FabricanteDto fabricanteDto = new FabricanteDto();
        fabricanteDto.setCodigo(f.getCodigo());
        fabricanteDto.setNombre(f.getNombre());
        return fabricanteDto;

    }

    /** Formulario (vista) → FAbricante (modelo). Del fabricante solo tenemos el código. */
    public static Fabricante toModelEntity(FabricanteDto f) {

        return new Fabricante(f.getCodigo(), f.getNombre());
    }
}
