package com.wradchuk.utils.net;

import com.wradchuk.utils.ffilesys.CreateFileConfig;

/***
 * Интерфейс для отправки команд на сервер и добавления файлов
 */
public interface AsyncResultPasser {
    void message(String msg);  // В данном методе ловим ответы сервера
    void create_file(CreateFileConfig _config); // Безопасная работа с записью в файл
}
