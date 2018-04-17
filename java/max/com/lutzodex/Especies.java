package max.com.lutzodex;

import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created on 18/07/2015.
 */
public final class Especies {

    private static final String TAG = "ClasseEspecies";
    private Map<String, Especie> mapEspeciesArquivo;
    private Map<String, Especie> mapEspeciesAtual;
    private Vector<String> listaPerguntas;
    private ArrayList<String> listaRespostas;
    private ArrayList<String> listaEstruturas;

    private Map<String, Map<String, Vector<Especie>>> mapRegiaoVecEspecies;

    private int idPergunta;
    private int incremento;
    private int idUltimaResposta;

    public Especies(Resources resources) {
        Log.i(TAG, "Especies");
        mapEspeciesArquivo = new HashMap<String, Especie>();
        mapRegiaoVecEspecies = new HashMap<String, Map<String, Vector<Especie>>>();

        listaPerguntas = new Vector<String>();
        setListaEstruturas(new ArrayList<String>());

        InputStream fileEspecies = resources.openRawResource(R.raw.especies);
        carregaArquivoEspecies(fileEspecies);

        InputStream fileEstruturas = resources.openRawResource(R.raw.estruturas);
        carregaArquivoEstruturas(fileEstruturas);

        mapEspeciesAtual = new HashMap<String, Especie>(mapEspeciesArquivo);

        idUltimaResposta = mapEspeciesArquivo.size() / 2;
        listaRespostas = new ArrayList<String>();
        for (int i=0;i<listaPerguntas.size();i++)
            listaRespostas.add("");

        idPergunta = -1;
        incremento = 1;
    }

