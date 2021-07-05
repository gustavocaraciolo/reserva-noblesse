package com.reserva.noblesse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Espaco.
 */
@Entity
@Table(name = "espaco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Espaco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 140)
    @Column(name = "nome", length = 140)
    private String nome;

    @ManyToMany(mappedBy = "espacos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "espacos" }, allowSetters = true)
    private Set<Reserva> reservas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Espaco id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Espaco nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Reserva> getReservas() {
        return this.reservas;
    }

    public Espaco reservas(Set<Reserva> reservas) {
        this.setReservas(reservas);
        return this;
    }

    public Espaco addReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.getEspacos().add(this);
        return this;
    }

    public Espaco removeReserva(Reserva reserva) {
        this.reservas.remove(reserva);
        reserva.getEspacos().remove(this);
        return this;
    }

    public void setReservas(Set<Reserva> reservas) {
        if (this.reservas != null) {
            this.reservas.forEach(i -> i.removeEspaco(this));
        }
        if (reservas != null) {
            reservas.forEach(i -> i.addEspaco(this));
        }
        this.reservas = reservas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Espaco)) {
            return false;
        }
        return id != null && id.equals(((Espaco) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Espaco{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
