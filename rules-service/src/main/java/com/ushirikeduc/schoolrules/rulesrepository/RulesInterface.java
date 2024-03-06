package com.ushirikeduc.schoolrules.rulesrepository;

import com.ushirikeduc.schoolrules.model.Rules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RulesInterface extends JpaRepository<Rules, Integer> {
}
