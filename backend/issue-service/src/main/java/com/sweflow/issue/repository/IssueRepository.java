package com.sweflow.issue.repository;

import com.sweflow.issue.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IssueRepository extends JpaRepository<IssueEntity, UUID> {
}
