package com.ushirikeduc.schools.requests;

import java.util.List;

public record CommuniqueRegisterRequest(
        String title ,
        String content ,
        String type,
        List<Integer> classConcerned


) {
}
