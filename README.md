
В проекте разработан микросервис банковский системы где хранятся лимиты и транзакций. Проект включает:
-Хранений информаций о каждой  расходной операции в разных валютах и проверять не привышает ли транзакция лимита
-Установление и хранение месячного лимит по расходам в долларах(USD) раздельно для двух
категорий расходов: товаров и услуг, где лимит обновляется в начале каждого месяца на установленный лимит. Если не установлен, принимать лимит равным 1000 USD;
- Запрашивание данных биржевых курсов валютных пар KZT/USD, RUB/USD и хранение их в собственной базе данных. И конвертация валют в реальном времени на USD(для проверки не привышает ли транзакция установленного лимита)

Проект реализован в Spring Boot framwork-е и содержит:
-REST API
-База данных для хранений сущностей "PoastgreSql"
-Работа с документацией (внешний API)
-RestTemplate
-Lombok
-Spring Data JPA

API:
CURRENCY:
HTTP Method: GET
URL: http://localhost:0001/currencies

HTTP Method: GET
URL: http://localhost:0001/currencies/{id}

HTTP Method: GET
URL: http://localhost:0001//currencies/name/{name}

HTTP Method: POST
URL: http://localhost:0001/currencies
Request Body: "name": "USD"

HTTP Method: DELETE
URL: http://localhost:0001/currencies/{id}

LIMIT:
Установка лимита
HTTP Method: POST
URL: http://localhost:0001/limits/set
Request Parameters: currencyName=USD limitSum=1000.00 type=PRODUCTS

Получение всех лимитов
HTTP Method: GET
URL: http://localhost:0001/limits/get

Получение лимита по типу(Poduct, service)
HTTP Method: GET
URL: http://localhost:0001/limits/{type}

Удаление лимита по ID
HTTP Method: DELETE
URL: http://localhost:0001/limits/{id}

TRANSACTION:
Добавление транзакции
HTTP Method: POST
URL: http://localhost:0001/transactions/add
Request Parameters: amount=100.00 currencyName=USD type=PRODUCT

Получение всех транзакций
HTTP Method: GET
URL: http://localhost:0001/transactions/all

Получение транзакций по состоянию лимита
HTTP Method: GET
URL: http://localhost:0001/transactions/limit-exceeded?limitExceeded=true

EXCHANGE_RATE
Обновление обменного курса
HTTP Method: GET
URL: http://localhost:0001/api/exchange-rates/update?currencyPair=USD/EUR





