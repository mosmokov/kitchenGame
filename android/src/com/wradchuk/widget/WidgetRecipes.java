package com.wradchuk.widget;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.wradchuk.main.Core;
import com.wradchuk.object.Scroll;
import com.wradchuk.utils.LogOut;
import com.wradchuk.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class WidgetRecipes {

    private JSONObject jsonScreens;
    public ArrayList<Scroll> scrolls = new ArrayList<>();
    public Table scrollTable;
    private ScrollPane scroller;
    public int isPress = 0;
    public int openID = -1;

    public WidgetRecipes(Core _core, Stage _stage, String _cont) {

        jsonScreens = Utils.read_json("view/recipe/recipe_list.json", Utils.DISCR.INTERNAL);

        try {

            int all = jsonScreens.getJSONObject(_cont).length();

            for(int i = 0; i < all; i++) {
                scrolls.add(new Scroll(_core.skin, jsonScreens.getJSONObject(_cont).getJSONObject("Scroll_"+i)));
                scrolls.get(i).setCap();
            }
        }catch(JSONException e) { LogOut.logEx(e.getMessage()); }

        final Table temp = scrollTable();
        scroller = new ScrollPane(temp);

        scrollTable = new Table();
        scrollTable.setFillParent(true);
        scrollTable.add(scroller).fill().expand();

        _stage.addActor(scrollTable);
    }
    public void render(@NotNull Stage _stage) {
        _stage. act();
        _stage.draw();
        for(int i = 0; i < scrolls.size(); i++)
            scrolls.get(i).scroller(scroller);
    }
    public Table scrollTable() {
        Table res    = new Table();
        int size     = scrolls.size();
        int row      = 0;
        int set_line = 0;
        int all_line = size / 3;
        int set_id   = 0;

        int offset = 15;
        int bottom_off = 270;
        int top_off = 190;
        int lr = 60;

        res.add(scrolls.get(0).scroll_widget).padTop(top_off).padBottom(offset).padLeft(lr).padRight(offset);
        res.add(scrolls.get(1).scroll_widget).padTop(top_off).padBottom(offset).padLeft(offset).padRight(offset);
        res.add(scrolls.get(2).scroll_widget).padTop(top_off).padBottom(offset).padLeft(offset).padRight(lr);
        res.row();

        for(int i = 3; i < size; i++) {
            if(row > 2) {
                res.row();
                row = 0;
                set_line++;
            }

            if(set_line < all_line-1) {

                if(row==0) res.add(scrolls.get(i).scroll_widget).padTop(offset).padBottom(offset).padLeft(lr).padRight(offset);
                if(row==1) res.add(scrolls.get(i).scroll_widget).padTop(offset).padBottom(offset).padLeft(offset).padRight(offset);
                if(row==2) res.add(scrolls.get(i).scroll_widget).padTop(offset).padBottom(offset).padLeft(offset).padRight(lr);

                row++; set_id = i+1;
            }
        }

        if(set_id<size) res.add(scrolls.get(set_id).scroll_widget).padTop(offset).padBottom(bottom_off).padLeft(lr).padRight(offset); set_id++;
        if(set_id<size) res.add(scrolls.get(set_id).scroll_widget).padTop(offset).padBottom(bottom_off).padLeft(offset).padRight(offset); set_id++;
        if(set_id<size) res.add(scrolls.get(set_id).scroll_widget).padTop(offset).padBottom(bottom_off).padLeft(offset).padRight(lr);

        res.row();
        return res;
    }

    public void isOpen() {

        int count = 0;


        for(int i = 0; i < scrolls.size(); i++) if(scrolls.get(i).openFull) count++;


        if(count==0) {
            for(int j = 0; j < scrolls.size(); j++) {
                if (scrolls.get(j).scroll_widget.isPressed()) {
                    isPress++;
                    openID = j;
                }
            }

            LogOut.log("state " + isPress + " openID " + openID);

            if(isPress>0)
                for(int j = 0; j < scrolls.size(); j++)
                    if(j==openID) scrolls.get(j).openFull = true;

            isPress=0;
            openID=-1;
        }




    }
}