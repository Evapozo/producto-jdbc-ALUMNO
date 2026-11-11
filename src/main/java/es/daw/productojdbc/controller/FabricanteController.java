package es.daw.productojdbc.controller;

import es.daw.productojdbc.dto.FabricanteDto;
import es.daw.productojdbc.exception.FabricanteConProductosException;
import es.daw.productojdbc.exception.FabricanteDuplicadoException;
import es.daw.productojdbc.model.Fabricante;
import es.daw.productojdbc.model.Producto;
import es.daw.productojdbc.service.FabricanteService;
import es.daw.productojdbc.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/fabricantes")
@RequiredArgsConstructor //añade esto porque ha quitado el constructor
public class FabricanteController {

    private final FabricanteService fabricanteService;
    private final ProductoService productoService; //RETO 2
//inyector por constructor el servicio
//    public FabricanteController(FabricanteService fabricanteService) {
//
//        this.fabricanteService = fabricanteService;
//    }

    @GetMapping
    public String listar(Model model) {
//        model.addAttribute("fabricantes", fabricanteService.listar());
        //RETO 1
        model.addAttribute("fabricantes", fabricanteService.findAllWithNumProductos());

        return "fabricantes/lista";

    }


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("fabricanteDto", new FabricanteDto());
        return "fabricantes/formulario";
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model) {
        model.addAttribute("fabricanteDto", FabricanteDto.toDto(fabricanteService.buscarPorCodigo(codigo)));
        //el servicio devulve un model lo que mando a la vista es un dto
        //lo qu erecogo un dto y voy convirtiendo un moelo en dto
        return "fabricantes/formulario";
    }

    //RETO 2
    @GetMapping("/{codigo}/productos")
    //nomenclatura explicita PathVariable("codigo")
    public String productosDelFabricante(@PathVariable Integer codigo, Model model) {
        // 1. Recibir directamente el objeto Fabricante (manda a la vista el objeto completo)
        Fabricante fabricante = fabricanteService.buscarPorCodigo(codigo);

        // 2. Comprobar si es null (si el servicio devuelve null cuando no lo encuentra)
        if (fabricante == null) {
            return "redirect:/fabricantes";
        }

        // 3. Obtener los productos del fabricante
        List<Producto> productos = productoService.findByFabricanteCodigo(codigo);

        // 4. Pasar las variables al modelo (sin usar .get())
        model.addAttribute("fabricante", fabricante);
        model.addAttribute("productos", productos);

        return "fabricantes/productos";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("fabricanteDto") FabricanteDto fabricanteDto, //se llamam igual en formulario
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "fabricantes/formulario";
        }
        try {
            if (fabricanteDto.getCodigo() == null) {
                Fabricante fabricante = fabricanteService.crear(fabricanteDto.toModelEntity(fabricanteDto));
                redirectAttributes.addFlashAttribute("mensaje",
                        "Fabricante creado con código " + fabricanteDto.getCodigo());
            } else {
                Fabricante fabricante = fabricanteService.actualizar(fabricanteDto.toModelEntity(fabricanteDto));
                redirectAttributes.addFlashAttribute("mensaje",
                        "Fabricante " + fabricante.getCodigo() + " actualizado");
            }
        } catch (FabricanteDuplicadoException e) {
            result.rejectValue("nombre", "", e.getMessage());
            return "fabricantes/formulario";//añade esto
        }
        return "redirect:/fabricantes";

    }

    @PostMapping("/borrar/{codigo}")
    public String borrar(@PathVariable Integer codigo,
                         RedirectAttributes redirectAttributes) {
        try {
            fabricanteService.borrar(codigo);
            redirectAttributes.addFlashAttribute("mensaje", "Fabricante " + codigo + " borrado");
        } catch (FabricanteConProductosException e) {
            // Regla de negocio incumplida: avisamos en el listado, no es una página de error
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/fabricantes";
    }


}
