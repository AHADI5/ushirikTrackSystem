package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.SchoolYear;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.repository.SchoolYearRepository;
import com.ushirikeduc.schools.requests.SchoolYearDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record SchoolYearService (
        SchoolYearRepository schoolYearRepository,
        SchoolRepository schoolRepository
){
    public List<SchoolYearDto> registerNewSchoolYears (int schoolID , List<SchoolYearDto> schoolYears) {
        //Getting the school
        School school = schoolRepository.findById((long) schoolID)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        List<SchoolYear> schoolYearList = new ArrayList<>();
        for(SchoolYearDto schoolYearDto : schoolYears) {
            SchoolYear schoolYear = SchoolYear
                    .builder()
                    .startingDate(schoolYearDto.startingDate())
                    .endingDate(schoolYearDto.endingDate())
                    .schoolYear(" " +String.valueOf(schoolYearDto.startingDate().getYear() + " - " +
                            String.valueOf(schoolYearDto.endingDate().getYear())))
                    .semesters(schoolYearDto.semesters())
                    .school(school)
                    .build();
            schoolYearList.add(schoolYear);
        }

        List<SchoolYear> savedSchoolYears =  schoolYearRepository.saveAll(schoolYearList);
        List<SchoolYearDto> schoolYearDtos = new ArrayList<>();
        for (SchoolYear schoolYear : savedSchoolYears) {
            SchoolYearDto schoolYearDto = new SchoolYearDto(
                    schoolYear.getStartingDate(),
                    schoolYear.getEndingDate(),
                    schoolYear.getSemesters()

            );
            schoolYearDtos.add(schoolYearDto);
        }

        return  schoolYearDtos;

    }
}