    private void carregaArquivoEspecies(InputStream file) {
        Log.i(TAG, "carregaArquivoEspecies");
        int iNumLinha = 0;
        try {

            InputStreamReader isr = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            String distribuicao="";
            String importanciaMedica="";
            String descritor="";
            String habitat="";

            while ((line = br.readLine()) != null) {

                String[] listStrings = line.split(";");

                iNumLinha++;
                if (iNumLinha == 1) {
                    for (String s : listStrings) {
                        listaPerguntas.add(s);
                    }
                    listaPerguntas.remove(0);

                    distribuicao = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    importanciaMedica = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    descritor = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);
                    habitat = listaPerguntas.elementAt(0);
                    listaPerguntas.remove(0);

                } else {
                    String strNomeEspecie = listStrings[0];
                    String strImagem="";
                    int index = strNomeEspecie.indexOf("[");
                    if (index != -1) {
                        strImagem = strNomeEspecie.substring(index+1, strNomeEspecie.length()-1);
                        strNomeEspecie = strNomeEspecie.substring(0, index);
                    }

                    Vector<String> vecAtributos = new Vector<String>();
                    for (String s : listStrings) {
                        vecAtributos.add(s);
                    }

                    Especie especie = new Especie(strNomeEspecie);
                    especie.setImagemEspecie(strImagem);
                    especie.setDistribuicao(distribuicao+": "+vecAtributos.elementAt(1));
                    especie.setImportanciaMedica(importanciaMedica+": " + vecAtributos.elementAt(2));
                    especie.setDescritor(descritor+": " + vecAtributos.elementAt(3));
                    especie.setHabitat(habitat+": " + vecAtributos.elementAt(4));

                    String[] listRegioes = vecAtributos.elementAt(5).split(",");
                    for (String regiao : listRegioes) {
                        if (regiao.charAt(0) == ' ')
                            regiao = regiao.substring(1);
                        especie.getVecRegioes().add(regiao);
                    }

                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    vecAtributos.remove(0);
                    vecAtributos.remove(0);

                    for (int i = vecAtributos.size(); i <= listaPerguntas.size(); i++) {
                        vecAtributos.add("");
                    }
                    especie.getVecAtributos().addAll(vecAtributos);

                    mapEspeciesArquivo.put(strNomeEspecie, especie);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    private void carregaArquivoEstruturas(InputStream file) {
        Log.i(TAG, "carregaArquivoEstruturas");
        int iNumLinha = 0;
        try {

            InputStreamReader isr = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {

                //String[] listStrings = line.split(";");

                iNumLinha++;
                if (iNumLinha == 1) {
                    continue;
                } else {
                    getListaEstruturas().add(line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }


    public void setResposta(String strResposta)
    {
        Log.i(TAG, "setResposta");
        if (idPergunta == -1) // filtra por regioes
        {
            ArrayList<String> listaApagar = new ArrayList<String>();
            for (Map.Entry<String, Especie> itEspecie : mapEspeciesAtual.entrySet())
            {
                Especie especie = itEspecie.getValue();
                if (!especie.getVecRegioes().contains(strResposta)) {
                    listaApagar.add(itEspecie.getKey());
                }
            }

            for (String s : listaApagar) {
                mapEspeciesAtual.remove(s);
            }
        }
        else
        {
            idUltimaResposta = idPergunta;
            ArrayList<String> listaApagar = new ArrayList<String>();
            for (Map.Entry<String, Especie> itEspecie : mapEspeciesAtual.entrySet()) {
                if (!itEspecie.getValue().getVecAtributos().get(idPergunta).isEmpty() &&
                        !itEspecie.getValue().getVecAtributos().get(idPergunta).equals(strResposta)) {
                    listaApagar.add(itEspecie.getKey());
                }
            }

            for (String s : listaApagar) {
                mapEspeciesAtual.remove(s);
            }
        }
        listaRespostas.set(idPergunta+1, strResposta);
        incrementaPergunta();
    }

    public void incrementaPergunta() {
        Log.i(TAG, "incrementaPergunta");
        idPergunta+= incremento;
        if (idPergunta == listaPerguntas.size()){
            idPergunta=0;
        }
        else if (idPergunta == -1){
            idPergunta=listaPerguntas.size()-1;
        }
    }

    public void setIncremento(int inc)
    {
        incremento=inc;
    }

    public String[] getPerguntas()
    {
        Log.i(TAG, "getPerguntas");
        String[] perguntas = new String[listaPerguntas.size()];

        for(int i=0; i < listaPerguntas.size();i++){
            perguntas[i] = listaPerguntas.elementAt(i);
        }
        return perguntas;
    }

    public String getPergunta()
    {
        return listaPerguntas.elementAt(idPergunta+1);
    }
    public Set<String> getRespostasPerguntaAtual()
    {
        Log.i(TAG, "getRespostasPerguntaAtual() p: " + idPergunta);

        int a=1;
        if(idPergunta == 352) {
            a = 0;
        }
        a = 3;
        Set<String> setRespostas = new LinkedHashSet<String>();
        if (idPergunta == -1)
        {
            for (Map.Entry<String, Especie> itEspecie : mapEspeciesAtual.entrySet()) {
                setRespostas.addAll(itEspecie.getValue().getVecRegioes());
            }
        }else {
            for (Map.Entry<String, Especie> itEspecie : mapEspeciesAtual.entrySet()) {
                if (!itEspecie.getValue().getVecAtributos().get(idPergunta).isEmpty())
                    setRespostas.add(itEspecie.getValue().getVecAtributos().get(idPergunta));
            }
        }
        return setRespostas;
    }

    public ArrayList<String> getRespostas()
    {
        return listaRespostas;
    }


    public List<String> getEspecies()
    {
        try {

            Log.i(TAG, "getEspecies");
            List<String> listEspecies = new ArrayList<>();
            for (Map.Entry<String, Especie> entry : mapEspeciesAtual.entrySet()){
                listEspecies.add(entry.getKey());
            }

            return listEspecies;
        }catch (Exception e){
            int a=0;
        }
        return null;
    }

    public int getNumeroEspecies()
    {
        return mapEspeciesAtual.size();
    }

    public boolean temResultado() {
        Log.i(TAG, "temResultado");
        if ((mapEspeciesAtual.size() == 1) || (idUltimaResposta == idPergunta)){
            return true;
        }
        return false;
    }

    public void recalcular() {
        Log.i(TAG, "recalcular");
        mapEspeciesAtual.clear();
        idPergunta = -1;
        mapEspeciesAtual = new HashMap<String, Especie>(mapEspeciesArquivo);
        for (int idPergunta=0;idPergunta<listaPerguntas.size();idPergunta++) {
            try {
                String strResposta = listaRespostas.get(idPergunta);
                if (!strResposta.isEmpty())
                {
                    setResposta(strResposta);
                }
            }catch (Exception e)
            {
                int a=0;
            }

        }
        this.idUltimaResposta = mapEspeciesArquivo.size() / 2;
    }

    public void apagaRespostas( Set<Integer> apagar) {
        Log.i(TAG, "apagaRespostas");
        for (Integer i : apagar) {
            listaRespostas.set(i, "");
        }
    }

    public ArrayList<String> getListaEstruturas() {
        return listaEstruturas;
    }

    public void setListaEstruturas(ArrayList<String> listaEstruturas) {
        this.listaEstruturas = listaEstruturas;
    }

    public Especie getEspecie (String nome)
    {
        return mapEspeciesAtual.get(nome);
    }
}
