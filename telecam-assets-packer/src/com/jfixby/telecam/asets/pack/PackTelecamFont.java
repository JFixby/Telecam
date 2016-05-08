
package com.jfixby.telecam.asets.pack;

import java.io.IOException;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.r3.ext.api.font.FontPackage;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.red.engine.core.resources.PackageUtils;
import com.jfixby.red.engine.core.resources.PackerSpecs;
import com.jfixby.tools.gdx.texturepacker.api.indexed.IndexedCompressor;
import com.jfixby.tools.texturepacker.red.indexed.RedIndexedCompressor;

public class PackTelecamFont {
	static public final String requiredChars = "0123456789:°";

	public static void main (final String[] args) throws IOException {
		if (args != null) {
			DesktopSetup.deploy();
			Json.installComponent(new RedJson());
			IndexedCompressor.installComponent(new RedIndexedCompressor());
		}

		final File fonts_folder = LocalFileSystem.ApplicationHome().child("font");
		final File input_font_folder = fonts_folder.child("otf.Arcon-Rounded-Regular");
		final String package_name_string = input_font_folder.getName();

		final String target_file_name = "Arcon-Rounded-Regular.otf";

		final File font_file = input_font_folder.child(target_file_name);

		final File bank_folder = LocalFileSystem.ApplicationHome().parent().child("telecam-assets").child("content")
			.child("bank-telecam");
		final File package_folder = bank_folder.child(package_name_string);

		package_folder.makeFolder();
// package_folder.clearFolder();

		final PackerSpecs specs = new PackerSpecs();
		specs.setPackageFolder(package_folder);

		final ChildrenList files = input_font_folder.listChildren();
		specs.addPackedFiles(files);

		final AssetID package_name = Names.newAssetID(package_name_string);
		final AssetID font_name = package_name.child(font_file.nameWithoutExtension());
		final List<AssetID> packed = Collections.newList();
		packed.add(font_name);
		specs.setRootFileName(target_file_name);
		specs.setPackedAssets(packed);

		specs.setPackageFormat(FontPackage.FONT_PACKAGE_FORMAT_TTF);
		specs.setVersion("1.0");

		final List<AssetID> required = Collections.newList();
		specs.setRequiredAssets(required);

		PackageUtils.pack(specs);

	}

}
