package by.gradomski.apartments.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class Ad {
    private long id;
    private String title;
    private BigDecimal price;
    private long apartmentId;
    private long authorId;
    private LocalDateTime creationDate;
    private boolean visibility;

    public Ad(){};
    public Ad(String title, long authorId, BigDecimal price, long apartmentId){       //TODO(author just id)
        this.title = title;
        this.authorId = authorId;
        this.price = price;
        this.apartmentId = apartmentId;
        creationDate = LocalDateTime.now();
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

    public long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
        if (apartmentId != ad.apartmentId) return false;
        if (authorId != ad.authorId) return false;
        if (visibility != ad.visibility) return false;
        if (title != null ? !title.equals(ad.title) : ad.title != null) return false;
        if (price != null ? !price.equals(ad.price) : ad.price != null) return false;
        return creationDate != null ? creationDate.equals(ad.creationDate) : ad.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (int) (apartmentId ^ (apartmentId >>> 32));
        result = 31 * result + (int) (authorId ^ (authorId >>> 32));
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (visibility ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName());
        builder.append(": ");
        builder.append("id=");
        builder.append(id);
        builder.append(", title=");
        builder.append(title);
        builder.append(", price=");
        builder.append(price);
        builder.append(", apartment id=");
        builder.append(apartmentId);
        builder.append(", \nauthorId=");
        builder.append(authorId);
        builder.append(", creationDate=");
        builder.append(creationDate);
        builder.append(", visibility=");
        builder.append(visibility);
        return builder.toString();
    }
}
