## RETO 2: Añadir a ProductoRepository un método findByFabricanteCodigo(Integer codigoFabricante), 
## su método en el servicio y una página /fabricantes/{codigo}/productos.
ProductoRepository + Jdbc, ProductoService y Fabricante controller (El usuario está "navegando dentro 
de un fabricante" para ver qué productos tiene asignados)
1. Repository:
    a) Añadir el nuevo metodo en el repository:  List<Producto> findByFabricanteCodigo(Integer codigoFabricante);
    b) Implementar el metodo en ProductoRepositoryJdbc: 

3. En el ProductoService:
   public List<Producto> findByFabricanteCodigo(Integer codigoFabricante){
   return productoRepository.findByFabricanteCodigo(codigoFabricante);
   }
4. Controller: 