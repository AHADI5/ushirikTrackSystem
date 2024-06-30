package com.ushirikeduc.classservice.dto;

public record ReportHomeWorkRequest(
        String  newStatus  ,
        int studentID  ,
        int homeWorkID
) {
}
