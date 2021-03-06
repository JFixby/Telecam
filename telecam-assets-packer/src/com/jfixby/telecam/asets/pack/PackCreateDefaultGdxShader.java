
package com.jfixby.telecam.asets.pack;

import java.io.IOException;

import com.jfixby.psd.unpacker.api.PSDUnpacker;
import com.jfixby.psd.unpacker.core.RedPSDUnpacker;
import com.jfixby.r3.api.shader.srlz.R3_SHADER_SETTINGS;
import com.jfixby.r3.api.shader.srlz.ShaderInfo;
import com.jfixby.r3.api.shader.srlz.ShadersContainer;
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
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.gson.GoogleGson;
import com.jfixby.texture.slicer.api.TextureSlicer;
import com.jfixby.texture.slicer.red.RedTextureSlicer;
import com.jfixby.tools.gdx.texturepacker.GdxTexturePacker;
import com.jfixby.tools.gdx.texturepacker.api.TexturePacker;

public class PackCreateDefaultGdxShader {

	public static void main (final String[] args) throws IOException {

		ScarabeiDesktop.deploy();
		PSDUnpacker.installComponent(new RedPSDUnpacker());
		Json.installComponent(new GoogleGson());
		TexturePacker.installComponent(new GdxTexturePacker());
		TextureSlicer.installComponent(new RedTextureSlicer());
		// TexturePacker.installComponent(new RedTexturePacker());
		pack();

	}

	private static void packShader (final File bank, final File folder) throws IOException {
		final String id_string = folder.getName();
		final ID asset = Names.newID(id_string);
		final File asset_folder = bank.child(id_string);

		final PackerSpecs specs = new PackerSpecs();
		specs.setPackageFolder(asset_folder);

		final FilesList files = folder.listDirectChildren();
		specs.addPackedFiles(files);

		specs.setRootFileName(R3_SHADER_SETTINGS.ROOT_FILE_NAME);

		final File root_file = folder.child(specs.getRootFileName());
		final List<ID> packed = Collections.newList();
		final ShadersContainer container = readInfo(root_file);
		for (final ShaderInfo shader : container.shaders) {
			final ID id_i = Names.newID(shader.shader_id);
			packed.add(id_i);
		}

		specs.setPackedAssets(packed);

		final List<ID> required = Collections.newList();
		specs.setRequiredAssets(required);

		specs.setPackageFormat(StandardPackageFormats.RedTriplane.Shader);
		specs.setVersion("1.0");

		PackageUtils.pack(specs);
	}

	private static ShadersContainer readInfo (final File root_file) throws IOException {
		return IO.deserialize(ShadersContainer.class, root_file.readBytes());
	}

	public static void pack () throws IOException {

		final File bank = LocalFileSystem.ApplicationHome().parent().child("telecam-assets").child("content").child("bank-r3");
		final File shaders = LocalFileSystem.ApplicationHome().child("shaders");
		final FilesList folders_list = shaders.listDirectChildren();
		for (int i = 0; i < folders_list.size(); i++) {
			final File folder = folders_list.getElementAt(i);
			try {
				packShader(bank, folder);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}
