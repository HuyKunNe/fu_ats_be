package com.fu.fuatsbe.serviceImp;

import com.fu.fuatsbe.entity.EmailSchedule;
import com.fu.fuatsbe.repository.EmailScheduleRepository;
import com.fu.fuatsbe.service.EmailScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailScheduleServiceImp implements EmailScheduleService {
    private final EmailScheduleRepository emailScheduleRepository;
    @Override
    public EmailSchedule getFirstMailSchedule() {
        EmailSchedule emailSchedule = emailScheduleRepository.getFirstEmailSchedule();
        return emailSchedule;
    }
}
