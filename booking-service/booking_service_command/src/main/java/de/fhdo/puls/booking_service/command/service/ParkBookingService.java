package de.fhdo.puls.booking_service.command.service;

import de.fhdo.puls.booking_service.command.configuration.BlockchainConfig;
import de.fhdo.puls.booking_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.booking_service.command.domain.ParkBookingAggregate;
import de.fhdo.puls.booking_service.command.exception.ParkBookingAlreadyExistsException;
import de.fhdo.puls.booking_service.command.exception.ParkBookingNotFoundException;
import de.fhdo.puls.booking_service.command.repository.ParkBookingRepository;
import de.fhdo.puls.booking_service.common.commands.CancelParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.CreateParkBookingCommand;
import de.fhdo.puls.booking_service.common.commands.UpdateParkBookingCommand;
import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.Date;


@Service
public class ParkBookingService {

    private final ParkBookingRepository parkBookingRepository;
    private BlockchainConfig blockchainConfig;
    private final DefaultTopicKafkaTemplate<String, ParkBookingCreatedEvent> bookingCreatedEventProducer;
    private final DefaultTopicKafkaTemplate<String, ParkBookingUpdatedEvent> bookingUpdatedEventProducer;
    private final DefaultTopicKafkaTemplate<String, ParkBookingCanceledEvent> bookingCanceledEventProducer;

    @Autowired
    public ParkBookingService(ParkBookingRepository parkBookingRepository,
                              DefaultTopicKafkaTemplate<String, ParkBookingCreatedEvent> bookingCreatedEventProducer,
                              DefaultTopicKafkaTemplate<String, ParkBookingUpdatedEvent> bookingUpdatedEventProducer,
                              DefaultTopicKafkaTemplate<String, ParkBookingCanceledEvent> bookingCanceledEventProducer){
        this.parkBookingRepository = parkBookingRepository;
        this.bookingCreatedEventProducer = bookingCreatedEventProducer;
        this.bookingUpdatedEventProducer = bookingUpdatedEventProducer;
        this.bookingCanceledEventProducer = bookingCanceledEventProducer;
    }


    /*---------------------------------------------------------------------*/
    /*Get aggregates from commands*/
    private ParkBookingAggregate getAggregateFromCreateCommand(CreateParkBookingCommand command){
        return new ParkBookingAggregate(command.getUserId(),
                command.getUserName(),
                command.getUserLastName(),
                command.getParkingSpaceId(),
                command.getParkingCity(),
                command.getPostCode(),
                command.getParkingStreet(),
                command.getParkingStreetNumber(),
                command.getStartOfBooking(),
                command.getEndOfBooking(),
                command.getParkingPricePerHour(),
                command.getParkingPriceTotal());
    }


    private ParkBookingAggregate getAggregateFromUpdateCommand(UpdateParkBookingCommand command){
        var aggregate = parkBookingRepository.getOne(command.getBookingId());
        if (aggregate != null){
            aggregate.setStartOfBooking(command.getStartOfBooking());
            aggregate.setEndOfBooking(command.getEndOfBooking());
            aggregate.setBookingPriceTotal(command.getParkingPriceTotal());
            aggregate.setLastModifiedDate(new Date());
            return aggregate;
        }
        else{
            return null;
        }
    }


    /*---------------------------------------------------------------------*/
    /*Get events from Aggregates*/
    private ParkBookingCreatedEvent createEventFromAggregate(ParkBookingAggregate aggregate){
        return new ParkBookingCreatedEvent(aggregate.getBookingId(),
                aggregate.getUserId(),
                aggregate.getUserName(),
                aggregate.getUserLastName(),
                aggregate.getParkingSpaceId(),
                aggregate.getParkingCity(),
                aggregate.getPostCode(),
                aggregate.getParkingStreet(),
                aggregate.getParkingStreetNumber(),
                aggregate.getBookingCreatedDate(),
                aggregate.getLastModifiedDate(),
                aggregate.getStartOfBooking(),
                aggregate.getEndOfBooking(),
                aggregate.getBookingPricePerHour(),
                aggregate.getBookingPriceTotal());
    }


    private ParkBookingUpdatedEvent updateEventFromAggregate(ParkBookingAggregate aggregate){
        return new ParkBookingUpdatedEvent(aggregate.getBookingId(),
                aggregate.getBookingCreatedDate(),
                aggregate.getLastModifiedDate(),
                aggregate.getStartOfBooking(),
                aggregate.getEndOfBooking(),
                aggregate.getBookingPriceTotal());
    }


    private ParkBookingCanceledEvent canceledEventFromAggregate(ParkBookingAggregate aggregate){
        return new ParkBookingCanceledEvent(aggregate.getBookingId(),true);
    }


    /*---------------------------------------------------------------------*/
    /*Command handling*/
    /* Throws a ParkBookingAlreadyExistsException, if the generated booking-ID is already used */
    public void handleCreateParkBookingCommand(CreateParkBookingCommand command) throws Exception{
        var parkBookingAggregate = getAggregateFromCreateCommand(command);
        validateBookingID(parkBookingAggregate);
        this.parkBookingRepository.save(parkBookingAggregate);

        Web3j web3j = blockchainConfig.buildConnection();
        Credentials credentials = blockchainConfig.getCredentialsFromPrivateKey();
        TransactionManager transactionManager = blockchainConfig.getRawTransactionManager(web3j,credentials);
        ContractGasProvider contractGasProvider = blockchainConfig.getContractGasProvider();

        //ToDo Generate Smart Contract wrappers

        var parkBookingCreatedEvent = createEventFromAggregate(parkBookingAggregate);
        this.bookingCreatedEventProducer.send("ParkBookingCreatedTopic", parkBookingCreatedEvent);
    }


    public void handleUpdateParkBookingCommand(UpdateParkBookingCommand command){
        var parkBookingAggregate = getAggregateFromUpdateCommand(command);
        this.parkBookingRepository.save(parkBookingAggregate);
        var parkBookingUpdatedEvent = updateEventFromAggregate(parkBookingAggregate);
        this.bookingUpdatedEventProducer.send("ParkBookingUpdatedTopic", parkBookingUpdatedEvent);
    }


    /* Throws a ParkBookingNotFoundException, if the booking to be canceled does not exist */
    public void handleCancelParkBookingCommand(CancelParkBookingCommand command)throws Exception{
        if (parkBookingRepository.existsById(command.getBookingId())){
            var parkBookingAggregate = parkBookingRepository.getOne(command.getBookingId());
            parkBookingRepository.deleteById(command.getBookingId());
            var parkBookingCanceledEvent = canceledEventFromAggregate(parkBookingAggregate);
            this.bookingCanceledEventProducer.send("ParkBookingCanceledTopic", parkBookingCanceledEvent);
        }
        else {
            throw new ParkBookingNotFoundException(command.getBookingId());
        }
    }


    /*---------------------------------------------------------------------*/

    private void validateBookingID(ParkBookingAggregate parkBookingAggregate) throws Exception{
        if (parkBookingRepository.existsById(parkBookingAggregate.getBookingId())){
            throw new ParkBookingAlreadyExistsException(parkBookingAggregate.getBookingId());
        }
    }
}
