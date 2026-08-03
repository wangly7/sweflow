package com.sweflow.issue.service;

import com.sweflow.common.events.IssueCreatedEvent;
import com.sweflow.issue.dto.CreateIssueRequest;
import com.sweflow.issue.dto.CreateIssueResponse;
import com.sweflow.issue.entity.IssueEntity;
import com.sweflow.issue.entity.IssueStatus;
import com.sweflow.issue.producer.IssueEventProducer;
import com.sweflow.issue.repository.IssueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueEventProducer issueEventProducer;
    private final IssueRepository issueRepository;

    @Transactional
    public CreateIssueResponse createIssue(CreateIssueRequest request) {
        UUID issueId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();

        IssueEntity issue = new IssueEntity();
        issue.setId(issueId);
        issue.setRepository(request.repository());
        issue.setTitle(request.title());
        issue.setDescription(request.description());
        issue.setCreatedBy(request.createdBy());
        issue.setStatus(IssueStatus.CREATED);

        issueRepository.save(issue);

        IssueCreatedEvent event = new IssueCreatedEvent(
                eventId,
                issueId,
                request.title(),
                request.description(),
                request.repository(),
                request.createdBy()
        );

        issueEventProducer.publish(event);
        return  new CreateIssueResponse(issueId, IssueStatus.CREATED);
    }
}
