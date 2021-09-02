package myClasses;


public class Book {
    protected String title;
    protected String type;
    protected String description;
    protected double quality;
    protected double price;

    public Book() {
        this("","","",0,0);
    }
    public Book (String title, String type, String description, double quality, double price) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.quality = quality;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getQuality() {
        return quality;
    }

    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String sortAuthorSurname() {
        int index = 0;
        String [] words = this.description.split(" ");
        for(int i=0; i<=words.length-2 ; i++){
            if(words[i].matches("[\\W]*[A-Z][a-z]+") && (words[i+1].matches("[A-Z][a-z]+[\\W]*"))){
                index = i+1;
                break;
            }
        }
        return words[index];
    }

    @Override
    public String toString()
    {
        return title + "(Вид: " + type + ", качество: " + quality + ", цена: " + price + ", описание: " + description + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            return false;
        if(this==obj)
            return true;
        if (!(obj instanceof Book))
            return false;
        Book a = (Book) obj;
        return title.equals(a.title) && type.equals(a.type) && quality == a.quality && price == a.price;
    }

    @Override
    public int hashCode()
    {
        return title.hashCode() + type.hashCode() + description.hashCode() + (int)quality + (int)price;
    }

}
