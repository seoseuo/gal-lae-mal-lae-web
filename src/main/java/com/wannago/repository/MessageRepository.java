package com.wannago.repository;
import java.util.List;
import com.wannago.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByCrIdx(Integer crIdx);
}
