package com.jfixby.telecam.run.fokker;

import java.lang.reflect.Field;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class GdxEntryPoint extends ApplicationAdapter {
    SpriteBatch batch;
    private ObjectMap<Application, Array<ShaderProgram>> shaders;

    @Override
    public void create() {
	printRegistry("create");
	batch = new SpriteBatch();

	// getAccessToTheShadersRegistryToPrintItOut();

    }

    private void getAccessToTheShadersRegistryToPrintItOut() {
	try {
	    Class<?> clazz = Class.forName("com.badlogic.gdx.graphics.glutils.ShaderProgram");
	    Field field = clazz.getDeclaredField("shaders");
	    field.setAccessible(true);
	    shaders = (ObjectMap<Application, Array<ShaderProgram>>) field.get(clazz);
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void resize(int width, int height) {
	super.resize(width, height);
	printRegistry("resize");
    }

    @Override
    public void pause() {
	super.pause();
	printRegistry("pause");
    }

    @Override
    public void resume() {
	super.resume();
	printRegistry("resume");
    }

    @Override
    public void dispose() {
	super.dispose();
	printRegistry("dispose");
    }

    byte[] weight = new byte[1024 * 1024 * 10];

    private void printRegistry(String string) {
	l("call: " + string);
	if (shaders == null) {
	    return;
	}
	l("ShadersContainer[" + this.shaders.size + "] :: " + string);
	Array<Application> keys = this.shaders.keys().toArray();
	for (int i = 0; i < this.shaders.size; i++) {
	    Application key = keys.get(i);
	    Array<ShaderProgram> vallue = shaders.get(key);
	    l(" " + key + " :-> " + vallue);
	}
	l("registry size is: " + this.shaders.size);
    }

    private void l(String string) {
	System.out.println("# " + string);
    }

    @Override
    public void render() {
	Gdx.gl.glClearColor(1, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	batch.begin();
	// batch.draw(img, 0, 0);
	batch.end();
    }
}
