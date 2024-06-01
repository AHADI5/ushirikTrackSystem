package com.ushirikeduc.classservice.dto;

import java.util.HashMap;
import java.util.Map;

public record StudentStatsByLevels(
        HashMap<String ,  Integer> studentStats
) {

}
