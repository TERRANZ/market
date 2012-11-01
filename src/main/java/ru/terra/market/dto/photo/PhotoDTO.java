package ru.terra.market.dto.photo;

import ru.terra.market.db.entity.Photo;

public class PhotoDTO
{
	public Integer id;
	public String path;
	public String name;
	public Integer productId;

	public PhotoDTO(Photo p)
	{
		this.id = p.getId();
		this.path = p.getPath();
		this.name = p.getName() != null ? p.getName() : "";
		this.productId = p.getProduct().getId();
	}
}
