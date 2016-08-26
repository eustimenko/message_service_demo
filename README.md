# Message Service Demo Project

## Структура модулей

`ms-domain` - Data Access Layer: содержит модели и репозитории.

`ms-logic` - Business Logic Layer: содержит сервисы, инкапсулирующие всю бизнесс-логику.

`ms-web-api` - Web API Layer: содержит web контроллеры REST API.

`ms-web` - Web Client Layer: содержит приложение на стороне клиента.

`ms-backend` - Вспомогательный модуль, который позволяет собирать бэкенд без фронтеда.

## Зависимости

Перед развёртыванием проекта необходимо установить:

1. Java 1.8
1. Maven 3.3
1. Tomcat 8.0
1. PostgreSQL 9.4

## Сборка

Для сборки достаточно выполнить команду `mvn install` в корневом проекте. 
Затем артифакт `ms-web-0.1.war` задеплоить в App Server.