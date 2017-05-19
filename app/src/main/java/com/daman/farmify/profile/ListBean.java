package com.daman.farmify.profile;

/**
 * Created by Daman on 09-05-2017.
 */

public class ListBean {
    int logo;
    String name;
    String hint;

    public ListBean(int logo, String name, String hint) {
        this.logo = logo;
        this.name = name;
        this.hint=hint;
    }

    public int getLogo() {
        return logo;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
