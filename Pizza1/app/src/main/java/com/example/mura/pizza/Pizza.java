package com.example.mura.pizza;

public class Pizza {
    private String name;
    private int imageRecourceId;

    public static final Pizza[] pizzas = {
            new Pizza("Diavolo",R.drawable.diavolo),
            new Pizza("Funghi",R.drawable.funghi)
    };

    public String getName() {
        return name;
    }

    public int getImageRecourceId() {
        return imageRecourceId;
    }

    public Pizza(String name, int imageRecourceId) {
        this.name = name;
        this.imageRecourceId = imageRecourceId;
    }
}
