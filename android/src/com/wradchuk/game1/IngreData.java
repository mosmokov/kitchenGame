package com.wradchuk.game1;

import com.wradchuk.game.object.MySprite;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.MyJson;

import org.json.JSONObject;

import java.util.Vector;

public class IngreData {
    private JSONObject[] ingredient  ; // Ингредиенты
    public int allIngre = -1;
    public int allState = 3;

    public MySprite[] pool;
    public int[] state;


    public IngreData(RecipesData recipesData) {

        allIngre = recipesData.idIngre.length;
        Debug.debug(recipesData.idIngre, "Test: ");

        ingredient = new JSONObject[allIngre];

        for(int i = 0; i < allIngre; i++)
            ingredient[i] = MyJson.object("ingredient", recipesData.idIngre[i]);

        pool = loadMySprite();

    }
    public MySprite[] loadMySprite() {

        MySprite[] res;
        MySprite[][] temp = new MySprite[allIngre][allState];
        Vector<MySprite> temp_vec = new Vector<>();
        Vector<Integer> temp_vec1 = new Vector<>();

        for(int i = 0; i < allIngre; i++)
            for(int j = 0; j < allState; j++)  {
                temp[i][j] = new MySprite("date/img/"+MyJson.getStringField(ingredient[i],"состояние_"+j),0,0);
                temp_vec.add(temp[i][j]);
                temp_vec1.add(j);
            }

        res = new MySprite[temp_vec.size()];
        state = new int[temp_vec1.size()];

        for(int i = 0; i < temp_vec.size(); i++) {
            res[i] = temp_vec.elementAt(i);
            state[i] = temp_vec1.elementAt(i);

            }

        return res;
    }

}
