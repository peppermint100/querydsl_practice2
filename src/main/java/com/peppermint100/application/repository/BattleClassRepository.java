package com.peppermint100.application.repository;

import com.peppermint100.application.entity.BattleClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleClassRepository extends JpaRepository<BattleClass, Long> {
}
