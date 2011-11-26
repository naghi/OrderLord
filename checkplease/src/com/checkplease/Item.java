package com.checkplease;

import android.graphics.Bitmap;

public class Item {

	private String name;
	private Double price;
	private Bitmap foodImage;
	
	private boolean selected;

	public Item(String name, Bitmap foodImage, Double price) {
		this.name = name;
		this.price = price;
		this.foodImage = foodImage;
		selected = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	public Bitmap getFoodImage() {
		return foodImage;
	}
	
	public void setFoodImage(Bitmap foodImage) {
		this.foodImage = foodImage;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}