package com.example.homestay.service.homestay.lockdate;

import java.time.LocalDate;
import java.util.List;

public interface LockDateService {

    void addLockDates(Integer homestayId, List<LocalDate> date);

    void removeLockDates(Integer homestayId, List<LocalDate> date);

    List<LocalDate> getLockDates(Integer homestayId);
}

