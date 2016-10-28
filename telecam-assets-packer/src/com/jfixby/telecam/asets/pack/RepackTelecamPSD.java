
package com.jfixby.telecam.asets.pack;

import com.github.wrebecca.bleed.RebeccaTextureBleeder;
import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileFilter;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.psd.unpacker.api.PSDUnpacker;
import com.jfixby.psd.unpacker.core.RedPSDUnpacker;
import com.jfixby.rana.api.pkg.fs.PackageDescriptor;
import com.jfixby.red.desktop.DesktopSetup;
import com.jfixby.texture.slicer.api.TextureSlicer;
import com.jfixby.texture.slicer.red.RedTextureSlicer;
import com.jfixby.tool.psd2scene2d.PSDRepackSettings;
import com.jfixby.tool.psd2scene2d.PSDRepacker;
import com.jfixby.tool.psd2scene2d.PSDRepackerResult;
import com.jfixby.tool.psd2scene2d.PSDRepackingStatus;
import com.jfixby.tools.bleed.api.TextureBleed;
import com.jfixby.tools.gdx.texturepacker.GdxTexturePacker;
import com.jfixby.tools.gdx.texturepacker.api.TexturePacker;

public class RepackTelecamPSD {
	private static boolean deleteGarbage = false;

	public static void main (final String[] args) {
		DesktopSetup.deploy();
		PSDUnpacker.installComponent(new RedPSDUnpacker());
		Json.installComponent(new RedJson());
		TexturePacker.installComponent(new GdxTexturePacker());
		TextureSlicer.installComponent(new RedTextureSlicer());
		TextureBleed.installComponent(new RebeccaTextureBleeder());

		final File input_folder = LocalFileSystem.ApplicationHome().parent().child("telecam-assets").child("raw").child("psd");
		final FileFilter filter = new FileFilter() {
			@Override
			public boolean fits (final File child) {
				final String name = child.getName().toLowerCase();
				// return name.contains("GameMainUI".toLowerCase())
				// && name.endsWith(".psd");
				return name.endsWith(".psd");
			}
		};
		final ChildrenList psd_files = input_folder.listDirectChildren().filterFiles(filter);
		if (psd_files.size() == 0) {
			L.d("No files found.");
			input_folder.listDirectChildren().print("content");
			Sys.exit();
		}
		psd_files.print("processing");

		final File output_folder = LocalFileSystem.ApplicationHome().parent().child("telecam-assets").child("content")
			.child("bank-telecam");
		output_folder.makeFolder();
		// output_folder.clearFolder();
		final String prefix = "com.jfixby.telecam.";
		for (final File psd_file : psd_files) {

			L.d("------------------------------------------------------------------------------------------");
			String package_name_string = prefix + psd_file.getName().replaceAll(" animated", "").replaceAll("border ", "scene-");
			package_name_string = package_name_string.substring(0, package_name_string.length() - ".psd".length());

			final AssetID package_name = Names.newAssetID(package_name_string);

			final int max_texture_size = (256);
			final int margin = 0;
			final int texturePadding = 16;
			final float imageQuality = 1f;
			final boolean compressAtlases = !true;
			final boolean forceRasterDecomposition = !true;
			final int gemserkPadding = 16;
			L.d("     psd_file", psd_file);
			L.d("output_folder", output_folder);
			L.d(" package_name", package_name_string);
			L.d("max_texture_size", max_texture_size);

			final PSDRepackingStatus status = new PSDRepackingStatus();
			try {

				final boolean ignore_atlas = !true;

				final PSDRepackSettings settings = PSDRepacker.newSettings();

				settings.setPSDFile(psd_file);
				settings.setPackageName(package_name);
				settings.setOutputFolder(output_folder);
				settings.setMaxTextureSize(max_texture_size);
				settings.setMargin(margin);
				settings.setIgonreAtlasFlag(ignore_atlas);
				settings.setGemserkPadding(gemserkPadding);
				settings.setAtlasMaxPageSize(1024);
				settings.setAtlasMinPageSize(1024);

				settings.setPadding(texturePadding);
				settings.setForceRasterDecomposition(forceRasterDecomposition);
				settings.setImageQuality(imageQuality);

				final PSDRepackerResult repackingResult = PSDRepacker.repackPSD(settings, status);

				final Collection<File> atlasPackages = repackingResult.listAtlasPackages();
				if (compressAtlases) {
					compressAtlases(atlasPackages);
				}
			} catch (final Throwable e) {
				e.printStackTrace();
				if (deleteGarbage) {
					final Collection<File> related_folders = status.getRelatedFolders();
					for (final File file : related_folders) {
						file.delete();
						L.d("DELETE", file);
					}
				}
				Sys.exit();

			}
			L.d(" done", package_name_string);

		}

		// PackGdxFileSystem.main(null);

	}

	private static void compressAtlases (final Collection<File> atlasPackages) {
		for (int i = 0; i < atlasPackages.size(); i++) {
			final File atlas_package = atlasPackages.getElementAt(i);
			compressAtlasPackage(atlas_package);
		}
	}

	private static void compressAtlasPackage (final File atlas_package) {
		final File packageDescriptorFile = atlas_package.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
		final File packageContentFolder = atlas_package.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);

	}

}
