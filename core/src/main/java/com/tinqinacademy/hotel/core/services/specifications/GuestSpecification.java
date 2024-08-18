package com.tinqinacademy.hotel.core.services.specifications;

import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.data.jpa.domain.Specification;

public class GuestSpecification {
    public static boolean isValid(String value){
        return value!=null && !value.isEmpty();
    }
    public static Specification<Guest> guestHasFirstName(String firstName){
        return isValid(firstName)
                ? (guest,cq,cb)->cb.equal(guest.get("firstName"),firstName)
                : ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
    public static Specification<Guest> guestHasLastName(String lastName){
        return isValid(lastName)
                ? (guest,cq,cb)->cb.equal(guest.get("lastName"),lastName)
                : ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
    public static Specification<Guest> guestHasPhoneNumber(String phoneNumber){
        return isValid(phoneNumber)
                ? (guest,cq,cb) ->cb.equal(guest.get("phoneNumber"),phoneNumber)
                : ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
    public static Specification<Guest> guestHasIdCardNumber(String idCardNumber) {
        return isValid(idCardNumber)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardNumber"), idCardNumber)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Guest> guestHasIdCardValidity(String idCardValidity) {
        return isValid(idCardValidity)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardValidity"), idCardValidity)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Guest> guestHasIdCardIssueAuthority(String idCardIssueAuthority) {
        return isValid(idCardIssueAuthority)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardIssueAuthority"), idCardIssueAuthority)
                : (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Guest> guestHasIdCardIssueDate(String idCardIssueDate) {
        return isValid(idCardIssueDate)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardIssueDate"), idCardIssueDate)
                : (root, query, cb) -> cb.conjunction();
    }
    public static Specification<Guest> guestHasBirthdate(String birthdate) {
        return isValid(birthdate)
                ? (guest, cq, cb) -> cb.equal(guest.get("idCardIssueDate"), birthdate)
                : (root, query, cb) -> cb.conjunction();
    }
}
