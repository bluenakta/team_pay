package show;

import show.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBooked_PaymentRequest(@Payload Booked booked){

        if(booked.isMe()){
            System.out.println("##### listener PaymentRequest : " + booked.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookingCanceled_PaymentCancel(@Payload BookingCanceled bookingCanceled){

        if(bookingCanceled.isMe()){
            System.out.println("##### listener PaymentCancel : " + bookingCanceled.toJson());
        }
    }

}
