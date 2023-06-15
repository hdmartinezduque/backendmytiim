package co.com.template.utils;


import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class Constants {

    public static final String EMPTY_MESSAGE="";
    public static final String ERROR_HANDLER_MESSAGE="Error General";
    public static final String LOGIN_ERROR="Usuario o Clave no son correctos";
    public static final String UPDATE_PASSWORD_ERROR="Ocurrió error al actualizar la clave";
    public static final String PASSWORD_REQUIRED="El password es requerido";
    public static final String USER_NAME_REQUIRED="El usuario es requerido";
    public static final String USER_ID_REQUIRED="El id del usuario es requerido";
    public static final String DESCRIPTION_REQUIRED="la descripción es requerida";
    public static final String NAME_REQUIRED= "El nombre es requerido";
    public static final String LAST_NAME_REQUIRED= "El apellido es requerido";
    public static final String PHONE_REQUIRED= "El teléfono es requerido";
    public static final String EMAIL_REQUIRED= "El Email es requerido";
    public static final String OBJECT_NOT_EXISTS_ERROR= "El objeto no existe";
    public static final String STATUS_NOT_EXISTS_ERROR= "El estado no existe";
    public static final String INVALID_RATING= "Calificación Invalida";
    public static final String USER_NOT_EXISTS_ERROR= "El usuario no existe";
    public static final Long FOLLOW_POLL_ID = 1L;
    public static final Long MAX_VALUE_QUALIFY = 5L;
    public static final Long MIN_VALUE_QUALIFY = 0L;
    public static final String DATE_REQUIRED= "Fecha requerida";
    public static final String NON_NEGATIVE_NUMBER_REQUIRED = "Este campo no permite números negativos";

    public static final String DATETIME_FORMAT = "dd/MM/yyyy hh:mm a";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final Long DEFAULT_COMMENT_TYPE_ID = 3L;
    public static final String EMAIL_NAME = "name";
    public static final String EMAIL_DATE = "commentDate";
    public static final String EMAIL_DESCRIBE = "commentContent";
    public static final String INDEX_TEMPLATE = "sendComment";
    public static final String SUBJECT_MESSAGE = "MYTIIM - Mensaje nuevo de:";

    public static final String OBJECIVE_TYPE_ID_REQUIRED="La categoría es requerida";
    public static final String OBJECTIVE_DESCRIBE_REQUIRED="La redacción del objetivo es requerido";
    public static final String SPACE_CHARACTER=" ";

    public static final String USER_NOT_ACTIVE_PENDING_ERROR= "El estado del usuario debe ser: activacion pendiente";

    public static final String QUESTION_EXIST_ON_POLL="La pregunta ya existe en la encuesta";

    public static final String COMMITMENT_REQUIRED = "Se requiere un compromiso";
    public static final Integer ZERO_INDEX =0;
    public static final Integer TEN_INDEX =10;
    public static final Long DEFAULT_RECOGNITION_STATUS_ID = 2L;
    public static final Long USER_TYPE = 1L;
    public static final Long GROUP_TYPE = 2L;

    public static final String COMMA_SEPARATOR = ",";
    public static final String POLL_NOT_EXISTS_ERROR = "La encuesta no existe";



}
