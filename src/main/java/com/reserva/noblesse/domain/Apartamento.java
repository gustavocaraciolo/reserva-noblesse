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
    @JsonIgnoreProperties(value = { "apartamentos" }, allowSetters = true)
    private Torre torre;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_apartamento__user",
        joinColumns = @JoinColumn(name = "apartamento_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

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

    public Torre getTorre() {
        return this.torre;
    }

    public Apartamento torre(Torre torre) {
        this.setTorre(torre);
        return this;
    }

    public void setTorre(Torre torre) {
        this.torre = torre;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Apartamento users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Apartamento addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Apartamento removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
