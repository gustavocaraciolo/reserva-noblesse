package com.reserva.noblesse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apartamento.
 */
@Entity
@Table(name = "apartamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Apartamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "apartamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apartamento" }, allowSetters = true)
    private Set<Torre> torres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartamento id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Apartamento numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public User getUser() {
        return this.user;
    }

    public Apartamento user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Torre> getTorres() {
        return this.torres;
    }

    public Apartamento torres(Set<Torre> torres) {
        this.setTorres(torres);
        return this;
    }

    public Apartamento addTorre(Torre torre) {
        this.torres.add(torre);
        torre.setApartamento(this);
        return this;
    }

    public Apartamento removeTorre(Torre torre) {
        this.torres.remove(torre);
        torre.setApartamento(null);
        return this;
    }

    public void setTorres(Set<Torre> torres) {
        if (this.torres != null) {
            this.torres.forEach(i -> i.setApartamento(null));
        }
        if (torres != null) {
            torres.forEach(i -> i.setApartamento(this));
        }
        this.torres = torres;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apartamento)) {
            return false;
        }
        return id != null && id.equals(((Apartamento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apartamento{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            "}";
    }
}
