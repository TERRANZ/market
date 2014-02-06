package ru.terra.market.dto.product;

import java.util.ArrayList;
import java.util.List;

import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.Product;
import ru.terra.market.dto.CommonDTO;
import ru.terra.market.dto.photo.PhotoDTO;

public class ProductDTO extends CommonDTO {	
	public String name;
	public Integer rating;
	public Boolean avail;
	public Integer category;
	public boolean ok = true;
	public Integer price;
	public List<PhotoDTO> photos;
	public String comment;

	public ProductDTO() {
	}

	public ProductDTO(Product p) {
		this.id = p.getId();
		this.name = p.getName();
		this.rating = p.getRating();
		this.avail = p.getAvail();
		this.category = p.getGroup().getId();
		this.photos = new ArrayList<PhotoDTO>();
		if (p.getPhotoList().size() > 0) {
			for (Photo photo : p.getPhotoList()) {
				this.photos.add(new PhotoDTO(photo));
			}
		} else {
			this.photos.add(new PhotoDTO(this.id));
		}
		this.comment = p.getComment();
		this.price = p.getPriceOut();
	}
}
