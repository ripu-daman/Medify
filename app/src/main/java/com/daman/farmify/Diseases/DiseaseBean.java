package com.daman.farmify.Diseases;

/**
 * Created by Daman on 17-05-2017.
 */

public class DiseaseBean {
    int id;
    String title,content;

    public DiseaseBean() {
    }

    public DiseaseBean(int id,String title, String content) {
        this.id=id;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DiseaseBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
