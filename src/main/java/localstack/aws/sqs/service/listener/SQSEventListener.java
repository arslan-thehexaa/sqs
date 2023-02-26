package localstack.aws.sqs.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import localstack.aws.sqs.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSEventListener {

  private final ObjectMapper objectMapper;

  @SqsListener(value = "${cloud.aws.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void receiveMessage(@Payload String payload, @Headers Map<String, Object> headers)
      throws JsonProcessingException {
    log.info("SQS received headers are: {}", headers);
    RequestDto messageDto = objectMapper.readValue(payload, RequestDto.class);
    log.info("Received id [{}] and message [{}] from SQS", messageDto.getId(),
        messageDto.getMessage());
  }
}
