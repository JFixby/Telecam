package com.jfixby.telecam.run.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.jfixby.android.api.Android;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.red.engine.core.FokkerStarter;
import com.jfixby.red.engine.core.FokkerStarterConfig;
import com.jfixby.redtriplane.fokker.adaptor.GdxAdaptor;
import com.jfixby.telecam.run.android.TelecamAndroidAssembler;

public class TestRunAndroid {
	public static void main(String[] arg) {
		FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();

		Android.installComponent(new AndroidTest());
		
		FokkerEngineAssembler engine_assembler = new TelecamAndroidAssembler();
		config.setEngineAssembler(engine_assembler);

		FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
		UnitsMachineExecutor machine = triplane_starter.getUnitsMachineExecutor();

		GdxAdaptor adaptor = new GdxAdaptor(machine);

		final FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
		cfg.title = "Test";
		cfg.useGL30 = false;
		cfg.width = 600;
		cfg.height = 400;

		ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();

		// gdx_listener = new HttpRequestTest();

		new FokkerLwjglApplication(gdx_listener, cfg);

	}
}
