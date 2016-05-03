package com.jfixby.telecam.run.fokker;

import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;

public class JsonTest {

	public static final void test() {
		L.d("RUNNING TEST");
		Bob bob = new Bob("X");
		bob.add("a");
		bob.add("b");
		bob.add("c");

		String data = Json.serializeToString(bob).toString();
		L.d("SRZ:STRING", data);
		bob = Json.deserializeFromString(Bob.class, data);
		L.d("bob", bob);
//		Sys.exit();
	}

}
