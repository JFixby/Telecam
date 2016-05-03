package com.jfixby.telecam.run.test;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.shader.fokker.FokkerShaderPackageReader;
import com.jfixby.r3.shader.fokker.ShaderEntry;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.pkg.ResourcesManager;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.red.engine.core.resources.RedAssetsManager;
import com.jfixby.red.triplane.resources.fsbased.RedResourcesManager;

/**
 * @author davedes
 */
public class BlendTest implements ApplicationListener {

    public static void main(String[] args) {
	FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
	cfg.title = "Gray Test";
	// cfg.useGL20 = true;
	cfg.width = 1024;
	cfg.height = 520;
	cfg.resizable = false;

	new FokkerLwjglApplication(new BlendTest(), cfg);
    }

    SpriteBatch batch;

    ShaderProgram shader;
    Texture texture_1;
    float grayscale = 0f;
    float time;

    private Texture texture_2;

    @Override
    public void create() {
	DesktopSetup.deploy();
	RedResourcesManager res_manager = new RedResourcesManager();
	ResourcesManager.installComponent(res_manager);
	Json.installComponent(new RedJson());
	AssetsManager.installComponent(new RedAssetsManager());
	// important since we aren't using some uniforms and attributes that
	// SpriteBatch expects
	ShaderProgram.pedantic = false;

	String shaders_path = "D:\\[DEV]\\[GIT]\\Games\\Telecam\\telecam-assets-packer\\shaders\\com.jfixby.r3.fokker.shader\\r3.shader.info";
	File shader_root_file = LocalFileSystem.newFile(shaders_path);

	FokkerShaderPackageReader reader = new FokkerShaderPackageReader();
	try {
	    reader.readRootFile(shader_root_file);
	} catch (IOException e) {
	    e.printStackTrace();
	    Sys.exit();
	}

	ShaderEntry test_shader_entry = reader
		.findStructure(Names.newAssetID("com.jfixby.r3.fokker.shader.normal"));

	String FRAG = test_shader_entry.getFragmentProgram().getSourceCode();
	String VERT = test_shader_entry.getVertexProgram().getSourceCode();

	shader = new ShaderProgram(VERT, FRAG);

	// shader didn't compile.. handle it somehow
	if (!shader.isCompiled()) {
	    System.err.println(shader.getLog());
	    System.exit(0);
	}

	// incase there were any warnings/info
	if (shader.getLog().length() != 0)
	    System.out.println(shader.getLog());

	// create our own sprite batcher which ALL sprites will use
	batch = new SpriteBatch();

	Viewport vp = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	// pass the custom batcher to our stage

	texture_1 = new Texture(Gdx.files.internal("data/libgdx.png"));
	texture_2 = new Texture(Gdx.files.internal("data/libgdx2.png"));

	// add entities/UI to stage...

    }

    @Override
    public void resize(int width, int height) {
	// stage.setViewport(width, height, true);
    }

    @Override
    public void render() {
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	time += Gdx.graphics.getDeltaTime();
	grayscale = (float) Math.sin(time) / 2f + 0.5f;

	shader.begin();
	shader.setUniformi("u_texture_0_current", 0);
	shader.setUniformi("u_texture_1_original", 1);
	shader.setUniformf("alpha_blend", 0.9f);

	shader.end();

	batch.setShader(shader);

	texture_2.bind(1);
	texture_1.bind(0);

	batch.begin();

	batch.draw(texture_1, 0, 0);
	batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
	batch.dispose();
	shader.dispose();
	texture_1.dispose();

    }
}
