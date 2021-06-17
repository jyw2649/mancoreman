package com.rsy0921.mancoreman.recyclerViewAdpter;

public class MenuCustomerReviewDto {

    private String menuName, designerName, description;

    public MenuCustomerReviewDto(String menuName, String designerName, String description) {
        this.menuName = menuName;
        this.designerName = designerName;
        this.description = description;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public String getDescription() {
        return description;
    }
}
