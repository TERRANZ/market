package ru.terra.market.db.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "group", catalog = "market", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Group.findAll", query = "SELECT c FROM Group c"),
		@NamedQuery(name = "Group.findById", query = "SELECT c FROM Group c WHERE c.id = :id"),
		@NamedQuery(name = "Group.findByName", query = "SELECT c FROM Group c WHERE c.name = :name"),
		@NamedQuery(name = "Group.findByParent", query = "SELECT c FROM Group c WHERE c.parent = :parent") })
public class Group implements Serializable {
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name", nullable = false, length = 512)
	private String name;
	@Basic(optional = false)
	@Column(name = "parent", nullable = false)
	private int parent;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
	private List<Product> productList;

	public Group() {
	}

	public Group(Integer id) {
		this.id = id;
	}

	public Group(Integer id, String name, int parent) {
		this.id = id;
		this.name = name;
		this.parent = parent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	@XmlTransient
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Group)) {
			return false;
		}
		Group other = (Group) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ru.terra.market.db.entity.Group[ id=" + id + " ]";
	}
}