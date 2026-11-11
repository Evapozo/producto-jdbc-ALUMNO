## RETO 1: Mostrar en el listado de fabricantes cuántos productos tiene cada uno (LEFT JOIN + COUNT + GROUP BY).
SELECT f.codigo, f.nombre, COUNT(p.codigo) AS num_productos
FROM fabricante f
LEFT JOIN producto p ON p.codigo_fabricante = f.codigo
GROUP BY f.codigo, f.nombre
ORDER BY f.nombre

1. Dto: FabricanteResumen con los datos que se necesitan mostrar (la consulta da la pista)
    codigo, nombre y numproductos con @Data y @AllArgsConstructor
2. Repository: añadir el metodo que devuelva una lista de resumenes.
    FabricanteRepository -> List<FabricanteResumen> findAllWithNumProductos();
3. Implementar el metodo en FabricanteRepositoryJdbc (añade la consulta)
4. Servicio: añadir el metodo (return metodo del repositorio)
5. Controlador: modifica el metodo listar para añadir el resumen (porque la consulta tiene codigo y nombre + num)
6. Vista: <th>Número de productos</th><td 
     th:text="${fabricante.numProductos}"></td>
    


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
## RETO 3. Regla de negocio nueva en el servicio: no permitir dos fabricantes con el mismo nombre (sin distinguir mayúsculas).
1. Excepcion propia: exception/FabricanteDuplicadoException.java
2. Repository, crear ambos metodos
3. RepositoryJdbc: implementar los metodos creados
4. Servicio: Crear fabricante -> existsByNombreIgnoreCase(nombre)
   Editar fabricante ->	existsByNombreIgnoreCaseAndCodigoNot(nombre, codigo)
5. GlobalExceptionHandler
6. Controller: controlar la excepcion 


## RETO 4. añadid una columna stock INT NOT NULL a la tabla producto (en schema.sql y data.sql) 
## y llevadla hasta la vista: modelo, repositorio (las 4 consultas y mapRow), formulario y listado. Anotad todos los sitios que habéis tenido que tocar.
1. BD: copiar y pegar los cambios e insert en schema.sql y data.sql (stock not null es obligatoria, 
    entonces daria error de base de datos)
2. Model: añadir el stock a la tabla producto private int stock;
3. Dto: añade stock con validaciones y lo añade en el constructor.
4. ProductoRepositoryJdbc: rs.getInt("stock"), 
    SELECT_BASE  añadir eñ stock y cambiar en las demás querys para añadir el stock, por ejemplo:
    en save "INSERT INTO producto (nombre, precio, stock, codigo_fabricante) VALUES (?, ?, ?, ?)";
    y añade ps.setInt(3, producto.getStock()); cambiando la posición siguiente de 3 a 4 
5. está fallando al guardado -> logger
6. añadir a la vista el stock en productos formulario.html (al editar o actualizar el producto)
    tiene que llegar a la vista el stock de la base de datos, por defecto aparecía 0.
    faltaba esto en el dto form.setStock(p.getStock()); 

## ERRORES
500
Error de BD 
400

## MIGRACION DE MAPPERS.
1. crear clase y directorio mapper, copiar los metodos antiguos y ajustar los parametros.
2. crea en el controller private final y sustituye toDto y toModelEntity
