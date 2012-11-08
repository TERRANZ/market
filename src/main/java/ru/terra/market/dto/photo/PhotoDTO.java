package ru.terra.market.dto.photo;

import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.User;

public class PhotoDTO
{
	public Integer id;
	public String path;
	public String name;
	public Integer productId;
	public String user;

	public PhotoDTO(Photo p)
	{
		this.id = p.getId();
		this.path = p.getPath();
		this.name = p.getName() != null ? p.getName() : "";
		this.productId = p.getProduct().getId();
		User u = p.getUser();
		if (u != null)
		{
			this.user = u.getLogin();
		}
	}

	public PhotoDTO(Integer productId)
	{
		this.path = "/market/qr?product=" + productId;
		this.productId = productId;
	}
}
