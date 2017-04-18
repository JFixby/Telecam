
package com.jfixby.telecam.run.fokker;

import java.util.ArrayList;

public class Bob {

	@Override
	public String toString () {
		return "Bob [string=" + this.string + ", test=" + this.test + "]";
	}

	private String string;

	public Bob () {
	}

	public Bob (final String string) {
		this.string = string;
	}

	public ArrayList<Bob> test = new ArrayList<Bob>();

	public void add (final String string) {
		this.test.add(new Bob(string));
	}

}
