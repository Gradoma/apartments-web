package by.gradomski.apartments.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Demand {
    private long id;
    private User applicant;
    private long apartmentId;
    private String description;
    private LocalDate expectedDate;
    private DemandStatus status;
    private LocalDateTime creationDate;

    public Demand(){};
    public Demand(User applicant, long apartmentId, LocalDate expectedDate){
        this.applicant = applicant;
        this.apartmentId = apartmentId;
        this.expectedDate = expectedDate;
        status = DemandStatus.CREATED;
        creationDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public DemandStatus getStatus() {
        return status;
    }

    public void setStatus(DemandStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Demand demand = (Demand) o;

        if (id != demand.id) return false;
        if (apartmentId != demand.apartmentId) return false;
        if (applicant != null ? !applicant.equals(demand.applicant) : demand.applicant != null) return false;
        if (description != null ? !description.equals(demand.description) : demand.description != null) return false;
        if (expectedDate != null ? !expectedDate.equals(demand.expectedDate) : demand.expectedDate != null)
            return false;
        if (status != demand.status) return false;
        return creationDate != null ? creationDate.equals(demand.creationDate) : demand.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (applicant != null ? applicant.hashCode() : 0);
        result = 31 * result + (int) (apartmentId ^ (apartmentId >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (expectedDate != null ? expectedDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName());
        builder.append(": ");
        builder.append("id=");
        builder.append(id);
        builder.append(", \napplicant=");
        builder.append(applicant);
        builder.append(", \napartmentId=");
        builder.append(apartmentId);
        builder.append(", description=");
        builder.append(description);
        builder.append(", expectedDate=");
        builder.append(expectedDate);
        builder.append(", creationDate=");
        builder.append(creationDate);
        builder.append(", status=");
        builder.append(status);
        return builder.toString();
    }
}
