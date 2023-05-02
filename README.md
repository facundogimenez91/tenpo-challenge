
# Challenge para Tenpo

## Stack utilizado:

- Java 17
- Spring Boot Webflux
- Netty Server
- JUnit y Mockito
- Redis
- Postgres

## Consideraciones generales:

- Se utilizo programacion reactiva para optimizar recursos
- Se considero que el servicio externo que devuelve el porcentaje trae solo el valor que se utilizara para hacer el calculo, no la cuenta en si. La cuenta se hace internamente usando este valor. 
- Se puede activar o desactivar un mock interno del cliente de porcentaje. Si se desea utilizar un mock externo puede usarse por ejemplo https://beeceptor.com/console/tenpochallenge o Mock Server de Postman (más info abajo)
- Se utilizo Redis y el cliente Redisson para guardar un cache por 30 minutos del último valor recuperado (configurable, ver más abajo)
- Si el servicio externo falla pero existe una entrada anterior se utilizara esta y se refrescara el cache por otros 30 minutos más. 
- Si el servicio externo falla y no existe entrada anterior se dara un error.
- El cliente de servicio externo reitenta 3 veces (se puede configurar cantidad de intento y espera de los mismos)
- Se guardan todas las respuestas y peticiones del endpoint de calculo en /record (ver postman)
- El endpoint de calculo tiene un semaforo en Redis para bloquear más de 3 peticiones en simultaneo. 
- La arquitectura es compatible para ser desplegada de forma distribuida.
- Los endpoints son thread-safe
- La base de datos no persiste al volver a iniciar el container, de querer hacerlo montar el volumen correspondiente.

### Configuracion de ejemplo:

```
spring:
  r2dbc: // DB Postgres donde se guardan los records de peticiones y respuestas
    url: r2dbc:postgresql://localhost:5431/challenge 
    username: challenge_app
  data:
    redis: // Redis Cache donde se guarda el valor de porcentaje y el semaforo
      host: localhost
      port: 6379
      expireTime: 1000000 // tiempo de expiracion de todas las keys de Redis (tiene que ser más alto que las properties de abajo)
      keyPrefix: challenge
percentage:
  client: // Cliente del servicio externo de porcentaje
    mockEnable: true // Indica si utiliza un mock interno o no
    mockFixedValue: 20 // Valor de respuesta si se utiliza el mock interno
    url: https://tenpochallenge.free.beeceptor.com // Url del servidor externo
    connectionTimeout: 1000 // timeout
    retries: 3 // cantidad de reintentos
    retriesDelay: 2000 // espera entre re-intento
service:
  percentage:
    ttlInMinutes: 30 // tiempo de validez del cache de porcentaje
  semaphore:
    permits: 3 // cantidad maxima de request en simultaneo
    acquireTimeoutInSec: 2  // tiempo de espera para adquirir el lock del semaforo
```
### Collection:

Se incluye una collection de Postman + Enviroment de ejemplo con todos los endpoints (root folder postman)

### DB Postgres:

Se incluye script para inicializar la DB de Postgres (root folder), el mismo se corre al iniciar el contenedor db

## Como probar el proyecto:

En el root del proyecto ejecutar
```
docker-compose build app
docker-compose up app
```
>>>>>>> a4673e7 (Initial commit)
