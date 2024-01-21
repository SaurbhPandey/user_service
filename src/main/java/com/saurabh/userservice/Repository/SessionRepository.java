package com.saurabh.userservice.Repository;

import com.saurabh.userservice.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session , Long> {
}
