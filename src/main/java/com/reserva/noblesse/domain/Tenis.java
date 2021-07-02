package com.reserva.noblesse.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tenis.
 */
@Entity
@Table(name = "tenis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tenis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Size(max = 140)
    @Column(name = "notes", length = 140)
    private String notes;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tenis id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Tenis date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return this.notes;
    }

    public Tenis notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return this.user;
    }

    public Tenis user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenis)) {
            return false;
        }
        return id != null && id.equals(((Tenis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tenis{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
