package com.jfixby.telecam.run.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.red.engine.core.FokkerStarter;
import com.jfixby.red.engine.core.FokkerStarterConfig;
import com.jfixby.redtriplane.fokker.adaptor.GdxAdaptor;

public class RunTelecamGWT {
    public static void main(String[] arg) {
	FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();

	// FokkerEngineAssembler engine_assembler = new TelecamGWTAssembler();
	// config.setEngineAssembler(engine_assembler);

	FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
	UnitsMachineExecutor machine = triplane_starter.getUnitsMachineExecutor();

	GdxAdaptor adaptor = new GdxAdaptor(machine);

	final FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
	cfg.title = "Test";
	cfg.useGL30 = false;
	cfg.width = 1024;
	cfg.height = 500;

	ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();

	new FokkerLwjglApplication(gdx_listener, cfg);
    }
}
