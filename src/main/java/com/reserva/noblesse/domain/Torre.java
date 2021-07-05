package com.reserva.noblesse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "torres" }, allowSetters = true)
    private Apartamento apartamento;

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

    public Apartamento getApartamento() {
        return this.apartamento;
    }

    public Torre apartamento(Apartamento apartamento) {
        this.setApartamento(apartamento);
        return this;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
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
