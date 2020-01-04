package com.whut.tomasyao.weixin.model.button;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 11:17
 */

public class Button {
    private String name;
    private String type;
    private Button[] sub_button;

    public Button() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Button[] getSub_button() {
        return this.sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
