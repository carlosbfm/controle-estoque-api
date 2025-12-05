package lancamento_de_produtos.exception;

public class RegistrationDuplicateException extends RuntimeException{
    public RegistrationDuplicateException(String registration) {
        super("A matrícula '" + registration + "' já está cadastrada no sistema.");
    }
}
