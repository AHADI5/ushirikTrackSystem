package com.ushirikeduc.disciplineservice.Dto;

import java.util.List;

public record OwnerIncidentsList(
        String name,
        int ownerID,
        List<IncidentResponse> incidents
) {
}
