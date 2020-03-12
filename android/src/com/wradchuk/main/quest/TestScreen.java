package com.wradchuk.main.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.wradchuk.main.Core;
import com.wradchuk.utils.sys.LogOut;
import com.wradchuk.utils.sys.Utils;

public class TestScreen implements Screen {
    final Core game;


    private final int ALL = 4;
    private Sprite bg;
    private Sprite[] sprite = new Sprite[ALL];



    public TestScreen(final Core _game) {
        game = _game;

        bg = new Sprite(new Texture("quest/test/background.png"));

        for(int i = 0; i < ALL; i++)  sprite[i] = new Sprite(new Texture("quest/test/"+(i+1)+".png"));

       //float ScreeX = game.virtualWidth/(ALL);

       //for(int i = 0; i < ALL; i++) {
       //    sprite[i].setPosition((i*ScreeX), game.virtualHeight-sprite[i].getHeight());
       //    //sprite[i].setPosition((ScreeX-(sprite[i].getWidth())/2)+(i*ScreeX), 0);
       //    LogOut.log(sprite[i].getX()+sprite[i].getWidth()/2 + " " + sprite[i].getY()+sprite[i].getHeight());
       //}

        setPosArraySprite(sprite, game.virtualHeight);



        LogOut.log(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());


        //posPerSprite(5, xz, 0, 50.0f);

    }

    @Override public void show() {}
    @Override public void render(float delta) {
        game.update();

        //for(int i = 0; i < ALL; i++)  game.rotateSprite(sprite[i], 2);
        game.batch.begin();
        game.batch.draw(bg, 0, 0, game.virtualWidth, game.virtualHeight);
        for(int i = 0; i < ALL; i++)  sprite[i].draw(game.batch);
        game.batch.end();



    }
    @Override public void resize(int width, int height) {
        game.resize(width, height);
    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }
    @Override public void dispose() {
        Utils.dispose(game.batch);
        Utils.dispose(bg.getTexture());
        for(int i = 0; i < ALL; i++)  Utils.dispose(sprite[i].getTexture());
    }



    public void setPositionPercent(Sprite _sprite, float _x, float _y) {
        float x = _x*(Gdx.graphics.getWidth()/100.0f)-_sprite.getOriginX();
        float y = _y*(Gdx.graphics.getHeight()/100.0f)-_sprite.getOriginY();
        _sprite.setOrigin(_sprite.getWidth()/2, _sprite.getHeight()/2);
        _sprite.setPosition(x, y);
        LogOut.log(_sprite.getX() + " " + _sprite.getY());
    }


    public void setPosArraySprite(Sprite[] _sprites, float _y) {
        float ScreeX = game.virtualWidth/_sprites.length;

        for(int i = 0; i < _sprites.length; i++) {
            if(_y>game.virtualHeight/2) _sprites[i].setPosition((i*ScreeX), _y-_sprites[i].getHeight());
            else _sprites[i].setPosition((i*ScreeX), _y);
            LogOut.log(_sprites[i].getX()+_sprites[i].getWidth()/2 + " " + _sprites[i].getY()+_sprites[i].getHeight());
        }
    }

    public Vector2 perScreen(float _width, float _heght, int _offsetW) {
        return null; //new Vector2((WIDTH-(_offsetW*2))/100, HEIGHT/100);
    }
    public Vector2 perSprite(Sprite _sprite) {
        return new Vector2(_sprite.getWidth()/100, _sprite.getHeight()/100);
    }

  // public void posPerSprite(int _coll, Sprite[] _sprite, float _perX, float _perY) {
  //     float step = (perScreen.x*100)/_coll;
  //     for(int i = 0; i < _coll; i++) {
  //         if(i==0) {
  //             _sprite[i].setPosition(offset, perScreen.y*_perY-(_sprite[i].getHeight()/2));
  //         }
  //         if(i!= 0 && i!=_coll-1) {
  //             _sprite[i].setPosition(offset+(step*i)-(_sprite[i].getWidth()/2), perScreen.y*_perY-(_sprite[i].getHeight()/2));
  //         }
  //         if(i==_coll-1) {
  //             _sprite[i].setPosition(offset+(perScreen.x*100)-(_sprite[i].getWidth()), perScreen.y*_perY-(_sprite[i].getHeight()/2));
  //         }

  //     }


  // }
}