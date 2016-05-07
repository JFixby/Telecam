
package com.jfixby.telecam.asets.pack;

import java.io.IOException;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.io.IO;
import com.jfixby.cmns.api.java.ByteArray;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.redtriplane.fokker.fs.AssetsInfo;
import com.jfixby.redtriplane.fokker.fs.GdxAssetsFileSystemPacker;
import com.jfixby.telecam.assets.cfg.TelecamAssetsConfig;
import com.jfixby.tools.gdx.texturepacker.api.indexed.IndexedCompressor;
import com.jfixby.tools.texturepacker.red.indexed.RedIndexedCompressor;

public class PackTelecamAndroidAssets {

	public static void main (final String[] args) throws IOException {
		if (args != null) {
			DesktopSetup.deploy();
			Json.installComponent(new RedJson());
			IndexedCompressor.installComponent(new RedIndexedCompressor());
		}

		final File input_folder = LocalFileSystem.newFile(TelecamAssetsConfig.PACKED_ASSETS_HOME);
		final File output_folder = LocalFileSystem.newFile(TelecamAssetsConfig.ANDROID_ASSETS_HOME);
		output_folder.makeFolder();
		output_folder.clearFolder();

		final AssetsInfo info = getCurrent();

		GdxAssetsFileSystemPacker.index(input_folder, output_folder);

		final ByteArray data = IO.serialize(info);
		output_folder.child(AssetsInfo.FILE_NAME).writeBytes(data);
		// os.close();
		info.print();

	}

	private static AssetsInfo getCurrent () throws IOException {
		final File info_file = LocalFileSystem.ApplicationHome().child(AssetsInfo.FILE_NAME);
		AssetsInfo info = null;
		if (info_file.exists()) {
			try {
				final ByteArray data = info_file.readBytes();
				info = IO.deserialize(AssetsInfo.class, data);
			} catch (final IOException e) {
				e.printStackTrace();
				info = new AssetsInfo();
			}
		} else {
			info = new AssetsInfo();
		}

		info.next();

		info_file.writeBytes(IO.serialize(info));
		return info;
	}

}
