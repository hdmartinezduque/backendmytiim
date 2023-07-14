package co.com.template.utils;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Constants {

    public static final String EMPTY_MESSAGE="";
    public static final String ERROR_HANDLER_MESSAGE="Error General";
    public static final String LOGIN_ERROR="Usuario o Clave no son correctos";
    public static final String PASSWORD_REQUIRED="El password es requerido";
    public static final String USER_NAME_REQUIRED="El usuario es requerido";
    public static final String USER_ID_REQUIRED="El id del usuario es requerido";
    public static final String DESCRIPTION_REQUIRED="la descripción es requerida";
    public static final String OBJECT_NOT_EXISTS_ERROR= "El objeto no existe";
    public static final String STATUS_NOT_EXISTS_ERROR= "El estado no existe";
    public static final String INVALID_RATING= "Calificación Invalida";
    public static final String USER_NOT_EXISTS_ERROR= "El usuario no existe";
    public static final Long MAX_VALUE_QUALIFY = 5L;
    public static final Long MIN_VALUE_QUALIFY = 0L;
    public static final String DATETIME_FORMAT = "dd/MM/yyyy hh:mm a";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final Long DEFAULT_COMMENT_TYPE_ID = 3L;
    public static final String EMAIL_NAME = "name";
    public static final String EMAIL_LASTNAME = "lastname";
    public static final String EMAIL_DATE = "commentDate";
    public static final String EMAIL_DESCRIBE = "commentContent";
    public static final String INDEX_TEMPLATE = "sendComment";
    public static final String FEEDBACK_TEMPLATE = "sendCommentFeedback";
    public static final String SUBJECT_MESSAGE = "MYTIIM - Mensaje nuevo de: ";
    public static final String OBJECTIVE_TYPE_ID_REQUIRED="La categoría es requerida";
    public static final String OBJECTIVE_DESCRIBE_REQUIRED="La redacción del objetivo es requerido";
    public static final String USER_NOT_ACTIVE_PENDING_ERROR= "El estado del usuario debe ser: activacion pendiente";
    public static final String QUESTION_EXIST_ON_POLL="La pregunta ya existe en la encuesta";
    public static final String COMMITMENT_REQUIRED = "Se requiere un compromiso";
    public static final Integer ZERO_INDEX = 0;
    public static final Integer TEN_INDEX =10;
    public static final Integer ONE_HUNDRED_LIMIT =100;
    public static final Long DEFAULT_RECOGNITION_STATUS_ID = 2L;
    public static final Long USER_TYPE = 1L;
    public static final Long GROUP_TYPE = 2L;
    public static final String COMMA_SEPARATOR = ",";
    public static final String POLL_NOT_EXISTS_ERROR = "La encuesta no existe";
    public static final String AUTHORIZATION_HEADER="authorization";
    public static final String USER_ID_TOKEN ="userId";
    public static final String ROLES_TOKEN ="roles";
    public static final String USER_NAME_TOKEN ="name";
    public static final Integer INCREMENTAL_VALUE = 1;
    public static final String DASH_VALUE = "-";
    public static final String URL_POLL = "/home/pendings";
    public static final String SUBJECT_NOTIFICATION_EMAIL = "(%s) Encuesta de %s";
    public static final String NOTIFICATION_TEMPLATE = "sendNotificationPoll";
    public static final String LOG_SEND_EMAIL ="Enviando correo a %s";
    public static final String LOG_SEND_EMAIL_ERROR ="Error al enviar correo a ";
    public static final String LOG_START ="Iniciando proceso notificaciones automaticas";
    public static final String LOG_SAVE_NOTIFICATION_ERROR ="Ocurrio un error al asociar la encuesta %s a un usuario; ";
    public static final String LOG_SAVE_POLL_QUESTIONS_ERROR ="Ocurrio un error al crear las preguntas para una nueva encuesta: ";
    public static final String LOG_END ="Finalizando proceso notificaciones automaticas";
    public static final String EMAIL_URL = "url";
    public static final String EMAIL_FEEDBACK_URL = "/home/recognition";
    public static final String EMAIL_IMAGE_URL = "src";
    public static final Integer ONE_HUNDRED_INDEX = 100;
    public static final Integer ONE_VALUE = 1;
    public static final String LOG_SAVE_POLL_QUESTIONS ="Se creó la encuesta con sus respectivas preguntas: ";
    public static final String LOG_SAVE_NOTIFICATION ="Se genera encuesta %s para los usuarios";
    public static final String RESOLVED = "Resuelto";
    public static final String UNRESOLVED = "No resuelto";
    public static final String PERIOD_REQUIRED= "El periodo es requerido";
    public static final String FILE_SEPARATOR= "|";
    public static final Integer ACTIVE_USER = 6;

    public static final String PROCESS_ERROR ="Ocurrio un error, favor de intentar mas tarde.";
    public static final String NO_CONFIGURATION_POLL_QUESTIONS ="No hay preguntas configuradas en la encuesta de ";
    public static final Long SYSTEM_INCREMENTAL_DAYS = 1L;
    public static final Long URL_SYSTEM = 2L;
    public static final Long IMAGE_URL_SYSTEM = 3L;
    public static final String AT_VALUE = "@";
    public static final Long ZERO_VALUE = 0L;
    public static final String SPACE_SEPARATOR = " ";

    public static final String SPACE_CHARACTER = " ";
    public static final String OBJECTIVE_HEADERS_CSV ="Usuario|Nombre completo|Email|Periodo|Equipo|Nombre del lider|Objetivos creados|Ultimo ingreso";
    public static final String COMMITMENT_HEADERS_CSV ="Usuario|Nombre completo|Email|Periodo|Equipo|Nombre del líder|" +
            "Objetivo|Estado del objetivo|Tipo de objetivo|Descripción del compromiso|Estado del compromiso|" +
            "Último ingreso";

    public static final String CONTINUE_POLL_HEADERS_CSV ="Usuario|Nombre completo|Email|Periodo|Código de encuesta|Equipo|Nombre del líder|" +
            "Respuesta de encuesta|Pregunta de encuesta";
    public static final String CLOSE_POLL_HEADERS_CSV ="Usuario|Nombre completo|Email|Periodo|Equipo|Nombre del líder|" +
            "Pregunta cierre de encuesta|Respuesta cierre de encuesta";

}
