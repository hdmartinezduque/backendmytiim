package co.com.template.services;

import co.com.template.Repositories.NotificationLogRepository;
import co.com.template.Repositories.entities.NotificationLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotificationLogService {

    private final NotificationLogRepository repository;

    public void saveLog(String message,Long typeId){
        repository.save(new NotificationLog(null,message,typeId));
    }

}
