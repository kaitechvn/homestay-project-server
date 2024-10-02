package com.example.homestay.repository.specification;

import com.example.homestay.model.Homestay;
import com.example.homestay.model.LockDate; // Import your LockDate model
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class HomestaySpecification {

    public static Specification<Homestay> byFilters(Integer districtId, Integer minPrice,
                                                    Integer maxPrice, Integer guests,
                                                    LocalDate checkIn, LocalDate checkOut) {
        return (root, query, criteriaBuilder) -> {

            // Check list
            List<Predicate> predicates = new ArrayList<>();

            // Helper method to add predicates only when value is not null
            addIfNotNull(predicates, districtId, () -> criteriaBuilder.equal(root.get("district").get("id"), districtId));
            addIfNotNull(predicates, minPrice, () -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            addIfNotNull(predicates, maxPrice, () -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            addIfNotNull(predicates, guests, () -> criteriaBuilder.greaterThanOrEqualTo(root.get("guests"), guests));
            // Check all parameter root (abc) with second parameter


            // Filter by Check-in and Check-out Dates
            if (checkIn != null && checkOut != null) {
                // Subquery to check for overlapping lock dates
                Subquery<Integer> lockDateSubquery = Objects.requireNonNull(query).subquery(Integer.class);
                Root<LockDate> lockDateRoot = lockDateSubquery.from(LockDate.class);

                // Create predicates for the lock date check
                Predicate lockDatePredicate = criteriaBuilder.and(
                        criteriaBuilder.equal(lockDateRoot.get("homestayId"), root.get("id")),
                        criteriaBuilder.and(
                                criteriaBuilder.greaterThanOrEqualTo(lockDateRoot.get("lockDate"), checkIn),
                                criteriaBuilder.lessThanOrEqualTo(lockDateRoot.get("lockDate"), checkOut)
                        )
                );

                lockDateSubquery.select(lockDateRoot.get("id")).where(lockDatePredicate);

                // Exclude homestays that have lock dates within the check-in/check-out range
                predicates.add(criteriaBuilder.not(criteriaBuilder.exists(lockDateSubquery)));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Helper method to add predicates only if the value is not null
    private static <T> void addIfNotNull(List<Predicate> predicates, T value, Supplier<Predicate> predicateSupplier) {
        if (value != null) {
            predicates.add(predicateSupplier.get());
        }
    }
}
