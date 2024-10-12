package com.ushirikeduc.classservice.dto.Communication;

import java.util.List;

public record CommunicationCorrespondents(
        String sectionName  ,
        List<ParentPerLevel> parentPerLevelList

) {
}
