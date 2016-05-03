package com.jfixby.telecam.run.fokker;

import java.util.Vector;

public class Bob {

	@Override
	public String toString() {
		return "Bob [string=" + string + ", test=" + test + "]";
	}

	private String string;

	public Bob() {
	}

	public Bob(String string) {
		this.string = string;
	}

	public Vector<Bob> test = new Vector<Bob>();

	public void add(String string) {
		test.add(new Bob(string));
	}

}
