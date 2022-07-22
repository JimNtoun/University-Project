package edu.acme.model;

public abstract class Basemodel {
    private Long id;

    public Basemodel() {
    }

    public Basemodel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Basemodel{" +
                "id=" + id +
                '}';
    }
}
