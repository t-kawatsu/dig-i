package com.dig_i.front.dao;

import com.dig_i.front.entity.ImageSize;

public interface ImageStore {

	public ImageSize[] getImageSizes();

	public String getImageDir(Integer id);

	public String getImageFile(Integer id, ImageSize is);

	public String getTmpImageDir(Integer id);

	public String getTmpImageFile(String fileId, ImageSize is, Integer id);
}
