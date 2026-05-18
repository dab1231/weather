# Weather Viewer

Приложение для просмотра погоды в различных городах. Использует OpenWeatherMap API для получения данных о погоде и отображает их в удобном интерфейсе.

🌐 **Деплой:** http://176.109.110.56:8080/weather

## Стек технологий

- **Java 17**
- **Spring MVC** — веб-фреймворк
- **Hibernate** — ORM
- **PostgreSQL** — база данных
- **Flyway** — миграции БД
- **Thymeleaf** — шаблонизатор
- **Bootstrap 5** — вёрстка
- **OpenWeatherMap API** — данные о погоде
- **Apache Tomcat** — сервер

## Функциональность

- Регистрация и авторизация пользователей
- Поиск городов по названию
- Добавление и удаление локаций
- Просмотр текущей погоды для каждой локации

## Как запустить локально

1. Клонируйте репозиторий
2. Установите PostgreSQL и создайте базу данных
3. Создайте файл `src/main/resources/application.properties` на основе `application.properties.example`
4. Соберите проект: `mvn clean package -DskipTests`
5. Задеплойте `target/weather.war` в Apache Tomcat (папка `webapps`)