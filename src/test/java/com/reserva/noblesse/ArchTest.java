package com.reserva.noblesse;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.reserva.noblesse");

        noClasses()
            .that()
            .resideInAnyPackage("com.reserva.noblesse.service..")
            .or()
            .resideInAnyPackage("com.reserva.noblesse.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.reserva.noblesse.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
