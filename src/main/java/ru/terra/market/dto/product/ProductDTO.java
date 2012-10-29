package ru.terra.market.dto.product;

import java.util.ArrayList;
import java.util.List;

import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.category.CategoryDTO;
import ru.terra.market.dto.photo.PhotoDTO;

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

	public ProductDTO()
	{
	}

	public ProductDTO(Product p)
	{
		this.id = p.getId();
		this.name = p.getName();
		this.rating = p.getRating();
		this.avail = p.getAvail();
		this.category = p.getCategory().getId();
		this.photos = new ArrayList<PhotoDTO>();
		if (p.getPhotoList() != null)
		{
			for (Photo photo : p.getPhotoList())
			{
				this.photos.add(new PhotoDTO(photo));
			}
		}
	}
}
