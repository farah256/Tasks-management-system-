package metier.pojo;

public class ListeTache {
    private String idList;
    private String description;

    public ListeTache() {
    }

    public ListeTache(String description) {
        this.description = description;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
