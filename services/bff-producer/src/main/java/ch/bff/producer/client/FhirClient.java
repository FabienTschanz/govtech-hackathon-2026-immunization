package ch.bff.producer.client;

import org.hl7.fhir.r4.model.Bundle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fhir-server", url = "${FHIR_BASE_URL:http://localhost:9111/ch-vacd-api-reference-server/fhir}")
public interface FhirClient {

    @GetMapping("/Patient")
    Bundle getPatient();

    @GetMapping("/Patient/{id}")
    org.hl7.fhir.r4.model.Patient getPatientById(@PathVariable("id") String id);

    /**
     * Beispiel: https://fhir.ch/ig/ch-vacd/4.0.0/Bundle-1-3-VaccinationRecord.json.html
     * @return Bundle with all vaccinations (VaccinationRecord)
     */
    @GetMapping("/Immunization")
    Bundle getVaccinationRecord(@RequestParam("patient") String patientIamId);

    /**
     * https://fhir.ch/ig/ch-vacd/6.0.0/immunization-administration-document.html
     * Required Sections:
     * - Section: Immunization Administration
     * Required Entries:
     * - Entry: Patient
     * - Entry: Immunization
     * - Entry: Organization
     * - Entry: Practitioner
     * - Entry: PractitionerRole
     *
     * @param immunizationAdministrationBundle
     * @return Bundle with all vaccinations (VaccinationRecord)
     */
    @PostMapping("/Bundle")
    Bundle postImmunizationAdministrationBundle(@RequestBody Bundle immunizationAdministrationBundle);
}
