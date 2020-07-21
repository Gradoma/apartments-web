package by.gradomski.apartments.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Apartment {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private long id;
    private String region;
    private String city;
    private String address;
    private int floor;
    private int rooms;
    private double square;
    private String year;
    private boolean furniture;
    private String description;
    private User owner;
    private User tenant;
    private ApartmentStatus status;
    private LocalDateTime registrationDate;
    private boolean visibility;
    private List<String> photoList;

    public Apartment(){};
    public Apartment(User owner, String region, String city, String address){
        this(owner, region, city, address, ApartmentStatus.REGISTERED);
    }
    public Apartment(User owner, String region, String city, String address, ApartmentStatus status){
        this.owner = owner;
        this.region = region;
        this.city = city;
        this.address = address;
        this.status = status;
        registrationDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getUnmodifiablePhotoList() {
        return Collections.unmodifiableList(photoList);
    }

    public void setPhoto(List<String> photoList) {
        this.photoList = photoList;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean hasFurniture() {
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
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
        if (visibility != apartment.visibility) return false;
        if (region != null ? !region.equals(apartment.region) : apartment.region != null) return false;
        if (city != null ? !city.equals(apartment.city) : apartment.city != null) return false;
        if (address != null ? !address.equals(apartment.address) : apartment.address != null) return false;
        if (year != null ? !year.equals(apartment.year) : apartment.year != null) return false;
        if (description != null ? !description.equals(apartment.description) : apartment.description != null)
            return false;
        if (owner != null ? !owner.equals(apartment.owner) : apartment.owner != null) return false;
        if (tenant != null ? !tenant.equals(apartment.tenant) : apartment.tenant != null) return false;
        if (status != apartment.status) return false;
        if (registrationDate != null ? !registrationDate.equals(apartment.registrationDate) : apartment.registrationDate != null)
            return false;
        return photoList != null ? photoList.equals(apartment.photoList) : apartment.photoList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
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
        result = 31 * result + (photoList != null ? photoList.hashCode() : 0);
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
        builder.append(", owner=");
        builder.append(owner);
        builder.append(", tenant=");
        builder.append(tenant);
        builder.append(", status=");
        builder.append(status);
        builder.append(", registrationDate=");
        builder.append(registrationDate);
        builder.append(", visibility=");
        builder.append(visibility);
        builder.append(", photoList=");
        builder.append(photoList);
        return builder.toString();
    }
}
