public class Order {

    public String titles;
    public double totalPrice;
    public Integer orderSize;

    public Order(String _titles, double _totalPrice, Integer _orderSize){
        titles = _titles;
        totalPrice = _totalPrice;
        orderSize = _orderSize;
    }


    public String getTitles() {
        return titles;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Integer getOrderSize() {
        return orderSize;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderSize(Integer orderSize) {
        this.orderSize = orderSize;
    }
}
