package com.swe.docagent.service;

import com.sweflow.common.enums.WorkflowStepType;
import com.sweflow.common.events.DocJobEvent;
import com.swe.docagent.agents.DesignDocAgent;
import org.springframework.stereotype.Service;

@Service
public class DocAgentDispatcher {
    private final DesignDocAgent designDocAgent;

    public DocAgentDispatcher(DesignDocAgent designDocAgent) {
        this.designDocAgent = designDocAgent;
    }

    public void handle(DocJobEvent event) {
        if (event.stepType() == WorkflowStepType.DESIGN_DOCUMENT) {
            designDocAgent.generate(event);
            return;
        }
        throw new IllegalArgumentException(
                "Unsupported document step type: " + event.stepType()
        );
    }
}
