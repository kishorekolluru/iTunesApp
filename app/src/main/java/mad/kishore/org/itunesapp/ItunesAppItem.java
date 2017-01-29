package mad.kishore.org.itunesapp;

/**
 * Created by kishorekolluru on 10/24/16.
 */

public class ItunesAppItem {
    private String name;
    private String icon;
    private double price;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name :"+name+"; image:"+icon+"; price: "+price;
    }
}
