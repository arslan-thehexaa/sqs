package localstack.aws.sqs.service.publisher;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import localstack.aws.sqs.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SQSEventPublisher {

  private final AmazonSQS amazonSQS;
  private final ObjectMapper objectMapper;

  @Value("${cloud.aws.queue.uri}")
  private String queueUri;

  @Value("${cloud.aws.queue.name}")
  private String queueName;

  public SendMessageResult publishEvent(RequestDto message) {
    log.info("Generating event : {}", message);
    SendMessageResult sendMessageResult = new SendMessageResult();
    try {
      SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(
              queueUri.concat(queueName))
          .withMessageBody(objectMapper.writeValueAsString(message))
          .withMessageGroupId(UUID.randomUUID().toString())
          .withMessageDeduplicationId(UUID.randomUUID().toString());
      sendMessageResult = amazonSQS.sendMessage(sendMessageRequest);
      log.info("Event has been published to SQS.");
    } catch (JsonProcessingException e) {
      log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
    } catch (Exception e) {
      log.error("Exception occurred while pushing event to sqs : {} and stacktrace ; {}",
          e.getMessage(), e);
    }
    return sendMessageResult;
  }
}
