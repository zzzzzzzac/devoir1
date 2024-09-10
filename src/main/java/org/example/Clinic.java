package org.example;
import java.util.LinkedList;


public class Clinic {
    private LinkedList<Patient> medecin;
    private LinkedList<Patient> radiologie;

    private TriageType medecinTriageType;
    private TriageType radiologieTriageType;
    public Clinic(TriageType medecinTriageType, TriageType radiologieTriageType) {
        this.medecin = new LinkedList<>();
        this.radiologie = new LinkedList<>();
        this.medecinTriageType = medecinTriageType;
        this.radiologieTriageType = radiologieTriageType;

    }
    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom){
        Patient patient = new Patient(name, gravity, visibleSymptom);

        if (medecinTriageType == TriageType.GRAVITY)
            addGravity(patient);

        else if (medecinTriageType == TriageType.FIFO) {
            addFifo(patient);
        }
        // ajouter patient au médecin
        medecin.add(patient);

    }

    private void addFifo(Patient patient){
        medecin.addLast(patient);

        // ajouter patient à la radiologie
        if (patient.visibleSymptom() == VisibleSymptom.BROKEN_BONE || patient.visibleSymptom() == VisibleSymptom.SPRAIN){
        radiologie.add(patient);
        }
    }
    
    private void addGravity(Patient patient){

        if (patient.gravity() > 5) {
            boolean inserted = false;
            int i = 0;
            while (i < medecin.size() && !inserted) {
                if (medecin.get(i).gravity() <= patient.gravity()) {
                    medecin.add(i, patient);
                    inserted = true;
                }
                i++;
            }
            if (!inserted) {
                medecin.addLast(patient);
            }
        // si gravity =< 5
        } else {
            medecin.addLast(patient);
        }

        if (patient.visibleSymptom() == VisibleSymptom.BROKEN_BONE || patient.visibleSymptom() == VisibleSymptom.SPRAIN){
            radiologie.add(patient);
        }
    }


    public LinkedList<Patient> getMedecin() {
        return medecin;
    }

    public LinkedList<Patient> getRadiologie() {
        return radiologie;
    }
}

//Les patients sont toujours assignés pour voir le médecin (premier arrivé, premier servi).
//Si le symptôme est BROKEN_BONE ou SPRAIN, alors le patient est également mis dans la file d'attente pour la radiologie (c'est une file d'attente différente)

//Exemple
//Scénario: Celui où un patient se présente à la clinique avec une migraine
//    Lorsqu'un patient se présente avec MIGRAINE (peu importe la gravité)
//    Alors il est le premier dans la file d'attente du médecin
//    Et il n'est pas dans la file d'attente de la radiologie

//Scénario: Celui où 2 patients se présentent à la clinique
//    Étant donné qu'il y a un patient dans la file d'attente du médecin
//    Lorsqu'un patient se présente avec FLU (peu importe la gravité)
//    Alors il est le deuxième dans la file d'attente du médecin
//    Et il n'est pas dans la file d'attente de la radiologie

//Scénario: Celui où un patient se présente à la clinique avec une entorse
//    Lorsqu'un patient se présente avec SPRAIN (peu importe la gravité)
//    Alors il est le premier dans la file d'attente du médecin
//    Et il est la première dans la file d'attente de la radiologie