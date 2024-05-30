# Manual de la web

## Página de trabajos

> **URL**: /app/trabajo

La página de trabajos contará con una tabla qué lista todas los Trabajos, mostrando sus datos, trabajadores asignados y 
fecha de inicio y fin en caso de haber sido finalizada.

En el apartado de ‘**Acciones**’ se observan dos botones, el botón de ‘**Eliminar**’ al ser pulsado eliminará de forma 
instantánea el trabajo de la base de datos y el botón de ‘**Editar**’ cargará la página de edición de este Trabajo con sus 
datos cargados en el formulario.

![](lista-trabajos.png)

### Nuevo trabajo

> **URL**: /app/trabajo/new

Por último esta vista contará con un botón de ‘**Nuevo trabajo**’ qué no dirigirá a la página de formulario para 
crear nuestro nuevo trabajo.

![](nuevo-trabajo.png)

### Editar trabajo

> **URL**: /app/trabajo/edit/codTrabajo

La vista de la página de editar Trabajo se vería de la siguiente forma: 

![](edit-trabajo.png)

## Página de trabajadores

> **URL**: /app/trabajador

La página de trabajadores cuenta con otra tabla que muestra la mayoría de los datos de los trabajadores (A excepción de
la contraseña por motivos obvios y de sus trabajos).

En el apartado de '**Acciones**' contamos con los mismos botones que en los trabajos, pero, además, se cuenta con un
enlace (situado en el nombre del trabajador) que lleva al detalle de ese trabajador.

![](lista-trabajadores.png)

### Nuevo trabajador

> **URL**: /app/trabajador/new

El formualrio para añadir un trabajador tiene el siguiente aspecto:

![](nuevo-trabajador.png)

Merece la pena mencionar que todos los campos son necesarios, el email debe de no encontrarse ya en la BBDD y además,
el DNI debe ser correcto (No solo el formato, debe cumplir con la validación del DNI)

### Editar trabajador

> **URL**: /app/trabajador/edit/idTrabajador

La vista para editar un trabajador se vería así:

![](edit-trabajador.png)

### Detalle del trabajador

> **URL**: /app/trabajador/idTrabajador

Como ya se ha dicho, al pulsar en el enlace del nombre de un trabajador, se nos lleva a su página de detalle, donde
podemos ver todos sus datos (A excepción, de nuevo, de su contraseña) y donde además tenemos una vista sencilla de 
los trabajos realizados por dicho trabajador:

![](detalle-trabajador.png)