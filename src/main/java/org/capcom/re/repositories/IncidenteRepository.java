package org.capcom.re.repositories;

import org.capcom.re.entities.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenteRepository extends JpaRepository<Incidente, Integer> {
}
