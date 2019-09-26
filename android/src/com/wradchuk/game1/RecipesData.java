package com.wradchuk.game1;

import com.wradchuk.utils.Debug;
import com.wradchuk.utils.MyJson;

import org.json.JSONObject;

/**
 * Класс для создания структуры рецепта
 */
public class RecipesData {

    private JSONObject recipe  ; // Рецепт блюда
    public String      model   ; // Изображение готового блюда
    public int[]       idIngre ; // Массив индексов ингредиентов
    public String      Name    ; // Название блюда
    public boolean     build   ; // Собирали блюдо или нет?
    public int[]       cost    ; // Стоимость рецепта
    public int[]       kolIngre; // Массив с количеством ингредиентов для готовки
    public int         rank    ; // Ранг блюда
    public String      desc    ; // Краткое описание блюда
    public boolean     open    ; // Доступно для готовки?
    public int         id      ; // Индекс блюа в файле кухни
    public int         category; // Категория блюда
    public String      techno  ; // Технология приготовления

    /**
     * Создать структуру рецепта
     * @param _kitchen название файла кухни
     *                 "dir/" + _kitchen + ".json"
     * @param id_recipe номер рецепта в файле кухни
     */
    public RecipesData(String _kitchen, int id_recipe) {
        
        recipe = MyJson.object(_kitchen, id_recipe);

        
        model    = MyJson.getStringField( recipe,  "модель")     ;
        idIngre  = MyJson.getIntArray(    recipe,  "идИнгред")   ;
        Name     = MyJson.getStringField( recipe,  "блюдо")      ;
        build    = MyJson.getBooleanField(recipe,  "собран")     ;
        cost     = MyJson.getIntArray(    recipe,  "стоимость")  ;
        kolIngre = MyJson.getIntArray(    recipe,  "колИнгред")  ;
        rank     = MyJson.getIntField(    recipe,  "ранг")       ;
        desc     = MyJson.getStringField( recipe,  "описание")   ;
        open     = MyJson.getBooleanField(recipe,  "открыт")     ;
        id       = MyJson.getIntField(    recipe,  "id")         ;
        category = MyJson.getIntField(    recipe,  "категория")  ;
        techno   = MyJson.getStringField( recipe,  "техПриготов");

        recipe = null;

        print();

    }



    /**
     * Для отладки вывожу в консоль
     */
    public void print() {
        Debug.debug("Создана структура рецепта " + Name+ "...");
        Debug.debug("");
        Debug.debug("----------------------------------------------");
        Debug.debug("модель: " + model);
        Debug.debug(idIngre, "идИнгред: ");
        Debug.debug("блюдо: " + Name);
        Debug.debug("собран: " + build);
        Debug.debug(cost, "стоимость: ");
        Debug.debug(kolIngre, "колИнгред: ");
        Debug.debug("ранг: " + rank);
        Debug.debug("описание: " + desc);
        Debug.debug("открыт: " + open);
        Debug.debug("id: " + id);
        Debug.debug("категория: " + category);
        Debug.debug("техПриготов: " + techno);
        Debug.debug("----------------------------------------------");
        Debug.debug("");
    }


}
