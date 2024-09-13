package com.ushirikeduc.schools.service;

import com.ushirikeduc.schools.model.*;
import com.ushirikeduc.schools.repository.SchoolRepository;
import com.ushirikeduc.schools.repository.SchoolYearRepository;
import com.ushirikeduc.schools.repository.SemesterPeriodRepository;
import com.ushirikeduc.schools.repository.SemesterRepository;
import com.ushirikeduc.schools.requests.PeriodInSemester;
import com.ushirikeduc.schools.requests.SchoolYearDto;
import com.ushirikeduc.schools.requests.SchoolYearResponse;
import com.ushirikeduc.schools.requests.SchoolYearSemesters;
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
    public List<SchoolYearResponse> registerNewSchoolYears(int schoolID, List<SchoolYearDto> schoolYears) {
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
                    .schoolYearStatus(SchoolYearStatus.NOT_STARTED)
                    .schoolYear(schoolYearDto.schoolYear())
                    .startingDate(schoolYearDto.startingDate())
                    .endingDate(schoolYearDto.endingDate())
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
        List<SchoolYearResponse> schoolYearResponses = new ArrayList<>();
        for (SchoolYear schoolYear : savedSchoolYears) {
//            SchoolYearDto schoolYearDto = new SchoolYearDto(
//                    schoolYear.getSchoolYear(),
//                    schoolYear.getStartingDate(),
//                    schoolYear.getEndingDate(),
//                    schoolYear.getSemesters()
//            );
            schoolYearResponses.add(schoolYearResponseListConstruct(schoolYear));
        }
        return schoolYearResponses;
    }

    public SchoolYearResponse schoolYearResponseListConstruct(SchoolYear schoolYear) {
        /*
        *This method takes a schoolYear Object and then construct avery simple format
        * in order to avoid recursive Json issues
        * */

        return new SchoolYearResponse(
                schoolYear.getSchoolYearID(),
                schoolYear.getSchoolYear(),
                schoolYear.getStartingDate() ,
                schoolYear.getEndingDate(),
                schoolYear.getSchoolYearStatus(),
                schoolYearSemesterConstruct(schoolYear.getSemesters())
        );

    }

    private List<SchoolYearSemesters> schoolYearSemesterConstruct(List<Semester> semesters) {
        List<SchoolYearSemesters> schoolYearSemesters = new ArrayList<>();
        for (Semester semester : semesters) {
            SchoolYearSemesters schoolYearSemester = new SchoolYearSemesters(
                    semester.getSemesterID(),
                    semester.getStartingDate(),
                    semester.getEndingDate(),
                    semesterPeriodsConstruct(semester.getSemesterPeriodList())
            );
            schoolYearSemesters.add(schoolYearSemester);
        }

        return  schoolYearSemesters;
    }
    private List<PeriodInSemester> semesterPeriodsConstruct(List<SemesterPeriod> semesterPeriodList) {
        List<PeriodInSemester> periodInSemesters = new ArrayList<>();
        for (SemesterPeriod semesterPeriod: semesterPeriodList) {
            PeriodInSemester periodInSemester = new PeriodInSemester(
                    semesterPeriod.getSemesterPeriodID() ,
                    semesterPeriod.getStartingDate(),
                    semesterPeriod.getEndingDate()
            );
            periodInSemesters.add(periodInSemester);
        }
        return periodInSemesters;
    }


    public List<SchoolYearResponse> getSchoolYearsBySchoolID(int schoolID) {
        // Getting the school
        List<SchoolYearResponse> schoolYearResponseList = new ArrayList<>();
        School school = schoolRepository.findById((long) schoolID)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));
        List<SchoolYear> schoolYearList = school.getSchoolYears();
        for (SchoolYear schoolYear : schoolYearList) {
            schoolYearResponseList.add(schoolYearResponseListConstruct(schoolYear));

        }
        return  schoolYearResponseList;

    }

    public SchoolYearResponse updateSchoolYear(int schoolYearID, SchoolYearDto schoolYearDto) {
        SchoolYear schoolYear = schoolYearRepository.findById(schoolYearID)
                .orElseThrow(()-> new ResourceNotFoundException("School not found"));
        schoolYear.setStartingDate(schoolYearDto.startingDate());
        schoolYear.setEndingDate(schoolYearDto.endingDate());
        schoolYear.setSemesters(schoolYearDto.semesters());
        schoolYear.setSchoolYear(schoolYearDto.schoolYear());

        return schoolYearResponseListConstruct(schoolYearRepository.save(schoolYear));

    }

    public void startSchoolYear(int schoolYearID) {
        SchoolYear schoolYear = schoolYearRepository.findById(schoolYearID)
                .orElseThrow(()-> new ResourceNotFoundException("School not found"));
        schoolYear.setSchoolYearStatus(SchoolYearStatus.PROGRESS);
        schoolYearRepository.save(schoolYear);
    }

    public void stopSchoolYear(int schoolYearID) {
        SchoolYear schoolYear = schoolYearRepository.findById(schoolYearID)
                .orElseThrow(()-> new ResourceNotFoundException("School not found"));
        schoolYear.setSchoolYearStatus(SchoolYearStatus.ENDED);
        schoolYearRepository.save(schoolYear);
    }

    //TODO UPDATE A SCHOOL YEAR !

}
