package ru.terra.market.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author terranz
 */
@Entity
@Table(name = "product", catalog = "market", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
		@NamedQuery(name = "Product.findByAvail", query = "SELECT p FROM Product p WHERE p.avail = :avail"),
		@NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
		@NamedQuery(name = "Product.findByRating", query = "SELECT p FROM Product p WHERE p.rating = :rating"),
		@NamedQuery(name = "Product.findByGroup", query = "SELECT p FROM Product p WHERE p.group = :group"),
		@NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price") })
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "avail")
	private Boolean avail;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 512)
	private String name;
	@Basic(optional = false)
	@Column(name = "rating", nullable = false)
	private int rating;
	@Basic(optional = false)
	@Column(name = "price", nullable = false)
	private Integer price;
	@JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private Group group;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
	private List<Photo> photoList;
	@Basic(optional = true)
	@Column(name = "comment", nullable = true, length = 512)
	private String comment;

	public Product() {
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Product(Integer id) {
		this.id = id;
	}

	public Product(Integer id, String name, int rating, int price) {
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAvail() {
		return avail;
	}

	public void setAvail(Boolean avail) {
		this.avail = avail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@XmlTransient
	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Product)) {
			return false;
		}
		Product other = (Product) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ru.terra.market.db.entity.controller.Product[ id=" + id + " ]";
	}

}
