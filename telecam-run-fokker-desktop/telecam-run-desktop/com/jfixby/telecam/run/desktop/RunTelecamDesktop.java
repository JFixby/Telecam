
package com.jfixby.telecam.run.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplication;
import com.badlogic.gdx.backends.lwjgl.FokkerLwjglApplicationConfiguration;
import com.jfixby.r3.engine.core.FokkerStarter;
import com.jfixby.r3.engine.core.FokkerStarterConfig;
import com.jfixby.r3.fokker.adaptor.GdxAdaptor;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;

public class RunTelecamDesktop {
	public static void main (final String[] arg) {

		setupBasicComponents();
		final FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();

		final FokkerEngineAssembler engine_assembler = new TelecamDesktopAssembler();
		config.setEngineAssembler(engine_assembler);

		final FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
		final UnitsMachineExecutor machine = triplane_starter.getUnitsMachineExecutor();

		final GdxAdaptor adaptor = new GdxAdaptor(machine);

		final FokkerLwjglApplicationConfiguration cfg = new FokkerLwjglApplicationConfiguration();
		cfg.title = "Test";
		cfg.useGL30 = false;
		cfg.width = 800;
		cfg.height = 1232;
// cfg.vSyncEnabled = false;
// cfg.r = 1;
// cfg.g = 1;
// cfg.b = 1;
// cfg.a = 1;
// cfg.overrideDensity = 10;
// cfg.foregroundFPS = 60;

		final ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();

		// gdx_listener = new HttpRequestTest();
		// GdxEntryPoint point = new GdxEntryPoint();
		// new LwjglApplication(point, cfg);
		new FokkerLwjglApplication(gdx_listener, cfg);

	}

	private static void setupBasicComponents () {
	}
}
