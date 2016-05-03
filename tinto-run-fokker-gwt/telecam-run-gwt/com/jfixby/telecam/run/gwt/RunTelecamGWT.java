package com.jfixby.telecam.run.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.red.engine.core.FokkerStarter;
import com.jfixby.red.engine.core.FokkerStarterConfig;
import com.jfixby.redtriplane.fokker.adaptor.GdxAdaptor;

public class RunTelecamGWT extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		// return new GwtApplicationConfiguration(1850, 750);
		return new GwtApplicationConfiguration(640, 380);
	}

	@Override
	public ApplicationListener createApplicationListener() {

		FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();

		FokkerEngineAssembler engine_assembler = new TelecamGWTAssembler();
		config.setEngineAssembler(engine_assembler);

		FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
		UnitsMachineExecutor machine = triplane_starter
				.getUnitsMachineExecutor();

		GdxAdaptor adaptor = new GdxAdaptor(machine);
		// HttpRequestTest test = new HttpRequestTest();
		// return test;
		return adaptor;

		// return new TestA();
	}
}