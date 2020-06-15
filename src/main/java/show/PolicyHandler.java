package show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import show.config.kafka.KafkaProcessor;

@Service
public class PolicyHandler{
	
	@Autowired
	PaymentRepository paymentRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBooked_PaymentRequest(@Payload Booked booked){

        if(booked.isMe()){
        	
        	Payment payment = new Payment();
        	payment.setBookId(booked.getId());
//        	payment.setStatus("REQUESTED");
        	payment.setStatus("PAYED");
        	
        	paymentRepository.save(payment);
            
        }
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookingCanceled_PaymentCancel(@Payload BookingCanceled bookingCanceled){

        if(bookingCanceled.isMe()){
        	
        	Payment paymentCanceled = paymentRepository.findByBookId(bookingCanceled.getId());
        	paymentCanceled.setBookId(bookingCanceled.getId());
        	paymentCanceled.setStatus("CANCELED");
        	
//        	paymentRepository.delete(paymentCanceled);
        	paymentRepository.save(paymentCanceled);
        	
        }
    }

}
