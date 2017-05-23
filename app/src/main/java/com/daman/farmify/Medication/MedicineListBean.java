package com.daman.farmify.Medication;

/**
 * Created by Daman on 21-05-2017.
 */

public class MedicineListBean {
    int id;
    String medicineName;

    public MedicineListBean(int id, String medicineName) {
        this.id = id;
        this.medicineName = medicineName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    @Override
    public String toString() {
        return "MedicineListBean{" +
                "id=" + id +
                ", medicineName='" + medicineName + '\'' +
                '}';
    }
}
