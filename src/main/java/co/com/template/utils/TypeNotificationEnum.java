package co.com.template.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum TypeNotificationEnum {

    AUTOMATIC_POLLS(1L);

    private Long id;
}
