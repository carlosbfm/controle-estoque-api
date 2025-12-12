package lancamento_de_produtos.utils;

import java.util.Random;

public class GeradorCodigo {

    public static String gerarCodigoProduto(int tamanho) {
        String caracteres = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }
        return codigo.toString();
    }
}
