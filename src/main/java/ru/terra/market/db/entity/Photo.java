/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "photo", catalog = "market", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p"),
		@NamedQuery(name = "Photo.findById", query = "SELECT p FROM Photo p WHERE p.id = :id"),
		@NamedQuery(name = "Photo.findByPath", query = "SELECT p FROM Photo p WHERE p.path = :path"),
		@NamedQuery(name = "Photo.findByName", query = "SELECT p FROM Photo p WHERE p.name = :name") })
public class Photo implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "path", nullable = false, length = 512)
	private String path;
	@Column(name = "name", length = 128)
	private String name;
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private Product productId;

	public Photo()
	{
	}

	public Photo(Integer id)
	{
		this.id = id;
	}

	public Photo(Integer id, String path)
	{
		this.id = id;
		this.path = path;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Product getProduct()
	{
		return productId;
	}

	public void setProductId(Product productId)
	{
		this.productId = productId;
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
		if (!(object instanceof Photo))
		{
			return false;
		}
		Photo other = (Photo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ru.terra.market.db.entity.controller.Photo[ id=" + id + " ]";
	}

}
