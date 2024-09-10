package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClinicTest {

    @Test
    void givenPatientWithMigraine_whenTriagingPatient_thenPatientIsInMedecinList() {

        Clinic clinic = new Clinic(TriageType.FIFO, TriageType.FIFO);

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);


        assertEquals(1, clinic.getMedecin().size());
        assertEquals(0, clinic.getRadiologie().size());
    }

    @Test
    void givenTwoPatientsWithMigraineAndFlu_whenTriagingPatient_thenPatientsIsInMedecinList() {

        Clinic clinic = new Clinic(TriageType.FIFO, TriageType.FIFO);

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);
        clinic.triagePatient("Bob", 1, VisibleSymptom.FLU);

        assertEquals(2, clinic.getMedecin().size());
    }

    @Test
    void givenTwoPatientsWithMigraineAndFlu_whenTriagingPatient_thenPatientsIsInMedecinListAndIsNotInRadiologyList() {

        Clinic clinic = new Clinic(TriageType.FIFO, TriageType.FIFO);

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);
        clinic.triagePatient("Bob", 1, VisibleSymptom.FLU);

        assertEquals(0, clinic.getRadiologie().size());
    }

    @Test
    void givenPatientWithSprain_whenTriagingPatient_thenPatientIsInRadiologyListAndInMedecinList() {

        Clinic clinic = new Clinic(TriageType.FIFO, TriageType.FIFO);

        clinic.triagePatient("John", 4, VisibleSymptom.SPRAIN);

        assertEquals(1, clinic.getMedecin().size());
        assertEquals(1, clinic.getRadiologie().size());
    }

    @Test
    void givenPatientWithHigherPriority_whenTriageWithGravity_thenIsPlacedFirst(){
        Clinic clinic = new Clinic(TriageType.GRAVITY, TriageType.FIFO);

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);
        clinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        Patient firstPatient = clinic.getMedecin().getFirst();
        assertEquals("Bob", firstPatient.name());
    }

    @Test
    void givenPatientWithHigherPriority_whenTriageWithGravity_thenPatientIsPlacedLastInRadiologyListAndFirstInMedecinList(){
        Clinic clinic = new Clinic(TriageType.GRAVITY, TriageType.FIFO);

        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);
        clinic.triagePatient("Jane", 5, VisibleSymptom.BROKEN_BONE);
        clinic.triagePatient("Bob", 7, VisibleSymptom.SPRAIN);

        Patient firstPatient = clinic.getMedecin().getFirst();
        Patient lastPatient = clinic.getRadiologie().getLast();
        assertEquals("Bob", firstPatient.name());
        assertEquals("Bob", lastPatient.name());
    }
}

