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
## Проверка работоспособности
### POST
![img.png](img/POSTRequest.png)
### GET
![img.png](img/GETRequest.png)