package com.wradchuk.game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wradchuk.game.object.MySprite;

import java.util.Vector;

public class LogicGame {

    public MySprite[] pool;
    public MySprite[] hlam = new MySprite[5];
    public int[] state;
    public int[] pos_x;
    public int[] pos_y;
    public int step = 100;
    public int start_pos = 0;


    public LogicGame(SpriteBatch _batch, MySprite[] _pool, int _state[]) {
        pool = _pool;
        state = _state;

        for(int i = 0; i < hlam.length; i++)
            hlam[i] = new MySprite("date/img/hlam_"+i+".png", 0 ,0);

        pos_x = new int[state.length+5];
        pos_y = new int[state.length+5];

        gener();

        for(int i = 0; i < pos_x.length; i++) {
            pos_x[i] = -(Gdx.graphics.getWidth()/2)+(step*i);
            pos_y[i] = -520;
            pool[i].position(pos_x[i], pos_y[i]);
            pool[i].setBatch(_batch);
        }

        start_pos = pos_x[0];
    }
    public void draw() {
        for(int i = 0; i < pool.length; i++) {
            move(i);
            pool[i].draw();
        }
    }
    public void gener() {
        Vector<MySprite> temp  = new Vector<>();
        Vector< Integer> temp1 = new Vector<>();

        for(int i = 0; i < pool.length; i++) {
            temp .add(pool [i]);
            temp1.add(state[i]);
        }
        for(int i = 0; i < hlam.length; i++) {
            temp .add(hlam[i]);
            temp1.add(   4   );
        }

        MySprite[] temp_pool  = new MySprite[temp .size()];
        int     [] temp_state = new int     [temp1.size()];

        for(int i = 0; i < temp_pool.length; i++) {
            temp_pool [i] = temp .elementAt(i);
            temp_state[i] = temp1.elementAt(i);
        }

        pool  = temp_pool ;
        state = temp_state;
    }
    public void move(int i) {

        int x = pool[i].getRealX()+3;
            if(x < step*pool.length-7) pool[i].position(x, pos_y[i]);
            else pool[i].setPosX(start_pos-step);
    }
}
