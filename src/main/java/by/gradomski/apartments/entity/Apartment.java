package by.gradomski.apartments.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Apartment {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private long id;
    private String photo;
    private String region;
    private String city;
    private String address;
    private int floor;
    private int rooms;
    private double square;
    private Date year;
    private boolean furniture;
    private String description;
    private User owner;
    private User tenant;
    private ApartmentStatus status;
    private Date registrationDate;
    boolean visibility;

    public Apartment(){};
    public Apartment(long id, User owner, String region, String city, String address){
        this.id = id;
        this.owner = owner;
        this.region = region;
        this.city = city;
        this.address = address;
        status = ApartmentStatus.REGISTERED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public boolean isFurniture() {
        return furniture;
    }

    public void setFurniture(boolean furniture) {
        this.furniture = furniture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

        Apartment apartment = (Apartment) o;

        if (id != apartment.id) return false;
        if (floor != apartment.floor) return false;
        if (rooms != apartment.rooms) return false;
        if (Double.compare(apartment.square, square) != 0) return false;
        if (furniture != apartment.furniture) return false;
        if (photo != null ? !photo.equals(apartment.photo) : apartment.photo != null) return false;
        if (region != null ? !region.equals(apartment.region) : apartment.region != null) return false;
        if (city != null ? !city.equals(apartment.city) : apartment.city != null) return false;
        if (address != null ? !address.equals(apartment.address) : apartment.address != null) return false;
        if (year != null ? !year.equals(apartment.year) : apartment.year != null) return false;
        if (description != null ? !description.equals(apartment.description) : apartment.description != null)
            return false;
        if (owner != null ? !owner.equals(apartment.owner) : apartment.owner != null) return false;
        if (tenant != null ? !tenant.equals(apartment.tenant) : apartment.tenant != null) return false;
        if (status != apartment.status) return false;
        if (visibility != apartment.visibility) return false;
        return registrationDate != null ? registrationDate.equals(apartment.registrationDate) : apartment.registrationDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + floor;
        result = 31 * result + rooms;
        temp = Double.doubleToLongBits(square);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (furniture ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (tenant != null ? tenant.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
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
        builder.append(", region=");
        builder.append(region);
        builder.append(", city=");
        builder.append(city);
        builder.append(", address=");
        builder.append(address);
        builder.append(", floor=");
        builder.append(floor);
        builder.append(", rooms=");
        builder.append(rooms);
        builder.append(", square=");
        builder.append(square);
        builder.append(", year=");
        builder.append(year);
        builder.append(", furniture=");
        builder.append(furniture);
        builder.append(", description=");
        builder.append(description);
        builder.append(", \nowner=");
        builder.append(owner);
        builder.append(", \ntenant=");
        builder.append(tenant);
        builder.append(", status=");
        builder.append(status);
        builder.append(", registrationDate=");
        builder.append(dateFormat.format(registrationDate));
        builder.append(", visibility=");
        builder.append(visibility);
        return "Apartment{" +


                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", tenant=" + tenant +
                ", status=" + status +
                ", registrationDate=" + registrationDate +
                ", visibility=" + visibility +
                '}';
    }
}
