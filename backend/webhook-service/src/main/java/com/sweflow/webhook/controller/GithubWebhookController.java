package com.sweflow.webhook.controller;

import com.sweflow.webhook.service.GitHubWebHookService;
import com.sweflow.common.events.GitHubPullRequestEvent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/github")
public class GithubWebhookController {
    private final GitHubWebHookService gitHubWebHookService;

    public GithubWebhookController(GitHubWebHookService gitHubWebHookService) {
        this.gitHubWebHookService = gitHubWebHookService;
    }

    @PostMapping
    public String receiveWebhook(@RequestBody GitHubPullRequestEvent event) {
        gitHubWebHookService.handlePullRequestEvent(event);
        return "ok";
    }
}
