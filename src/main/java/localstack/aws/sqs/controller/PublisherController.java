package localstack.aws.sqs.controller;

import localstack.aws.sqs.dto.RequestDto;
import localstack.aws.sqs.service.publisher.SQSEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherController {

    private final SQSEventPublisher sqsEventPublisher;

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(sqsEventPublisher.publishEvent(requestDto).getMessageId());
    }
}
