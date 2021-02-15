import java.util.ArrayList;
import java.util.List;

public class Caminhos {   //maxima o valor dos itens coletados

    private static final List<String> caminhosPossiveis = new ArrayList<String>();   //armazena todos os caminhos possiveis
    private static String caminho = "";



    public String caminhoMaisCurto(EP labirinto, boolean [][]visitado) {

        explora(labirinto, labirinto.getPartida().getX(), labirinto.getPartida().getY(), visitado);

        //se nao existe um caminho
        if(caminhosPossiveis.isEmpty()){
            return "";
        }

        int tamanho = 0;
        String caminhoAntigo = caminhosPossiveis.get(0);
        int tamanhoAntigo = labirinto.calculaTamCaminho(caminhoAntigo);
        String resp = caminhoAntigo;   //guarda o caminho mais curto


        //percorre todos os camihos possiveis
        for(int i = 0; i  < caminhosPossiveis.size(); i++){

            String pegaCaminho = caminhosPossiveis.get(i);
            tamanho = labirinto.calculaTamCaminho(pegaCaminho);
            if(tamanhoAntigo > tamanho){
                resp = pegaCaminho;
                tamanhoAntigo = tamanho;
            }
        }
        //retornamos o caminho mais curto
        return resp;
    }

    public String caminhoMaisLongo(EP labirinto, boolean [][]visitado) {

        explora(labirinto, labirinto.getPartida().getX(), labirinto.getPartida().getY(), visitado);
        String resp = "";   //guarda o caminho mais longo

        //se nao existe um caminho
        if(caminhosPossiveis.isEmpty()){
            return "";
        }

        int tamanho = 0;
        int tamanhoAntigo = 0;

        //percorre todos os camihos possiveis
        for(int i = 0; i  < caminhosPossiveis.size(); i++){

            String pegaCaminho = caminhosPossiveis.get(i);
            tamanho = labirinto.calculaTamCaminho(pegaCaminho);
            if(tamanhoAntigo < tamanho){
                resp = pegaCaminho;
                tamanhoAntigo = tamanho;
            }
        }
        //retornamos o caminho mais longo
        return resp;
    }


    public String caminhoMaisValioso(EP labirinto, boolean [][]visitado) {

        explora(labirinto, labirinto.getPartida().getX(), labirinto.getPartida().getY(), visitado);
        String resp = "";   //guarda o caminho mais valioso

        //se nao existe um caminho
        if(caminhosPossiveis.isEmpty()){
            return "";
        }

        //somamos os valores dos itens coletados de cada caminho e verificamos qual dos caminhos eh o mais valioso
        int soma = 0;
        int somaAntiga = 0;

        //percorre todos os camihos possiveis
        for(int i = 0; i  < caminhosPossiveis.size(); i++){
            soma = 0;

            String pegaCaminho = caminhosPossiveis.get(i);
            String [] separa = pegaCaminho.split("");

            int cont = 0;

            //enquanto nao percorrer todas as posicoes do caminho atual
            while(cont < separa.length){
                if(labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont+1])] != null){
                    soma = soma + labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont+1])].valor;
                }
                cont = cont + 2;
            }

            //se o valor dos itens do caminho atual for maior que o antigo, entao o caminho mais valioso eh o atual
            if(somaAntiga < soma){
                resp = pegaCaminho;
                somaAntiga = soma;
            }
        }
        //retornamos o caminho mais valioso
        return resp;
    }


    public String caminhoMaisRapido(EP labirinto, boolean [][]visitado) {

        explora(labirinto, labirinto.getPartida().getX(), labirinto.getPartida().getY(), visitado);

        String resp = "";   //guarda o caminho mais longo

        //se nao existe um caminho
        if(caminhosPossiveis.isEmpty()){
            return "";
        }

        double tempo = 0;
        String caminhoAntigo = caminhosPossiveis.get(0);

        double tempoAntigo = labirinto.calculaTempo(labirinto, caminhoAntigo);

        //percorre todos os camihos possiveis
        for(int i = 0; i  < caminhosPossiveis.size(); i++){

            String pegaCaminho = caminhosPossiveis.get(i);
            tempo = labirinto.calculaTempo(labirinto, pegaCaminho);
            if(tempoAntigo > tempo){
                resp = pegaCaminho;
                tempoAntigo = tempo;
            }
        }
        //retornamos o caminho mais longo
        return resp;
    }



    //verifica se uma posicao eh valida (pertence ao labirinto, nao foi explorada e nao eh uma posicao bloqueada("X"))
    public boolean ehValido(EP labirinto, int x, int y, boolean visited[][]) {
        if (labirinto.ehPosicaoValida(x, y) && !labirinto.foiExplorado(x, y) && !labirinto.ehParede(x, y)) {
            return true;
        }
        return false;
    }


    //explora o labirinto para achar todos os caminhos possiveis
    public void explora(EP labirinto, int x, int y, boolean visited[][]) {

        // se a posicao eh INVALIDA OU eh uma posicao bloquada ("X") OU a posicao ja foi explorada
        if (!labirinto.ehPosicaoValida(x, y) || labirinto.ehParede(x, y) || labirinto.foiExplorado(x, y)) {
            return;
        }

        //se a posicao for a CHEGADA e a posicao de CHEGADA nao eh igual a PARTIDA, adiciona o caminho atual na Lista de caminhos possiveis
        if (labirinto.ehChegada(x, y)) {
            if(labirinto.getPartida().getX() == labirinto.getChegada().getX() && labirinto.getPartida().getY() == labirinto.getChegada().getY()){
                return;
            }
            caminhosPossiveis.add(caminho);
            return;
        }

        labirinto.setVisitado(x, y, true);    //atribui o status de JA EXPLORADA

        //se eh possivel andar para BAIXO, adiciona posicao no caminho atual
        if (ehValido(labirinto, x + 1, y, visited)) {
            int coorX = x + 1;
            int coorY = y;
            String aux = Integer.toString(coorX) + Integer.toString(coorY);
            caminho += aux;
            explora(labirinto, x + 1, y, visited);
            caminho = caminho.substring(0, caminho.length() - 2);
        }

        //se eh possivel andar para CIMA, adiciona posicao no caminho atual
        if (ehValido(labirinto, x - 1, y, visited)) {
            int coorX = x - 1;
            int coorY = y;
            String aux = Integer.toString(coorX) + Integer.toString(coorY);
            caminho += aux;
            explora(labirinto, x - 1, y, visited);
            caminho = caminho.substring(0, caminho.length() - 2);
        }

        //se eh possivel andar para ESQUERDA, adiciona posicao no caminho atual
        if (ehValido(labirinto, x, y - 1, visited)) {
            int coorX = x;
            int coorY = y - 1;
            String aux = Integer.toString(coorX) + Integer.toString(coorY);
            caminho += aux;
            explora(labirinto, x, y - 1, visited);
            caminho = caminho.substring(0, caminho.length() - 2);
        }

        //se eh possivel andar para DIREITA, adiciona posicao no caminho atual
        if (ehValido(labirinto, x, y + 1, visited)) {
            int coorX = x;
            int coorY = y + 1;
            String aux = Integer.toString(coorX) + Integer.toString(coorY);
            caminho += aux;
            explora(labirinto, x, y + 1, visited);
            caminho = caminho.substring(0, caminho.length() - 2);
        }

        labirinto.setVisitado(x, y, false);   //atribui o status de NAO EXPLORADO para percorrer
        //outros possiveis caminhos
    }
}