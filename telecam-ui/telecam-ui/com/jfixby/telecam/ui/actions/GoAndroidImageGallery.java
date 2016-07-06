
package com.jfixby.telecam.ui.actions;

import com.jfixby.r3.api.ui.UI;
import com.jfixby.telecam.ui.TelecamUnit;

public class GoAndroidImageGallery extends TelecamUIAction {
	{
// Intent intent = new Intent();
// intent.setAction(Intent.ACTION_VIEW);
// intent.setDataAndType(Uri.parse("file://" + "/sdcard/test.jpg"), "image/*");
// startActivity(intent);
	}

	@Override
	public void start (final TelecamUnit ui) {
	}

	@Override
	public void push (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		UI.pushAction(goPhotoShoot);
		return true;
	}
}
