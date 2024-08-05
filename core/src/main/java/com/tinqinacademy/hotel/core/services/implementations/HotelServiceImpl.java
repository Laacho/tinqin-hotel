package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.enums.BathRoomType;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsInput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.core.services.contracts.HotelService;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Reservation;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BedRepository bedRepository;


    @Override
    public GetFreeRoomsOutput getFreeRooms(GetFreeRoomsInput input) {
        log.info("Start getFreeRooms input: {}", input);
        List<Room> roomsByMe = roomRepository.findRoomsByMe();
        List<Reservation> reservationByEndDateAfter = reservationRepository.findReservationByEndDateAfter(input.getStartDate());
        for (Reservation reservation : reservationByEndDateAfter) {
            roomsByMe.add(reservation.getRoom());
        }
        List<UUID> result = new ArrayList<>();
        for (Room room : roomsByMe) {
            if (room.getBathroomPrototype().equals(BathRoomPrototype.getByCode(input.getBathRoomType().toLowerCase()))) {
                if (room.getBeds().size() == input.getBeds().size()) {
                    List<BedSize> bedSizeList = room.getBeds().stream().map(BedEntity::getBedType).toList();
                    List<String> bedsInput = input.getBeds();
                    boolean allBedSizesMatch=true;
                    for (BedSize bedSize : bedSizeList) {
                        if(!bedsInput.contains(bedSize.getCode())){
                            allBedSizesMatch=false;
                            break;
                        }
                    }
                    if(allBedSizesMatch){
                        result.add(room.getId());
                    }
                }
            }
        }
        GetFreeRoomsOutput output = GetFreeRoomsOutput.builder()
                .ids(result)
                .build();
        log.info("End getFreeRooms output: {}", output);
        return output;
    }

    @Override
    public GetRoomByIDOutput getRoomById(GetRoomByIDInput input) {
        log.info("Start getRoomByID input: {}", input);
        Optional<Room> result = roomRepository.findRoomById(input.getRoomID());
        if (result.isEmpty()) {
            throw new InvalidRoomByIdExceptions("Room with this id: " + input.getRoomID() + "doesnt exists!");
        }
        Room room = result.get();
        List<Bed> bedList = new ArrayList<>();
        for (BedEntity bedEntity : room.getBeds()) {
            bedList.add(Bed.getByCode(bedEntity.getBedType().getCode()));
        }
        List<Reservation> allByRoomId = reservationRepository.findAllByRoomIdOrderByStartDate(input.getRoomID());
        Map<LocalDate, LocalDate> datesOccupied = new LinkedHashMap<>();
        for (Reservation reservation : allByRoomId) {
            LocalDate startDate = reservation.getStartDate();
            LocalDate endDate = reservation.getEndDate();
            datesOccupied.put(startDate, endDate);
        }

        GetRoomByIDOutput output = GetRoomByIDOutput.builder()
                .id(room.getId())
                .price(room.getPrice())
                .floor(room.getFloor())
                .bathRoomType(BathRoomType.getByCode(String.valueOf(room.getBathroomPrototype())))
                .bed(bedList)
                .datesOccupied(datesOccupied)
                .build();
        log.info("End getRoomById output: {}", output);
        return output;
    }


    @Override
    public UnbookRoomOutput unbookRoom(UnbookRoomInput input) {
        log.info("Start deleteRoom input: {}", input);
        reservationRepository.deleteById(input.getBookingRoomId());
        UnbookRoomOutput output = UnbookRoomOutput.builder()
                .message("Deleted successfully")
                .build();
        log.info("End deleteRoom output: {}", output);
        return output;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Start bookRoom input: {}", input);
        User user = User.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .birthday(input.getBirthdate())
                .email(input.getEmail())
                .build();
        userRepository.save(user);
        Optional<Room> result = roomRepository.findRoomById(input.getRoomId());
        if (result.isEmpty()) {
            throw new InvalidRoomByIdExceptions("Room with this ID: " + input.getRoomId() + "doesnt exists!");
        }
        Long daysAtTheHotel = ChronoUnit.DAYS.between(input.getStartDate(), input.getEndDate());
        BigDecimal finalPrice = result.get().getPrice().multiply(BigDecimal.valueOf(daysAtTheHotel));
        Reservation reservation = Reservation.builder()
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .price(finalPrice)
                .room(result.get())
                .user(user)
                .build();
        reservationRepository.save(reservation);
        BookRoomOutput output = BookRoomOutput.builder()
                .message("Successfully booked a room!")
                .build();
        log.info("End bookRoom output: {}", output);
        return output;

    }
}
