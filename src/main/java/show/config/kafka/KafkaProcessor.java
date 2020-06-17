package show.config.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface KafkaProcessor {

     String INPUT_1 = "event-in1";
     String INPUT_2 = "event-in2";
     String OUTPUT_1 = "event-out1";


     @Input(INPUT_1)
     SubscribableChannel inboundTopic1();

     @Input(INPUT_2)
     SubscribableChannel inboundTopic2();

     @Output(OUTPUT_1)
     MessageChannel outboundTopic1();


}
