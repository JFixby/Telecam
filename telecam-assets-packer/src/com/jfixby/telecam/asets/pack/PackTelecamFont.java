
package com.jfixby.telecam.asets.pack;

import java.io.IOException;

import com.jfixby.rana.api.pkg.StandardPackageFormats;
import com.jfixby.red.engine.core.resources.PackageUtils;
import com.jfixby.red.engine.core.resources.PackerSpecs;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.desktop.ScarabeiDesktop;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FilesList;
import com.jfixby.scarabei.api.file.LocalFileSystem;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;
import com.jfixby.tools.gdx.texturepacker.api.indexed.IndexedCompressor;
import com.jfixby.tools.texturepacker.red.indexed.RedIndexedCompressor;

public class PackTelecamFont {
	static public final String requiredChars = "0123456789:°";

	public static void main (final String[] args) throws IOException {
		if (args != null) {
			ScarabeiDesktop.deploy();
			Json.installComponent(new GoogleGson());
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

		final FilesList files = input_font_folder.listDirectChildren();
		specs.addPackedFiles(files);

		final ID package_name = Names.newID(package_name_string);
		final ID font_name = package_name.child(font_file.nameWithoutExtension());
		final List<ID> packed = Collections.newList();
		packed.add(font_name);
		specs.setRootFileName(target_file_name);
		specs.setPackedAssets(packed);

		specs.setPackageFormat(StandardPackageFormats.libGDX.TTFFont);
		specs.setVersion("1.0");

		final List<ID> required = Collections.newList();
		specs.setRequiredAssets(required);

		PackageUtils.pack(specs);

	}

}
