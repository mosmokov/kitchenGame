package com.wradchuk.main;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.wradchuk.utils.Debug;
import com.wradchuk.utils.PatchedAndroidApplication;

public class TestRoom extends PatchedAndroidApplication implements ApplicationListener {

    public PerspectiveCamera cam;
    public CameraInputController camController;
    public Environment environment;
    public ModelBatch modelBatch  ;
    public Model model;
    public ModelInstance instances;
    //public AnimationController controller;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(this, config);
    }
    @Override protected void onPause() {
        super.onPause();
        Gdx.graphics.requestRendering();
    }
    @Override
    public void create() {
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 0);
        cam.lookAt(0,0,0);
        cam.near = 0.01f;
        cam.far = 3000f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        model = Debug.loadModelG3D("Models/room.obj");
        instances = new ModelInstance(model);
        //Debug.debug("isEm: "+instances.animations.first().id);
  //   controller = new AnimationController(instances);
  //   controller.setAnimation("watermelon", -1, new AnimationController.AnimationListener() {
  //      @Override
  //      public void onEnd(AnimationController.AnimationDesc animation) {
  //          Debug.debug("onEnd");
  //      }

  //      @Override
  //      public void onLoop(AnimationController.AnimationDesc animation) {
  //          Debug.debug("onLoop");
  //      }
  //  });
    }
    @Override
    public void resize(int width, int height) {
        Gdx.gl20.glViewport(0,0, width, height);
    }
    @Override
    public void render() {
        Gdx.gl20.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Debug.debug("X: " + cam.position.x + " Y: " +cam.position.y+ " Z: " + cam.position.z);

        camController.update();
        //controller.update(Debug.getTime());

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}