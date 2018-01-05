package AboutYouParser;

class OfferClass {
    private String name;
    private String brand;
    private String price;
    private String initialPrice;

    OfferClass(String name, String brand, String price, String initialPrice) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.initialPrice = initialPrice;
    }

    OfferClass(String name, String brand, String price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getBrand() {
        return brand;
    }

    void setBrand(String brand) {
        this.brand = brand;
    }

    String getPrice() {
        return price;
    }

    void setPrice(String price) {
        this.price = price;
    }

    String getInitialPrice() {
        return initialPrice;
    }

    void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    @Override
    public String toString() {
        return "OfferClass{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", initialPrice='" + initialPrice + '\'' +
                '}';
    }
}
