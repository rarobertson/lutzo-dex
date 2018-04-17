package max.com.lutzodex;

import java.util.Vector;

/**
 * Created on 28/12/2017.
 */

public class Especie {

    private String nomeEspecie;
    private Vector<String> vecAtributos;
    private String distribuicao;
    private String importanciaMedica;
    private String descritor;
    private String habitat;
    private String imagemEspecie;
    private String tamanho;
    private Vector<String> vecRegioes;

    public Especie(String nomeEspecie) {
        this.nomeEspecie = nomeEspecie;
        vecAtributos = new Vector<String>();
        vecRegioes = new Vector<String>();
    }
    public String getNomeEspecie() {
        return nomeEspecie;
    }

    public void setNomeEspecie(String nomeEspecie) {
        this.nomeEspecie = nomeEspecie;
    }

    public Vector<String> getVecAtributos() {
        return vecAtributos;
    }

    public void setVecAtributos(Vector<String> vecAtributos) {
        this.vecAtributos = vecAtributos;
    }

    public String getDistribuicao() {
        return distribuicao;
    }

    public void setDistribuicao(String distribuicao) {
        this.distribuicao = distribuicao;
    }

    public String getImportanciaMedica() {
        return importanciaMedica;
    }

    public void setImportanciaMedica(String importanciaMedica) {
        this.importanciaMedica = importanciaMedica;
    }

    public String getDescritor() {
        return descritor;
    }

    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getImagemEspecie() {
        return imagemEspecie;
    }

    public void setImagemEspecie(String imagemEspecie) {
        this.imagemEspecie = imagemEspecie;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Vector<String> getVecRegioes() {
        return vecRegioes;
    }

    public void setVecRegioes(Vector<String> vecRegioes) {
        this.vecRegioes = vecRegioes;
    }
}
