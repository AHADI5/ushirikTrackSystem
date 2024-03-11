package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.Address;
import com.ushirikeduc.schools.model.Director;
import com.ushirikeduc.schools.model.School;

public class SchoolWithDetails {
    private School school;
    private Director director;
    private Address address;
    private Address addressDirector;

    public SchoolWithDetails(School school, Director director, Address address, Address addressDirector) {
        this.school = school;
        this.director = director;
        this.address = address;
        this.addressDirector = addressDirector;
    }


}
