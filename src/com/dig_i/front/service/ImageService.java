package com.dig_i.front.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dig_i.front.dao.ImageStore;
import com.dig_i.front.entity.ImageSize;
import com.dig_i.front.util.ImageUtil;

@Scope("prototype")
@Service
public class ImageService {

	@Value("#{appProperties[ 'app.image.baseDir' ]}")
	protected String imageBaseAbsolutePath;

	private ImageUtil imageUtil = new ImageUtil();

	public String getImageBaseDir() {
		if (imageBaseAbsolutePath == null) {
			imageBaseAbsolutePath = ServletActionContext.getServletContext()
					.getRealPath("/assets/external/images");
		}
		return imageBaseAbsolutePath;
	}

	public String storeTmpImageFromUrl(ImageStore imageStore, URL url,
			Integer id) throws Exception {

		File dest = File.createTempFile(id + "-" + url.getPath().hashCode(),
				"tmp");
		dest.deleteOnExit();

		InputStream in = url.openStream();
		OutputStream out = new FileOutputStream(dest);
		try {
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		} finally {
			out.close();
			in.close();
		}

		return storeTmpImage(imageStore, dest, id);
	}

	public String storeTmpImage(ImageStore imageStore, File file, Integer id)
			throws Exception {

		String fileId = String.valueOf(System.currentTimeMillis());

		File dir = new File(getImageBaseDir() + imageStore.getTmpImageDir(id));
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		for (ImageSize is : imageStore.getImageSizes()) {
			File destFile = new File(getImageBaseDir()
					+ imageStore.getTmpImageFile(fileId, is, id));
			imageUtil.resize(file, destFile, is.getWidth(), is.getHeight());
		}

		return fileId;
	}

	public boolean clearTmpImageDir(ImageStore imageStore, Integer id) {

		File dir = new File(getImageBaseDir() + imageStore.getTmpImageDir(id));
		if (dir.isDirectory()) {
			return dir.delete();
		}

		return true;
	}

	public void storeImageFromUrl(ImageStore imageStore, URL url, Integer id)
			throws Exception {

		File dest = File.createTempFile(id + "-" + url.getPath().hashCode(),
				"tmp");
		dest.deleteOnExit();

		InputStream in = url.openStream();
		OutputStream out = new FileOutputStream(dest);
		try {
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		} finally {
			out.close();
			in.close();
		}

		storeImage(imageStore, dest, id);
	}

	public void storeImage(ImageStore imageStore, File file, Integer id)
			throws Exception {
		File imageDir = new File(getImageBaseDir() + imageStore.getImageDir(id));
		if (!imageDir.isDirectory()) {
			imageDir.mkdirs();
		}

		for (ImageSize size : imageStore.getImageSizes()) {
			File destFile = new File(getImageBaseDir()
					+ imageStore.getImageFile(id, size));
			imageUtil.resize(file, destFile, size.getWidth(), size.getHeight());
		}
	}
}
