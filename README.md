# Micro-Servicio RestFull de usuarios para optar al cargo de ingeniero de software

## Descripción
Este proyecto es una aplicación de Spring Boot 3.0.4 que utiliza Gradle como sistema de construcción. La aplicación utiliza una base de datos H2 y JPA para la persistencia de datos. También se ha implementado autenticación y autorización JWT con algoritmo aleatorio y seguro con HS256.

La aplicación proporciona servicios RESTFull (CRUD) para manejar los datos de usuarios.Teniendo una relación con teléfonos, la relación entre usuarios y teléfonos es de uno a muchos.

También cuenta con pocas pruebas unitarias para las clases encargadas de validar el email, contraseña y creación de JWT.

## Requisitos
- JDK 11 o superior
- Gradle 7.0 o superior

## Instalación
1. Clone el repositorio: `git clone https://github.com/JanoDevG/javaRestService`
2. Navegue hasta el directorio del proyecto: `cd javaRestService`
3. Cambiarse a la rama develop (entendiendo que no es una versión oficial): `git checkout develop`
4. Construya el proyecto: `gradle build`
5. Ejecute el proyecto: `gradle bootRun`

## Endpoints
- `<HOST>/token/login`: retorna un JWT para autorizar las transacciones del dominio `/api` el cual debe ser adjuntado como Bearer, Ej: `Authorization= Bearer <JWT>`
- `<HOST>/api/user`
    - `GET`: devuelve todos los usuarios
    - `GET /user?email=<EMAIL> `: devuelve todos los usuarios
    - `POST`: crea un nuevo usuario
    - `PUT`: actualiza un usuario existente
    - `DELETE`: elimina un usuario existente
## Modelo de request body

El modelo requerido es utilizado para `POST y PUT`:
```json
{
  // RETIRE LOS COMENTARIOS DE ESTE JSON SI LO UTILIZARÁ
  // recuerde que obtendrá un 401 si no adjunta el header: 'Bearer <JWT>' en los headers de cuaqluier petición hacia /api/user.
    "name": "Alejandro",
    "email": "mail@domain.com", // el correo se valida que tenga un formato válido
    "password": "passWOrd123", // la contraseña debe contar con mayúsculas, minúsculas y dos números como mínimo para permitir crearla
    "phones": [
        {
            "number": "11111",
            "citycode": "22222",
            "contrycode": "33333"
        }
    ]
}
```

## Curl's para importar a postman desde localhost

Recuerde reemplazar el JWT a cada petición


- `POST:`
```
curl --location 'localhost:8002/api/user' \
--header 'Authorization: Bearer <JWT>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Alejandro",
    "email": "mail@gmail.com",
    "password": "aASD123sd",
    "phones": [
        {
            "number": "12345",
            "citycode": "123123",
            "contrycode": "123213"
        }
    ]
}'
```
- `PUT:`
```
curl --location 'localhost:8002/api/user?email=<EMAIL>' \
--header 'Authorization: Bearer <JWT>' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Alejandro",
    "email": "jdsaasdsdfdhjf@gmail.com",
    "password": "aASD123sd",
    "phones": [
        {
            "number": "12345",
            "citycode": "123123",
            "contrycode": "123213"
        }
    ]
}'
```
- `GET All:`
```
curl --location 'localhost:8002/api/user' \
--header 'Authorization: Bearer <JWT>'
```
- `GET by email:`
```
curl --location 'localhost:8002/api/user?email=<EMAIL>' \
--header 'Authorization: Bearer <JWT>'
```
- `DELETE:`
```
curl --location --request DELETE 'localhost:8002/api/user?email=<EMAIL>' \
--header 'Authorization: Bearer <JWT>'
```

## Autor
Alejandro Gutiérrez - [correo electrónico](janodevg@outlook.cl)

[LinkedIn](https://www.linkedin.com/in/janodevg/)

## Licencia
Este proyecto está bajo la Licencia Apache License. Ver [LICENSE](LICENSE) para más información.