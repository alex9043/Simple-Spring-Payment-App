# Simple-Spring-Payment-App
## Запуск приложения
- Необходимо запустить Docker-Desktop
- Создать сеть Docker с именем "SSPA-postgres"
```shell
docker network create sspa-postgress
```
- Запустить docker-compose файл в каталоге проекта
```shell
docker compose up --build
```
- Приложение должно запуститься. Проверить это можно в docker desktop (В разделе Containers рядом с simple-spring-payment-app должен быть зеленый символ)
![img.png](img/img.png)
- Получить доступ к приложению можно по адресу `localhost:8000/api/v1/payments`
## Сменить порт
Для смены порта в файле docker-compose.yml изменить services.backend.ports на {желаемый порт}:8000
## База данных
Сейчас Spring работает в режиме обновления. Это значит, что он будет создавать БД один раз, 
затем сохранять ее и использовать.
Если надо сделать так, чтобы БД уничтожалась каждый раз при новом сеансе, 
надо в файле docker-compose.yml перевести значение `SPRING_JPA_HIBERNATE_DDL_AUTO` в состояние `create-drop`.
Для того, чтобы стереть БД, можно зайти в Docker Desktop, в раздел Volumes, и удалить оба Volume. 
![img.png](img/DeleteVolume.png)
## Проверка работоспособности
### POST
![POSTRequest.png](img/POSTRequest.png)
### GET
![GETRequest.png](img/GETRequest.png)
### GET All no params
![GETAllNP.png](img/GETAllNP.png)
### GET All with params ?page_num=int
![GETALLP.png](img/GETALLP.png)
### DELETE
![DELETERequest.png](img/DELETERequest.png)
### Обработка ошибок
#### Поиск несуществующего платежа
![MissingPayment.png](img/MissingPayment.png)
#### Ошибки валидации при создании платежа
![ValidationErrors.png](img/ValidationErrors.png)
#### Ошибка типа числа (число больше Integer)
![ErrorType.png](img/ErrorType.png)
#### Получение несуществующей ошибки
![NotFound.png](img/NotFound.png)
