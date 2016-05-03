//package com.jfixby.telecam.run.test;
//
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
//import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
//import com.jfixby.red.engine.core.FokkerStarter;
//import com.jfixby.red.engine.core.FokkerStarterConfig;
//import com.jfixby.redtriplane.fokker.adaptor.GdxAdaptor;
//import com.jfixby.telecam.run.gwt.TelecamGWTAssembler;
//
//public class TestRunGWT {
//	public static void main(String[] arg) {
//		FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();
//
//		FokkerEngineAssembler engine_assembler = new TelecamGWTAssembler();
//		config.setEngineAssembler(engine_assembler);
//
//		FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
//		UnitsMachineExecutor machine = triplane_starter
//				.getUnitsMachineExecutor();
//
//		GdxAdaptor adaptor = new GdxAdaptor(machine);
//
//		final LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
//		cfg.title = "Test";
//		cfg.useGL30 = false;
//		cfg.width = 600;
//		cfg.height = 400;
//
//		ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();
//
//		// gdx_listener = new HttpRequestTest();
//
//		new LwjglApplication(gdx_listener, cfg);
//
//	}
//}
