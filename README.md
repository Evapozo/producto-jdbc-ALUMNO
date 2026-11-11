## RETO 1: se proporcion una sql 


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
4. Controller: FabricanteController   @GetMapping("/{codigo}/productos")
5. Vistas: añadir fabricante/productos.html y rellenar los huecos en interrogación, 
    y en lista.html cambiar el nombre por de la tabla por uno con enlace:
    <td>
    <a th:href="@{/fabricantes/{codigo}/productos(codigo=${f.codigo})}"
    th:text="${f.nombre}">Nombre</a>
    </td>

si da error 500 mirar en la terminal (controller), 
## RETO 3

## RETO 4
1. BD: copiar y pegar los cambios e insert en schema.sql y data.sql (stock not null es obligatoria, 
    entonces daria error de base de datos)
2. Model: añadir el stock a la tabla producto private int stock;
3. Dto: añade stock con validaciones y lo añade en el constructor.
4. ProductoRepositoryJdbc: rs.getInt("stock"), 
    SELECT_BASE  añadir eñ stock y cambiar en las demás querys para añadir el stock, por ejemplo:
    en save "INSERT INTO producto (nombre, precio, stock, codigo_fabricante) VALUES (?, ?, ?, ?)";
    y añade ps.setInt(3, producto.getStock()); cambiando la posición siguiente de 3 a 4 
5. está fallando al guardado -> 
6. añadir a la vista el stock en productos formulario.html (al editar o actualizar el producto)
    tiene que llegar a la vista el stock de la base de datos, por defecto aparecía 0.
    faltaba esto en el dto form.setStock(p.getStock()); 