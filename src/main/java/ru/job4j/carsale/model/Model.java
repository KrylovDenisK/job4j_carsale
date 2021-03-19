package ru.job4j.carsale.model;


import javax.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "body_id")
    private Body body;


    public Model() {
    }

    public static Model of(String name, Brand brand, Body body) {
        Model model = new Model();
        model.name = name;
        model.brand = brand;
        model.body = body;
        return model;
    }

    public Model(String name, Brand brand, Body body) {
        this.name = name;
        this.brand = brand;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return Objects.equals(id, model.id)
                && Objects.equals(name, model.name)
                && Objects.equals(brand, model.brand)
                && Objects.equals(body, model.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, body);
    }
}

