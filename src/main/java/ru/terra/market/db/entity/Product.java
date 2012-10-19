/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author terranz
 */
@Entity
@Cacheable(true)
@Table(name = "product", catalog = "market", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
		@NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
		@NamedQuery(name = "Product.findByRating", query = "SELECT p FROM Product p WHERE p.rating = :rating"),
		@NamedQuery(name = "Product.findByAvail", query = "SELECT p FROM Product p WHERE p.avail = :avail") })
public class Product implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 512)
	private String name;
	@Basic(optional = false)
	@Column(name = "rating", nullable = false)
	private int rating;
	@Column(name = "avail")
	private Boolean avail;
	@JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private Category category;

	public Product()
	{
	}

	public Product(Integer id)
	{
		this.id = id;
	}

	public Product(Integer id, String name, int rating)
	{
		this.id = id;
		this.name = name;
		this.rating = rating;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getRating()
	{
		return rating;
	}

	public void setRating(int rating)
	{
		this.rating = rating;
	}

	public Boolean getAvail()
	{
		return avail;
	}

	public void setAvail(Boolean avail)
	{
		this.avail = avail;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Product))
		{
			return false;
		}
		Product other = (Product) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ru.terra.market.db.entity.Product[ id=" + id + " ]";
	}

}
