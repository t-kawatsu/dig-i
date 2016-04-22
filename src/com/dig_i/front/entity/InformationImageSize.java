package com.dig_i.front.entity;

public enum InformationImageSize implements ImageSize {

	SMALL("s", 48, 48);

	private int width;
	private int height;
	private String name;

	private InformationImageSize(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public String getName() {
		return name;
	}

	public static ImageSize nameOf(String name) {
		for(InformationImageSize is : values()) {
			if(is.getName().equals(name)) {
	    		return is;
	    	}
	    }
	    return null;
	}
}
