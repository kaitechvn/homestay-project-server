package com.example.homestay.service.homestay.lockdate;

import com.example.homestay.model.LockDate;
import com.example.homestay.repository.LockDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LockDateServiceImpl implements LockDateService {

    private final LockDateRepository lockDateRepository;

    @Override
    public void addLockDates(Integer homestayId, List<LocalDate> dates) {
        for (LocalDate date : dates) {
            Optional<LockDate> existingLockDate = lockDateRepository.findByHomestayIdAndLockDate(homestayId, date);

            if (existingLockDate.isEmpty()) {
                LockDate lockDate = new LockDate();
                lockDate.setHomestayId(homestayId);
                lockDate.setLockDate(date);
                lockDateRepository.save(lockDate);
            } else {
                throw new IllegalStateException("Lock date already exists for this homestay on the date: " + date);
            }
        }
    }

    @Override
    public void removeLockDates(Integer homestayId, List<LocalDate> dates) {
        // Iterate through the list of dates
        for (LocalDate date : dates) {
            Optional<LockDate> lockDate = lockDateRepository.findByHomestayIdAndLockDate(homestayId, date);

            // If lockDate is present, delete it
            if (lockDate.isPresent()) {
                lockDateRepository.delete(lockDate.get());
            } else {
                // Optionally, you can log or collect missing dates
                System.out.println("Lock date not found for homestay ID " + homestayId + " and date " + date);
            }
        }
    }

    @Override
    public List<LocalDate> getLockDates(Integer homestayId) {
        List<LockDate> lockDates = lockDateRepository.findAllByHomestayId(homestayId);
        return lockDates.stream()
                .map(LockDate::getLockDate)
                .collect(Collectors.toList());
    }
}
