package com.ushirikeduc.schools.controller;

import com.ushirikeduc.schools.model.Address;

public record SchoolResponse(int id, String name, String postalBox, String email, Address address) {
}
