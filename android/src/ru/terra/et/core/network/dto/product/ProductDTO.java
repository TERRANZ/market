package ru.terra.et.core.network.dto.product;

import java.util.ArrayList;
import java.util.List;

import ru.terra.et.core.network.dto.photo.PhotoDTO;


public class ProductDTO
{
	public Integer id;
	public String name;
	public Integer rating;
	public Boolean avail;
	public Integer category;
	public boolean ok = true;
	public Integer price;
	public List<PhotoDTO> photos;
}
