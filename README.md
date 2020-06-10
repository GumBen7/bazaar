# bazaar
приложение, предоставляющее сервис работы с данными в БД
## Installation
собирать командой
mvn clean compile assembly:single
## Usage
url локальной PostgreSQL бд
"//localhost:5432/bazaar_db"
дамп базы данных лежит в корневой папке bazaar_db
для использования бд программа логинится как пользователь : bazaarman ; с паролем : bazaarman
этот пользователь является собственником бд bazaar_db