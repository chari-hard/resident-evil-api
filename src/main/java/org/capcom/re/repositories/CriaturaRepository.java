package org.capcom.re.repositories;

import org.capcom.re.entities.Criatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriaturaRepository extends JpaRepository<Criatura, Integer> {
}
