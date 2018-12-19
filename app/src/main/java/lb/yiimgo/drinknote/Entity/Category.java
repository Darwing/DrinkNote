package lb.yiimgo.drinknote.Entity;

/**
 * Created by Darwing on 15-Dec-18.
 */

public class Category {
    private Integer Id;
    private String Name;
    private Integer Amount;
    private String Category;

/*public Category(Integer Id, String Name, Integer Amount,String Category)
{
    this.Id = Id;
    this.Name = Name;
    this.Amount = Amount;
    this.Category = Category;
}*/

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
