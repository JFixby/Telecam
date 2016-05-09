
package com.jfixby.telecam.ui.actions;

import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.ui.UIAction;
import com.jfixby.telecam.ui.TelecamUnit;

public class GoAndroidImageGallery implements UIAction<TelecamUnit> {
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
	public void perform (final TelecamUnit ui) {
	}

	@Override
	public boolean isDone (final TelecamUnit ui) {
		Sys.exit();
		return true;
	}
}
