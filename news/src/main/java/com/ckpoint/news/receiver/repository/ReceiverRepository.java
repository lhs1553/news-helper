package com.ckpoint.news.receiver.repository;

import com.ckpoint.news.receiver.domain.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
}
