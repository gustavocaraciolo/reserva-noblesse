package com.reserva.noblesse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Torre.
 */
@Entity
@Table(name = "torre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Torre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @OneToMany(mappedBy = "torre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "torre" }, allowSetters = true)
    private Set<Apartamento> apartamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Torre id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Torre numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Set<Apartamento> getApartamentos() {
        return this.apartamentos;
    }

    public Torre apartamentos(Set<Apartamento> apartamentos) {
        this.setApartamentos(apartamentos);
        return this;
    }

    public Torre addApartamento(Apartamento apartamento) {
        this.apartamentos.add(apartamento);
        apartamento.setTorre(this);
        return this;
    }

    public Torre removeApartamento(Apartamento apartamento) {
        this.apartamentos.remove(apartamento);
        apartamento.setTorre(null);
        return this;
    }

    public void setApartamentos(Set<Apartamento> apartamentos) {
        if (this.apartamentos != null) {
            this.apartamentos.forEach(i -> i.setTorre(null));
        }
        if (apartamentos != null) {
            apartamentos.forEach(i -> i.setTorre(this));
        }
        this.apartamentos = apartamentos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Torre)) {
            return false;
        }
        return id != null && id.equals(((Torre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Torre{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            "}";
    }
}
