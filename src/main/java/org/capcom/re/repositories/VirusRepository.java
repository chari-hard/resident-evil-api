package org.capcom.re.repositories;

import org.capcom.re.entities.Virus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VirusRepository extends JpaRepository<Virus, Integer> {
}
