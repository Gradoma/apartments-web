package by.gradomski.apartments.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Ad {
    private long id;
    private String title;
    private BigDecimal price;
    private Apartment apartment;
    private User author;
    private Date creationDate;
    private boolean visibility;

    public Ad(){};
    public Ad(long id, String title, User author, BigDecimal price, Apartment apartment){
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.apartment = apartment;
        creationDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Apartment getAppartment() {
        return apartment;
    }

    public void setAppartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisible() {
        return visibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ad ad = (Ad) o;

        if (id != ad.id) return false;
        if (title != null ? !title.equals(ad.title) : ad.title != null) return false;
        if (price != null ? !price.equals(ad.price) : ad.price != null) return false;
        if (apartment != null ? !apartment.equals(ad.apartment) : ad.apartment != null) return false;
        if (author != null ? !author.equals(ad.author) : ad.author != null) return false;
        if (visibility != apartment.visibility) return false;
        return creationDate != null ? creationDate.equals(ad.creationDate) : ad.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (visibility ? 1 : 0);
        return result;
    }
}
