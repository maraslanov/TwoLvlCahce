# Задание
Create a configurable two-level cache (for caching Objects).
Level 1 is memory, level 2 is filesystem. 
Config params should let one specify the cache strategies and max sizes of level 1 and 2.
 
# Описание Решения
Логика работы кэша Cache зависит от двух классов ClientHolder (кэш оперативной памяти) и FileHolder(кэш, данные которого записываются на жесткий диск).
Кэш оперативной памяти включает 2 коллекции HashMap для хранения данных и их ликвидности (не потокобезопасные на текущий момент).
Кэш на жестком диске состоит из коллекций для хранения путей, в которых эти файлы расположены (также HashMap).
Оба кэша имеют параметры размер(кол-во записей), которые можно настроить в конфигурационном файле приложения.
В нем также можно установить как часто данные из оперативной памяти будут перемещаться на жесткий диск. опционально, можно установить любую существующую директорию для хранения файлов. 
Cache может добавлять данные и доставать их (из памяти или с жесткого диска). При добавлении происходит сравнение текущего объема кэша 1 уровня со значением настройки, при превышении объема кэша происходит вытеснение данных на жесткий диск.
Вытесняются все данные имеющие минимальную частоту обращения к данным в кэше 1 уровеня, но и при условии, что объем кэша 2 уровня позволяет добавить новые данные.
При получении данных сначала проверяется их наличии в кэше 1 уровня, при отсутсвии в кэше 2 уровня.

# О приложении
Настройки приложения располагаются в конфигурационном файле application.properties (WEB-INF/classes в war).
Web-приложение написанное с использованием Spring Boot (версия 2.1.0.RELEASE), Swagger(springfox-swagger) для получение инфомрации от веб-прилоежние (пользователь может отправлять Rest запросы для добавления данных в кэш и их получения).
Приложение запускается на порту 8080 по умолчанию, если не указан порт в настройках конфигурационном файле. 
Старт приложение можно производить запуском start.bat. 