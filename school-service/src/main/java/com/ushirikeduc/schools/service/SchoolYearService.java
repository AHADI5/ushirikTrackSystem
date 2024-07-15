package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.School;
import com.ushirikeduc.schools.model.SchoolYear;
import com.ushirikeduc.schools.model.Semester;
import com.ushirikeduc.schools.model.SemesterPeriod;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.repository.SchoolYearRepository;
import com.ushirikeduc.schools.repository.SemesterPeriodRepository;
import com.ushirikeduc.schools.repository.SemesterRepository;
import com.ushirikeduc.schools.requests.SchoolYearDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public record SchoolYearService (
        SchoolYearRepository schoolYearRepository,
        SchoolRepository schoolRepository ,
        SemesterRepository semesterRepository,
        SemesterPeriodRepository semesterPeriodRepository
){
    public List<SchoolYearDto> registerNewSchoolYears(int schoolID, List<SchoolYearDto> schoolYears) {
        // Getting the school
        School school = schoolRepository.findById((long) schoolID)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        List<SchoolYear> schoolYearList = new ArrayList<>();
        List<Semester> semesterList = new ArrayList<>();
        List<SemesterPeriod> semesterPeriodList = new ArrayList<>();

        for(SchoolYearDto schoolYearDto : schoolYears) {
            for (Semester semester : schoolYearDto.semesters()) {
                for (SemesterPeriod semesterPeriod : semester.getSemesterPeriodList()) {
                    SemesterPeriod semesterPeriodItem = SemesterPeriod
                            .builder()
                            .startingDate(semesterPeriod.getStartingDate())
                            .endingDate(semesterPeriod.getEndingDate())
                            .build();
                    semesterPeriodList.add(semesterPeriodItem);
                }
                // Saving a period
                List<SemesterPeriod> savedSemesterPeriods =  semesterPeriodRepository.saveAll(semesterPeriodList);

                Semester semesterItem = Semester
                        .builder()
                        .startingDate(semester.getStartingDate())
                        .endingDate(semester.getEndingDate())
                        .semesterPeriodList(savedSemesterPeriods)
                        .build();
                semesterList.add(semesterItem);
                //Saving semesters
                Semester savedSemester = semesterRepository.save(semesterItem);

                // TODO: iterate over all periods by setting semester attribute to the semester
                for (SemesterPeriod savedSemesterPeriod : savedSemesterPeriods) {
                    savedSemesterPeriod.setSemester(savedSemester);
                    semesterPeriodRepository.save(savedSemesterPeriod);
                }
//                semesterPeriodRepository.saveAll(savedSemesterPeriods);
            }

            List<Semester>  savedSemesters =  semesterRepository.saveAll(semesterList);

            SchoolYear schoolYear = SchoolYear
                    .builder()
                    .startingDate(schoolYearDto.startingDate())
                    .endingDate(schoolYearDto.endingDate())
                    .schoolYear(" " + String.valueOf(schoolYearDto.startingDate().getYear()) + " - " +
                            String.valueOf(schoolYearDto.endingDate().getYear()))
                    .semesters(savedSemesters)
                    .school(school)
                    .build();
            schoolYearList.add(schoolYear);
            //Saving school Year
            SchoolYear savedSchoolYear = schoolYearRepository.save(schoolYear);

            // TODO: iterate over all semester by setting the schoolYear attribute to schoolYear
            for (Semester savedSemester : savedSemesters) {
                //Setting semester's school year
                savedSemester.setSchoolYear(savedSchoolYear);

                semesterRepository.save(savedSemester);

            }
//            semesterRepository.saveAll(savedSemesters);
        }

        List<SchoolYear> savedSchoolYears = schoolYearRepository.saveAll(schoolYearList);
        List<SchoolYearDto> schoolYearDtos = new ArrayList<>();
        for (SchoolYear schoolYear : savedSchoolYears) {
            SchoolYearDto schoolYearDto = new SchoolYearDto(
                    schoolYear.getStartingDate(),
                    schoolYear.getEndingDate(),
                    schoolYear.getSemesters()
            );
            schoolYearDtos.add(schoolYearDto);
        }
        return schoolYearDtos;
    }


}
