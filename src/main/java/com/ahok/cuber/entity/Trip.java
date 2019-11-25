package com.ahok.cuber.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Component
@Entity
@Table(name = "trip")
public class Trip implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private Float distance;
    private Date started_at;
    private Date ended_at;
    private Float price;
    private Float estimated_time;
    private String start_location;
    private String end_location;
    private Status status;

    @OneToOne(mappedBy="client")
    private Client client;
    @OneToOne(mappedBy="driver")
    private Driver driver;

    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;

    @Column(columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private Date updated_at;

    @PrePersist
    private void preInsert () {
        this.created_at = new Date();
        this.updated_at = new Date();
    }

    private enum Status {
        CLIENT_WAITING (0),
        CLIENT_PICKED_UP (1),
        COULD_NOT_MEET (2),
        FINISHED (3);

        public int state;

        Status (int state) {
            this.state = state;
        }
    }

    @PreUpdate
    private void preUpdate () {
        this.updated_at = new Date();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id='" + id + '\'' +
                ", distance=" + distance +
                ", started_at=" + started_at +
                ", ended_at=" + ended_at +
                ", price=" + price +
                ", estimated_time=" + estimated_time +
                ", start_location='" + start_location + '\'' +
                ", end_location='" + end_location + '\'' +
                ", status=" + status +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "com.ahok.cuber.util.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Date getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Date started_at) {
        this.started_at = started_at;
    }

    public Date getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Date ended_at) {
        this.ended_at = ended_at;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(Float estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
