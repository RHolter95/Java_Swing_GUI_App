public class Book {


    public int id;
    public String title;
    public Double price;

    public Book(int _id, String _title, Double _price ){
    id = _id;
    title = _title;
    price = _price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
