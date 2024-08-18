package com.tinqinacademy.hotel.core.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

public class SpecificationUtils {
    public static <T> Specification<T> combineSpecifications(List<Specification<T>> specifications){

        List<Specification<T>> specificationList = specifications.stream()
                .filter(Objects::nonNull)
                .toList();

        Specification<T> first = specificationList.getFirst();
        for (int i = 1; i <specificationList.size() ; i++) {
            first=first.and(specificationList.get(i));
        }
        return first;
    }
}
