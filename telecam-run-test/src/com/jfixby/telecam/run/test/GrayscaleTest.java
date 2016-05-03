package com.jfixby.telecam.run.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jfixby.r3.api.shader.srlz.SHADER_PARAMETERS;
import com.jfixby.telecam.assets.pack.shader.CreateR3Shader_BW;

/**
 * @author davedes
 */
public class GrayscaleTest implements ApplicationListener {

	public static void main(String[] args) {
		FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
		cfg.title = "Gray Test";
		// cfg.useGL20 = true;
		cfg.width = 1024;
		cfg.height = 520;
		cfg.resizable = false;

		new FokkerLwjglApplication(new GrayscaleTest(), cfg);
	}

	final String VERT = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" + "attribute vec4 "
			+ ShaderProgram.COLOR_ATTRIBUTE + ";\n" + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +

	"uniform mat4 u_projTrans;\n" + " \n" + "varying vec4 vColor;\n" + "varying vec2 vTexCoord;\n" +

	"void main() {\n" + "	vColor = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" + "	vTexCoord = "
			+ ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" + "	gl_Position =  u_projTrans * "
			+ ShaderProgram.POSITION_ATTRIBUTE + ";\n" + "}";

	final String FRAG = CreateR3Shader_BW.FRAG;

	SpriteBatch batch;
	Stage stage;
	ShaderProgram shader;
	Texture tex;
	float grayscale = 0f;
	float time;

	@Override
	public void create() {
		// important since we aren't using some uniforms and attributes that
		// SpriteBatch expects
		ShaderProgram.pedantic = false;

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
		batch = new SpriteBatch(1000, shader);
		batch.setShader(shader);

		Viewport vp = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// pass the custom batcher to our stage
		stage = new Stage(vp, batch);

		tex = new Texture(Gdx.files.internal("data/libgdx.png"));

		// add entities/UI to stage...
		Image img1 = new Image(tex);
		img1.setPosition(250, 50);
		stage.addActor(img1);

		Image img2 = new Image(tex);
		img2.setScale(0.5f);
		stage.addActor(img2);
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
		shader.setUniformf(SHADER_PARAMETERS.GRAYSCALE, grayscale);
		shader.setUniformf("asd", 6f);
		shader.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
		tex.dispose();
		stage.dispose();
	}
}
