package ru.terra.market.db.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * @author terranz
 */
@Entity
@XmlRootElement
public class Product implements Serializable {
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
    @Column(name = "mincount", nullable = false)
    private int mincount;
    @Column(name = "barcode", length = 256)
    private String barcode;
    @Basic(optional = false)
    @Column(name = "qtype", nullable = false)
    private int qtype;
    @Basic(optional = false)
    @Column(name = "price_in", nullable = false)
    private int priceIn;
    @Basic(optional = false)
    @Column(name = "price_out", nullable = false)
    private int priceOut;
    @Basic(optional = false)
    @Column(name = "rating", nullable = false)
    private int rating;
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;
    @Basic(optional = true)
    @Column(name = "comment", nullable = true, length = 512)
    private String comment;
    @Column(name = "avail")
    private Boolean avail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private List<Photo> photoList;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public int getMincount() {
        return mincount;
    }

    public void setMincount(int mincount) {
        this.mincount = mincount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQtype() {
        return qtype;
    }

    public void setQtype(int qtype) {
        this.qtype = qtype;
    }

    public int getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(int priceIn) {
        this.priceIn = priceIn;
    }

    public int getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(int priceOut) {
        this.priceOut = priceOut;
    }

    public Boolean getAvail() {
        return avail;
    }

    public void setAvail(Boolean avail) {
        this.avail = avail;
    }

}