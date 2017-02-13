package org.nevack.android.resistorbee;

class ColorCode {
    private int color;
    private String name;
    private float value;

    public ColorCode(int color, String name, float value) {
        this.color = color;
        this.name = name;
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
