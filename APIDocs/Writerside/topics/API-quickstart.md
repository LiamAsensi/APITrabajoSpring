# Introduccion a la API

<!-- This document describes how to start using your API: authorization, authentication, accessing API resources. -->
En esta sección encontrarás una introducción breve a la API.

## Autenticación

No hace falta autenticarse para usar los servicios de esta API.

## Manejo de las respuestas
La API devuelve, en llamadas exitosas, un JSON con un objeto 'result' que dentro contiene
los datos de la petición, ya sea un array de trabajadores, un único trabajo, una cadena
con un mensaje...

En casos en los que ocurre algun error, se devuelve una propiedad 'errorMessage' con un
mensaje indicando el error específico que ha ocurrido.

En todos los casos, se devuelve una propiedad booleana 'error' que indica si ha ocurrido
un error con la petición o no.

## Siguientes pasos
Explora los endpoints de la API y lo que devuelven o investiga la documentación del uso
de la página web para realizar un CRUD sencillo sobre los datos de la API.