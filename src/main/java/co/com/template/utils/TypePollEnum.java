package co.com.template.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypePollEnum {
    FOLLOW(1L,"Seguimiento Continuo","SC"),
    CLOSE(2L, "Cierre Periodo","CP");

    private Long id;
    private String describe;
    private String start;

}
