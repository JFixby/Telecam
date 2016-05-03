// package com.jfixby.telecam.run.ios;
//
// import org.robovm.apple.foundation.NSAutoreleasePool;
// import org.robovm.apple.uikit.UIApplication;
//
// import com.badlogic.gdx.ApplicationListener;
// import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
// import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
// import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
// import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
// import com.jfixby.red.engine.core.FokkerStarter;
// import com.jfixby.red.engine.core.FokkerStarterConfig;
// import com.jfixby.redtriplane.fokker.adaptor.GdxAdaptor;
//
// public class RunTelecamiOS extends IOSApplication.Delegate {
// @Override
// protected IOSApplication createApplication() {
// FokkerStarterConfig config = FokkerStarter.newRedTriplaneConfig();
//
// FokkerEngineAssembler engine_assembler = new TelecamiOSAssembler();
// config.setEngineAssembler(engine_assembler);
//
// FokkerStarter triplane_starter = FokkerStarter.newRedTriplane(config);
// UnitsMachineExecutor machine = triplane_starter
// .getUnitsMachineExecutor();
//
// GdxAdaptor adaptor = new GdxAdaptor(machine);
//
// IOSApplicationConfiguration cfg = new IOSApplicationConfiguration();
//
// ApplicationListener gdx_listener = adaptor.getGDXApplicationListener();
//
// return new IOSApplication(gdx_listener, cfg);
// }
//
// public static void main(String[] argv) {
// NSAutoreleasePool pool = new NSAutoreleasePool();
// UIApplication.main(argv, null, RunTelecamiOS.class);
// pool.close();
// }
// }
